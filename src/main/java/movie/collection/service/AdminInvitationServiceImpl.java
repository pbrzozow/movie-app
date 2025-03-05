package movie.collection.service;

import movie.collection.model.Token;
import movie.collection.model.Role;
import movie.collection.model.TokenType;
import movie.collection.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
@Service
public class AdminInvitationServiceImpl implements AdminInvitationService {
    private final TokenService tokenService;
    private final UserService userService;
//    private final EmailSender emailSender;

    public AdminInvitationServiceImpl(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
//        this.emailSender = emailSender;
    }



    @Override
    public String inviteAdmin(String username) throws MessagingException {
        User user = userService.findUserByUsername(username);
        String token = generateInvitationToken(user);
        String invitationLink = getConfirmationLink(token);
//        emailSender.sendEmail(user.getEmail(),"Admin invitation link. ",invitationLink);
        return invitationLink;
    }

    @Override
    @Transactional
    public void activateAdminPrivileges(String token) {
        Token invitationToken = tokenService.getToken(token, TokenType.ADMIN_INVITATION);
        User user = invitationToken.getUser();
        user.setRole(Role.ADMIN);
        userService.saveUser(user);
        tokenService.deleteToken(invitationToken);
    }


    private String generateInvitationToken(User user) {
        Token token = tokenService.createInvitationToken(user);
        return token.getToken();
    }

    private String getConfirmationLink(String token){
        return "http://localhost:8080/accept?token="+token;
    }


}
