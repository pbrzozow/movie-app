package movie.collection.service;

import movie.collection.model.Movie;
import movie.collection.model.WatchedMovie;
import movie.collection.model.User;
import movie.collection.repository.WatchedMovieRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchedMovieService {
    private final WatchedMovieRepository watchedMovieRepository;
    private final UserService userService;
    private final MovieService movieService;


    public WatchedMovieService(WatchedMovieRepository watchedMovieRepository, UserService userService, MovieService movieService) {
        this.watchedMovieRepository = watchedMovieRepository;
        this.userService = userService;
        this.movieService = movieService;
    }



    public void changeMovieWatchedStatus(Long movieId, String username){
        Movie movie = movieService.findMovieById(movieId);
        User user = userService.findUserByUsername(username);
        Optional<WatchedMovie> movieStatus = watchedMovieRepository.findByUserAndMovie(user, movie);
        if (movieStatus.isPresent()){
            watchedMovieRepository.delete(movieStatus.get());
        }else {
            watchedMovieRepository.save(WatchedMovie.builder().user(user).movie(movie).build());
        }
    }
}
