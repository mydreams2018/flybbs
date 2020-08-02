package cn.kungreat.flybbs.security;

import cn.kungreat.flybbs.vo.JsonResult;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class ImageFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletRequest re = request;
        String requestURI = re.getRequestURI();
        if("/defaultLogin".equals(requestURI) || "/login".equals(requestURI)){
            //如果已经存在认证对象 时不要再次认证
            SecurityContext context = SecurityContextHolder.getContext();
            if(context.getAuthentication() != null){
                String isAuth = context.getAuthentication().getName();
                if(isAuth != null && !"anonymousUser".equals(isAuth)){
                    re.getSession().removeAttribute("image_code");
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write(JSON.toJSONString(new JsonResult(false,"已经存在用户,请先退出","/home.html",1,"")));
                    return ;
                }
            }
        }
        if("/defaultLogin".equals(requestURI) || "/javaDetails/updateSave".equals(requestURI)
                ||"/login".equals(requestURI) || "/javaPosts/save".equals(requestURI) ||
                "/javaDetails/save".equals(requestURI) || "/javaPosts/updatePosts".equals(requestURI)
                ||"/assemblerPosts/updatePosts".equals(requestURI) || "/assemblerPosts/save".equals(requestURI)
                ||"/assemblerDetails/updateSave".equals(requestURI) || "/assemblerDetails/save".equals(requestURI)
                ||"/dataPosts/updatePosts".equals(requestURI) || "/dataPosts/save".equals(requestURI)
                ||"/dataDetails/updateSave".equals(requestURI) || "/dataDetails/save".equals(requestURI)
                || "/messageDetails/save".equals(requestURI) ||  "/sendMessage/save".equals(requestURI)
        ){
            Object code = re.getSession().getAttribute("image_code");
            long seconds = 90000;
            try{
                Object obj = re.getSession().getAttribute("time");
                long time = (obj==null?90000:(long)obj);
                seconds = new Date().getTime()-time;
            }catch (Exception e){
                e.printStackTrace();
            }
            if(code == null || seconds > 80000 || !code.equals(request.getParameter("img-code"))){
                re.getSession().removeAttribute("image_code");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(new JsonResult(false,"验证码错误-或者超时","/register.html",1,"")));
                return ;
            }
        }
        re.getSession().removeAttribute("image_code");
        filterChain.doFilter(request,response);
    }
}