package cn.kungreat.clienttest.async;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>异步服务接口实现</h1>
 * */
@Service
@Transactional
public class AsyncServiceImpl implements IAsyncService {

    /**
     * 异步任务需要加上注解, 并指定使用的线程池  还不如自已管理=>线程池
     * */
    @Async("getAsyncExecutor")
    @Override
    public void asyncImportGoods() {
            // ...
    }

}
