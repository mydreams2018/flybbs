package cn.kungreat.sentinel.controller;

import cn.kungreat.sentinel.feign.SentinelFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>OpenFeign 集成 Sentinel 实现熔断降级</h1>
 * */
@Slf4j
@RestController
@RequestMapping("/sentinel-feign")
public class SentinelFeignController {

    private final SentinelFeignClient sentinelFeignClient;

    public SentinelFeignController(SentinelFeignClient sentinelFeignClient) {
        this.sentinelFeignClient = sentinelFeignClient;
    }

    /**
     * <h2>通过 Feign 接口去获取结果</h2>
     * */
    @GetMapping("/result-by-feign")
    public String getResultByFeign(@RequestParam Integer code) {
        log.info("coming in get result by feign: [{}]", code);
        return sentinelFeignClient.getResultByFeign(code);
    }
}
