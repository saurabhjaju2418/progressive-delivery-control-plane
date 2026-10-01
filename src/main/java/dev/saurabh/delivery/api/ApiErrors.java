package dev.saurabh.delivery.api;
import jakarta.persistence.EntityNotFoundException;import org.springframework.http.HttpStatus;import org.springframework.web.bind.annotation.*;import java.util.Map;
@RestControllerAdvice public class ApiErrors{
 @ExceptionHandler(EntityNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND) public Map<String,String> missing(EntityNotFoundException e){return Map.of("error",e.getMessage());}
 @ExceptionHandler(IllegalStateException.class) @ResponseStatus(HttpStatus.CONFLICT) public Map<String,String> conflict(IllegalStateException e){return Map.of("error",e.getMessage());}
 @ExceptionHandler(IllegalArgumentException.class) @ResponseStatus(HttpStatus.BAD_REQUEST) public Map<String,String> bad(IllegalArgumentException e){return Map.of("error",e.getMessage());}
}
