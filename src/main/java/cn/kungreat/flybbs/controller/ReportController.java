package cn.kungreat.flybbs.controller;

import cn.kungreat.flybbs.domain.ReportBack;
import cn.kungreat.flybbs.service.ReportService;
import cn.kungreat.flybbs.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/insert")
    public JsonResult insert(ReportBack record){
        JsonResult jsonResult = new JsonResult();
        try{
            reportService.insert(record);
            jsonResult.setMsg("success");
            jsonResult.setAction("/jie/detail.html?classId="+record.getClassId()+"&id="+record.getId());
        }catch(Exception e){
            jsonResult.setResult(false);
            jsonResult.setStatus(1);
            jsonResult.setId("imgCode");
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }
}
