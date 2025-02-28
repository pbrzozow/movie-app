package movie.collection.service;

import movie.collection.exception.UserNotFoundException;
import movie.collection.model.User;
import movie.collection.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


     User findUserByUsername(String username) {
         return userRepository.findUserByUsername(username)
                 .orElseThrow(() -> new UserNotFoundException("User with username: " + username + "does not exist"));
     }
}

