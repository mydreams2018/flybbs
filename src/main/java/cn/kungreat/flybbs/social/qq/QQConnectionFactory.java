package cn.kungreat.flybbs.social.qq;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

public class QQConnectionFactory extends OAuth2ConnectionFactory<QQSubscriber> {

    public QQConnectionFactory(String providerId, String clientId, String clientSecret) {
        super(providerId, new QQServiceProvider(clientId,clientSecret), new QQAdapter());
    }
}
