package cn.kungreat.base.service;

import cn.kungreat.base.domain.Oauth2User;
import cn.kungreat.base.domain.User;

public interface Oauth2UserService {
    int insert(Oauth2User record,User user);

    User selectByPrimaryKey(String openId);

    int updateByPrimaryKey(Oauth2User record);
}
