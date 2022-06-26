package cn.kungreat.base.mapper;

import cn.kungreat.base.domain.UserCollect;
import cn.kungreat.base.query.UserCollectQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCollectMapper {
    int deleteByPrimaryKey(@Param("id") Long id, @Param("account") String account);

    int insert(UserCollect record);

    UserCollect selectByPrimaryKey(UserCollect collect);

    List<UserCollect> selectAll(UserCollectQuery query);

    Integer selectCount(UserCollectQuery query);
}