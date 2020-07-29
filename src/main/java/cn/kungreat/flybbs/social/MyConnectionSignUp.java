package cn.kungreat.flybbs.social;

import cn.kungreat.flybbs.domain.User;
import cn.kungreat.flybbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MyConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private UserService userService;

    @Override
    public String execute(Connection<?> connection) {
        String s = UUID.randomUUID().toString().substring(24,35);
        User user = new User();
        user.setAccount(s);
        user.setOriginFrom(connection.getKey().getProviderId());
        user.setPassword("yjssaje");
        user.setImg(connection.getImageUrl());
        user.setAlias(connection.getDisplayName());
        userService.insert(user);
        return s;
    }
}
