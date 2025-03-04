package movie.collection.service;

import movie.collection.dto.CreateUserDto;
import movie.collection.exception.TokenNotFoundException;
import movie.collection.exception.UsernameAlreadyExistsException;
import movie.collection.model.Token;
import movie.collection.model.Role;
import movie.collection.model.TokenType;
import movie.collection.model.User;
import movie.collection.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
//    private final EmailService emailService;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
//        this.emailService = emailService;
    }

    public User createUser(CreateUserDto createUserDto){
        User user = User.builder()
                .username(createUserDto.getUsername())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .email(createUserDto.getEmail())
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }
    @Transactional
    public String registerUser(CreateUserDto createUserDto) throws UsernameAlreadyExistsException, MessagingException {
        validateUsername(createUserDto.getUsername());

        User user = createUser(createUserDto);
        String token = generateConfirmationToken(user);

        sendConfirmationEmail(user.getEmail(), token);
        return token;
    }

    private String generateConfirmationToken(User user) {
        Token token = tokenService.createConfirmationToken(user);
        return token.getToken();
    }

    private void sendConfirmationEmail(String email, String token) throws MessagingException {
        String subject = "Account activation link!";
        String confirmationLink = getConfirmationLink(token);
        System.out.println(confirmationLink);
//        emailService.sendEmailWithLink(email, subject, confirmationLink);
    }


    private String getConfirmationLink(String token){
        return "http://localhost:8080/confirm?token="+token;
    }



    private void validateUsername(String username) throws UsernameAlreadyExistsException {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()){
            throw new UsernameAlreadyExistsException("Username is already occupied! ");
        }
    }

    @Transactional
    public void confirmToken(String token, TokenType tokenType) throws TokenNotFoundException {
        Token confirmationToken = tokenService.getToken(token,tokenType);
        User user = confirmationToken.getUser();
        user.setActive(true);
        userRepository.save(user);
        tokenService.deleteToken(confirmationToken);
    }
}
