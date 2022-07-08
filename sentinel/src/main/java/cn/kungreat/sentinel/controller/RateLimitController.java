package cn.kungreat.sentinel.controller;

import cn.kungreat.sentinel.blockhandler.KunBlockHandler;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基于 Sentinel 控制台配置流控规则
 * Sentinel 是懒加载的, 先去访问一下, 就可以在 Sentinel Dashboard 看到了
 * */
@Slf4j
@RestController
@RequestMapping("/dashboard")
public class RateLimitController {

    /**
     * <h2>在 dashboard 中 "流控规则" 中按照资源名称新增流控规则</h2>
     * */
    @GetMapping("/byresource")
    @SentinelResource(
            value = "byResource",
            blockHandler = "kunHandleBlockException",
            blockHandlerClass = KunBlockHandler.class
    )
    public String byResource() {
        log.info("coming in rate limit controller by resource");
        return  "byResource";
    }

    /**
     * <h2>在 "簇点链路" 中给 url 添加流控规则</h2>
     * */
    @GetMapping("/byurl")
    @SentinelResource(value = "byUrl")
    public String byUrl() {
        log.info("coming in rate limit controller by url");
        return "byUrl";
    }
}
