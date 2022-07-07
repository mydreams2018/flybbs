package cn.kungreat.stream.controller;

import cn.kungreat.stream.config.DefaultSendService;
import cn.kungreat.stream.config.custom.KunSendService;
import cn.kungreat.stream.vo.MyMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>构建消息驱动</h1>
 * */
@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    private final DefaultSendService defaultSendService;
    private final KunSendService kunSendService;

    public MessageController(DefaultSendService defaultSendService,
                             KunSendService kunSendService) {
        this.defaultSendService = defaultSendService;
        this.kunSendService = kunSendService;
    }

    /**
     * <h2>默认信道</h2>
     * */
    @GetMapping("/default")
    public void defaultSend() throws Exception {
        defaultSendService.sendMessage(MyMessage.defaultMessage());
    }

    /**
     * <h2>自定义信道</h2>
     * */
    @GetMapping("/kun")
    public void kunSend() throws JsonProcessingException {
        kunSendService.sendMessage(MyMessage.defaultMessage());
    }
}
