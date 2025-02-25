package movie.collection.controller;

import movie.collection.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final AuthenticationManager authManager;

    public AuthController( AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model){
        LoginRequest login = new LoginRequest();
        model.addAttribute("login",login);
        return "login";
    }




    //TODO 1
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("login") LoginRequest loginRequest,Model model){
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authManager.authenticate(authInputToken);
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/";
        }
        return "redirect:/login";
    }
}
