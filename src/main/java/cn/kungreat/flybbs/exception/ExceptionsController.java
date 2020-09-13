package cn.kungreat.flybbs.exception;

import cn.kungreat.flybbs.vo.JsonResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public JsonResult accessDeniedException(AccessDeniedException ex){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(false);
        jsonResult.setStatus(1);
        jsonResult.setMsg(ex.getMessage());
        return jsonResult;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public JsonResult illegalArgumentException(IllegalArgumentException ex){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(false);
        jsonResult.setStatus(1);
        jsonResult.setMsg(ex.getMessage());
        return jsonResult;
    }
}
