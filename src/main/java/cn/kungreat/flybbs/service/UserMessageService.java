package cn.kungreat.flybbs.service;

import cn.kungreat.flybbs.domain.UserMessage;
import cn.kungreat.flybbs.query.UserMessageQuery;

import java.util.List;

public interface UserMessageService {
    List<UserMessage> selectAll(UserMessageQuery query);
    int selectCount(UserMessageQuery query);
    int deleteByPrimaryKey(Long id);
    int deleteByAccount(UserMessageQuery query);
    int deleteByAll(UserMessageQuery query);
    void insertBaych(UserMessage userMessage);
}
