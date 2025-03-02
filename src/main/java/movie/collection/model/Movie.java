package movie.collection.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,name = "external_id")
    private String externalId;

    private String icon;

    private String title;

    private Double rating=0.0;

    @Column(name = "watched_times")
    private Integer watchedTimes = 0;

    @Column(name = "release_year")
    private Integer releaseYear;

    private Integer duration;
    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieStatus> movieStatuses;

}
