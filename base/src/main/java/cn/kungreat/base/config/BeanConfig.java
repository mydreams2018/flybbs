package cn.kungreat.base.config;

import cn.kungreat.base.filter.AnotherImageFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class BeanConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean EKPSSOClientAuthentication() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AnotherImageFilter());
        registration.addUrlPatterns("/register","/user/resetPassword");
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

//    @Bean
//    @ConfigurationProperties(prefix = "cn.kungreat.social")
//    public SocialProperties configBean(){
//        return new SocialProperties();
//    }
    @Bean
    public ClientRegistrationRepository MyClientRegistrationRepository(){
        return new ClientRegistrationRepository(){
            @Override
            public ClientRegistration findByRegistrationId(String registrationId) {
                return MyClientRegistrations.valueOf(registrationId.toUpperCase()).getClientRegistration();
            }
        };
    }

    @Bean
    public DefaultOAuth2AuthorizationRequestResolver defaultOAuth2AuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository){
        return new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository,"/authorization/login");
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