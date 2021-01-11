package cn.kungreat.flybbs.mapper;

import cn.kungreat.flybbs.domain.Oauth2User;

public interface Oauth2UserMapper {

    int insert(Oauth2User record);

    Oauth2User selectByPrimaryKey(String openId);

    int updateByPrimaryKey(Oauth2User record);
}