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
        UserMessageQuery qs = new UserMessageQuery();
        qs.setAlias(loginUser.getAlias());
        return userMessageMapper.selectAll(qs);
    }

    @Override
    public int selectCount(UserMessageQuery query){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserMessageQuery qs = new UserMessageQuery();
        qs.setAlias(loginUser.getAlias());
        return userMessageMapper.selectCount(qs);
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
        UserMessageQuery qs = new UserMessageQuery();
        qs.setAlias(loginUser.getAlias());
        return userMessageMapper.deleteByAccount(qs);
    }

    @Override//管理员用
    public int deleteByAll(UserMessageQuery query){
        return userMessageMapper.deleteByAll(query);
    }

    @Override
    public int insert(UserMessage record) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        record.setSrcAlias(loginUser.getAlias());
        return userMessageMapper.insert(record);
    }
}
