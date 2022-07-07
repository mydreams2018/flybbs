package cn.kungreat.stream.config;

import cn.kungreat.stream.vo.MyMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

/**
 * <h1>使用默认的通信信道实现消息的发送</h1>
 * */
@Slf4j
@EnableBinding(Source.class)
public class DefaultSendService {

    private final Source source;
    private final ObjectMapper objectMapper;

    public DefaultSendService(Source source, ObjectMapper objectMapper) {
        this.source = source;
        this.objectMapper = objectMapper;
    }

    /**
     * <h2>使用默认的输出信道发送消息</h2>
     * */
    public void sendMessage(MyMessage message) throws Exception {
        String _message = objectMapper.writeValueAsString(message);
        log.info("in DefaultSendService send message: [{}]", _message);
        // MessageBuilder 统一消息的编程模型, 是 Stream 组件的重要组成部分之一
        source.output().send(MessageBuilder.withPayload(_message).build());
    }
}
