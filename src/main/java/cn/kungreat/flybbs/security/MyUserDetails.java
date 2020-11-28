package cn.kungreat.flybbs.security;

import cn.kungreat.flybbs.domain.User;
import cn.kungreat.flybbs.service.PermissionService;
import cn.kungreat.flybbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        Set<MyRole> roles = new HashSet<>();
        List<String> ps = permissionService.selectPermissions(username);
        if(ps != null && ps.size()>0){
            for(int x=0;x< ps.size();x++){
                roles.add(new MyRole("ROLE_"+ps.get(x)));
            }
        }
        return new LoginUser(user,roles);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        User user = userService.selectByPrimaryKey(userId);
        if (user == null) {
            throw new UsernameNotFoundException(userId);
        }
        Set<MyRole> roles = new HashSet<>();
        List<String> ps = permissionService.selectPermissions(userId);
        if(ps != null && ps.size()>0){
            for(int x=0;x< ps.size();x++){
                roles.add(new MyRole("ROLE_"+ps.get(x)));
            }
        }
        return new LoginUser(user,roles);
    }
}