package movie.collection.service;

import movie.collection.dto.UserDto;
import movie.collection.exception.MovieNotFoundException;
import movie.collection.exception.UserNotFoundException;
import movie.collection.mapper.UserMapper;
import movie.collection.model.Movie;
import movie.collection.model.Rating;
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
     User findUserWithHistoryByUsername(String username) throws UserNotFoundException{
            User user = userRepository.findUserWithComments(username);
            if (user == null) {
                throw new UserNotFoundException("User with username: " + username + " does not exist");
            }

            User userWithRatings = userRepository.findUserWithRatings(username);
            if (userWithRatings != null) {
                user.setRatings(userWithRatings.getRatings());
            }

            User userWithStatuses = userRepository.findUserWithWatchedMovies(username);
            if (userWithStatuses != null) {
                user.setWatchedMovies(userWithStatuses.getWatchedMovies());

            }
            return user;
        }

    User saveUser(User user){
        return userRepository.save(user);
    }
}

