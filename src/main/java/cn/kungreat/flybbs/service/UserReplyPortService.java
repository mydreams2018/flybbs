package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.UserReplyPort;

import java.util.List;

public interface UserReplyPortService {
    int insert(UserReplyPort record);

    List<UserReplyPort> selectAll();

    int updateByPrimaryKey(UserReplyPort record);
}
