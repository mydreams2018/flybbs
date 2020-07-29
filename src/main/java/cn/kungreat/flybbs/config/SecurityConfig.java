package cn.kungreat.flybbs.config;

import cn.kungreat.flybbs.security.*;
import cn.kungreat.flybbs.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetails myUserDetails;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private SuccessHandler successHandler;
    @Autowired
    private FaliureHandler faliureHandler;
    @Autowired
    private PersistentTokenRepository tokenRepository;
    @Autowired
    private SpringSocialConfigurer socialConfigurer;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetails).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 接口层要获取认证对象的时候  不要在这里放行 这里 不会封装认证对象过来
        web.ignoring().antMatchers("/favicon.ico","/register.html","/home.html","/accountReplyPosts.html",
                "/address.html","/out.html","/java.html","/accountPosts.html","/javaPosts/javaDetails","/assemblerPosts/details","/dataPosts/details","/image",
                "/","/index","/register", "/postsCategory/list","/javaPosts/selectAll","/assemblerPosts/selectAll","/dataPosts/selectAll","/javaDetails/selectReply","/dataDetails/selectReply","/assemblerDetails/selectReply",
                "/userImg/**","/summernote/**","/css/**","/js/**","/assembler/**","/data/**","/games/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // by default uses a Bean by the name of corsConfigurationSource
                // if Spring MVC is on classpath and no CorsConfigurationSource is provided,
                // Spring Security will use CORS configuration provided to Spring MVC
                .cors().and().csrf().disable()
                .apply(socialConfigurer).and()
                .addFilterBefore(new ImageFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new SingleSessionFilter(), ExceptionTranslationFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/index").loginProcessingUrl("/defaultLogin")
                .successHandler(successHandler)
                .failureHandler(faliureHandler)
                .and()
                .logout().logoutUrl("/clearAll").clearAuthentication(true)
                .invalidateHttpSession(true).deleteCookies("JSESSIONID","remember-me")
                .logoutSuccessHandler(new LogoutHandler())
                .and()
                .rememberMe().tokenRepository(tokenRepository)
                .tokenValiditySeconds(60 * 6000)
                .userDetailsService(myUserDetails).authenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                SingleSession.single.put(SecurityContextHolder.getContext().getAuthentication().getName()
                        ,request.getSession().getId()+":"+request.getSession().getCreationTime());
                UserContext.setCurrentName(SecurityContextHolder.getContext().getAuthentication().getName());
            }
        });

    }
}