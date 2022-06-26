package cn.kungreat.base.mapper;

import cn.kungreat.base.domain.UserReplyPort;
import java.util.List;

public interface UserReplyPortMapper {

    int insert(UserReplyPort record);

    List<UserReplyPort> selectAll(UserReplyPort record);

    int updateByPrimaryKey(UserReplyPort record);

    UserReplyPort selectByPrimaryKey(UserReplyPort replyPort);
}