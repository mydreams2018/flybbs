package cn.kungreat.flybbs.social.config;

import cn.kungreat.flybbs.security.SingleSession;
import cn.kungreat.flybbs.util.UserContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MySuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SingleSession.single.put(authentication.getName()
                ,request.getSession().getId()+":"+request.getSession().getCreationTime());
        UserContext.setCurrentName(authentication.getName());
        response.sendRedirect("https://www.kungreat.cn/home.html");
    }
}
