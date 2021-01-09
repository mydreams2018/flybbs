package cn.kungreat.flybbs.security;

import cn.kungreat.flybbs.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class LoginUser implements UserDetails {
    private Collection<? extends GrantedAuthority> grantedAuthorities;
    private User user;
    public LoginUser(User user,Collection<? extends GrantedAuthority> grantedAuthorities){
        this.user = user;
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getAccount();
    }

    //下面4个返回 false 会抛异常 .可以用来作状态处理
    @Override
    public boolean isAccountNonExpired() {
        return user.getState()==1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getAlias(){
        return user.getAlias();
    }
}
