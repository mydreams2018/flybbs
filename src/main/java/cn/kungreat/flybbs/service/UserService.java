package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.User;
import cn.kungreat.flybbs.query.UserQuery;
import cn.kungreat.flybbs.vo.CategoryTotal;
import cn.kungreat.flybbs.vo.QueryResult;

import java.util.List;

public interface UserService {

    int insert(User record);

    User selectByPrimaryKey(String account);
    int updateByPrimaryKey(User record);
    int updateImg(String account,String path);
    List<CategoryTotal> selectCategoryTotal(UserQuery query);
    int updateAccumulatePoints(int number,String account);
    QueryResult query(UserQuery query);
}
