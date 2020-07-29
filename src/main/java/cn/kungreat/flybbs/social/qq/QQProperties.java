package cn.kungreat.flybbs.social.qq;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QQProperties {
    private String appId;
    private String appSecret;
    private String providerId= "qq";
}
