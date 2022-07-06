package cn.kungreat.clienttest.feign;

import cn.kungreat.clienttest.feign.hystrix.AuthorityFeignClientFallback;
import cn.kungreat.clienttest.feign.hystrix.AuthorityFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * contextId  fallback = AuthorityFeignClientFallback.class
 * */
@FeignClient(contextId = "AuthorityFeignClient", value = "flybbs-base",
        fallbackFactory = AuthorityFeignClientFallbackFactory.class)
public interface AuthorityFeignClient {

    @RequestMapping(value = "/api/index", method = RequestMethod.GET)
    Map getByFeign();
}
