package cn.kungreat.seata.config;

import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 *Seata 所需要的数据源代理配置类
 * */
@Configuration
public class DataSourceProxyAutoConfiguration {

    private final DataSource dataSource;

    public DataSourceProxyAutoConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * <h2>配置数据源代理, 用于 Seata 全局事务回滚</h2>
     * before image + after image -> undo_log
     * */
    @Primary
    @Bean("dataSource")
    public DataSource dataSource() {
        return new DataSourceProxy(dataSource);
    }
}
