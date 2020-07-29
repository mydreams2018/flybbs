package cn.kungreat.flybbs.social.config;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class MySpringSocialConfigurer extends SpringSocialConfigurer {

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter  filter  = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl("/auth");
        filter.setAuthenticationSuccessHandler(new MySuccessHandler());
        return (T)filter;
    }
}
