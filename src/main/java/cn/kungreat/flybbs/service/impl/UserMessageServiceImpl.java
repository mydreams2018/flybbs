package cn.kungreat.flybbs.service.impl;

import cn.kungreat.flybbs.domain.UserMessage;
import cn.kungreat.flybbs.mapper.UserMessageMapper;
import cn.kungreat.flybbs.query.UserMessageQuery;
import cn.kungreat.flybbs.security.LoginUser;
import cn.kungreat.flybbs.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
@Service
public class UserMessageServiceImpl implements UserMessageService {
    @Autowired
    private UserMessageMapper userMessageMapper;

    @Override
    public List<UserMessage> selectAll(UserMessageQuery query) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        query.setAlias(loginUser.getAlias());
        return userMessageMapper.selectAll(query);
    }

    @Override
    public int selectCount(UserMessageQuery query){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        query.setAlias(loginUser.getAlias());
        return userMessageMapper.selectCount(query);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        Assert.isTrue(id != null,"ID异常");
        UserMessage userMessage = userMessageMapper.selectByPrimaryKey(id);
        Assert.isTrue(userMessage != null,"数据异常");
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Assert.isTrue(userMessage.getReceiveAlias().equals(loginUser.getAlias()),"没有权限删除");
        return userMessageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByAccount(UserMessageQuery query) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        query.setAlias(loginUser.getAlias());
        return userMessageMapper.deleteByAccount(query);
    }

    @Override//管理员用
    public int deleteByAll(UserMessageQuery query){
        return userMessageMapper.deleteByAll(query);
    }

    @Override
    public void insertBaych(UserMessage userMessage) {
        userMessageMapper.insertBaych(userMessage);
    }
}
