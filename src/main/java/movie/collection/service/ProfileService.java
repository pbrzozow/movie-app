package movie.collection.service;

import movie.collection.dto.UserDto;
import movie.collection.dto.UserProfile;
import movie.collection.exception.InvalidCredentialsException;
import movie.collection.mapper.UserProfileMapper;
import movie.collection.model.Comment;
import movie.collection.model.Rating;
import movie.collection.model.User;
import movie.collection.model.WatchedMovie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

@Service
public class ProfileService {
    private final UserService userService;
    private final CommentService commentService;
    private final RatingService ratingService;
    private final UserProfileMapper userProfileMapper;

    public ProfileService(UserService userService, CommentService commentService, RatingService ratingService, UserProfileMapper userProfileMapper) {
        this.userService = userService;
        this.commentService = commentService;
        this.ratingService = ratingService;
        this.userProfileMapper = userProfileMapper;
    }

    @Transactional
    public void deleteComment(Long id,String username){
        Comment comment = commentService.findCommentById(id);
        if (comment.getUser().getUsername().equals(username)){
            commentService.deleteComment(id);
        }else{
            throw new InvalidCredentialsException("Cannot delete a comment, bad credentials were provided! ");
        }
    }
    @Transactional
    public void updateRating(int rate,Long id,String username){
        Rating rating = ratingService.findRatingById(id);
        if (rating.getUser().getUsername().equals(username)){
            ratingService.saveRating(rate,id,username);
        }else{
            throw new InvalidCredentialsException("Cannot update a ranking, bad credentials were provided! ");
        }
    }

    public UserProfile findUserProfileByUsername(String username){
        User user = userService.findUserWithHistoryByUsername(username);
        return userProfileMapper.entityToProfile(user);
    }



}
