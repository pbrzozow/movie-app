package movie.collection.service;

import movie.collection.exception.TokenNotFoundException;
import movie.collection.model.ConfirmationToken;
import movie.collection.model.User;
import movie.collection.repository.ConfirmationTokenRepository;
import ognl.Token;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository tokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }
    public ConfirmationToken generate(User user){
        String token = UUID.randomUUID().toString();
        return new ConfirmationToken(token,user);
    }

    public ConfirmationToken saveToken(ConfirmationToken confirmationToken){
        return tokenRepository.save(confirmationToken);
    }
    public ConfirmationToken getToken(String token) throws TokenNotFoundException {
        return tokenRepository.getByToken(token).orElseThrow(()->new TokenNotFoundException("Provided token is invalid! "));
    }
    public void deleteToken(ConfirmationToken token){
        tokenRepository.delete(token);
    }

}
