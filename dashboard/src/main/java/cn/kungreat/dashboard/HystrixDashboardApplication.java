package cn.kungreat.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 *  本地服务页面  http://127.0.0.1:10066/dashboard/hystrix
 * 监控别的微服务 样式 需要别的服务开启 actuator 端点 *  只对走了 hystrix 链路的服务有用
 * http://127.0.0.1:8888/actuator/hystrix.stream
 * */
@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardApplication.class, args);
    }
}
