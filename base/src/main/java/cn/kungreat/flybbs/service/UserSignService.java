package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.UserSign;

public interface UserSignService {
    int insert(UserSign record);

    UserSign selectByPrimaryKey();

    int updateByPrimaryKey(UserSign record);

    void signOn();
}
