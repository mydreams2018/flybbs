package cn.kungreat.clienttest.service.merge;

import cn.kungreat.clienttest.service.NacosClientService;
import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;

import java.util.Collections;
import java.util.List;

/**
 *批量请求 Hystrix Command
 * */
public class NacosClientBatchCommand extends HystrixCommand<List<List<ServiceInstance>>> {
    private final static Logger LOGGER = LoggerFactory.getLogger(NacosClientBatchCommand.class);

    private final NacosClientService nacosClientService;
    private final List<String> serviceIds;

    protected NacosClientBatchCommand(
            NacosClientService nacosClientService, List<String> serviceIds
    ) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("NacosClientBatchCommand")));
        this.nacosClientService = nacosClientService;
        this.serviceIds = serviceIds;
    }

    @Override
    protected List<List<ServiceInstance>> run() throws Exception {
        LOGGER.info("use nacos client batch command to get result: [{}]", JSON.toJSONString(serviceIds));
        return nacosClientService.getNacosClientInfos(serviceIds);
    }

    @Override
    protected List<List<ServiceInstance>> getFallback() {
        LOGGER.warn("nacos client batch command failure, use fallback");
        return Collections.emptyList();
    }
}
