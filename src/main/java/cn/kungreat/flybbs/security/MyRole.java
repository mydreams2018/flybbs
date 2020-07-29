package cn.kungreat.flybbs.security;

import org.springframework.security.core.GrantedAuthority;

public class MyRole implements GrantedAuthority {
    private String role;
    public MyRole(String role){
        this.role =role;
    }
    @Override
    public String getAuthority() {
        return role;
    }
}
