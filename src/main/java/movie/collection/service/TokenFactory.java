package movie.collection.service;

import movie.collection.model.Token;
import movie.collection.model.TokenType;
import movie.collection.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class TokenFactory {
    public static Token generate(User user, TokenType tokenType){
        String tokenId = UUID.randomUUID().toString();
        Token token = new Token();
        token.setToken(tokenId);
        token.setTokenType(tokenType);
        token.setExpirationTime(LocalDateTime.now().plusHours(24));
        token.setUser(user);
        return token;
    }
}
