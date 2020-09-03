package cn.kungreat.flybbs.service.impl;

import cn.kungreat.flybbs.domain.DetailsText;
import cn.kungreat.flybbs.domain.Report;
import cn.kungreat.flybbs.mapper.DetailsTextMapper;
import cn.kungreat.flybbs.mapper.ReportMapper;
import cn.kungreat.flybbs.query.ReportQuery;
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
        return 0;
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
}