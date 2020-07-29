package cn.kungreat.flybbs.social.weixin;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WeiXinProperties {
    private String appId;
    private String appSecret;
    private String providerId= "weixin";
}
