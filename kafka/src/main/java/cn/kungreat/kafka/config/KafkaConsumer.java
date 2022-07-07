package cn.kungreat.kafka.config;

import cn.kungreat.kafka.vo.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <h1>Kafka 消费者</h1>
 * */
@Slf4j
@Component
public class KafkaConsumer {

    public final static ObjectMapper mapper = new ObjectMapper();

    /**
     * <h2>监听 Kafka 消息并消费</h2>
     * */
    @KafkaListener(topics = {"kungreat-springboot","flybbs"}, groupId = "springboot-kafka")
    public void listener01(ConsumerRecord<String,String> record) throws Exception {
        String key = record.key();
        String value = record.value();
        Message kafkaMessage = mapper.readValue(value,Message.class);
        log.info("in listener01 consume kafka message: [{}], [{}]",
                key, mapper.writeValueAsString(kafkaMessage));
    }

    /**
     * <h2>监听 Kafka 消息并消费</h2>
     * */
    @KafkaListener(topics = {"kungreat-springboot"}, groupId="springboot-kafka-2")
    public void listener02(ConsumerRecord<?, ?> record) throws Exception {
        Optional<?> _kafkaMessage = Optional.ofNullable(record.value());
        if (_kafkaMessage.isPresent()) {
            Object message = _kafkaMessage.get();
            Message kafkaMessage = mapper.readValue(message.toString(),
                    Message.class);
            log.info("in listener02 consume kafka message: [{}]",
                    mapper.writeValueAsString(kafkaMessage));
        }
    }
}
