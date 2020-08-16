package cn.kungreat.flybbs.mapper;

import cn.kungreat.flybbs.domain.DetailsText;
import java.util.List;

public interface DetailsTextMapper {
    int deleteByPrimaryKey(Long id);

    long insert(DetailsText record);

    DetailsText selectByPrimaryKey(Long id);

    List<DetailsText> selectAll();

    int updateByPrimaryKey(DetailsText record);
}