package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.ReportBack;

import java.util.List;

public interface ReportService {
    int deleteByPrimaryKey(Long id);
    long insert(ReportBack record);
    int updateByPrimaryKey(ReportBack record);
    ReportBack selectByPrimaryKey(Long id);
    List<ReportBack> selectAll();
}
