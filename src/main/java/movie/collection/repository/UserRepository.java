package movie.collection.repository;

import movie.collection.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUsername(String username);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.watchedMovies WHERE u.username = :username")
    User findUserWithWatchedMovies(@Param("username") String username);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.comments WHERE u.username = :username")
    User findUserWithComments(@Param("username") String username);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.ratings WHERE u.username = :username")
    User findUserWithRatings(@Param("username") String username);

}
