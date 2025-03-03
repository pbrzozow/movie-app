package movie.collection.scheduler;

import movie.collection.mapper.MovieMapper;
import movie.collection.service.MovieService;
import movie.collection.service.external.MovieFetcher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class MovieScheduler {
    private final MovieFetcher movieFetcher;

    public MovieScheduler(MovieFetcher movieFetcher) {
        this.movieFetcher = movieFetcher;
    }

    @Scheduled(fixedRate = 300000)
    public void fetchAndSaveMovies(){
        System.out.println("fetching movies");

        CompletableFuture<Void> fetchMovies = movieFetcher.fetchMovies();
        fetchMovies
                .thenRun(() -> System.out.println("Movies fetched and saved"))
                .exceptionally(ex -> {
                    System.err.println("Error fetching movies: " + ex.getMessage());
                    return null;
                });
//        movieService.initialize();
        System.out.println("movies fetched");
}
}
