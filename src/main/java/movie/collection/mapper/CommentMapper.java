package movie.collection.mapper;

import movie.collection.dto.CommentDto;
import movie.collection.dto.CommentSummary;
import movie.collection.dto.UserDto;
import movie.collection.model.Comment;
import org.springframework.stereotype.Component;

import java.sql.Date;

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
    public CommentSummary entityToSummary(Comment comment){
        String username = comment.getUser().getUsername();
        Long commentId = comment.getId();
        String movieTitle = comment.getMovie().getTitle();
        String text = comment.getText();
        Date date = comment.getDate();
        return new CommentSummary(commentId,date,movieTitle,username, text);
    }
}
