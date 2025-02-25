package movie.collection.service;

import movie.collection.dto.CreateUserDto;
import movie.collection.exception.TokenNotFoundException;
import movie.collection.exception.UsernameAlreadyExistsException;
import movie.collection.model.ConfirmationToken;
import movie.collection.model.User;
import movie.collection.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RegistrationServiceTest {
    private RegistrationService registrationService;
    private UserRepository userRepository;
    private ConfirmationTokenService  tokenService;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        tokenService = mock(ConfirmationTokenService.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        this.registrationService = new RegistrationService(userRepository,passwordEncoder,tokenService);
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists(){
        CreateUserDto createUserDto = new CreateUserDto("user","u@u","user");
        User user = User.builder().username("user").email("user@user").password("test").build();

        when(userRepository.findUserByUsername(createUserDto.getUsername())).thenReturn(Optional.ofNullable(user));

        assertThrows(UsernameAlreadyExistsException.class,()->registrationService.registerUser(createUserDto));
        verify(tokenService, times(0)).createToken(any(User.class));
    }

    @Test
    void shouldNotConfirmIfTokenDoesNotExist(){

        when(tokenService.getToken(anyString())).thenThrow(TokenNotFoundException.class);

        verify(userRepository, times(0)).save(any());
        verify(tokenService, times(0)).deleteToken(any());
    }

    @Test
    void shouldCreateAUserSuccessfully(){
        CreateUserDto createUserDto = new CreateUserDto("user","u@u","user");
        User user = User.builder().build();
        ConfirmationToken confirmationToken = new ConfirmationToken(1L, "random", user);

        when(userRepository.findUserByUsername(createUserDto.getUsername())).thenReturn(Optional.empty());
        when(registrationService.createUser(createUserDto)).thenReturn(user);
        when(tokenService.createToken(user)).thenReturn(confirmationToken);

        String token = registrationService.registerUser(createUserDto);

        verify(tokenService, times(1)).createToken(any(User.class));
        assertEquals(token,confirmationToken.getToken());
    }
}
