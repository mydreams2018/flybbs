package cn.kungreat.flybbs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String,String> accessDeniedException(AccessDeniedException ex){
        HashMap<String, String> map = new HashMap<>();
        map.put("msg",ex.getMessage());
        return map;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String,String> illegalArgumentException(IllegalArgumentException ex){
        HashMap<String, String> map = new HashMap<>();
        map.put("msg",ex.getMessage());
        return map;
    }
}
