package cn.kungreat.rocketmq.config;

import cn.kungreat.rocketmq.vo.MyMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * <h1>第四个, RocketMQ 消费者, 指定消费带有 tag 的消息, 且消费的是 Java Pojo</h1>
 * */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = RocketMQProducer.TOPIC,
        consumerGroup = "kun-rocketmq-tag-object",
        selectorExpression = "qinyi"    //根据tag做过滤
)
public class RocketMQConsumerObject implements RocketMQListener<MyMessage> {

    @Override
    public void onMessage(MyMessage message) {
        log.info("consume message in RocketMQConsumerObject: [{}]",
                JSON.toJSONString(message));
    }
}
