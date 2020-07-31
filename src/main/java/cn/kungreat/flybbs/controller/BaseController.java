package cn.kungreat.flybbs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class BaseController {

    @RequestMapping(value = "/")
    public Map index(){
        return Collections.emptyMap();
    }
    @RequestMapping(value = "/index")
    public Map ix(){
        return Collections.emptyMap();
    }
}
