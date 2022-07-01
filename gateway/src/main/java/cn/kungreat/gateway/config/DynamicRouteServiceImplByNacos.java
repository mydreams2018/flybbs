package cn.kungreat.gateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * <h1>通过 nacos 下发动态路由配置, 监听 Nacos 中路由配置变更</h1>
 * */
@Component
@DependsOn({"gatewayConfig"})
public class DynamicRouteServiceImplByNacos {
    private final static Logger LOGGER = LoggerFactory.getLogger(DynamicRouteServiceImplByNacos.class);

    /** Nacos 配置服务 */
    private ConfigService configService;
    private final DynamicRouteServiceImpl dynamicRouteService;

    public DynamicRouteServiceImplByNacos(DynamicRouteServiceImpl dynamicRouteService) {
        this.dynamicRouteService = dynamicRouteService;
    }

    /**
     *Bean 在容器中构造完成之后会执行 init 方法
     * */
    @PostConstruct
    public void init() {
        LOGGER.info("gateway route init....");
        try {
            // 初始化 Nacos 配置客户端
            this.configService = initConfigService();
            if (this.configService == null) {
                LOGGER.error("init config service fail");
                return;
            }
            // 通过 Nacos Config 并指定路由配置路径去获取路由配置
            String configInfo = this.configService.getConfig(
                    GatewayConfig.NACOS_ROUTE_DATA_ID,
                    GatewayConfig.NACOS_ROUTE_GROUP,
                    GatewayConfig.DEFAULT_TIMEOUT
            );
            LOGGER.info("get current gateway config [{}]", configInfo);
            List<RouteDefinition> definitionList = JSON.parseArray(configInfo,RouteDefinition.class);
            if (definitionList != null && definitionList.size() > 0) {
                dynamicRouteService.updateList(definitionList);
            }
        } catch (Exception ex) {
            LOGGER.error("gateway route init has some error: [{}]", ex.getMessage(), ex);
        }
        // 设置监听器
        dynamicRouteByNacosListener(GatewayConfig.NACOS_ROUTE_DATA_ID, GatewayConfig.NACOS_ROUTE_GROUP);
    }

    /**
     * <h2>初始化 Nacos Config</h2>
     * */
    private ConfigService initConfigService() {
        try {
            Properties properties = new Properties();
            properties.setProperty("serverAddr", GatewayConfig.NACOS_SERVER_ADDR);
            properties.setProperty("namespace", GatewayConfig.NACOS_NAMESPACE);
            return configService = NacosFactory.createConfigService(properties);
        } catch (Exception ex) {
            LOGGER.error("init gateway nacos config error: [{}]", ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * <h2>监听 Nacos 下发的动态路由配置</h2>
     * */
    private void dynamicRouteByNacosListener(String dataId, String group) {

        try {
            // 给 Nacos Config 客户端增加一个监听器
            configService.addListener(dataId, group, new Listener() {
                /**
                 * 是否自己提供线程池执行操作 返回null
                 * */
                @Override
                public Executor getExecutor() {
                    return null;
                }
                /**
                 * @param configInfo Nacos 中最新的配置定义
                 * */
                @Override
                public void receiveConfigInfo(String configInfo) {
                    LOGGER.info("start to update config: [{}]", configInfo);
                    List<RouteDefinition> definitionList =  JSON.parseArray(configInfo, RouteDefinition.class);
                    if(definitionList != null && definitionList.size() > 0){
                        dynamicRouteService.updateList(definitionList);
                    }
                }
            });
        } catch (NacosException ex) {
            LOGGER.error("dynamic update gateway config error: [{}]", ex.getMessage(), ex);
        }
    }
}
