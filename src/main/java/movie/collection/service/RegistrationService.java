package movie.collection.service;

import movie.collection.dto.CreateUserDto;
import movie.collection.exception.TokenNotFoundException;
import movie.collection.exception.UsernameAlreadyExistsException;
import movie.collection.mapper.UserMapper;
import movie.collection.model.ConfirmationToken;
import movie.collection.model.Role;
import movie.collection.model.User;
import movie.collection.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService tokenService;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder, ConfirmationTokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    private User createUser(CreateUserDto createUserDto){
        User user = User.builder()
                .username(createUserDto.getUsername())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .email(createUserDto.getEmail())
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }
    @Transactional
    public String registerUser(CreateUserDto createUserDto)throws UsernameAlreadyExistsException{
        validateUsername(createUserDto.getUsername());
        User user = createUser(createUserDto);
        ConfirmationToken token = tokenService.generate(user);
        var confirmationToken = tokenService.saveToken(token);
        return confirmationToken.getToken();
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
