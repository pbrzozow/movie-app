package movie.collection.mapper;

import movie.collection.dto.MovieDto;
import movie.collection.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    private CommentMapper commentMapper;

    public MovieMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }


    public MovieDto entityToDto(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .icon(movie.getIcon())
                .title(movie.getTitle())
                .duration(movie.getDuration())
                .description(movie.getDescription())
                .releaseYear(movie.getReleaseYear())
                .category(movie.getCategory().name())
                .watchedTimes(movie.getWatchedTimes())
                .rating(movie.getRating())
                .comments(movie.getComments().stream().map(commentMapper::entityToDto).toList())
                .build();
    }
}
