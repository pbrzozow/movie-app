package movie.collection.repository;

import movie.collection.model.Movie;
import movie.collection.model.MovieStatus;
import movie.collection.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieStatusRepository extends JpaRepository<MovieStatus,Long> {

    Optional<MovieStatus> findByUserAndMovie(User user, Movie movie);
}
