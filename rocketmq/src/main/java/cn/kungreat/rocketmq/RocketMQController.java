package cn.kungreat.rocketmq;

import cn.kungreat.rocketmq.config.RocketMQProducer;
import cn.kungreat.rocketmq.vo.MyMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>SpringBoot 集成 RocketMQ</h1>
 * */
@Slf4j
@RestController
public class RocketMQController {

    private static final MyMessage RocketMQMessage = new MyMessage(
            1,
            "Study-RocketMQ-In-SpringBoot");

    private final RocketMQProducer rocketMQProducer;

    public RocketMQController(RocketMQProducer rocketMQProducer) {
        this.rocketMQProducer = rocketMQProducer;
    }

    @GetMapping("/message-with-value")
    public void sendMessageWithValue() {
        rocketMQProducer.sendMessageWithValue(JSON.toJSONString(RocketMQMessage));
    }

    @GetMapping("/message-with-key")
    public void sendMessageWithKey() {
        rocketMQProducer.sendMessageWithKey("Qinyi", JSON.toJSONString(RocketMQMessage));
    }

    @GetMapping("/message-with-tag")
    public void sendMessageWithTag() {
        rocketMQProducer.sendMessageWithTag("qinyi",
                JSON.toJSONString(RocketMQMessage));
    }

    @GetMapping("/message-with-all")
    public void sendMessageWithAll() {
        rocketMQProducer.sendMessageWithAll("kun", "qinyi",
                JSON.toJSONString(RocketMQMessage));
    }
}
