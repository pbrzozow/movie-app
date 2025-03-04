package movie.collection.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Enumerated(value = EnumType.STRING)
    private TokenType tokenType;
    private LocalDateTime expirationTime;
    @ManyToOne
    @JoinColumn(nullable = false, name = "app_user_id")
    private User user;

    public boolean isExpired(){
        return expirationTime.isBefore(LocalDateTime.now());
    }
}
