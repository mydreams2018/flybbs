package cn.kungreat.flybbs.social.qq;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class QQOAuth2Template extends OAuth2Template {

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.add(new StringHttpMessageConverter());
        return restTemplate;
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String st = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(st,"&");
        String accessToken = StringUtils.substringAfterLast(items[0],"=");
        Long expiresIn = Long.parseLong(StringUtils.substringAfterLast(items[1],"="));
        String refreshToken = StringUtils.substringAfterLast(items[2],"=");
        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }
}
