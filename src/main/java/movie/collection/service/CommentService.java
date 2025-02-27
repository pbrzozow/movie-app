package movie.collection.service;

import movie.collection.dto.CommentDto;
import movie.collection.exception.MovieNotFoundException;
import movie.collection.exception.UserNotFoundException;
import movie.collection.mapper.CommentMapper;
import movie.collection.model.Comment;
import movie.collection.model.Movie;
import movie.collection.model.User;
import movie.collection.repository.CommentRepository;
import movie.collection.repository.MovieRepository;
import movie.collection.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper, MovieRepository movieRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public CommentDto saveComment(String text, String username, Long movieId){
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(()->new MovieNotFoundException("Movie with id: "+ movieId + "does not exist"));
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username: " + username + "does not exist"));
        Comment comment = Comment.builder()
                .movie(movie)
                .text(text)
                .user(user)
                .build();
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.entityToDto(savedComment);
    }
}
