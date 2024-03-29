package cn.kungreat.base.service.impl;

import cn.kungreat.base.domain.UserCollect;
import cn.kungreat.base.mapper.UserCollectMapper;
import cn.kungreat.base.query.UserCollectQuery;
import cn.kungreat.base.service.UserCollectService;
import cn.kungreat.common.vo.QueryResult;
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
            Assert.isTrue(collect.getPortTitle()!=null && collect.getPortTitle().length()>0,"贴子名称异常");
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