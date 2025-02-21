package movie.collection.service;

import movie.collection.dto.CommentDto;
import movie.collection.dto.MovieDto;
import movie.collection.dto.MovieSummary;
import movie.collection.dto.UserDto;
import movie.collection.exception.MovieNotFoundException;
import movie.collection.model.*;
import movie.collection.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private static final int PAGE_SIZE = 30;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Page<MovieSummary> listMovies(int pageNo) {
        initialize();
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        return movieRepository.findAllMovieSummaries(pageable);
    }

    public MovieDto findMovieById(String id) throws MovieNotFoundException{
        long movieId = Long.parseLong(id);
        Movie movie= movieRepository.findMovieWithComments(movieId);
        if (movie==null){throw new MovieNotFoundException("Movie does not exists!");}
        return movieToDto(movie);
    }

    private MovieDto movieToDto(Movie movie) {
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
                .comments(movie.getComments().stream().map(this::commentToDto).toList())
                .build();
    }

    private CommentDto commentToDto(Comment comment) {
        UserDto userDto=userToDto(comment.getUser());
        return new CommentDto(comment.getDate(),userDto, comment.getText());

    }

    private UserDto userToDto(User user) {
        return new UserDto(user.getUsername());
    }


    void initialize(){
        Movie theGodfather = Movie.builder().icon("https://th.bing.com/th/id/OIP.3C9P6X2vrW-EGjNpsSMgyQHaK9?rs=1&pid=ImgDetMain")
                .title("The godfather")
                .rating(5.0)
                .category(Category.DRAMA)
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();
        Movie movie3= Movie.builder().icon("https://th.bing.com/th/id/OIP.3C9P6X2vrW-EGjNpsSMgyQHaK9?rs=1&pid=ImgDetMain")
                .title("lama")
                .rating(5.0)
                .category(Category.DRAMA)
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();
        Movie movie2 = Movie.builder().icon("https://th.bing.com/th/id/OIP.3C9P6X2vrW-EGjNpsSMgyQHaK9?rs=1&pid=ImgDetMain")
                .title("gamma")
                .rating(5.0)
                .category(Category.DRAMA)
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();
        Movie movie1 = Movie.builder().icon("https://th.bing.com/th/id/OIP.3C9P6X2vrW-EGjNpsSMgyQHaK9?rs=1&pid=ImgDetMain")
                .title("The godfather")
                .rating(5.0)
                .category(Category.DRAMA)
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();
        Movie movie4 = Movie.builder().icon("https://th.bing.com/th/id/OIP.3C9P6X2vrW-EGjNpsSMgyQHaK9?rs=1&pid=ImgDetMain")
                .title("alfa")
                .rating(5.0)
                .category(Category.DRAMA)
                .description("Gangster movie")
                .duration(50)
                .releaseYear(1972)
                .watchedTimes(5)
                .build();

        Movie movie5 = Movie.builder().icon("https://th.bing.com/th/id/OIP.3C9P6X2vrW-EGjNpsSMgyQHaK9?rs=1&pid=ImgDetMain")
                .title("delta")
                .rating(5.0)
                .category(Category.DRAMA)
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
    }
}
