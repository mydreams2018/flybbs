package cn.kungreat.seata.config;

import com.alibaba.cloud.seata.web.SeataHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * <h1>Web Mvc 配置</h1>
 * */
//@Configuration
public class SeataWebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * <h2>添加拦截器配置</h2>
     * */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // Seata 传递 xid 事务 id 给其他的微服务
        // 只有这样, 其他的服务才会写 undo_log, 才能够实现回滚
        // com.alibaba.cloud.seata.web.SeataHandlerInterceptorConfiguration  默认添加了这个
//        registry.addInterceptor(new SeataHandlerInterceptor()).addPathPatterns("/**");
    }

}
