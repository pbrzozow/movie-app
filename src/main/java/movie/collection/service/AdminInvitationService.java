package movie.collection.service;

import javax.mail.MessagingException;

public interface AdminInvitationService {
    String inviteAdmin(String username) throws MessagingException;
    void activateAdminPrivileges(String token);
}
