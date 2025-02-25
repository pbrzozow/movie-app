package movie.collection.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ConfirmationToken {
    public ConfirmationToken(String token, User user) {
        this.token = token;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String token;

    @ManyToOne
    @JoinColumn(nullable = false, name = "app_user_id")
    private User user;
}
