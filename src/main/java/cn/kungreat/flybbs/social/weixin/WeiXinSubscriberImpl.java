package cn.kungreat.flybbs.social.weixin;


import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class WeiXinSubscriberImpl extends AbstractOAuth2ApiBinding implements WeiXinSubscriber {

    private static final String GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo?openid=";

    public WeiXinSubscriberImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(getFormMessageConverter());
        messageConverters.add(getJsonMessageConverter());
        messageConverters.add(getByteArrayMessageConverter());
        return messageConverters;
    }

    @Override
    public WeiXinSubscriberInfo getSubscriberInfo(String openId) {
        String url = GET_USERINFO + openId;
        WeiXinSubscriberInfo weiXinSubscriberInfo = null;
        try {
            weiXinSubscriberInfo = getRestTemplate().getForObject(url,WeiXinSubscriberInfo.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return weiXinSubscriberInfo;
    }
}
