package cn.kungreat.base.mapper;

import cn.kungreat.base.domain.UserMessage;
import cn.kungreat.base.query.UserMessageQuery;

import java.util.List;

public interface UserMessageMapper {
    int deleteByPrimaryKey(Long id);
    int deleteByAccount(UserMessageQuery query);
    int deleteByAll(UserMessageQuery query);

    List<UserMessage> selectAll(UserMessageQuery query);
    int selectCount(UserMessageQuery query);
    UserMessage selectByPrimaryKey(Long id);

    void insertBaych(UserMessage userMessage);
}