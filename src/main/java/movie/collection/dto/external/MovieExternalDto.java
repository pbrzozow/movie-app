package movie.collection.dto.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class MovieExternalDto {
    private  String id;
    private  String url;
    private  String primaryTitle;
    private  String originalTitle;
    private  String type;
    private  String description;
    private  String primaryImage;
    private  List<String> genres;
    private  Boolean isAdult;
    private  Integer startYear;
    private  Integer endYear;
    private  Integer runtimeMinutes;
    private  Double averageRating;
    private  Integer numVotes;
}
