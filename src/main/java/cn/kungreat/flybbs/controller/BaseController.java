package cn.kungreat.flybbs.controller;

import cn.kungreat.flybbs.domain.User;
import cn.kungreat.flybbs.service.UserService;
import cn.kungreat.flybbs.vo.JsonResult;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

@RestController
public class BaseController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/index")
    public Map index(){
        return Collections.emptyMap();
    }

    @RequestMapping(value = "/getCurrentUser")
    public Authentication getCurrentUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication();
    }

    @RequestMapping(value = "/image")
    public String image(HttpServletRequest request, HttpServletResponse response){
        int randomStr = RandomUtils.nextInt(1,10);
        int randomMid = RandomUtils.nextInt(1,10);
        int randomend = RandomUtils.nextInt(1,10);
        int syb1 = RandomUtils.nextInt(1,5);
        String rt;
        switch (syb1){
            case 1:
                rt = randomStr+"+"+randomMid+"*"+randomend;
                break;
            case 2:
                rt = randomStr+"+"+randomMid+"-"+randomend;
                break;
            case 3:
                rt = randomStr+"*"+randomMid+"-"+randomend;
                break;
            case 4:
                rt = randomStr+"-"+randomMid+"*"+randomend;
                break;
            default:
                rt = randomStr+"*"+randomMid+"+"+randomend;
        }
        request.getSession().setAttribute("image_code", rt);
        request.getSession().setAttribute("time", System.currentTimeMillis());
        return rt;
    }

    @RequestMapping(value = "/register")
    public JsonResult register(User record){
        JsonResult jsonResult = new JsonResult();
        try{
            userService.insert(record);
            jsonResult.setMsg("success");
            jsonResult.setAction("/user/login.html");
        }catch(Exception e){
            jsonResult.setResult(false);
            jsonResult.setStatus(1);
            jsonResult.setId("imgCode");
            jsonResult.setMsg(e.getMessage());
            jsonResult.setAction("/user/reg.html");
        }
        return jsonResult;
    }
}