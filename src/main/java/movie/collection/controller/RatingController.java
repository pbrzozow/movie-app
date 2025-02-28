package movie.collection.controller;

import movie.collection.service.RatingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/{movieId}")
    public String rateAMovie(@PathVariable("movieId") Long movieId, @RequestParam("rate") int rate, Principal principal){
        String username = principal.getName();
        ratingService.saveRating(rate,movieId,username);
        return "redirect:/movie/"+movieId;
    }
}
