package movie.collection.controller;

import movie.collection.dto.MovieDto;
import movie.collection.dto.MovieSummary;
import movie.collection.service.MovieService;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }


    @GetMapping("/")
    public String listMovies(Model model, @RequestParam(required = false, defaultValue = "0") int page){
        Page<MovieSummary> movies = movieService.listMovies(page);
        model.addAttribute("movies",movies);
        return "home";
    }

    @GetMapping("/search")
    public String searchForMovie(Model model, @RequestParam(value = "title",defaultValue = "") String title, @RequestParam(value = "category",defaultValue = "") String category,@RequestParam(required = false, defaultValue = "0") int page){
        Page<MovieSummary> movies = movieService.findMoviesByTitleAndCategory(title,category,page);
        model.addAttribute("movies",movies);
        return "search";
    }

    @GetMapping("/movie/{id}")
    public String findMovieById(Model model, @PathVariable Long id, Principal principal){
        MovieDto movieDto = movieService.findMovieDtoById(id);
        Authentication authentication = (Authentication) principal;
        String role = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")) ? "ADMIN" : "USER";
        model.addAttribute("role",role);
        model.addAttribute("username",principal.getName());
        model.addAttribute("movie",movieDto);
        model.addAttribute("comments",movieDto.getComments());
        return "show-movie";
    }

    @PostMapping("/movie/disable/{id}")
    public String deactivateMovie(@PathVariable Long id){
        movieService.deactivateMovie(id);
        return "redirect:/search";
    }


}
