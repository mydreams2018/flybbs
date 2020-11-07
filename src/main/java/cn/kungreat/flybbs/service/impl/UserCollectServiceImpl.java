package cn.kungreat.flybbs.service.impl;

import cn.kungreat.flybbs.domain.UserCollect;
import cn.kungreat.flybbs.mapper.UserCollectMapper;
import cn.kungreat.flybbs.service.UserCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class UserCollectServiceImpl implements UserCollectService {
    @Autowired
    private UserCollectMapper userCollectMapper;

    @Override
    public UserCollect selectByPrimaryKey(UserCollect collect) {
        collect.setUserAccount(SecurityContextHolder.getContext().getAuthentication().getName());
        return userCollectMapper.selectByPrimaryKey(collect);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return userCollectMapper.deleteByPrimaryKey(id,SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
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
}
