package cn.kungreat.clienttest.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * contextId
 * */
@FeignClient(contextId = "AuthorityFeignClient", value = "flybbs-base")
public interface AuthorityFeignClient {

    @RequestMapping(value = "/api/index", method = RequestMethod.GET)
    Map getByFeign();
}
