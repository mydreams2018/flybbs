package cn.kungreat.clienttest.hystrix;

import cn.kungreat.clienttest.service.NacosClientService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import rx.Observable;

import java.util.Collections;
import java.util.List;

/**
 * HystrixCommand, 隔离策略是基于信号量实现的
 * */
public class NacosClientHystrixObservableCommand
        extends HystrixObservableCommand<List<ServiceInstance>> {

    private final static Logger LOGGER = LoggerFactory.getLogger(NacosClientHystrixObservableCommand.class);

    /** 要保护的服务 */
    private final NacosClientService nacosClientService;

    /** 方法需要传递的参数 */
    private final List<String> serviceIds;

    public NacosClientHystrixObservableCommand(NacosClientService nacosClientService,
                                               List<String> serviceIds) {
        super(
                Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("NacosClientService"))
                        .andCommandKey(HystrixCommandKey.Factory.asKey("NacosClientHystrixObservableCommand"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                        .withFallbackEnabled(true)          // 开启降级
                        .withCircuitBreakerEnabled(true)    // 开启熔断器
                )
        );
        this.nacosClientService = nacosClientService;
        this.serviceIds = serviceIds;
    }

    /**
     * 要保护的方法调用写在这里
     *
     * isUnsubscribed() 指示此订阅者是否已从其订阅列表中取消订阅。
     * 返回值：
     * 如果此订阅者已取消订阅，则为 true，否则为 false
     * */
    @Override
    protected Observable<List<ServiceInstance>> construct() {
        // Observable 有三个关键的事件方法, 分别是 onNext、onCompleted、onError
        return Observable.unsafeCreate(subscriber -> {
            try {
                if (!subscriber.isUnsubscribed()) {
                    LOGGER.info("subscriber command task: [{}]", Thread.currentThread().getName());
                    serviceIds.forEach(
                            s -> subscriber.onNext(nacosClientService.getNacosClientInfo(s)));
                    subscriber.onCompleted();
                    LOGGER.info("command task completed [{}]", Thread.currentThread().getName());
                }
            } catch (Exception ex) {
                subscriber.onError(ex);
            }
        });
    }

    /**
     *服务降级
     */
    @Override
    protected Observable<List<ServiceInstance>> resumeWithFallback() {
        return Observable.unsafeCreate(subscriber -> {
            try {
                if (!subscriber.isUnsubscribed()) {
                    LOGGER.info("(fallback) subscriber command task: [{}]", Thread.currentThread().getName());
                    subscriber.onNext(Collections.emptyList());
                    subscriber.onCompleted();
                    LOGGER.info("(fallback) command task completed: [{}]", Thread.currentThread().getName());
                }
            } catch (Exception ex) {
                subscriber.onError(ex);
            }
        });
    }
}
