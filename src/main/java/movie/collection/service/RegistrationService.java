package movie.collection.service;

import movie.collection.dto.CreateUserDto;
import movie.collection.exception.TokenNotFoundException;
import movie.collection.exception.UsernameAlreadyExistsException;
import movie.collection.model.ConfirmationToken;
import movie.collection.model.Role;
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
    private final ConfirmationTokenService tokenService;
    private final EmailService emailService;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder, ConfirmationTokenService tokenService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.emailService = emailService;
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
        ConfirmationToken confirmationToken = tokenService.createToken(user);
        return confirmationToken.getToken();
    }

    private void sendConfirmationEmail(String email, String token) throws MessagingException {
        String subject = "Account activation link!";
        String confirmationLink = getConfirmationLink(token);
        emailService.sendEmailWithLink(email, subject, confirmationLink);
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
    public void confirmToken(String token) throws TokenNotFoundException {
        ConfirmationToken confirmationToken = tokenService.getToken(token);
        User user = confirmationToken.getUser();
        user.setActive(true);
        userRepository.save(user);
        tokenService.deleteToken(confirmationToken);
    }
}
