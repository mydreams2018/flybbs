package cn.kungreat.flybbs.social.weixin;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WeiXinSubscriberInfo {
    private String openId;
    private String ret;
    private String msg;
    private String nickname;
    private String gender;
    private String figureurl_qq_1; //大小为40×40像素的QQ头像URL
}
