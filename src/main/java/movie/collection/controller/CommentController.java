package movie.collection.controller;

import movie.collection.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{movieId}")
    public String publishComment(@PathVariable("movieId") Long movieId, @RequestParam String text,Principal principal){
        String username = principal.getName();
        commentService.saveComment(text,username,movieId);
        return "redirect:/movie/"+movieId;
    }
}
