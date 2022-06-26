package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.UserCollect;
import cn.kungreat.flybbs.query.UserCollectQuery;
import cn.kungreat.flybbs.vo.QueryResult;

public interface UserCollectService {
    UserCollect selectByPrimaryKey(UserCollect collect);
    int deleteByPrimaryKey(Long id);
    UserCollect sendCollect(UserCollect collect);
    QueryResult queryReport(UserCollectQuery query);
}
