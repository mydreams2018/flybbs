package cn.kungreat.admin;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@EnableAdminServer
@SpringBootApplication
public class Admin {
    /*
    * 监控中心
    */
    public static void main(String[] args) {
        SpringApplication.run(Admin.class, args);
    }

    @Bean
    @Primary
    public NacosDiscoveryProperties nacosProperties() {
        NacosDiscoveryProperties nacosDiscoveryProperties = new NacosDiscoveryProperties();
        //此处我只改了ip，其他参数可以根据自己的需求改变
        nacosDiscoveryProperties.setIp("www.kungreat.cn");
        return nacosDiscoveryProperties;
    }
}
