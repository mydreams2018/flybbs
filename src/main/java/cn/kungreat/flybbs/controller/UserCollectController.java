package cn.kungreat.flybbs.controller;

import cn.kungreat.flybbs.domain.UserCollect;
import cn.kungreat.flybbs.service.UserCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/userCollect")
public class UserCollectController{
    @Autowired
    private UserCollectService userCollectService;

    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.POST)
    public UserCollect selectByPrimaryKey(UserCollect collect){
        return userCollectService.selectByPrimaryKey(collect);
    }

    @RequestMapping(value = "/sendCollect",method = RequestMethod.POST)
    public UserCollect sendCollect(UserCollect collect){
        return userCollectService.sendCollect(collect);
    }
}
