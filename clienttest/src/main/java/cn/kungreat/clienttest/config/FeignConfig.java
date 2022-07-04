package cn.kungreat.clienttest.config;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * <h1>OpenFeign 配置类</h1>
 * */
@Configuration
public class FeignConfig {

    /**
     * <h2>开启 OpenFeign 日志</h2>
     * */
    @Bean
    public Logger.Level feignLogger() {
        return Logger.Level.FULL;   //  需要注意, 日志级别需要修改成 debug
    }

    /**
     * OpenFeign 开启重试  100-1000 ms内
     * period = 100 发起当前请求的时间间隔, 单位是 ms
     * maxPeriod = 1000 发起当前请求的最大时间间隔, 单位是 ms
     * maxAttempts = 2 最多请求次数
     * */
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(
                100,
                SECONDS.toMillis(1),
                5
        );
    }

    /**
      * 对请求的连接和响应时间进行限制
     * */
    @Bean
    public Request.Options options() {
        return new Request.Options(
                5000, TimeUnit.MICROSECONDS,
                5000, TimeUnit.MILLISECONDS,
                true);
    }
}
