package movie.collection.dto;

import java.util.Date;

public record CommentDto(Long id, Date date,UserDto userDto,String text) {
}
