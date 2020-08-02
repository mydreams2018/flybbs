package cn.kungreat.flybbs.security;

import cn.kungreat.flybbs.vo.JsonResult;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FaliureHandler implements AuthenticationFailureHandler {
    //重定向工具类
//    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private RequestCache requestCache = new HttpSessionRequestCache();

    /*
    *  spring 默认的处理器  SimpleUrlAuthenticationFailureHandler 可以继承 默认的处理器
     *   在做一些判断 后调用 super(request,response,exception)返回默认的处理信息
    *           也可自定意返回
     **/

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        SavedRequest cache = requestCache.getRequest(request, response);
        String path = (cache==null?"/index":cache.getRedirectUrl());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(new JsonResult(false,exception.getMessage(),path,1,"")));
    /*    String accept = request.getHeader("Accept");
        if(accept.contains("text/html")){
            request.setAttribute("error",exception.getMessage());
            request.getRequestDispatcher("/login").forward(request,response);
        }else{
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new JsonResult(false,exception.getMessage())));
        }*/
    }
}