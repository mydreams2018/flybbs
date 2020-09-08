package cn.kungreat.flybbs.mapper;

import cn.kungreat.flybbs.domain.Report;
import cn.kungreat.flybbs.query.ReportQuery;

import java.util.List;

public interface ReportMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Report record);

    Report selectByPrimaryKey(Report record);
    int updateByPrimaryKey(Report record);

    Integer selectCount(ReportQuery query);
    List<Report> selectAll(ReportQuery query);

    void updateReplyNumber(Report port);
}