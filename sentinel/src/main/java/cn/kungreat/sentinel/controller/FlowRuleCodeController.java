package cn.kungreat.sentinel.controller;

import cn.kungreat.sentinel.blockhandler.KunBlockHandler;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>流控规则硬编码的 Controller</h1>
 * */
@Slf4j
@RestController
@RequestMapping("/code")
public class FlowRuleCodeController {

    /**
     * 初始化流控规则
     * */
    @PostConstruct
    public void init() {
        // 流控规则集合
        List<FlowRule> flowRules = new ArrayList<>();
        // 创建流控规则
        FlowRule flowRule = new FlowRule();
        // 设置流控规则 QPS, 限流阈值类型 (QPS, 并发线程数)
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 流量控制手段
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        // 设置受保护的资源
        flowRule.setResource("flowRuleCode");
        //设置受保护的资源的阈值  1秒 1次 qps
        flowRule.setCount(1);
        flowRules.add(flowRule);
        // 加载配置好的规则
        FlowRuleManager.loadRules(flowRules);
    }

    /**
     *采用硬编码限流规则的 Controller 方法    exceptionsToIgnore 忽略的异常 让它往上抛
     * */
    @SentinelResource(value = "flowRuleCode", blockHandler = "kunHandleBlockException",
            blockHandlerClass = KunBlockHandler.class,
            exceptionsToIgnore = {NullPointerException.class}
    )
    @GetMapping("/flow-rule")
    public String flowRuleCode(){
        log.info("request flowRuleCode");
        return "request flowRuleCode";
    }

}
