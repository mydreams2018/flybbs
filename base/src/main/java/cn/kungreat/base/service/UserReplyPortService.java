package cn.kungreat.base.service;

import cn.kungreat.base.domain.UserReplyPort;

import java.util.List;

public interface UserReplyPortService {

    List<UserReplyPort> selectAll();

    int updateByPrimaryKey();
}
