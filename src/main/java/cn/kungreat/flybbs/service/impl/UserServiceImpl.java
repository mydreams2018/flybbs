package cn.kungreat.flybbs.service.impl;

import cn.kungreat.flybbs.domain.User;
import cn.kungreat.flybbs.mapper.UserMapper;
import cn.kungreat.flybbs.query.UserQuery;
import cn.kungreat.flybbs.service.UserService;
import cn.kungreat.flybbs.vo.CategoryTotal;
import cn.kungreat.flybbs.vo.QueryResult;
import com.alibaba.druid.util.StringUtils;
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
        Assert.isTrue(userMapper.selectByPrimaryKey(record.getAccount())==null,"用户名已经存在");
        record.setRegisterTime(new Date());
        record.setIsVip(false);
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
    public List<CategoryTotal> selectCategoryTotal(UserQuery query) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(manager.contains(name),"没有权限访问");
        return userMapper.selectCategoryTotal(query);
    }

    @Override
    public List<String> categoryNames() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(manager.contains(name),"没有权限访问");
        return userMapper.categoryNames();
    }

    @Override
    public int updateAccumulatePoints(int number, String account) {
        User user = userMapper.selectByPrimaryKey(account);
        int current = user.getAccumulatePoints() + number;
        return userMapper.updateAccumulatePoints(current,user.getAccumulatePoints(),account);
    }

    @Override
    public QueryResult query(UserQuery query) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(manager.contains(name),"没有权限访问");
        long count = userMapper.selectCount(query);
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
}
