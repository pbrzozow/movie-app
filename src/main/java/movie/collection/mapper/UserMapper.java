package movie.collection.mapper;

import movie.collection.dto.UserDto;
import movie.collection.exception.UserNotFoundException;
import movie.collection.model.User;
import movie.collection.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final UserRepository userRepository;

    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto entityToDto(User user){
        return new UserDto(user.getId(),user.getUsername());
    }
    public User dtoToEntity(UserDto userDto){
        return userRepository
                .findUserById(userDto.id())
                .orElseThrow(()-> new UserNotFoundException("User does not exist"));
    }
}

