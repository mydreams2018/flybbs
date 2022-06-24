package cn.kungreat.flybbs.mapper;

import cn.kungreat.flybbs.domain.DetailsText;
import cn.kungreat.flybbs.domain.Report;
import cn.kungreat.flybbs.query.DetailsTextQuery;

import java.util.List;

public interface DetailsTextMapper {
    int deleteByPrimaryKey(DetailsTextQuery query);

    long insert(DetailsText record);

    DetailsText selectByPrimaryKey(DetailsTextQuery query);
    DetailsText selectByPort(DetailsText record);

    Integer selectCount(DetailsTextQuery query);
    List<DetailsText> selectAll(DetailsTextQuery query);
    List<DetailsText> selectChildAnswer(DetailsTextQuery query);
    int updateByPrimaryKey(DetailsTextQuery query);

    DetailsText selectLikeAccount(DetailsTextQuery query);

    int updateLikeAccount(DetailsTextQuery query);
    int updateAdoption(DetailsTextQuery query);

    int updateByPortId(DetailsText detailsText);

    List<Report> lastReplyPort(Report query);
}