package movie.collection.controller;

import movie.collection.service.MovieStatusService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/status")
public class MovieStatusController {
    private final MovieStatusService movieStatusService;

    public MovieStatusController(MovieStatusService movieStatusService) {
        this.movieStatusService = movieStatusService;
    }

    @PostMapping("/{movieId}")
    public String changeMovieStatus(@PathVariable("movieId") Long movieId, Principal principal){
        movieStatusService.changeMovieStatus(movieId,principal.getName());
        return "redirect:/movie/"+movieId;
    }
}
