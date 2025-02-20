package movie.collection.dto;

import java.util.Date;

public record CommentDto(Date date,UserDto userDto,String text) {
}
