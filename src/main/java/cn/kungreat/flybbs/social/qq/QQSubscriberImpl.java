package cn.kungreat.flybbs.social.qq;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

public class QQSubscriberImpl extends AbstractOAuth2ApiBinding implements QQSubscriber {

    private static final String GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    private static final String GET_SUBSCRIBERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    private String appId;
    private String openId;

    public QQSubscriberImpl(String accessToken, String appId){
        // token 要求当成参数传送 默认是放在头信息里
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(GET_OPENID,accessToken);
        String op = getRestTemplate().getForObject(url, String.class);
        String openId = StringUtils.substringBetween(op, "\"openid\":\"", "\"}");
        this.openId = openId;
    }

    @Override
    public QQSubscriberInfo getSubscriberInfo() {
        String url = String.format(GET_SUBSCRIBERINFO,appId,openId);
        String forObject = getRestTemplate().getForObject(url, String.class);
        QQSubscriberInfo subscriber = JSON.parseObject(forObject,QQSubscriberInfo.class);
        subscriber.setOpenId(openId);
        return subscriber;
    }
}
