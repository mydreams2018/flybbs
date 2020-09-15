package cn.kungreat.flybbs.service.impl;

import cn.kungreat.flybbs.domain.DetailsText;
import cn.kungreat.flybbs.domain.Report;
import cn.kungreat.flybbs.domain.User;
import cn.kungreat.flybbs.mapper.DetailsTextMapper;
import cn.kungreat.flybbs.mapper.ReportMapper;
import cn.kungreat.flybbs.query.ReportQuery;
import cn.kungreat.flybbs.query.UserQuery;
import cn.kungreat.flybbs.service.ReportService;
import cn.kungreat.flybbs.service.UserService;
import cn.kungreat.flybbs.vo.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private UserService userService;
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private DetailsTextMapper detailsTextMapper;
    @Value("${port.isauth}")
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
        reportMapper.insert(record);
        DetailsText details = new DetailsText();
        details.setIsPort(true);
        details.setCreateData(date);
        details.setPortId(record.getId());
        details.setDetailsText(record.getDetailsText());
        details.setUserAccount(name);
        details.setClassId(record.getClassId());
        detailsTextMapper.insert(details);
        return 1;
    }

    @Override
    public int updateByPrimaryKey(Report record) {
        Assert.isTrue(record.getClassId()!=null&&record.getClassId()>=1&&record.getClassId()<5,"类型ID异常");
        Assert.isTrue(record.getId() != null,"ID异常");
        Assert.isTrue(StringUtils.isNotEmpty(record.getName()),"标题不能为空");
        Assert.isTrue(StringUtils.isNotEmpty(record.getDetailsText()),"内容不能为空");
        Report port = reportMapper.selectById(record);
        Assert.isTrue(port != null,"贴子异常");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(name.equals(port.getUserAccount()),"没有权限操作");
        reportMapper.updateByPrimaryKey(record);
        DetailsText detailsText = new DetailsText();
        detailsText.setClassId(record.getClassId());
        detailsText.setPortId(record.getId());
        detailsText.setDetailsText(record.getDetailsText());
        detailsTextMapper.updateByPortId(detailsText);
        return 1;
    }

    @Override
    public int updateBystate(Report record) {
        return reportMapper.updateBystate(record);
    }

    @Override
    public Report selectByPrimaryKey(Report record) {
        Assert.isTrue(record.getClassId()!=null&&record.getClassId()>=1&&record.getClassId()<5,"类型ID异常");
        Assert.isTrue(record.getId() != null,"ID异常");
        record.setPortIsauth(portIsauth);
        Report report = reportMapper.selectByPrimaryKey(record);
        if(report != null){
            DetailsText de = new DetailsText();
            de.setPortId(record.getId());
            de.setClassId(record.getClassId());
            de.setPortIsauth(portIsauth);
            report.setDetails(detailsTextMapper.selectByPort(de));
        }
        return report;
    }

    @Override
    public QueryResult queryReport(ReportQuery query) {
        Assert.isTrue(query.getClassId()!=null&&query.getClassId()>=1&&query.getClassId()<5,"类型ID异常");
        query.setPortIsauth(portIsauth);
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

    @Override
    public void incrementNumber(Report port) {
        reportMapper.incrementNumber(port);
    }

    @Override
    public void decrementNumber(Report port) {
        reportMapper.decrementNumber(port);
    }

    @Override
    public List<Report> lastSendPort(Report query){
        Assert.isTrue(StringUtils.isNotEmpty(query.getAlias()),"用户为空");
        User user = userService.selectByunique(null, query.getAlias());
        Assert.isTrue(user!=null,"用户为空");
        query.setUserAccount(user.getAccount());
        query.setPortIsauth(portIsauth);
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