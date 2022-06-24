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

import java.util.*;
import java.util.stream.Collectors;

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
        List<DetailsText> list  = Collections.emptyList();
        if (count >  0){
            list = detailsTextMapper.selectAll(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult result = new QueryResult();
        result.setDatas(linkChild(list,query));
        result.setPage(query);
        return result;
    }

    private List<DetailsText> linkChild(List<DetailsText> list,DetailsTextQuery query){
        List<DetailsText> rt = list;
        if(list.size()>0){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                DetailsText detailsText = list.get(i);
                stringBuilder.append(detailsText.getId());
                if(i+1 < list.size()){
                    stringBuilder.append(",");
                }
            }
            query.setReplyIds(stringBuilder.toString());
            List<DetailsText> childAnswer = detailsTextMapper.selectChildAnswer(query);
            for (int x = 0; x < childAnswer.size()-1; x++) {
                DetailsText outX = childAnswer.get(x);
                for (int y = 0; y < childAnswer.size(); y++) {
                    DetailsText innerY =  childAnswer.get(y);
                    if(outX.getId().equals(innerY.getReplyParent())){
                        if(outX.getChildAnswers() == null){
                            outX.setChildAnswers(new ArrayList<>());
                        }
                        outX.getChildAnswers().add(innerY);
                    }
                }
            }
            rt = childAnswer.stream().
                    filter(item->item.getReplyParent()==null).collect(Collectors.toList());
        }
        return rt;
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
        userReplyPortService.updateByPrimaryKey();//用户周回贴统计
        return 1;
    }

    @Transactional
    public void likeAccount(DetailsTextQuery query) {
        Assert.isTrue(query.getClassId()!=null&&query.getClassId()>=1&&query.getClassId()<5,"类型ID异常");
        Assert.isTrue(query.getId()!=null,"ID异常");
        DetailsText s = detailsTextMapper.selectLikeAccount(query);
        Assert.isTrue(s!=null,"贴子异常");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(s.getLikeAccount()==null||!s.getLikeAccount().contains(name),"已经点过赞了");
        query.setLikeAccount(s.getLikeAccount()+","+name);
        query.setLikeNumber(s.getLikeNumber());
        detailsTextMapper.updateLikeAccount(query);
    }
//自已删除自已的回贴
    @Transactional
    public int deleteReplyPort(DetailsTextQuery query) {
        Assert.isTrue(query.getClassId()!=null&&query.getClassId()>=1&&query.getClassId()<5,"类型ID异常");
        Assert.isTrue(query.getId()!=null,"ID异常");
        query.setPortIsauth(1);
        DetailsText detailsText = detailsTextMapper.selectByPrimaryKey(query);
        Assert.isTrue(detailsText!=null,"贴子异常");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.selectByPrimaryKey(name);
        Assert.isTrue(detailsText.getUserAccount().equals(name),"没有删除权限");
        Report port = new Report();
        port.setClassId(query.getClassId());
        port.setId(detailsText.getPortId());
        reportService.decrementNumber(port);//删除回贴数量
        if(detailsText.getIsAdoption()){//是否采纳
            port.setPortState("未结");
            reportService.updateBystate(port);//设为未结
            Report report = reportMapper.selectById(port);
            if(report.getExperience() != null && report.getExperience() > 0){//删除自已飞吻
                userMapper.updateAccumulatePoints(user.getAccumulatePoints()-report.getExperience(),user.getAccumulatePoints(),name, UserAccumulate.countVipLevel(user.getAccumulatePoints()-report.getExperience()));
            }
        }
        UserMessageQuery messageQuery = new UserMessageQuery();
        messageQuery.setClassId(query.getClassId());
        messageQuery.setDetailsId(query.getId());
        userMessageService.deleteByAll(messageQuery);//删除@信息
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
        userMessageService.deleteByAll(messageQuery);//删除@信息
        Set<String> set = UserAccumulate.hasReplyAlias(query.getDetailsText());//查看是否有@信息
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
            userMessageService.insertBaych(userMessage);//添加@信息
        }
        detailsTextMapper.updateByPrimaryKey(query);
    }
//用户首页最近的回贴
    @Override
    public List<Report> lastReplyPort(Report query) {
        Assert.isTrue(StringUtils.isNotEmpty(query.getAlias()),"用户为空");
        User user = userMapper.selectByunique(null, query.getAlias());
        Assert.isTrue(user!=null,"用户为空");
        query.setUserAccount(user.getAccount());
        query.setPortIsauth(1);
        return detailsTextMapper.lastReplyPort(query);
    }
}