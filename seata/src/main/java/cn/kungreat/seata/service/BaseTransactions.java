package cn.kungreat.seata.service;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BaseTransactions {
    private final RestTemplate restTemplate;

    public BaseTransactions(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    public void transStart(){

    }
}
