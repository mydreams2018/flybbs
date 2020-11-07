package cn.kungreat.flybbs.mapper;

import cn.kungreat.flybbs.domain.UserCollect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCollectMapper {
    int deleteByPrimaryKey(@Param("id") Long id, @Param("account") String account);

    int insert(UserCollect record);

    UserCollect selectByPrimaryKey(UserCollect collect);

    List<UserCollect> selectAll();
}