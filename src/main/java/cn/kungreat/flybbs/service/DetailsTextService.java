package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.DetailsText;
import cn.kungreat.flybbs.query.DetailsTextQuery;
import cn.kungreat.flybbs.vo.QueryResult;

public interface DetailsTextService {
    QueryResult queryReport(DetailsTextQuery query);
    long insert(DetailsText record);

    void likeAccount(DetailsTextQuery query);
}
