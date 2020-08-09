package cn.kungreat.flybbs.mapper;

import cn.kungreat.flybbs.domain.ReportBack;
import java.util.List;

public interface ReportMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReportBack record);

    ReportBack selectByPrimaryKey(Long id);

    List<ReportBack> selectAll();

    int updateByPrimaryKey(ReportBack record);
}