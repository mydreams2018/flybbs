package cn.kungreat.flybbs.social.qq;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQSubscriber> {

    private String appId;
    private static final String  AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";
    private static final String  AUTHENTICATE_ACCESS_URL = "https://graph.qq.com/oauth2.0/token";
    public QQServiceProvider(String clientId, String clientSecret) {
        super(new QQOAuth2Template(clientId,clientSecret,AUTHORIZE_URL,AUTHENTICATE_ACCESS_URL));
        this.appId = clientId;
    }

    @Override
    public QQSubscriber getApi(String accessToken) {
        return new QQSubscriberImpl(accessToken,appId);
    }
}
