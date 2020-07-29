package cn.kungreat.flybbs.mapper;

import cn.kungreat.flybbs.domain.User;
import cn.kungreat.flybbs.query.UserQuery;
import cn.kungreat.flybbs.vo.CategoryTotal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    int insert(User record);

    User selectByPrimaryKey(String account);

    List<User> selectAll(UserQuery query);
    long selectCount(UserQuery query);

    List<User> selectByAccounts(@Param("acts") List<String> acts);
    int updateByPrimaryKey(User record);

    int updateAccumulatePoints(@Param("current") int current, @Param("original") int original, @Param("account") String account);

    int updateImg(@Param("account") String account, @Param("path") String path);

    List<CategoryTotal> selectCategoryTotal(UserQuery query);

    List<String> categoryNames();
}