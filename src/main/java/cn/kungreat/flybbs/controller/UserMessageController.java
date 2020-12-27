package cn.kungreat.flybbs.controller;

import cn.kungreat.flybbs.domain.UserMessage;
import cn.kungreat.flybbs.query.UserMessageQuery;
import cn.kungreat.flybbs.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/userMessage")
public class UserMessageController {
    @Autowired
    private UserMessageService userMessageService;

    @RequestMapping(value = "/selectAll",method = RequestMethod.POST)
    public List<UserMessage> selectAll(UserMessageQuery query){
        return userMessageService.selectAll(query);
    }

    @RequestMapping(value = "/deleteByAccount",method = RequestMethod.POST)
    public int deleteByAccount(UserMessageQuery query){
        return userMessageService.deleteByAccount(query);
    }

    @RequestMapping(value = "/selectCount",method = RequestMethod.POST)
    public int selectCount(UserMessageQuery query){
        return userMessageService.selectCount(query);
    }
}
