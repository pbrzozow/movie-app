package movie.collection.repository;

import movie.collection.model.Movie;
import movie.collection.model.WatchedMovie;
import movie.collection.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WatchedMovieRepository extends JpaRepository<WatchedMovie,Long> {

    Optional<WatchedMovie> findByUserAndMovie(User user, Movie movie);
}
