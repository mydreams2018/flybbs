package cn.kungreat.base.service;

import cn.kungreat.base.domain.UserSign;

public interface UserSignService {
    int insert(UserSign record);

    UserSign selectByPrimaryKey();

    int updateByPrimaryKey(UserSign record);

    void signOn();
}
