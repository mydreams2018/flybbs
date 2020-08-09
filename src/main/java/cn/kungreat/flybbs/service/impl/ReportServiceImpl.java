package cn.kungreat.flybbs.service.impl;

import cn.kungreat.flybbs.domain.DetailsTextBack;
import cn.kungreat.flybbs.domain.ReportBack;
import cn.kungreat.flybbs.mapper.DetailsTextMapper;
import cn.kungreat.flybbs.mapper.ReportMapper;
import cn.kungreat.flybbs.service.ReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
@Service
public class ReportServiceImpl implements ReportService {
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
    public long insert(ReportBack record) {
        String s = record.validMessage();
        Assert.isTrue(StringUtils.isEmpty(s),s);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        record.setUserAccount(name);
        Date date = new Date();
        record.setCreateTime(date);
        reportMapper.insert(record);
        DetailsTextBack details = new DetailsTextBack();
        details.setCreateData(date);
        details.setPortId(record.getId());
        details.setDetailsText(record.getDetailsText());
        details.setUserAccount(name);
        details.setClassId(record.getClassId());
        detailsTextMapper.insert(details);
        return 1;
    }

    @Override
    public int updateByPrimaryKey(ReportBack record) {
        return 0;
    }

    @Override
    public ReportBack selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public List<ReportBack> selectAll() {
        return null;
    }
}
