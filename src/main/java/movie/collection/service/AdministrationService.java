package movie.collection.service;

import javax.mail.MessagingException;

public interface AdministrationService {
    String inviteAdmin(String username) throws MessagingException;
    void acceptToken(String token);
}
