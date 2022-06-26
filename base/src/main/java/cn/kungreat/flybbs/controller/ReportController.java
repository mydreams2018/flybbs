package cn.kungreat.flybbs.controller;

import cn.kungreat.flybbs.domain.Report;
import cn.kungreat.flybbs.query.ReportQuery;
import cn.kungreat.flybbs.service.ReportService;
import cn.kungreat.flybbs.vo.JsonResult;
import cn.kungreat.flybbs.vo.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public JsonResult insert(Report record){
        JsonResult jsonResult = new JsonResult();
        try{
            reportService.insert(record);
            jsonResult.setMsg("success");
            jsonResult.setAction("/jie/detail.html?classId="+record.getClassId()+"&id="+record.getId());
        }catch(Exception e){
            jsonResult.setResult(false);
            jsonResult.setStatus(0);
            jsonResult.setId("imgCode");
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public JsonResult update(Report record){
        JsonResult jsonResult = new JsonResult();
        try{
            reportService.updateByPrimaryKey(record);
            jsonResult.setAction("/jie/detail.html?classId="+record.getClassId()+"&id="+record.getId());
        }catch(Exception e){
            jsonResult.setResult(false);
            jsonResult.setStatus(0);
            jsonResult.setId("imgCode");
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "/queryReport",method = RequestMethod.POST)
    public QueryResult queryReport(ReportQuery query){
        return reportService.queryReport(query);
    }

    @RequestMapping(value = "/myQueryReport",method = RequestMethod.POST)
    public QueryResult myQueryReport(ReportQuery query){
        query.setClassId(1);//没用的代码,防止报错
        query.setUserAccount(SecurityContextHolder.getContext().getAuthentication().getName());
        return reportService.myQueryReport(query);
    }

    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.POST)
    public Report selectByPrimaryKey(Report record){
        return reportService.selectByPrimaryKey(record);
    }

}