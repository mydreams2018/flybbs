package cn.kungreat.sentinel.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <h1>通过 Sentinel 对 OpenFeign 实现熔断降级</h1>
 * */
@FeignClient(value = "kun-sentinel-feign", fallback = SentinelFeignClientFallback.class)
public interface SentinelFeignClient {
    @RequestMapping(value = "feign-sentinel", method = RequestMethod.GET)
    String getResultByFeign(@RequestParam Integer code);
}
