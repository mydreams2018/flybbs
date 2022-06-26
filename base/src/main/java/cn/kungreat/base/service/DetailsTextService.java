package cn.kungreat.base.service;

import cn.kungreat.base.domain.DetailsText;
import cn.kungreat.base.domain.Report;
import cn.kungreat.base.query.DetailsTextQuery;
import cn.kungreat.base.vo.QueryResult;

import java.util.List;

public interface DetailsTextService {
    QueryResult queryReport(DetailsTextQuery query);
    long insert(DetailsText record);

    void likeAccount(DetailsTextQuery query);

    int deleteReplyPort(DetailsTextQuery query);

    void acceptReply(DetailsTextQuery query);

    DetailsText selectByPrimaryKey(DetailsTextQuery query);

    void updateByPrimaryKey(DetailsTextQuery query);

    List<Report> lastReplyPort(Report query);
}
