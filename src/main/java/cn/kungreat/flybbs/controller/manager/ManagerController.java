package cn.kungreat.flybbs.controller.manager;

import cn.kungreat.flybbs.query.UserQuery;
import cn.kungreat.flybbs.service.UserService;
import cn.kungreat.flybbs.vo.ManagerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/manager")
public class ManagerController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getAllUser",method = RequestMethod.POST)
    public ManagerResult getAllUser(UserQuery userQuery){
        if(userQuery.getPage() != null && userQuery.getPage() > 0){
            userQuery.setCurrentPage(userQuery.getPage());
        }
        if(userQuery.getLimit() != null && userQuery.getLimit() > 0){
            userQuery.setPageSize(userQuery.getLimit());
        }
        return userService.getAllUser(userQuery);
    }
}
