package cn.kungreat.flybbs.mapper;

import cn.kungreat.flybbs.domain.DetailsText;
import cn.kungreat.flybbs.query.DetailsTextQuery;

import java.util.List;

public interface DetailsTextMapper {
    int deleteByPrimaryKey(Long id);

    long insert(DetailsText record);

    DetailsText selectByPrimaryKey(Long id);
    DetailsText selectByPort(DetailsText record);

    Integer selectCount(DetailsTextQuery query);
    List<DetailsText> selectAll(DetailsTextQuery query);

    int updateByPrimaryKey(DetailsText record);
}