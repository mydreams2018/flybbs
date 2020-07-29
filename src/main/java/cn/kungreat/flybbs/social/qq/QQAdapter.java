package cn.kungreat.flybbs.social.qq;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

public class QQAdapter implements ApiAdapter<QQSubscriber> {

    @Override
    public boolean test(QQSubscriber api) {
        return true;
    }

    @Override
    public void setConnectionValues(QQSubscriber api, ConnectionValues values) {
        QQSubscriberInfo subscriberInfo = api.getSubscriberInfo();
        values.setDisplayName(subscriberInfo.getNickname());
        values.setImageUrl(subscriberInfo.getFigureurl_qq_1());
        values.setProfileUrl(null); //个人主页
        values.setProviderUserId(subscriberInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQSubscriber api) {
        return null;
    }

    @Override
    public void updateStatus(QQSubscriber api, String message) {

    }
}
