package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.Report;
import cn.kungreat.flybbs.query.ReportQuery;
import cn.kungreat.flybbs.vo.QueryResult;

public interface ReportService {
    int deleteByPrimaryKey(Long id);
    long insert(Report record);
    int updateByPrimaryKey(Report record);
    Report selectByPrimaryKey(Report record);

    QueryResult queryReport(ReportQuery query);

    void incrementNumber(Report port);
    void decrementNumber(Report port);
}
