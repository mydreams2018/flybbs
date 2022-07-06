package cn.kungreat.clienttest.service;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NacosClientService {

    private final DiscoveryClient discoveryClient;

    public NacosClientService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    /**
     * <h2>打印 Nacos Client 信息到日志中</h2>
     * */
    public List<ServiceInstance> getNacosClientInfo(String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }

    /**
     * <提供给编程方式的 Hystrix 请求合并
     * */
    public List<List<ServiceInstance>> getNacosClientInfos(List<String> serviceIds) {
        List<List<ServiceInstance>> result = new ArrayList<>(serviceIds.size());
        serviceIds.forEach(s -> result.add(discoveryClient.getInstances(s)));
        return result;
    }

}
