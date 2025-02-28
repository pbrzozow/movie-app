package movie.collection.repository;

import movie.collection.model.Movie;
import movie.collection.model.Rating;
import movie.collection.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {

    Optional<Rating> findByMovieAndUser(Movie movie, User user);
}
