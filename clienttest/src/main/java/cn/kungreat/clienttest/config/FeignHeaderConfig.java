package cn.kungreat.clienttest.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * <h1>Feign 调用时, 把 Header 也传递到服务提供方</h1>
 * */
@Configuration
public class FeignHeaderConfig {

    /**
     *给 Feign 配置请求拦截器  RequestTemplate template
     * RequestInterceptor 是我们提供给 open-feign 的请求拦截器, 把 Header 信息传递
     * */
    @Bean
    public RequestInterceptor headerInterceptor() {
        return template -> {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null != attributes) {
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (null != headerNames) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        String values = request.getHeader(name);
                        // 不能把当前请求的 content-length 传递到下游的服务提供方, 这明显是不对的
                        // 请求可能一直返回不了, 或者是请求响应数据被截断
                        if (!name.equalsIgnoreCase("content-length")) {
//                            template.header(name, values);  添加头部信息
                        }
                    }
                }
            }
        };
    }
}
