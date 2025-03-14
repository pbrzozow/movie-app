package movie.collection.service;

import movie.collection.exception.TokenExpiredException;
import movie.collection.exception.TokenNotFoundException;
import movie.collection.model.Token;
import movie.collection.model.TokenType;
import movie.collection.model.User;
import movie.collection.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token createConfirmationToken(User user) {
        Token token = TokenFactory.generate(user,TokenType.CONFIRMATION);
        return saveToken(token);
    }
    public Token createInvitationToken(User user){
        Token token = TokenFactory.generate(user,TokenType.ADMIN_INVITATION);
        return saveToken(token);
    }


    private Token saveToken(Token token){
        return tokenRepository.save(token);
    }

    public Token getToken(String token, TokenType tokenType) throws TokenNotFoundException, TokenExpiredException {
        Token existingToken = tokenRepository.getByTokenAndTokenType(token, tokenType)
                .orElseThrow(() -> new TokenNotFoundException("Provided token is invalid! "));
        if(existingToken.isExpired()){
            throw new TokenNotFoundException("Token is already expired! ");
        }
        return existingToken;
    }
    public void deleteToken(Token token){
        tokenRepository.delete(token);
    }

}
