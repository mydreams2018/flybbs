package cn.kungreat.flybbs.service.impl;

import cn.kungreat.flybbs.domain.DetailsText;
import cn.kungreat.flybbs.domain.Report;
import cn.kungreat.flybbs.domain.User;
import cn.kungreat.flybbs.domain.UserMessage;
import cn.kungreat.flybbs.mapper.DetailsTextMapper;
import cn.kungreat.flybbs.mapper.ReportMapper;
import cn.kungreat.flybbs.mapper.UserMapper;
import cn.kungreat.flybbs.query.DetailsTextQuery;
import cn.kungreat.flybbs.query.UserMessageQuery;
import cn.kungreat.flybbs.security.LoginUser;
import cn.kungreat.flybbs.service.DetailsTextService;
import cn.kungreat.flybbs.service.ReportService;
import cn.kungreat.flybbs.service.UserMessageService;
import cn.kungreat.flybbs.service.UserReplyPortService;
import cn.kungreat.flybbs.util.UserAccumulate;
import cn.kungreat.flybbs.vo.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class DetailsTextServiceImpl implements DetailsTextService {
    @Autowired
    private DetailsTextMapper detailsTextMapper;
    @Autowired
    private ReportService reportService;
    @Autowired
    private ReportMapper reportMapper;
    @Value("${port.isauth}")
    private Integer portIsauth;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserReplyPortService userReplyPortService;
    @Autowired
    private UserMessageService userMessageService;
    @Override
    public QueryResult queryReport(DetailsTextQuery query) {
        Assert.isTrue(query.getClassId()!=null&&query.getClassId()>=1&&query.getClassId()<5,"类型ID异常");
        Assert.isTrue(query.getPortId()!=null,"贴子ID异常");
        query.setPortIsauth(1);
        Integer count = detailsTextMapper.selectCount(query);
        List list  = Collections.emptyList();
        if (count >  0){
            list = detailsTextMapper.selectAll(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }

    @Transactional
    public long insert(DetailsText record) {
        String s = record.validMessage();
        Assert.isTrue(StringUtils.isEmpty(s),s);
        record.setIsPort(false);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        record.setUserAccount(name);
        Date date = new Date();
        record.setCreateData(date);
        record.setAuthFlag(portIsauth.equals(1));
        detailsTextMapper.insert(record);
        Report report = new Report();
        report.setClassId(record.getClassId());
        report.setId(record.getPortId());
        reportService.incrementNumber(report);
        userReplyPortService.updateByPrimaryKey();//用户回贴统计
        Set<String> set = UserAccumulate.hasReplyAlias(record.getDetailsText());
        if(set.size() > 0){
            UserMessage userMessage = new UserMessage();
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userMessage.setSrcAlias(loginUser.getAlias());
            userMessage.setAuthFlag(portIsauth.equals(1));
            userMessage.setClassId(record.getClassId());
            userMessage.setPortId(record.getPortId());
            userMessage.setDetailsId(record.getId());
            userMessage.setReceiveDate(date);
            userMessage.setReceiveAliasSet(set);
            userMessageService.insertBaych(userMessage);
        }
        return 1;
    }

    @Transactional
    public void likeAccount(DetailsTextQuery query) {
        Assert.isTrue(query.getClassId()!=null&&query.getClassId()>=1&&query.getClassId()<5,"类型ID异常");
        Assert.isTrue(query.getId()!=null,"ID异常");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        DetailsText s = detailsTextMapper.selectLikeAccount(query);
        Assert.isTrue(s!=null,"贴子异常");
        Assert.isTrue(s.getLikeAccount()==null||!s.getLikeAccount().contains(name),"已经点过赞了");
        query.setLikeAccount(s.getLikeAccount()+","+name);
        query.setLikeNumber(s.getLikeNumber());
        detailsTextMapper.updateLikeAccount(query);
    }

    @Transactional
    public int deleteReplyPort(DetailsTextQuery query) {
        Assert.isTrue(query.getClassId()!=null&&query.getClassId()>=1&&query.getClassId()<5,"类型ID异常");
        Assert.isTrue(query.getId()!=null,"ID异常");
        query.setPortIsauth(1);
        DetailsText detailsText = detailsTextMapper.selectByPrimaryKey(query);
        Assert.isTrue(detailsText!=null,"贴子异常");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.selectByPrimaryKey(name);
        Assert.isTrue(user.getIsManager()==1||detailsText.getUserAccount().equals(name),"没有删除权限");
        Report port = new Report();
        port.setClassId(query.getClassId());
        port.setId(detailsText.getPortId());
        reportService.decrementNumber(port);
        if(detailsText.getIsAdoption()){
            port.setPortState("未结");
            reportService.updateBystate(port);
            Report report = reportMapper.selectById(port);
            if(report.getExperience() != null && report.getExperience() > 0){
                if(detailsText.getUserAccount().equals(name)){
                    userMapper.updateAccumulatePoints(user.getAccumulatePoints()-report.getExperience(),user.getAccumulatePoints(),name, UserAccumulate.countVipLevel(user.getAccumulatePoints()-report.getExperience()));
                }else{
                    User user1 = userMapper.selectByPrimaryKey(detailsText.getUserAccount());
                    userMapper.updateAccumulatePoints(user1.getAccumulatePoints()-report.getExperience(),user1.getAccumulatePoints(),detailsText.getUserAccount(),UserAccumulate.countVipLevel(user1.getAccumulatePoints()-report.getExperience()));
                }
            }
        }
        UserMessageQuery messageQuery = new UserMessageQuery();
        messageQuery.setClassId(query.getClassId());
        messageQuery.setDetailsId(query.getId());
        userMessageService.deleteByAll(messageQuery);
        return detailsTextMapper.deleteByPrimaryKey(query);
    }

    @Transactional
    public void acceptReply(DetailsTextQuery query) {
        Assert.isTrue(query.getClassId()!=null&&query.getClassId()>=1&&query.getClassId()<5,"类型ID异常");
        Assert.isTrue(query.getId()!=null,"ID异常");
        query.setPortIsauth(1);
        DetailsText detailsText = detailsTextMapper.selectByPrimaryKey(query);
        Assert.isTrue(detailsText!=null,"贴子异常");
        Report port = new Report();
        port.setClassId(query.getClassId());
        port.setId(detailsText.getPortId());
        Report report = reportMapper.selectById(port);
        Assert.isTrue(report.getPortState().equals("未结"),"此贴已结");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(name.equals(report.getUserAccount()),"无权限操作此贴");
        port.setPortState("已结");
        reportService.updateBystate(port);
        detailsTextMapper.updateAdoption(query);
        if(report.getExperience() != null && report.getExperience() > 0){
            User user = userMapper.selectByPrimaryKey(detailsText.getUserAccount());
            userMapper.updateAccumulatePoints(user.getAccumulatePoints()+report.getExperience(),user.getAccumulatePoints(),detailsText.getUserAccount(),UserAccumulate.countVipLevel(user.getAccumulatePoints()+report.getExperience()));
        }
    }

    @Override
    public DetailsText selectByPrimaryKey(DetailsTextQuery query){
        Assert.isTrue(query.getClassId()!=null&&query.getClassId()>=1&&query.getClassId()<5,"类型ID异常");
        Assert.isTrue(query.getId()!=null,"ID异常");
        query.setPortIsauth(1);
        return detailsTextMapper.selectByPrimaryKey(query);
    }

    @Transactional
    public void updateByPrimaryKey(DetailsTextQuery query) {
        Assert.isTrue(StringUtils.isNotEmpty(query.getDetailsText()),"贴子内容不能为空");
        Assert.isTrue(query.getClassId()!=null&&query.getClassId()>=1&&query.getClassId()<5,"类型ID异常");
        Assert.isTrue(query.getId()!=null,"ID异常");
        query.setPortIsauth(1);
        DetailsText detailsText = detailsTextMapper.selectByPrimaryKey(query);
        Assert.isTrue(detailsText!=null,"贴子异常");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(name.equals(detailsText.getUserAccount()),"无权限操作此贴");
        query.setAuthFlag(portIsauth.equals(1));
        UserMessageQuery messageQuery = new UserMessageQuery();
        messageQuery.setClassId(query.getClassId());
        messageQuery.setDetailsId(detailsText.getId());
        userMessageService.deleteByAll(messageQuery);
        Set<String> set = UserAccumulate.hasReplyAlias(query.getDetailsText());
        if(set.size() > 0){
            UserMessage userMessage = new UserMessage();
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userMessage.setSrcAlias(loginUser.getAlias());
            userMessage.setAuthFlag(portIsauth.equals(1));
            userMessage.setClassId(query.getClassId());
            userMessage.setPortId(detailsText.getPortId());
            userMessage.setDetailsId(detailsText.getId());
            userMessage.setReceiveDate(new Date());
            userMessage.setReceiveAliasSet(set);
            userMessageService.insertBaych(userMessage);
        }
        detailsTextMapper.updateByPrimaryKey(query);
    }
}
