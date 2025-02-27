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

    @PostMapping("/{movieId}/{username}")
    public String publishComment(@PathVariable("movieId") String movieId, @PathVariable("username") String username, @RequestParam String text){
        commentService.saveComment(text,username,Long.parseLong(movieId));
        return "redirect:/movie/"+movieId;
    }
}
