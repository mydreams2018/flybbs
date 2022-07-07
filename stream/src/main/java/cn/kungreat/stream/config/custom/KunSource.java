package cn.kungreat.stream.config.custom;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 *自定义输出信道
 * */
public interface KunSource {

    String OUTPUT = "kunOutput";

    /** 输出信道的名称是 kunOutput, 需要使用 Stream 绑定器在 yml 文件中声明 */
    @Output(KunSource.OUTPUT)
    MessageChannel kunOutput();
}
