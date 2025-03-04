package movie.collection.mapper;

import movie.collection.dto.MovieDto;
import movie.collection.dto.MovieSummary;
import movie.collection.dto.external.MovieExternalDto;
import movie.collection.model.Movie;
import movie.collection.model.MovieStatus;
import movie.collection.model.Rating;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    private final CommentMapper commentMapper;

    public MovieMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public MovieSummary entityToSummary(Movie movie){
        return MovieSummary.builder()
                .id(movie.getId())
                .category(movie.getCategory())
                .description(movie.getDescription())
                .rating(movie.getRating())
                .duration(movie.getDuration())
                .title(movie.getTitle())
                .icon(movie.getIcon())
                .externalId(movie.getExternalId())
                .releaseYear(movie.getReleaseYear())
                .watchedTimes(movie.getWatchedTimes())
                .build();
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
                .movieStatus(MovieStatus.ACTIVE)
                .category(externalDto.getGenres().get(0))
                .build();
    }
}
