package cn.kungreat.flybbs.social.qq;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QQSubscriberInfo {
    private String openId;
    private String ret;
    private String msg;
    private String nickname;
    private String gender;
    private String figureurl_qq_1; //大小为40×40像素的QQ头像URL
}
