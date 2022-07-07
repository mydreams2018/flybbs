package cn.kungreat.stream.config;

import cn.kungreat.stream.StreamApplication;
import cn.kungreat.stream.vo.MyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * <h1>使用默认的信道实现消息的接收</h1>
 * */
@Slf4j
@EnableBinding(Sink.class)
public class DefaultReceiveService {

    /**
     * 使用默认的输入信道接收消息
     * */
    @StreamListener(Sink.INPUT)
    public void receiveMessage(Object payload) throws Exception {
        log.info("in DefaultReceiveService consume message start");
        MyMessage qinyiMessage = StreamApplication.OBJECT_MAP.readValue(
                payload.toString(), MyMessage.class);
        //消费消息
        log.info("in DefaultReceiveService consume message success: [{}]",
                StreamApplication.OBJECT_MAP.writeValueAsString(qinyiMessage));
    }
}
