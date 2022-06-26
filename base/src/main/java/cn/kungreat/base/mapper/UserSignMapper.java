package cn.kungreat.base.mapper;

import cn.kungreat.base.domain.UserSign;

public interface UserSignMapper {
    int insert(UserSign record);

    UserSign selectByPrimaryKey(String account);

    int updateByPrimaryKey(UserSign record);
}