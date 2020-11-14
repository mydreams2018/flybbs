package cn.kungreat.flybbs.service.impl;

import cn.kungreat.flybbs.domain.UserReplyPort;
import cn.kungreat.flybbs.mapper.UserReplyPortMapper;
import cn.kungreat.flybbs.service.UserReplyPortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
@Service
public class UserReplyPortServiceImpl implements UserReplyPortService {
    @Autowired
    private UserReplyPortMapper userReplyPortMapper;

    @Override
    public List<UserReplyPort> selectAll() {
        UserReplyPort replyPort = new UserReplyPort();
        Calendar instance = Calendar.getInstance();
        replyPort.setReplyYear(instance.get(Calendar.YEAR));
        replyPort.setReplyWeek(instance.get(Calendar.WEEK_OF_YEAR));
        return userReplyPortMapper.selectAll(replyPort);
    }

    @Override
    public int updateByPrimaryKey(){
        UserReplyPort record = new UserReplyPort();
        Calendar instance = Calendar.getInstance();
        record.setReplyYear(instance.get(Calendar.YEAR));
        record.setReplyWeek(instance.get(Calendar.WEEK_OF_YEAR));
        record.setUserAccount(SecurityContextHolder.getContext().getAuthentication().getName());
        UserReplyPort replyPort = userReplyPortMapper.selectByPrimaryKey(record);
        if(replyPort == null){
            record.setReplyNumber(1);
            userReplyPortMapper.insert(record);
        }else{
            userReplyPortMapper.updateByPrimaryKey(replyPort);
        }
        return 1;
    }
}