package cn.kungreat.rocketmq.config;

import cn.kungreat.rocketmq.vo.MyMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 *第二个 RocketMQ 消费者, 指定了消费带有 tag 的消息  实现了过滤
 * */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "kungreat-study-rocketmq",
        consumerGroup = "kun-rocketmq-tag-string",
        selectorExpression = "qinyi" // 根据tag过滤
)
public class RocketMQConsumerTagString implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        MyMessage rocketMessage = JSON.parseObject(message, MyMessage.class);
        log.info("consume message in RocketMQConsumerTagString: [{}]",
                JSON.toJSONString(rocketMessage));
    }
}
