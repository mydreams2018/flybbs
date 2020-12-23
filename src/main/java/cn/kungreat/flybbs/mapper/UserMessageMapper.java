package cn.kungreat.flybbs.mapper;

import cn.kungreat.flybbs.domain.UserMessage;
import cn.kungreat.flybbs.query.UserMessageQuery;

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