package cn.kungreat.flybbs.filter;

import cn.kungreat.flybbs.vo.JsonResult;
import com.alibaba.fastjson.JSON;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

public class AnotherImageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest re =(HttpServletRequest) request;
        String requestURI = re.getRequestURI();
        if("/register".equals(requestURI)){
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
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(new JsonResult(false,"验证码错误-或者超时","/register.html")));
                return ;
            }
        }
        re.getSession().removeAttribute("image_code");
        chain.doFilter(request,response);
    }
}
