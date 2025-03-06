package movie.collection.mapper;

import movie.collection.dto.UserProfile;
import movie.collection.model.Comment;
import movie.collection.model.Rating;
import movie.collection.model.User;
import movie.collection.model.WatchedMovie;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserProfileMapper {
    private final CommentMapper commentMapper;
    private final RatingMapper ratingMapper;
    private final WatchedMovieMapper watchedMovieMapper;

    public UserProfileMapper(CommentMapper commentMapper, RatingMapper ratingMapper, WatchedMovieMapper watchedMovieMapper) {
        this.commentMapper = commentMapper;
        this.ratingMapper = ratingMapper;
        this.watchedMovieMapper = watchedMovieMapper;
    }
    public UserProfile entityToProfile(User user){
        List<Comment> comments = user.getComments();
        List<Rating> ratings = user.getRatings();
        List<WatchedMovie> watchedMovies = user.getWatchedMovies();
        UserProfile userProfile = new UserProfile();
        userProfile.setId(user.getId());
        userProfile.setComments(comments.stream().map(commentMapper::entityToSummary).toList());
        userProfile.setWatchedMovies(watchedMovies.stream().map(watchedMovieMapper::entityToSummary).toList());
        userProfile.setRatings(ratings.stream().map(ratingMapper::entityToSummary).toList());
        return userProfile;
    }
}
