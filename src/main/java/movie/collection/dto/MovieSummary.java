package movie.collection.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieSummary {
    private Long id;
    private String externalId;
    private String icon;
    private String title;
    private Double rating=0.0;
    private Integer watchedTimes=0;
    private Integer releaseYear=0;
    private Integer duration=0;
    private String description;
    private String category;
}
