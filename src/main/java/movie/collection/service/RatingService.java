package movie.collection.service;

import movie.collection.dto.RatingDto;
import movie.collection.mapper.RatingMapper;
import movie.collection.model.Movie;
import movie.collection.model.Rating;
import movie.collection.model.User;
import movie.collection.repository.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    private final RatingMapper ratingMapper;
    private final UserService userService;
    private final MovieService movieService;
    private final RatingRepository ratingRepository;

    public RatingService(RatingMapper ratingMapper, UserService userService, MovieService movieService, RatingRepository ratingRepository) {
        this.ratingMapper = ratingMapper;
        this.userService = userService;
        this.movieService = movieService;
        this.ratingRepository = ratingRepository;
    }

    public RatingDto saveRating(int rate, Long movieId, String username){
        User user = userService.findUserByUsername(username);
        Movie movie = movieService.findMovieById(movieId);

        Rating rating = ratingRepository.findByMovieAndUser(movie, user)
                .orElse(Rating.builder().movie(movie).user(user).build());
        rating.setRating(rate);
        Rating savedRanking = ratingRepository.save(rating);
        return ratingMapper.entityToDto(savedRanking);
    }
}
