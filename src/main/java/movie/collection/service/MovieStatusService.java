package movie.collection.service;

import movie.collection.model.Movie;
import movie.collection.model.MovieStatus;
import movie.collection.model.User;
import movie.collection.repository.MovieStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieStatusService {
    private final MovieStatusRepository movieStatusRepository;
    private final UserService userService;
    private final MovieService movieService;


    public MovieStatusService(MovieStatusRepository movieStatusRepository, UserService userService, MovieService movieService) {
        this.movieStatusRepository = movieStatusRepository;
        this.userService = userService;
        this.movieService = movieService;
    }

    public void changeMovieStatus(Long movieId, String username){
        Movie movie = movieService.findMovieById(movieId);
        User user = userService.findUserByUsername(username);
        Optional<MovieStatus> movieStatus = movieStatusRepository.findByUserAndMovie(user, movie);
        if (movieStatus.isPresent()){
            movieStatusRepository.delete(movieStatus.get());
        }else {
            movieStatusRepository.save(MovieStatus.builder().user(user).movie(movie).build());
        }
    }
}
