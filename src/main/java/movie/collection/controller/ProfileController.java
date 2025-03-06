package movie.collection.controller;

import movie.collection.dto.UserDto;
import movie.collection.dto.UserProfile;
import movie.collection.service.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public String showUserProfile(Model model, Principal principal){
        UserProfile user = profileService.findUserProfileByUsername(principal.getName());
        model.addAttribute("user",user);
        return "user-profile";
    }
    @PostMapping("/comment/delete/{commentId}")
    public String deleteUserComment(@PathVariable Long commentId,Principal principal) {
        profileService.deleteComment(commentId,principal.getName());
        return "redirect:/profile";
    }

    @PostMapping("/rating/update/{ratingId}")
    public String deleteUserRating(@PathVariable Long ratingId, @RequestParam("newRating") int rate,Principal principal) {
        profileService.updateRating(rate, ratingId,principal.getName());
        return "redirect:/profile";
    }
}
