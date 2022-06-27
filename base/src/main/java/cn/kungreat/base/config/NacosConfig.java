package cn.kungreat.base.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class NacosConfig {
    /**
     *初始化 NacosDiscoveryProperties 手动指定ip > 再走配置文件读取 >  后续再走 postConstruct
     */
    @Bean
    @Primary
    public NacosDiscoveryProperties nacosProperties() {
        NacosDiscoveryProperties nacosDiscoveryProperties = new NacosDiscoveryProperties();
        nacosDiscoveryProperties.setIp("www.kungreat.cn");
        return nacosDiscoveryProperties;
    }
}