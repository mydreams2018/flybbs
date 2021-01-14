package cn.kungreat.flybbs.service.impl;

import cn.kungreat.flybbs.domain.UserCollect;
import cn.kungreat.flybbs.mapper.UserCollectMapper;
import cn.kungreat.flybbs.query.UserCollectQuery;
import cn.kungreat.flybbs.service.UserCollectService;
import cn.kungreat.flybbs.vo.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class UserCollectServiceImpl implements UserCollectService {
    @Autowired
    private UserCollectMapper userCollectMapper;

    @Override
    public UserCollect selectByPrimaryKey(UserCollect collect) {
        collect.setUserAccount(SecurityContextHolder.getContext().getAuthentication().getName());
        return userCollectMapper.selectByPrimaryKey(collect);
    }

    @Transactional
    public int deleteByPrimaryKey(Long id) {
        return userCollectMapper.deleteByPrimaryKey(id,SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Transactional
    public UserCollect sendCollect(UserCollect collect){
        if(collect.getId() == null){
            Assert.isTrue(collect.getClassId() != null && collect.getPortId() != null,"ID异常");
            collect.setUserAccount(SecurityContextHolder.getContext().getAuthentication().getName());
            collect.setCollectTime(new Date());
            userCollectMapper.insert(collect);
            return collect;
        }
        deleteByPrimaryKey(collect.getId());
        return null;
    }

    @Override
    public QueryResult queryReport(UserCollectQuery query){
        Assert.isTrue(query.getClassId() != null,"ID异常");
        query.setUserAccount(SecurityContextHolder.getContext().getAuthentication().getName());
        Integer count = userCollectMapper.selectCount(query);
        List list  = Collections.emptyList();
        if (count >  0){
            list = userCollectMapper.selectAll(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult  result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }
}
