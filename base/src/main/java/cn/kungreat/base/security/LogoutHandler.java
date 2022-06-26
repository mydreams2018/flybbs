package cn.kungreat.base.security;

import cn.kungreat.base.FlybbsApplication;
import cn.kungreat.base.vo.JsonResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(FlybbsApplication.MAP_JSON.writeValueAsString(new JsonResult(true,"已经退出","/index.html",1,"imgCode")));
    }
}