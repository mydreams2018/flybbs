package cn.kungreat.flybbs.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JsonResult {
    private boolean result = true;
    private String message;
    private String path;
    public JsonResult(boolean result, String message,String path) {
        this.result = result;
        this.message = message;
        this.path = path;
    }
    public JsonResult(){}
}