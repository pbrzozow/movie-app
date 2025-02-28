package movie.collection.repository;

import movie.collection.dto.MovieSummary;
import movie.collection.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Query("select new movie.collection.dto.MovieSummary(m.id,m.externalId, m.icon, m.title,m.rating, m.watchedTimes, m.releaseYear, m.duration, m.description, m.category) from Movie m")
    Page<MovieSummary> findAllMovieSummaries(Pageable pageable);

    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.comments WHERE m.id = :id")
    Movie findMovieWithComments(@Param("id") Long id);

    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.ratings WHERE m.id = :id")
    Movie findMovieWithRatings(@Param("id") Long id);


    Optional<Movie> findById(Long externalId);
    Optional<Movie> findByExternalId(String externalId);

}
