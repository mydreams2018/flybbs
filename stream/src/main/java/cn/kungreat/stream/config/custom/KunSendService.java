package cn.kungreat.stream.config.custom;

import cn.kungreat.stream.StreamApplication;
import cn.kungreat.stream.vo.MyMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

/**
 * <h1>使用自定义的通信信道 QinyiSource 实现消息的发送</h1>
 * */
@Slf4j
@EnableBinding(KunSource.class)
public class KunSendService {

    private final KunSource kunSource;

    public KunSendService(KunSource kunSource) {
        this.kunSource = kunSource;
    }

    /**
     * <h2>使用自定义的输出信道发送消息</h2>
     * */
    public void sendMessage(MyMessage message) throws JsonProcessingException {
        String _message = StreamApplication.OBJECT_MAP.writeValueAsString(message);
        log.info("in kunSendService send message: [{}]", _message);
        kunSource.kunOutput().send(MessageBuilder.withPayload(_message).build());
    }
}
