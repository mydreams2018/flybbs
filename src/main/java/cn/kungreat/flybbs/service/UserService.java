package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.User;

public interface UserService {

    int insert(User record);

    User selectByPrimaryKey(String account);
    int updateByPrimaryKey(User record);
    int updateImg(String account,String path);
    int updateAccumulatePoints(int number,String account);

    User selectByunique(String account, String alias);

    void rePass(User user);

    void resetPassword(User user);
}
