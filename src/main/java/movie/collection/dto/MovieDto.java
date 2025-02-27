package movie.collection.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class MovieDto {
    private Long id;
    private String icon;
    private String title;
    private Double rating=0.0;
    private Integer watchedTimes = 0;
    private Integer releaseYear;
    private Integer duration;
    private String description;
    private String category;

    private List<CommentDto> comments;

}
