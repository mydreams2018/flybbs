package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.DetailsText;
import cn.kungreat.flybbs.domain.Report;
import cn.kungreat.flybbs.query.DetailsTextQuery;
import cn.kungreat.flybbs.vo.QueryResult;

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
