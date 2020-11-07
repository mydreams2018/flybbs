package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.UserCollect;

public interface UserCollectService {
    UserCollect selectByPrimaryKey(UserCollect collect);
    int deleteByPrimaryKey(Long id);
    UserCollect sendCollect(UserCollect collect);
}
