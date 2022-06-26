package cn.kungreat.base.service;

import cn.kungreat.base.domain.User;
import cn.kungreat.base.query.UserQuery;
import cn.kungreat.base.vo.ManagerResult;


public interface UserService {

    int insert(User record);

    User selectByPrimaryKey(String account);
    int updateByPrimaryKey(User record);
    int updateImg(String account,String path);
    int updateAccumulatePoints(int number,String account);

    User selectByunique(String account, String alias);

    void rePass(User user);

    void resetPassword(User user);

    ManagerResult getAllUser(UserQuery userQuery);
}
