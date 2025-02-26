package movie.collection.mapper;

import movie.collection.dto.MovieDto;
import movie.collection.dto.external.MovieExternalDto;
import movie.collection.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    private final CommentMapper commentMapper;

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
                .category(movie.getCategory())
                .watchedTimes(movie.getWatchedTimes())
                .rating(movie.getRating())
                .comments(movie.getComments().stream().map(commentMapper::entityToDto).toList())
                .build();
    }

    public Movie externalDtoToEntity(MovieExternalDto externalDto){
        return Movie.builder()
                .externalId(externalDto.getId())
                .title(externalDto.getPrimaryTitle())
                .description(externalDto.getDescription())
                .icon(externalDto.getPrimaryImage())
                .releaseYear(externalDto.getStartYear())
                .duration(externalDto.getRuntimeMinutes())
                .category(
                        externalDto.getGenres().get(0))
                .build();
    }
}
