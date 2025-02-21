package movie.collection.service;


import movie.collection.dto.CreateUserDto;
import movie.collection.dto.UserDto;
import movie.collection.mapper.UserMapper;
import movie.collection.model.Role;
import movie.collection.model.User;
import movie.collection.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto createUser(CreateUserDto createUserDto){
        User user = User.builder()
                .username(createUserDto.username())
                .password(createUserDto.password())
                .email(createUserDto.email())
                .role(Role.USER)
                .build();
        User savedUser = userRepository.save(user);
        return userMapper.enitityToDto(savedUser);
    }

}
