package cn.kungreat.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <h1>基于 SpringCloud Stream 构建消息驱动微服务</h1>
 * */
@EnableDiscoveryClient
@SpringBootApplication
public class StreamApplication {

    public static final ObjectMapper OBJECT_MAP = new ObjectMapper();

    public static void main(String[] args) {
        SpringApplication.run(StreamApplication.class,args);
    }
}
