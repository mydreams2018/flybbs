package cn.kungreat.flybbs.social.weixin;

public interface WeiXinSubscriber {
    WeiXinSubscriberInfo getSubscriberInfo(String openId);
}
