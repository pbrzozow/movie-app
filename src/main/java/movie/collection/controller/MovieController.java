package movie.collection.controller;

import movie.collection.dto.MovieDto;
import movie.collection.dto.MovieSummary;
import movie.collection.service.MovieService;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String searchForMovie(Model model, @RequestParam(value = "title",defaultValue = "") String title, @RequestParam(value = "category",defaultValue = "") String category){
        List<MovieSummary> movies = movieService.findMoviesByTitleAndCategory(title,category);
        System.out.println(movies);
        model.addAttribute("movies",movies);
        return "search";
    }

    @GetMapping("/movie/{id}")
    public String findMovieById(Model model, @PathVariable Long id, Principal principal){
        MovieDto movieDto = movieService.findMovieDtoById(id);
        model.addAttribute("username",principal.getName());
        model.addAttribute("movie",movieDto);
        model.addAttribute("comments",movieDto.getComments());
        return "show-movie";
    }


}
