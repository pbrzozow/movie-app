package movie.collection.controller;

import movie.collection.service.AdminInvitationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Controller
public class AdminController {
    private final AdminInvitationService adminInvitationService;

    public AdminController(AdminInvitationService adminInvitationService) {
        this.adminInvitationService = adminInvitationService;
    }

    @PostMapping("/admin/invite")
    public String inviteAdmin(@RequestParam("admin_username") String username){
        try {
            String link = adminInvitationService.inviteAdmin(username);
            System.out.println(link);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String getAdminPage(){
        return "admin";
    }

    @GetMapping("/accept")
    public String acceptInvitation(@RequestParam("token")String token){
        adminInvitationService.activateAdminPrivileges(token);
        return "redirect:/";
    }

}
