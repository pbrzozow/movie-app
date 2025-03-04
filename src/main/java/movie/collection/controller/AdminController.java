package movie.collection.controller;

import movie.collection.dto.MovieSummary;
import movie.collection.service.AdministrationService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Controller
public class AdminController {
    private final AdministrationService administrationService;

    public AdminController(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    @PostMapping("/admin/invite")
    public String inviteAdmin(@RequestParam("admin_username") String username){
        try {
            String link = administrationService.inviteAdmin(username);
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
        administrationService.acceptToken(token);
        return "redirect:/";
    }

}
