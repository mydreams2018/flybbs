package cn.kungreat.clienttest.filter;

import cn.kungreat.clienttest.controller.NacosClientHystrixController;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 *初始化 Hystrix 请求上下文环境
 * */
@Component
@WebFilter(filterName = "HystrixRequestContextServletFilter",
        urlPatterns = "/**",
        asyncSupported = true)
public class HystrixRequestContextServletFilter implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(HystrixRequestContextServletFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // 初始化 Hystrix 请求上下文
        // 在不同的 context 中缓存是不共享的
        // 这个初始化是必须的
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            // hystrix 配置
            hystrixConcurrencyStrategyConfig();
            // 请求正常通过
            chain.doFilter(request, response);
        } finally {
            // 关闭 Hystrix 请求上下文
            context.shutdown();
        }
    }

    /**
     *配置 Hystrix 的默认并发策略    可能和slueth 的冲突
     * */
    public void hystrixConcurrencyStrategyConfig() {
        try {
            HystrixConcurrencyStrategy target =
                    HystrixConcurrencyStrategyDefault.getInstance();
            HystrixConcurrencyStrategy strategy =
                    HystrixPlugins.getInstance().getConcurrencyStrategy();
            if (strategy instanceof HystrixConcurrencyStrategyDefault) {
                // 如果已经就是我们想要配置的
                return;
            }
            // 将原来其他的配置保存下来
            HystrixCommandExecutionHook commandExecutionHook =
                    HystrixPlugins.getInstance().getCommandExecutionHook();
            HystrixEventNotifier eventNotifier =
                    HystrixPlugins.getInstance().getEventNotifier();
            HystrixMetricsPublisher metricsPublisher =
                    HystrixPlugins.getInstance().getMetricsPublisher();
            HystrixPropertiesStrategy propertiesStrategy =
                    HystrixPlugins.getInstance().getPropertiesStrategy();

            // 先重置, 再把我们自定义的配置与原来的配置写回去
            HystrixPlugins.reset();
            HystrixPlugins.getInstance().registerConcurrencyStrategy(target);
            HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
            HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
            HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
            HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
            LOGGER.info("config hystrix concurrency strategy success");
        } catch (Exception ex) {
            LOGGER.error("Failed to register Hystrix Concurrency Strategy: [{}]",
                    ex.getMessage(), ex);
        }
    }
}
