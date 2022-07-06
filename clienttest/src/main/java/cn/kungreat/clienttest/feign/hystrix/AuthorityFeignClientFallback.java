package cn.kungreat.clienttest.feign.hystrix;

import cn.kungreat.clienttest.feign.AuthorityFeignClient;
import cn.kungreat.clienttest.service.NacosClientAnnoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 *AuthorityFeignClient 后备 fallback
 * */

@Component
public class AuthorityFeignClientFallback implements AuthorityFeignClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthorityFeignClientFallback.class);

    @Override
    public Map getByFeign() {
        LOGGER.error("AuthorityFeignClientFallback");
        return Collections.emptyMap();
    }
}
