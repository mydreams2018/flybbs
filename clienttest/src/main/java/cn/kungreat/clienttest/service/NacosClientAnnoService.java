package cn.kungreat.clienttest.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class NacosClientAnnoService {
    private final static Logger LOGGER = LoggerFactory.getLogger(NacosClientAnnoService.class);

    private final DiscoveryClient discoveryClient;

    public NacosClientAnnoService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    // 使用注解实现 Hystrix 请求合并
    @HystrixCollapser(
            batchMethod = "getNacosClientInfos",
            scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
            collapserProperties = {
                    @HystrixProperty(name = "timerDelayInMilliseconds", value = "300")
            }
    )
    public Future<List<ServiceInstance>> getNacosClientInfo(String serviceId) {
        // 系统运行正常, 不会走这个方法  会走合并方法 不管有没有合并
        throw new RuntimeException("This method body should not be executed!");
    }

    @HystrixCommand
    public List<List<ServiceInstance>> getNacosClientInfos(List<String> serviceIds) {
        LOGGER.info("NacosClientAnnoService [{}]",serviceIds);
        List<List<ServiceInstance>> result = new ArrayList<>(serviceIds.size());
        serviceIds.forEach(s -> result.add(discoveryClient.getInstances(s)));
        return result;
    }

}
