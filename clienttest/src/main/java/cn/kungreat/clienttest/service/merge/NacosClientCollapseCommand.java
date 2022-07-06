package cn.kungreat.clienttest.service.merge;

import cn.kungreat.clienttest.service.NacosClientService;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *请求合并器
 * */

public class NacosClientCollapseCommand
        extends HystrixCollapser<List<List<ServiceInstance>>, List<ServiceInstance>, String> {

    private final static Logger LOGGER = LoggerFactory.getLogger(NacosClientBatchCommand.class);

    private final NacosClientService nacosClientService;
    private final String serviceId;

    public NacosClientCollapseCommand(NacosClientService nacosClientService, String serviceId) {
        super(
                Setter.withCollapserKey(
                        HystrixCollapserKey.Factory.asKey("NacosClientCollapseCommand")
                ).andCollapserPropertiesDefaults(
                        HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(300))
        );
        this.nacosClientService = nacosClientService;
        this.serviceId = serviceId;
    }

    /**
     *获取请求中的参数
     * */
    @Override
    public String getRequestArgument() {
        return this.serviceId;
    }

    /**
     * <h2>创建批量请求 Hystrix Command</h2>
     * */
    @Override
    protected HystrixCommand<List<List<ServiceInstance>>> createCommand(
            Collection<CollapsedRequest<List<ServiceInstance>,String>> collapsedRequests) {
        List<String> serviceIds = new ArrayList<>(collapsedRequests.size());
        serviceIds.addAll(
                collapsedRequests.stream()
                        .map(CollapsedRequest::getArgument)
                        .collect(Collectors.toList())
        );
        return new NacosClientBatchCommand(nacosClientService, serviceIds);
    }

    /**
     *响应分发给单独的请求
     * */
    @Override
    protected void mapResponseToRequests(List<List<ServiceInstance>> batchResponse,
                                         Collection<CollapsedRequest<List<ServiceInstance>, String>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<List<ServiceInstance>, String> collapsedRequest :collapsedRequests) {
            // 从批量响应集合中按顺序取出结果
            List<ServiceInstance> instances = batchResponse.get(count++);
            // 将结果返回原 Response 中
            collapsedRequest.setResponse(instances);
        }
    }
}
