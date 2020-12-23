package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.User;
import cn.kungreat.flybbs.query.UserQuery;
import cn.kungreat.flybbs.vo.QueryResult;

public interface UserService {

    int insert(User record);

    User selectByPrimaryKey(String account);
    int updateByPrimaryKey(User record);
    int updateImg(String account,String path);
    int updateAccumulatePoints(int number,String account);
    QueryResult query(UserQuery query);

    User selectByunique(String account, String alias);

    void rePass(User user);

    void resetPassword(User user);
}
