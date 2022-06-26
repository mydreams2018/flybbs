package cn.kungreat.base.service;

import cn.kungreat.base.domain.UserMessage;
import cn.kungreat.base.query.UserMessageQuery;

import java.util.List;

public interface UserMessageService {
    List<UserMessage> selectAll(UserMessageQuery query);
    int selectCount(UserMessageQuery query);
    int deleteByPrimaryKey(Long id);
    int deleteByAccount(UserMessageQuery query);
    int deleteByAll(UserMessageQuery query);
    void insertBaych(UserMessage userMessage);
}
