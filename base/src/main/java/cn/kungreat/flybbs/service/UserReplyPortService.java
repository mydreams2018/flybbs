package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.UserReplyPort;

import java.util.List;

public interface UserReplyPortService {

    List<UserReplyPort> selectAll();

    int updateByPrimaryKey();
}
