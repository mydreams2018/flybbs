package cn.kungreat.flybbs.mapper;

import cn.kungreat.flybbs.domain.DetailsTextBack;
import java.util.List;

public interface DetailsTextMapper {
    int deleteByPrimaryKey(Long id);

    long insert(DetailsTextBack record);

    DetailsTextBack selectByPrimaryKey(Long id);

    List<DetailsTextBack> selectAll();

    int updateByPrimaryKey(DetailsTextBack record);
}