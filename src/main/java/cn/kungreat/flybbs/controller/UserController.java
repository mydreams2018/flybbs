package cn.kungreat.flybbs.controller;

import cn.kungreat.flybbs.domain.Report;
import cn.kungreat.flybbs.domain.User;
import cn.kungreat.flybbs.query.UserQuery;
import cn.kungreat.flybbs.service.ReportService;
import cn.kungreat.flybbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ReportService reportService;
    @RequestMapping(value = "/home",method = RequestMethod.POST)
    public User home(UserQuery query){
        return userService.selectByunique(null,query.getAlias());
    }

    @RequestMapping(value = "/lastSendPort",method = RequestMethod.POST)
    public List<Report> lastSendPort(Report query){
        return reportService.lastSendPort(query);
    }
}
