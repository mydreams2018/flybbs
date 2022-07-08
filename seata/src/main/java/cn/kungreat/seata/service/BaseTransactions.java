package cn.kungreat.seata.service;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

@Service
public class BaseTransactions {

    @GlobalTransactional(rollbackFor = Exception.class)
    public void transStart(){
//        ....   这时就调用自已的方法实现 调用别的微服务的事务  会有 seata 的实现
    }
}
