package cn.kungreat.stream.partition;

import cn.kungreat.stream.StreamApplication;
import cn.kungreat.stream.vo.MyMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * <h1>自定义从 Message 中提取 partition key 的策略</h1>
 * */
@Slf4j
@Component
public class KunPartitionKeyExtractorStrategy implements PartitionKeyExtractorStrategy {

    @Override
    public Object extractKey(Message<?> message) {
        MyMessage kunMessage = null;
        try {
            kunMessage = StreamApplication.OBJECT_MAP.readValue(
                    message.getPayload().toString(), MyMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //自定义提取key
        String key = kunMessage.getProjectName();
        log.info("SpringCloud Stream kun Partition Key: [{}]", key);
        return key;
    }
}
