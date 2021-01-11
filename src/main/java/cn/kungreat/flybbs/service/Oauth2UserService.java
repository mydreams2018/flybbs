package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.Oauth2User;
import cn.kungreat.flybbs.domain.User;

public interface Oauth2UserService {
    int insert(Oauth2User record,User user);

    User selectByPrimaryKey(String openId);

    int updateByPrimaryKey(Oauth2User record);
}
