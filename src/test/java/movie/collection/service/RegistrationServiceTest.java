package movie.collection.service;

import movie.collection.dto.CreateUserDto;
import movie.collection.exception.TokenNotFoundException;
import movie.collection.exception.UsernameAlreadyExistsException;
import movie.collection.model.Token;
import movie.collection.model.TokenType;
import movie.collection.model.User;
import movie.collection.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RegistrationServiceTest {
    private RegistrationService registrationService;
    private UserRepository userRepository;
    private TokenService  tokenService;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        tokenService = mock(TokenService.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        EmailService emailService = mock(EmailService.class);
        this.registrationService = new RegistrationService(userRepository,passwordEncoder,tokenService);
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists(){
        CreateUserDto createUserDto = new CreateUserDto("user","u@u","user");
        User user = User.builder().username("user").email("user@user").password("test").build();

        when(userRepository.findUserByUsername(createUserDto.getUsername())).thenReturn(Optional.ofNullable(user));

        assertThrows(UsernameAlreadyExistsException.class,()->registrationService.registerUser(createUserDto));
        verify(tokenService, times(0)).createConfirmationToken(any(User.class));
    }

    @Test
    void shouldNotConfirmIfTokenDoesNotExist(){

        when(tokenService.getToken(anyString(),eq(TokenType.CONFIRMATION))).thenThrow(TokenNotFoundException.class);

        assertThrows(TokenNotFoundException.class,()->registrationService.confirmToken("adaa"));
        verify(userRepository, times(0)).save(any());
        verify(tokenService, times(0)).deleteToken(any());
    }

    @Test
    void shouldCreateAUserSuccessfully() throws MessagingException {
        CreateUserDto createUserDto = new CreateUserDto("user","u@u","user");
        User user = User.builder().build();
        Token confirmationToken = new Token(1L, "random",TokenType.CONFIRMATION ,LocalDateTime.now() ,user );

        when(userRepository.findUserByUsername(createUserDto.getUsername())).thenReturn(Optional.empty());
        when(registrationService.createUser(createUserDto)).thenReturn(user);
        when(tokenService.createConfirmationToken(user)).thenReturn(confirmationToken);

        String token = registrationService.registerUser(createUserDto);

        verify(tokenService, times(1)).createConfirmationToken(any(User.class));
        assertEquals(token,confirmationToken.getToken());
    }
}
