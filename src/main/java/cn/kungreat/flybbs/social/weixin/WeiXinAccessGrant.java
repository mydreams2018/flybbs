package cn.kungreat.flybbs.social.weixin;

import lombok.Getter;
import lombok.Setter;
import org.springframework.social.oauth2.AccessGrant;

@Setter
@Getter
public class WeiXinAccessGrant extends AccessGrant {

    private String openId;

    public WeiXinAccessGrant(String accessToken) {
        super(accessToken);
    }
}
