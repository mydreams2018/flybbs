package cn.kungreat.clienttest.hystrix;

import cn.kungreat.clienttest.service.NacosClientService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;

import java.util.Collections;
import java.util.List;

/**
 *带有缓存功能的 Hystrix
 * */

public class CacheHystrixCommand extends HystrixCommand<List<ServiceInstance>> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CacheHystrixCommand.class);

    /** 需要保护的服务 */
    private final NacosClientService nacosClientService;

    /** 方法需要传递的参数 */
    private final String serviceId;

    private static final HystrixCommandKey CACHED_KEY =
            HystrixCommandKey.Factory.asKey("CacheHystrixCommand");

    public CacheHystrixCommand(NacosClientService nacosClientService, String serviceId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey
                        .Factory.asKey("CacheHystrixCommandGroup")).andCommandKey(CACHED_KEY));
        this.nacosClientService = nacosClientService;
        this.serviceId = serviceId;
    }

    @Override
    protected List<ServiceInstance> run() throws Exception {
        LOGGER.info("CacheHystrixCommand In Hystrix Command to get service instance: [{}], [{}]",
                this.serviceId, Thread.currentThread().getName());
        return this.nacosClientService.getNacosClientInfo(this.serviceId);
    }

    @Override
    protected String getCacheKey() {
        return serviceId;
    }

    @Override
    protected List<ServiceInstance> getFallback() {
        return Collections.emptyList();
    }

    /**
     * 根据缓存 key 清理一次 Hystrix 请求上下文中的缓存
     * */
    public static void flushRequestCache(String serviceId) {
        HystrixRequestCache.getInstance(CACHED_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear(serviceId);
        LOGGER.info("flush request cache in hystrix command: [{}], [{}]",
                serviceId, Thread.currentThread().getName());
    }
}
