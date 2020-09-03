package cn.kungreat.flybbs.service.impl;

import cn.kungreat.flybbs.mapper.DetailsTextMapper;
import cn.kungreat.flybbs.query.DetailsTextQuery;
import cn.kungreat.flybbs.service.DetailsTextService;
import cn.kungreat.flybbs.vo.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

@Service
public class DetailsTextServiceImpl implements DetailsTextService {
    @Autowired
    private DetailsTextMapper detailsTextMapper;
    @Value("${port.isauth}")
    private Integer portIsauth;

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
}
