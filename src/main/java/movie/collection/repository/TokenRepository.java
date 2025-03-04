package movie.collection.repository;

import movie.collection.model.Token;
import movie.collection.model.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

    Optional<Token> getByTokenAndTokenType(String token, TokenType tokenType);
}
