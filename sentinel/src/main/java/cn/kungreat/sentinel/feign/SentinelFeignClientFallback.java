package cn.kungreat.sentinel.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <h1>Sentinel 对 OpenFeign 接口的降级策略</h1>
 * */
@Slf4j
@Component
public class SentinelFeignClientFallback implements SentinelFeignClient {

    @Override
    public String getResultByFeign(Integer code) {
        log.error("request supply for test has some error: [{}]", code);
        return "sentinel feign fallback";
    }
}
