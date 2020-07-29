package cn.kungreat.flybbs.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SingleSessionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityContext context = SecurityContextHolder.getContext();
        if(context.getAuthentication() != null){
            String isAuth = context.getAuthentication().getName();
            if(isAuth != null && !"anonymousUser".equals(isAuth)){
                try {
                    String s = SingleSession.single.get(isAuth);
                    String[] split = s.split(":");
                    if(!split[1].equals(request.getSession().getCreationTime())){
                        if(!request.getSession().getId().equals(split[0])){
                            response.sendRedirect("/clearAll");
                            return ;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
