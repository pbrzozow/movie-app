package movie.collection.service;

import movie.collection.dto.MovieDto;
import movie.collection.dto.MovieSummary;
import movie.collection.exception.MovieNotFoundException;
import movie.collection.mapper.MovieMapper;
import movie.collection.model.*;
import movie.collection.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class MovieService {
    private static final int PAGE_SIZE = 30;

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    public Page<MovieSummary> listMovies(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        return movieRepository.findAllMovieSummaries(pageable);
    }

    public Page<MovieSummary> findMoviesByTitleAndCategory(String title,String category,int pageNo){
        String searchTitle = (title == null || title.isBlank()) ? "%" : title;
        String searchCategory = (category == null || category.isBlank()) ? "%" : category;
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        return movieRepository.findMoviesByTitleLikeAndCategoryLike(searchTitle,searchCategory,pageable);
    }

    public MovieDto findMovieDtoById(Long id) throws MovieNotFoundException{
        Movie movie = findMovieById(id);
        return movieMapper.entityToDto(movie);
    }
    public void deactivateMovie(Long movieId){
        Movie movie = findMovieById(movieId);
        movie.setMovieStatus(MovieStatus.INACTIVE);
        movieRepository.save(movie);
    }

    protected Movie findMovieById(Long id) {
        Movie movie = movieRepository.findMovieWithComments(id);
        if (movie == null) {
            throw new MovieNotFoundException("Movie with id: " + id + " does not exist");
        }

        Movie movieWithRatings = movieRepository.findMovieWithRatings(id);
        if (movieWithRatings != null) {
            movie.setRatings(movieWithRatings.getRatings());
            movie.setRating(movie.getRatings().stream().mapToInt(Rating::getRating).average().orElse(0.0));
        }

        Movie movieWithStatuses = movieRepository.findMovieWithStatuses(id);
        if (movieWithStatuses != null) {
            movie.setWatchedMovies(movieWithStatuses.getWatchedMovies());
            movie.setWatchedTimes(movie.getWatchedMovies().size());
        }
        return movie;
    }

    @Transactional
    public void saveOrUpdateMovie(Movie movie){
        Optional<Movie> existingMovie = movieRepository.findByExternalIdAndMovieStatus(movie.getExternalId(),MovieStatus.ACTIVE);
        if (existingMovie.isPresent()){
            Movie savedMovie = existingMovie.get();
            savedMovie.setCategory(movie.getCategory());
            savedMovie.setIcon(movie.getIcon());
            savedMovie.setDescription(movie.getDescription());
            savedMovie.setTitle(movie.getTitle());
            savedMovie.setDuration(movie.getDuration());
            savedMovie.setReleaseYear(movie.getReleaseYear());
            movieRepository.save(savedMovie);
        }else {
            movieRepository.save(movie);
        }
    }

}
