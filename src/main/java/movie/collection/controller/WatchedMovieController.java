package movie.collection.controller;

import movie.collection.service.WatchedMovieService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/status")
public class WatchedMovieController {
    private final WatchedMovieService watchedMovieService;

    public WatchedMovieController(WatchedMovieService watchedMovieService) {
        this.watchedMovieService = watchedMovieService;
    }

    @PostMapping("/{movieId}")
    public String changeMovieStatus(@PathVariable("movieId") Long movieId, Principal principal){
        watchedMovieService.changeMovieWatchedStatus(movieId,principal.getName());
        return "redirect:/movie/"+movieId;
    }
}
