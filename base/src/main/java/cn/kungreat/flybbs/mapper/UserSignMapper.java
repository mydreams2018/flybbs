package cn.kungreat.flybbs.mapper;

import cn.kungreat.flybbs.domain.UserSign;

public interface UserSignMapper {
    int insert(UserSign record);

    UserSign selectByPrimaryKey(String account);

    int updateByPrimaryKey(UserSign record);
}