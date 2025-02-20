package movie.collection.repository;

import movie.collection.dto.MovieSummary;
import movie.collection.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Query("select new movie.collection.dto.MovieSummary(m.id, m.icon, m.title,m.rating, m.watchedTimes, m.releaseYear, m.duration, m.description, m.category) from Movie m")
    Page<MovieSummary> findAllMovieSummaries(Pageable pageable);

    Optional<Movie> findMovieById(Long id);
}
