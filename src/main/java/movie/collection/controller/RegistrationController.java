package movie.collection.controller;

import movie.collection.dto.CreateUserDto;
import movie.collection.model.TokenType;
import movie.collection.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;

@Controller
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model){
        CreateUserDto user = new CreateUserDto();
        model.addAttribute("user",user);
        return "register";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute("user") CreateUserDto user){
        System.out.println(user.toString());
        try {
            registrationService.registerUser(user);
        } catch (MessagingException e) {
            return "redirect:/register?error";
        }
        return "redirect:/login";
    }
    @GetMapping("/confirm")
    public String confirmToken(@RequestParam("token") String token){
        registrationService.confirmToken(token, TokenType.CONFIRMATION);
        return "redirect:/login";
    }

}
