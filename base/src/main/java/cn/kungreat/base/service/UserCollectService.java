package cn.kungreat.base.service;

import cn.kungreat.base.domain.UserCollect;
import cn.kungreat.base.query.UserCollectQuery;
import cn.kungreat.base.vo.QueryResult;

public interface UserCollectService {
    UserCollect selectByPrimaryKey(UserCollect collect);
    int deleteByPrimaryKey(Long id);
    UserCollect sendCollect(UserCollect collect);
    QueryResult queryReport(UserCollectQuery query);
}
