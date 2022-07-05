package cn.kungreat.clienttest.controller;


import cn.kungreat.clienttest.hystrix.*;
import cn.kungreat.clienttest.service.NacosClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/hystrix-client")
public class NacosClientHystrixController {
    private final static Logger LOGGER = LoggerFactory.getLogger(NacosClientHystrixController.class);


    private final UseHystrixCommandAnnotation nacosClientService;

    private final NacosClientService nacosClientServiceSrc;

    private final CacheHystrixCommandAnnotation cacheHystrixCommandAnnotation;

    public NacosClientHystrixController(UseHystrixCommandAnnotation nacosClientService, NacosClientService nacosClientServiceSrc, CacheHystrixCommandAnnotation cacheHystrixCommandAnnotation) {
        this.nacosClientService = nacosClientService;
        this.nacosClientServiceSrc = nacosClientServiceSrc;
        this.cacheHystrixCommandAnnotation = cacheHystrixCommandAnnotation;
    }

    /**
     * 根据 服务名 =>   获取服务所有的实例信息
     * */
    @GetMapping("/service-instance")
    public List<ServiceInstance> logNacosClientInfo(@RequestParam(defaultValue = "flybbs-base")
                                                        String serviceId) {
        return nacosClientService.getNacosClientInfo(serviceId);
    }

    @GetMapping("/service-instance-two")
    public List<ServiceInstance> getServiceInstanceByServiceId(
            @RequestParam(defaultValue = "flybbs-base") String serviceId) throws Exception {

        // 第一种方式  execute = queue + get
        List<ServiceInstance> serviceInstances01 = new NacosClientHystrixCommand(
                nacosClientServiceSrc, serviceId
        ).execute();    // 同步阻塞

        // 第二种方式
        List<ServiceInstance> serviceInstances02;
        Future<List<ServiceInstance>> future = new NacosClientHystrixCommand(
                nacosClientServiceSrc, serviceId
        ).queue();      // 异步非阻塞
        // 这里可以做一些别的事, 需要的时候再去拿结果
        serviceInstances02 = future.get();

        // 第三种方式 observe  热响应调用  只有调用 single  才会真的调用
        Observable<List<ServiceInstance>> observable = new NacosClientHystrixCommand(
                nacosClientServiceSrc, serviceId).observe();
        List<ServiceInstance> serviceInstances03 = observable.toBlocking().single();

        // 第四种方式 异步冷响应调用
        Observable<List<ServiceInstance>> toObservable = new NacosClientHystrixCommand(
                nacosClientServiceSrc, serviceId
        ).toObservable();
        List<ServiceInstance> serviceInstances04 = toObservable.toBlocking().single();

        return serviceInstances01;
    }

    @GetMapping("/service-instance-three")
    public List<ServiceInstance> getServiceInstancesByServiceIdObservable(
            @RequestParam(defaultValue = "flybbs-base") String serviceId) {

        List<String> serviceIds = Arrays.asList(serviceId, serviceId, serviceId);
        List<List<ServiceInstance>> result = new ArrayList<>(serviceIds.size());

        NacosClientHystrixObservableCommand observableCommand =
                new NacosClientHystrixObservableCommand(nacosClientServiceSrc, serviceIds);
        //异步执行命令
        Observable<List<ServiceInstance>> observe = observableCommand.observe();

        // 注册获取结果
        observe.subscribe(
                new Observer<>() {
                    // 执行 onNext 之后再去执行 onCompleted
                    @Override
                    public void onCompleted() {
                        LOGGER.info("all tasks is complete: [{}], [{}]", serviceId, Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<ServiceInstance> instances) {
                        result.add(instances);
                    }
                }
        );
        LOGGER.info("observable command result is : [{}], [{}]",
                result, Thread.currentThread().getName());
        return result.get(0);
    }

    @GetMapping("/cache-hystrix-command")
    public void cacheHystrixCommand(@RequestParam(defaultValue = "flybbs-base") String serviceId) {
        // 使用缓存 Command, 发起两次请求  [只有在同一次请求上下文中 中有用、实际用处不大]
        CacheHystrixCommand command1 = new CacheHystrixCommand(
                nacosClientServiceSrc, serviceId);
        CacheHystrixCommand command2 = new CacheHystrixCommand(
                nacosClientServiceSrc, serviceId);

        List<ServiceInstance> result01 = command1.execute();
        List<ServiceInstance> result02 = command2.execute();
        //清除缓存
        CacheHystrixCommand.flushRequestCache(serviceId);
    }

    @GetMapping("/cache-annotation")
    public List<ServiceInstance> useCacheByAnnotation01(@RequestParam(defaultValue = "flybbs-base") String serviceId) {
        // 使用缓存 发起两次请求  [只有在同一次请求上下文中 中有用、实际用处不大]

        List<ServiceInstance> result01 =
                cacheHystrixCommandAnnotation.useCacheByAnnotation02(serviceId);
        List<ServiceInstance> result02 =
                cacheHystrixCommandAnnotation.useCacheByAnnotation02(serviceId);

        // 清除掉缓存
        cacheHystrixCommandAnnotation.flushCacheByAnnotation02(serviceId);
        List<ServiceInstance> result03 =
                cacheHystrixCommandAnnotation.useCacheByAnnotation02(serviceId);
        // 这里有第四次调用
        return cacheHystrixCommandAnnotation.useCacheByAnnotation02(serviceId);
    }
}
