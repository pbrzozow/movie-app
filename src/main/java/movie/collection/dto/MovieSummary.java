package movie.collection.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MovieSummary {
    private Long id;
    private String icon;
    private String title;
    private Double rating;
    private int watchedTimes;
    private int releaseYear;
    private int duration;
    private String description;
    private String category;
}
