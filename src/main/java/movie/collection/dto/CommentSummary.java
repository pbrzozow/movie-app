package movie.collection.dto;


import java.util.Date;

public record CommentSummary(Long id, Date date, String movieTitle,String username, String text) {
}
