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

    public MovieDto findMovieById(String id) throws MovieNotFoundException{
        long movieId = Long.parseLong(id);
        Movie movie= movieRepository.findMovieWithComments(movieId);
        if (movie==null){throw new MovieNotFoundException("Movie does not exists!");}
        return movieMapper.entityToDto(movie);
    }

    @Transactional
    public void saveMovieOrUpdateExisting(Movie movie){
        Optional<Movie> existingMovie = movieRepository.findByExternalId(movie.getExternalId());
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
