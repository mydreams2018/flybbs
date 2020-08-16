package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.Report;
import cn.kungreat.flybbs.query.ReportQuery;
import cn.kungreat.flybbs.vo.QueryResult;

import java.util.List;

public interface ReportService {
    int deleteByPrimaryKey(Long id);
    long insert(Report record);
    int updateByPrimaryKey(Report record);
    Report selectByPrimaryKey(Long id);
    List<Report> selectAll();

    QueryResult queryReport(ReportQuery query);
}
