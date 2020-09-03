package cn.kungreat.flybbs.controller;

import cn.kungreat.flybbs.query.DetailsTextQuery;
import cn.kungreat.flybbs.service.DetailsTextService;
import cn.kungreat.flybbs.vo.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/detailsText")
public class DetailsTextController {
    @Autowired
    private DetailsTextService detailsTextService;

    @RequestMapping(value = "/queryDetails",method = RequestMethod.POST)
    public QueryResult queryReport(DetailsTextQuery query){
        return detailsTextService.queryReport(query);
    }
}
