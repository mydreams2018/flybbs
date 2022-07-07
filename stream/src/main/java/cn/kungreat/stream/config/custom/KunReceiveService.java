package cn.kungreat.stream.config.custom;

import cn.kungreat.stream.StreamApplication;
import cn.kungreat.stream.vo.MyMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 *使用自定义的输入信道实现消息的接收
 * */
@Slf4j
@EnableBinding(KunSink.class)
public class KunReceiveService {

    /** 使用自定义的输入信道接收消息 */
    @StreamListener(KunSink.INPUT)
    public void receiveMessage(@Payload Object payload) throws JsonProcessingException {
        log.info("in kunReceiveService consume message start");
        MyMessage qinyiMessage = StreamApplication.OBJECT_MAP.readValue(payload.toString(), MyMessage.class);
        log.info("in kunReceiveService consume message success: [{}]",
                StreamApplication.OBJECT_MAP.writeValueAsString(qinyiMessage));
    }
}
