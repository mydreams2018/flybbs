package cn.kungreat.base.service;

import cn.kungreat.base.domain.Report;
import cn.kungreat.base.query.ReportQuery;
import cn.kungreat.base.vo.QueryResult;

import java.util.List;

public interface ReportService {
    int deleteByPrimaryKey(Long id);
    long insert(Report record);
    int updateByPrimaryKey(Report record);
    int updateBystate(Report record);
    Report selectByPrimaryKey(Report record);

    QueryResult queryReport(ReportQuery query);

    void incrementNumber(Report port);
    void decrementNumber(Report port);

    List<Report> lastSendPort(Report query);

    QueryResult myQueryReport(ReportQuery query);
}
