package movie.collection.service;

import movie.collection.dto.CommentDto;
import movie.collection.exception.CommentNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;


@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final MovieService movieService;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper, MovieService movieService, UserService userService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.movieService = movieService;
        this.userService = userService;
    }

    public CommentDto saveComment(String text, String username, Long movieId){
        Movie movie = movieService.findMovieById(movieId);
        User user = userService.findUserByUsername(username);
        Comment comment = Comment.builder()
                .movie(movie)
                .text(text)
                .user(user)
                .build();
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.entityToDto(savedComment);
    }
    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
    protected Comment findCommentById(Long id){
        return commentRepository.findById(id).orElseThrow(()-> new CommentNotFoundException("Comment with id: "+id +" does not exist!"));
    }
}
