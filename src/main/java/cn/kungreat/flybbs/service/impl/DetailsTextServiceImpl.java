package cn.kungreat.flybbs.service.impl;

import cn.kungreat.flybbs.domain.DetailsText;
import cn.kungreat.flybbs.domain.Report;
import cn.kungreat.flybbs.domain.User;
import cn.kungreat.flybbs.mapper.DetailsTextMapper;
import cn.kungreat.flybbs.mapper.UserMapper;
import cn.kungreat.flybbs.query.DetailsTextQuery;
import cn.kungreat.flybbs.service.DetailsTextService;
import cn.kungreat.flybbs.service.ReportService;
import cn.kungreat.flybbs.vo.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class DetailsTextServiceImpl implements DetailsTextService {
    @Autowired
    private DetailsTextMapper detailsTextMapper;
    @Autowired
    private ReportService reportService;
    @Value("${port.isauth}")
    private Integer portIsauth;
    @Autowired
    private UserMapper userMapper;
    @Override
    public QueryResult queryReport(DetailsTextQuery query) {
        Assert.isTrue(query.getClassId()!=null&&query.getClassId()>=1&&query.getClassId()<5,"类型ID异常");
        Assert.isTrue(query.getPortId()!=null,"贴子ID异常");
        query.setPortIsauth(portIsauth);
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

    @Override
    public long insert(DetailsText record) {
        String s = record.validMessage();
        Assert.isTrue(StringUtils.isEmpty(s),s);
        record.setIsPort(false);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        record.setUserAccount(name);
        record.setCreateData(new Date());
        detailsTextMapper.insert(record);
        Report report = new Report();
        report.setClassId(record.getClassId());
        report.setId(record.getPortId());
        reportService.incrementNumber(report);
        return 1;
    }

    @Override
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

    @Override
    public int deleteReplyPort(DetailsTextQuery query) {
        Assert.isTrue(query.getClassId()!=null&&query.getClassId()>=1&&query.getClassId()<5,"类型ID异常");
        Assert.isTrue(query.getId()!=null,"ID异常");
        DetailsText detailsText = detailsTextMapper.selectByPrimaryKey(query);
        Assert.isTrue(detailsText!=null,"贴子异常");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.selectByPrimaryKey(name);
        Assert.isTrue(user.getIsManager()==1||detailsText.getUserAccount().equals(name),"没有删除权限");
        Report port = new Report();
        port.setClassId(query.getClassId());
        port.setId(detailsText.getPortId());
        reportService.decrementNumber(port);
        return detailsTextMapper.deleteByPrimaryKey(query);
    }
}
