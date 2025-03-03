package movie.collection.scheduler;

import movie.collection.mapper.MovieMapper;
import movie.collection.service.MovieService;
import movie.collection.service.external.MovieFetcher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MovieScheduler {
    private final MovieFetcher movieFetcher;
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    public MovieScheduler(MovieFetcher movieFetcher, MovieService movieService, MovieMapper movieMapper) {
        this.movieFetcher = movieFetcher;
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @Scheduled(fixedRate = 300000)
    public void fetchAndSaveMovies(){
        System.out.println("fetching movies");
        movieFetcher.fetchMovies()
                .thenAccept(movies ->
                    movies.stream().map(movieMapper::externalDtoToEntity).forEach(movieService::saveMovieOrUpdateExisting))
                .exceptionally(ex -> {
                    System.err.println("Error fetching movies: " + ex.getMessage());
                    return null;});
//        movieService.initialize();
        System.out.println("movies fetched");
}
}
