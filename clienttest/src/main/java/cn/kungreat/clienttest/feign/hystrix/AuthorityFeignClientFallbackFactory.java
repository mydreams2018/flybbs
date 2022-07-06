package cn.kungreat.clienttest.feign.hystrix;

import cn.kungreat.clienttest.feign.AuthorityFeignClient;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * OpenFeign 集成 Hystrix 的另一种模式
 * */

@Component
public class AuthorityFeignClientFallbackFactory
        implements FallbackFactory<AuthorityFeignClient> {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthorityFeignClientFallbackFactory.class);

    @Override
    public AuthorityFeignClient create(Throwable throwable) {
        LOGGER.warn("authority feign client get token by feign request error " +
                "(Hystrix FallbackFactory): [{}]", throwable.getMessage());
        return Collections::emptyMap;
    }
}
