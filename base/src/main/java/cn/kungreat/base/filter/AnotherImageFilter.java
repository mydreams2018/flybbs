package cn.kungreat.base.filter;

import cn.kungreat.base.FlybbsApplication;
import cn.kungreat.base.util.Calculator;
import cn.kungreat.common.vo.JsonResult;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AnotherImageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest re =(HttpServletRequest) request;
        if("/api/register".equals(re.getRequestURI()) || "/api/user/resetPassword".equals(re.getRequestURI())){
            Object code = re.getSession().getAttribute("image_code");
            Object obj = re.getSession().getAttribute("time");
            re.getSession().removeAttribute("image_code");
            long time = (obj==null?65000:System.currentTimeMillis()-(long)obj);
            Object tarCode = request.getParameter("image_code");
            if(code == null || obj ==null || time > 60000 || tarCode ==null || Calculator.count(code.toString()) != Integer.parseInt(tarCode.toString())){
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(FlybbsApplication.MAP_JSON.writeValueAsString(new JsonResult(false,"验证码错误-或者超时","/user/reg.html",0,"imgCode")));
                return;
            }
        }
        chain.doFilter(request,response);
    }
}