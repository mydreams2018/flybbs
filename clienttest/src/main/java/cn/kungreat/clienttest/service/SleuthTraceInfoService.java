package cn.kungreat.clienttest.service;

import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *使用代码更直观的看到 Sleuth 生成的相关跟踪信息
 * */
@Service
public class SleuthTraceInfoService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SleuthTraceInfoService.class);

    /** brave.Tracer 跟踪对象 */
    private final Tracer tracer;

    public SleuthTraceInfoService(Tracer tracer) {
        this.tracer = tracer;
    }

    /**
     * 打印当前的跟踪信息到日志中 十进制输出   获得 traceId  spanId   默认的日志包是转16进制
     * */
    public String logCurrentTraceInfo() {
        LOGGER.info("Sleuth trace id: [{}]", tracer.currentSpan().context().traceId());
        LOGGER.info("Sleuth span id: [{}]", tracer.currentSpan().context().spanId());
        return String.valueOf(tracer.currentSpan().context().traceId());
    }
}
