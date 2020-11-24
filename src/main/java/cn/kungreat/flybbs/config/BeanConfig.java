package cn.kungreat.flybbs.config;

import cn.kungreat.flybbs.filter.AnotherImageFilter;
import cn.kungreat.flybbs.social.config.SocialProperties;
//import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class BeanConfig {

  /*  @Bean(initMethod = "init",destroyMethod = "close")
    @ConfigurationProperties(prefix = "datasource1")
    public DruidDataSource initDruid(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setValidationQuery("SELECT 1");
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setInitialSize(2);
        druidDataSource.setMinIdle(2);
        druidDataSource.setMaxActive(10);
        druidDataSource.setTimeBetweenLogStatsMillis(60000);
        druidDataSource.setMaxWait(60000);
        //是否缓存preparedStatement，也就是PSCache  官方建议MySQL下建议关闭
//        druidDataSource.setPoolPreparedStatements(false);
//        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        return druidDataSource;
    }*/

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean EKPSSOClientAuthentication() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AnotherImageFilter());
        registration.addUrlPatterns("/register");
        registration.setName("anotherImageFilter");
        return registration;
    }

   /* @Bean   RequestContextListener 可以不用?
    public ServletListenerRegistrationBean<RequestContextListener> requestContextListener(){
        ServletListenerRegistrationBean<RequestContextListener> listener = new ServletListenerRegistrationBean<>();
        listener.setListener(new RequestContextListener());
        listener.setOrder(1);
        return listener;
    }*/

    @Bean
    @ConfigurationProperties(prefix = "cn.kungreat.social")
    public SocialProperties configBean(){
        return new SocialProperties();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //启动创建 一个数据表用来存放token   只能用一次
//		tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}