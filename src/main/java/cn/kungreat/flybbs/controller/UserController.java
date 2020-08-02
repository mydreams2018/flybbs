package cn.kungreat.flybbs.controller;

import cn.kungreat.flybbs.query.UserQuery;
import cn.kungreat.flybbs.service.PermissionMappingService;
import cn.kungreat.flybbs.service.PermissionService;
import cn.kungreat.flybbs.service.UserService;
import cn.kungreat.flybbs.vo.JsonResult;
import cn.kungreat.flybbs.vo.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private PermissionMappingService permissionMappingService;

    @RequestMapping(value = "/categoryNames")
    public List<String> categoryNames(){
        return userService.categoryNames();
    }

    @RequestMapping(value = "/list")
    public QueryResult list(UserQuery query){
        return userService.query(query);
    }

    @RequestMapping(value = "/savePermissions",method = RequestMethod.POST)
    public JsonResult savePermissions(String account,String listPermission){
        JsonResult jsonResult = new JsonResult();
        try {
            if(StringUtils.isEmpty(listPermission)){
                permissionMappingService.insertBatch(null,account);
            }else{
                String[] st = listPermission.trim().split(",");
                permissionMappingService.insertBatch(Arrays.asList(st),account);
            }
            jsonResult.setMsg("success");
        }catch (Exception e){
            jsonResult.setMsg(e.getMessage());
            jsonResult.setResult(false);
        }
        return jsonResult;
    }
}
