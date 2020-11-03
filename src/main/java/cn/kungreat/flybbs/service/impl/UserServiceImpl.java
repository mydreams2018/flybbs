package cn.kungreat.flybbs.service.impl;

import cn.kungreat.flybbs.domain.User;
import cn.kungreat.flybbs.mapper.UserMapper;
import cn.kungreat.flybbs.query.UserQuery;
import cn.kungreat.flybbs.service.UserService;
import cn.kungreat.flybbs.util.UserAccumulate;
import cn.kungreat.flybbs.vo.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Value("#{'${user.manager}'.split(',')}")
    private List<String> manager;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public int insert(User record) {
        String s = record.validMessage();
        Assert.isTrue(StringUtils.isEmpty(s),s);
        Assert.isTrue(userMapper.selectByunique(record.getAccount(),record.getAlias())==null,"用户或别名已经存在");
        record.setRegisterTime(new Date());
        Calendar c = Calendar.getInstance();
        record.setRegisterYear(c.get(Calendar.YEAR));
        record.setPassword(bCryptPasswordEncoder.encode(record.getPassword()));
        return userMapper.insert(record);
    }

    @Override
    public User selectByPrimaryKey(String account) {
        return userMapper.selectByPrimaryKey(account);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateImg(String account, String path) {
        return userMapper.updateImg(account,path);
    }

    @Override
    public int updateAccumulatePoints(int number, String account) {
        User user = userMapper.selectByPrimaryKey(account);
        Integer accumulatePoints = user.getAccumulatePoints();
        int current = accumulatePoints + number;
        Assert.isTrue(current >= 0,"飞吻数据不足");
        return userMapper.updateAccumulatePoints(current, accumulatePoints,account, UserAccumulate.countVipLevel(current));
    }

    @Override
    public QueryResult query(UserQuery query) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(manager.contains(name),"没有权限访问");
        Integer count = userMapper.selectCount(query);
        List list  = Collections.emptyList();
        if (count >  0){
            list = userMapper.selectAll(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult  result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }

    @Override
    public User selectByunique(String account, String alias) {
        return userMapper.selectByunique(account,alias);
    }
}
