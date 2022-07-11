package cn.kungreat.rocketmq.config;

import cn.kungreat.rocketmq.vo.MyMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * <h1>第一个 RocketMQ 消费者</h1>
 * */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = RocketMQProducer.TOPIC,
        consumerGroup = "kun-rocketmq-string"
)
public class RocketMQConsumerString implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        MyMessage rocketMessage = JSON.parseObject(message, MyMessage.class);
        log.info("consume message in RocketMQConsumerString: [{}]",
                JSON.toJSONString(rocketMessage));
    }
}
