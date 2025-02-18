package movie.collection.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {

    @GetMapping("/")
    public String home(){
        System.out.println("wykonano");
        return "index";
    }

    @GetMapping("/work")
    private String work(){
        return "smth";
    }


}
