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

    public void initialize(){
        Movie theGodfather = Movie.builder().icon("https://th.bing.com/th/id/OIP.3C9P6X2vrW-EGjNpsSMgyQHaK9?rs=1&pid=ImgDetMain")
                .title("The godfather")
                .rating(5.0)
                .category("Drama")
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();
        Movie movie3= Movie.builder().icon("https://th.bing.com/th/id/OIP.3C9P6X2vrW-EGjNpsSMgyQHaK9?rs=1&pid=ImgDetMain")
                .title("lama")
                .rating(5.0)
                .category("Drama")
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();
        Movie movie2 = Movie.builder().icon("https://th.bing.com/th/id/OIP.3C9P6X2vrW-EGjNpsSMgyQHaK9?rs=1&pid=ImgDetMain")
                .title("gamma")
                .rating(5.0)
                .category("Drama")
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();
        Movie movie1 = Movie.builder().icon("https://th.bing.com/th/id/OIP.3C9P6X2vrW-EGjNpsSMgyQHaK9?rs=1&pid=ImgDetMain")
                .title("The godfather")
                .rating(5.0)
                .category("Drama")
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();
        Movie movie4 = Movie.builder().icon("https://th.bing.com/th/id/OIP.3C9P6X2vrW-EGjNpsSMgyQHaK9?rs=1&pid=ImgDetMain")
                .title("alfa")
                .rating(5.0)
                .category("Drama")
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();

        Movie movie5 = Movie.builder().icon("https://th.bing.com/th/id/OIP.3C9P6X2vrW-EGjNpsSMgyQHaK9?rs=1&pid=ImgDetMain")
                .title("delta")
                .rating(5.0)
                .category("Drama")
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();
        Movie m1 = Movie.builder().icon("https://www.themoviedb.org/t/p/original/qMxAmzGQO722q0UlssCOPhrXmvX.jpg")
                .title("Avengers")
                .rating(5.0)
                .category("Drama")
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();
        Movie m2 = Movie.builder().icon("https://www.themoviedb.org/t/p/original/qMxAmzGQO722q0UlssCOPhrXmvX.jpg")
                .title("avg")
                .rating(5.0)
                .category("Drama")
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();
        Movie m3 = Movie.builder().icon("https://www.themoviedb.org/t/p/original/qMxAmzGQO722q0UlssCOPhrXmvX.jpg")
                .title("Stranges")
                .rating(5.0)
                .category("Drama")
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();
        Movie m4 = Movie.builder().icon("https://www.themoviedb.org/t/p/original/qMxAmzGQO722q0UlssCOPhrXmvX.jpg")
                .title("Ula")
                .rating(5.0)
                .category("Drama")
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();
        Movie m5 = Movie.builder().icon("https://www.themoviedb.org/t/p/original/qMxAmzGQO722q0UlssCOPhrXmvX.jpg")
                .title("Kula")
                .rating(5.0)
                .category("Drama")
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();
        Movie m6 = Movie.builder().icon("https://www.themoviedb.org/t/p/original/qMxAmzGQO722q0UlssCOPhrXmvX.jpg")
                .title("Cebula")
                .rating(5.0)
                .category("Drama")
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();
        movieRepository.save(theGodfather);
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movieRepository.save(movie3);
        movieRepository.save(movie4);
        movieRepository.save(movie5);
        movieRepository.save(m1);
        movieRepository.save(m2);
        movieRepository.save(m3);
        movieRepository.save(m4);
        movieRepository.save(m5);
        movieRepository.save(m6);





    }

    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }
}
