package movie.collection.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import movie.collection.model.Comment;
import movie.collection.model.WatchedMovie;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {
    private Long id;
    private List<CommentSummary> comments;
    private List<RatingSummary> ratings;
    private List<WatchedMovieSummary> watchedMovies;
}
