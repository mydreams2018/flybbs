package cn.kungreat.clienttest.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <h1>自定义异步任务线程池, 异步任务异常捕获处理器 开启 Spring 异步任务支持</h1>
 * */
@EnableAsync
@Configuration
public class AsyncPoolConfig implements AsyncConfigurer {

    /**
     * <h2>将自定义的线程池注入到 Spring 容器中</h2>
     * */
    @Bean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(20);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("kungreat-Async-");
        // 等待所有任务结果候再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //最大等待时间所有任务结果  秒
        executor.setAwaitTerminationSeconds(60);
        // 定义拒绝策略
        executor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        executor.initialize();
        return executor;
    }

    /**
     * <h2>指定系统中的异步任务在出现异常时使用到的处理器</h2>
     * */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

    /**
     * 异步任务异常捕获处理器
     * */
    class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        @Override
        public void handleUncaughtException(Throwable throwable, Method method,
                                            Object... objects) {

        }
    }
}
