package cn.kungreat.clienttest.controller;

import cn.kungreat.clienttest.service.SleuthTraceInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 打印跟踪信息
 * */
@RestController
@RequestMapping("/sleuth")
public class SleuthTraceInfoController {

    private final SleuthTraceInfoService traceInfoService;

    public SleuthTraceInfoController(SleuthTraceInfoService traceInfoService) {
        this.traceInfoService = traceInfoService;
    }

    /**
     *打印日志跟踪信息
     * */
    @GetMapping("/trace-info")
    public String logCurrentTraceInfo() {
       return traceInfoService.logCurrentTraceInfo();
    }
}
