package movie.collection.dto;

import lombok.Builder;
import java.util.List;
@Builder
public class MovieDto {
    private Long id;
    private String icon;
    private String title;
    private Double rating=0.0;
    private int watchedTimes = 0;
    private int releaseYear;
    private int duration;
    private String description;
    private String category;

    private List<CommentDto> comments;

}
