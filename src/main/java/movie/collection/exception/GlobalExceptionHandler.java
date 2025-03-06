package movie.collection.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MovieNotFoundException.class, TokenNotFoundException.class, TokenExpiredException.class})
    public String handleNotFoundExceptions(){
        return "404";
    }
    @ExceptionHandler({Exception.class})
    public String handleException(Model model,Exception e){
        model.addAttribute("error",e.getMessage());
        return "error-page";
    }
}
