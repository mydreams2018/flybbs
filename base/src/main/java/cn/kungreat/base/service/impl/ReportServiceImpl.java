package cn.kungreat.base.service.impl;

import cn.kungreat.base.domain.DetailsText;
import cn.kungreat.base.domain.Report;
import cn.kungreat.base.domain.User;
import cn.kungreat.base.mapper.DetailsTextMapper;
import cn.kungreat.base.mapper.ReportMapper;
import cn.kungreat.base.query.ReportQuery;
import cn.kungreat.base.service.ReportService;
import cn.kungreat.base.service.UserService;
import cn.kungreat.common.vo.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
@Service
@RefreshScope
public class ReportServiceImpl implements ReportService {
    @Autowired
    private UserService userService;
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private DetailsTextMapper detailsTextMapper;
    @Value("${portIsauth:1}")
    private Integer portIsauth;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Transactional
    public long insert(Report record) {
        String s = record.validMessage();
        Assert.isTrue(StringUtils.isEmpty(s),s);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if(record.getExperience() != null && record.getExperience() > 0){
            userService.updateAccumulatePoints(-record.getExperience(),name);
        }
        record.setUserAccount(name);
        Date date = new Date();
        record.setCreateTime(date);
        record.setAuthFlag(portIsauth.equals(1));
        reportMapper.insert(record);
        DetailsText details = new DetailsText();
        details.setIsPort(true);
        details.setCreateData(date);
        details.setPortId(record.getId());
        details.setDetailsText(record.getDetailsText());
        details.setUserAccount(name);
        details.setClassId(record.getClassId());
        details.setAuthFlag(portIsauth.equals(1));
        detailsTextMapper.insert(details);
        return 1;
    }

    @Transactional
    public int updateByPrimaryKey(Report record) {
        Assert.isTrue(record.getClassId()!=null&&record.getClassId()>=1&&record.getClassId()<5,"类型ID异常");
        Assert.isTrue(record.getId() != null,"ID异常");
        Assert.isTrue(StringUtils.isNotEmpty(record.getName()),"标题不能为空");
        Assert.isTrue(StringUtils.isNotEmpty(record.getDetailsText()),"内容不能为空");
        Report port = reportMapper.selectById(record);
        Assert.isTrue(port != null,"贴子异常");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(name.equals(port.getUserAccount()),"没有权限操作");
        record.setAuthFlag(portIsauth.equals(1));
        reportMapper.updateByPrimaryKey(record);
        DetailsText detailsText = new DetailsText();
        detailsText.setClassId(record.getClassId());
        detailsText.setPortId(record.getId());
        detailsText.setDetailsText(record.getDetailsText());
        detailsText.setAuthFlag(portIsauth.equals(1));
        detailsTextMapper.updateByPortId(detailsText);
        return 1;
    }

    @Transactional
    public int updateBystate(Report record) {
        return reportMapper.updateBystate(record);
    }

    @Override
    public Report selectByPrimaryKey(Report record) {
        Assert.isTrue(record.getClassId()!=null&&record.getClassId()>=1&&record.getClassId()<5,"类型ID异常");
        Assert.isTrue(record.getId() != null,"ID异常");
        record.setPortIsauth(1);
        Report report = reportMapper.selectByPrimaryKey(record);
        if(report != null){
            DetailsText de = new DetailsText();
            de.setPortId(record.getId());
            de.setClassId(record.getClassId());
            de.setPortIsauth(1);
            report.setDetails(detailsTextMapper.selectByPort(de));
        }
        return report;
    }

    @Override
    public QueryResult queryReport(ReportQuery query) {
        Assert.isTrue(query.getClassId()!=null&&query.getClassId()>=1&&query.getClassId()<5,"类型ID异常");
        query.setPortIsauth(1);
        Integer count = reportMapper.selectCount(query);
        List list  = Collections.emptyList();
        if (count >  0){
            list = reportMapper.selectAll(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }

    @Transactional
    public void incrementNumber(Report port) {
        reportMapper.incrementNumber(port);
    }

    @Transactional
    public void decrementNumber(Report port) {
        reportMapper.decrementNumber(port);
    }

    @Override
    public List<Report> lastSendPort(Report query){
        Assert.isTrue(StringUtils.isNotEmpty(query.getAlias()),"用户为空");
        User user = userService.selectByunique(null, query.getAlias());
        Assert.isTrue(user!=null,"用户为空");
        query.setUserAccount(user.getAccount());
        query.setPortIsauth(1);
        List<Report> ports = new ArrayList<>(40);
        for(int x=1;x<5;x++){
            query.setClassId(x);
            List<Report> temp = reportMapper.lastSendPort(query);
            if(temp != null &&temp.size()>0){
                ports.addAll(temp);
            }
        }
        if(ports.size() > 1){
            order(ports);
        }
        return ports;
    }
//查个人发贴-我的发贴
    @Override
    public QueryResult myQueryReport(ReportQuery query){
        Integer count = reportMapper.mySelectCount(query);
        List list  = Collections.emptyList();
        if (count >  0){
            list = reportMapper.mySelectAll(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }

    private void order(List<Report> reports){
        int size = reports.size();
        Report temp;
        for(int x = 0;x < size-1;x++){
            if(x==10){
               return;
            }
            for(int y=x+1;y<size;y++){
                if(reports.get(x).getCreateTime().before(reports.get(y).getCreateTime())){
                    temp = reports.get(x);
                    reports.set(x,reports.get(y));
                    reports.set(y,temp);
                }
            }
        }
    }
}