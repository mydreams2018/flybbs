package cn.kungreat.base.controller;

import cn.kungreat.base.domain.DetailsText;
import cn.kungreat.base.query.DetailsTextQuery;
import cn.kungreat.base.service.DetailsTextService;
import cn.kungreat.common.vo.JsonResult;
import cn.kungreat.common.vo.QueryResult;
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

    @RequestMapping(value = "/sendReply",method = RequestMethod.POST)
    public JsonResult sendReply(DetailsText detailsText){
        JsonResult jsonResult = new JsonResult();
        try{
            jsonResult.setId(String.valueOf(detailsTextService.insert(detailsText)));
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setStatus(0);
            jsonResult.setId("imgCode");
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "/likeAccount",method = RequestMethod.POST)
    public JsonResult likeAccount(DetailsTextQuery query){
        JsonResult jsonResult = new JsonResult();
        try{
            detailsTextService.likeAccount(query);
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setStatus(0);
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }
    @RequestMapping(value = "/deleteReplyPort",method = RequestMethod.POST)
    public JsonResult deleteReplyPort(DetailsTextQuery query){
        JsonResult jsonResult = new JsonResult();
        try{
            detailsTextService.deleteReplyPort(query);
        }catch(Exception e){
            jsonResult.setResult(false);
            jsonResult.setStatus(0);
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "/acceptReply",method = RequestMethod.POST)
    public JsonResult acceptReply(DetailsTextQuery query){
        JsonResult jsonResult = new JsonResult();
        try{
            detailsTextService.acceptReply(query);
        }catch(Exception e){
            jsonResult.setResult(false);
            jsonResult.setStatus(0);
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.POST)
    public DetailsText selectByPrimaryKey(DetailsTextQuery query){
        return detailsTextService.selectByPrimaryKey(query);
    }

    @RequestMapping(value = "/updateByPrimaryKey",method = RequestMethod.POST)
    public JsonResult updateByPrimaryKey(DetailsTextQuery query){
        JsonResult jsonResult = new JsonResult();
        try{
            detailsTextService.updateByPrimaryKey(query);
        }catch(Exception e){
            jsonResult.setResult(false);
            jsonResult.setStatus(0);
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }
}