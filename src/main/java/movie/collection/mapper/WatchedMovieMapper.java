package movie.collection.mapper;

import movie.collection.dto.WatchedMovieSummary;
import movie.collection.model.WatchedMovie;
import org.springframework.stereotype.Component;

@Component
public class WatchedMovieMapper {

    public WatchedMovieSummary entityToSummary(WatchedMovie watchedMovie){
        String movieTitle = watchedMovie.getMovie().getTitle();
        Long id = watchedMovie.getId();
        String username = watchedMovie.getUser().getUsername();
        return new WatchedMovieSummary(id,movieTitle,username);
    }
}
