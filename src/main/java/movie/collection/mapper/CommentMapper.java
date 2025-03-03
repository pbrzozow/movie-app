package movie.collection.mapper;

import movie.collection.dto.CommentDto;
import movie.collection.dto.UserDto;
import movie.collection.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    private final UserMapper userMapper;

    public CommentMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public CommentDto entityToDto(Comment comment){
        UserDto userDto= userMapper.entityToDto(comment.getUser());
        return new CommentDto(comment.getId(), comment.getDate(),userDto, comment.getText());
    }
}
