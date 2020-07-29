package cn.kungreat.flybbs.social.config;

import cn.kungreat.flybbs.social.qq.QQProperties;
import cn.kungreat.flybbs.social.weixin.WeiXinProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SocialProperties {

    private QQProperties qq = new QQProperties();
    private WeiXinProperties weixin = new WeiXinProperties();
}
