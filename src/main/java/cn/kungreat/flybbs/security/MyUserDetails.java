package cn.kungreat.flybbs.security;

import cn.kungreat.flybbs.domain.User;
import cn.kungreat.flybbs.service.PermissionService;
import cn.kungreat.flybbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MyUserDetails implements UserDetailsService, SocialUserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    /*   return new org.springframework.security.core.userdetails.User("","",
              AuthorityUtils.commaSeparatedStringToAuthorityList(""));
              spring 提供的实现
              第三个权限集合接收String  多个用,号分割
               返回List<GrantedAuthority>
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectByPrimaryKey(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Set<MyRole> roles = new HashSet<>();
                List<String> ps = permissionService.selectPermissions(username);
                if(ps != null && ps.size()>0){
                    for(int x=0;x< ps.size();x++){
                        roles.add(new MyRole("ROLE_"+ps.get(x)));
                    }
                }
                return roles;
            }

            @Override
            public String getPassword() {
                return user==null?"":user.getPassword();
            }

            @Override
            public String getUsername() {
                return user==null?"":user.getAccount();
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
        };
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        User user = userService.selectByPrimaryKey(userId);
        if (user == null) {
            throw new UsernameNotFoundException(userId);
        }
        return new SocialUserDetails() {
            @Override
            public String getUserId() {
                return user.getAccount();
            }
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Set<MyRole> roles = new HashSet<>();
                List<String> ps = permissionService.selectPermissions(userId);
                if(ps != null && ps.size()>0){
                    for(int x=0;x< ps.size();x++){
                        roles.add(new MyRole("ROLE_"+ps.get(x)));
                    }
                }
                return roles;
            }
            @Override
            public String getPassword() {
               return  null;
            }

            @Override
            public String getUsername() {
                return user==null?"":user.getAccount();
            }

            @Override
            public boolean isAccountNonExpired() {
                return user.getState() == 1;
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
        };
    }
}