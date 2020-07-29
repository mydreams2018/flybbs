package cn.kungreat.flybbs.social.config;

import cn.kungreat.flybbs.social.MyConnectionSignUp;
import cn.kungreat.flybbs.social.qq.QQConnectionFactory;
import cn.kungreat.flybbs.social.qq.QQProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SocialProperties socialProperties;
    @Autowired
    private MyConnectionSignUp myConnectionSignUp;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        Jdbc8UsersConnectionRepository jdbcUsersConnectionRepository = new Jdbc8UsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        if(myConnectionSignUp != null){
            jdbcUsersConnectionRepository.setConnectionSignUp(myConnectionSignUp);
        }
        return jdbcUsersConnectionRepository;
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        QQProperties qq = socialProperties.getQq();
        QQConnectionFactory qqConnectionFactory = new QQConnectionFactory(qq.getProviderId(),qq.getAppId(),qq.getAppSecret());
        connectionFactoryConfigurer.addConnectionFactory(qqConnectionFactory);
    }

    @Bean
    public SpringSocialConfigurer socialConfigurer(){
        MySpringSocialConfigurer mySpringSocialConfigurer = new MySpringSocialConfigurer();
        return mySpringSocialConfigurer;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator factoryLocator){
        return new ProviderSignInUtils(factoryLocator,getUsersConnectionRepository(factoryLocator));
    }

    @Override
    public UserIdSource getUserIdSource() {
        return  new AuthenticationNameUserIdSource();
    }
}
