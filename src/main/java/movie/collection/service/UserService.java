package movie.collection.service;

import movie.collection.exception.UserNotFoundException;
import movie.collection.model.Role;
import movie.collection.model.User;
import movie.collection.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


     User findUserByUsername(String username) {
         return userRepository.findUserByUsername(username)
                 .orElseThrow(() -> new UserNotFoundException("User with username: " + username + "does not exist"));
     }
     @PostConstruct
    public void initAdmin(){
         User user = User.builder().email("a@a").username("admin").role(Role.ADMIN).password(passwordEncoder.encode("admin123")).isActive(true).build();
     userRepository.save(user);
    }
    User saveUser(User user){
        return userRepository.save(user);
    }
}

