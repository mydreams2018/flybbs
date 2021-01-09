package cn.kungreat.flybbs.config;

import cn.kungreat.flybbs.security.*;
import cn.kungreat.flybbs.util.UserAccumulate;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.*;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


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
    private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    private DefaultOAuth2AuthorizationRequestResolver defaultOAuth2AuthorizationRequestResolver;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetails).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 接口层要获取认证对象的时候  不要在这里放行 这里 不会封装认证对象过来
        web.ignoring().antMatchers(
                "/image","/register","/userImg/**"
                ,"/report/queryReport","/report/selectByPrimaryKey","/detailsText/queryDetails"
        ,"/user/home","/user/lastSendPort","/userReplyPort/selectAll","/user/resetPassword");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // by default uses a Bean by the name of corsConfigurationSource
                // if Spring MVC is on classpath and no CorsConfigurationSource is provided,
                // Spring Security will use CORS configuration provided to Spring MVC
                .cors().and().csrf().disable()
                .oauth2Login(oauth2 -> oauth2
                        .clientRegistrationRepository(clientRegistrationRepository)
                        .authorizedClientRepository(new OAuth2AuthorizedClientRepository(){
                            @Override
                            public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, Authentication principal, HttpServletRequest request) {
                                System.out.println("authorizedClientRepository-loadAuthorizedClient");
                                ClientRegistration clientRegistration = MyClientRegistrations.valueOf(clientRegistrationId.toUpperCase()).getClientRegistration();
                                return null ;
                            }
                            @Override
                            public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal, HttpServletRequest request, HttpServletResponse response) {
                                System.out.println("authorizedClientRepository-saveAuthorizedClient");
                            }
                            @Override
                            public void removeAuthorizedClient(String clientRegistrationId, Authentication principal, HttpServletRequest request, HttpServletResponse response) {
                                System.out.println("authorizedClientRepository-removeAuthorizedClient");
                            }
                        }).authorizedClientService(new OAuth2AuthorizedClientService(){
                            @Override
                            public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
                                System.out.println("authorizedClientService-loadAuthorizedClient");
                                return null;
                            }
                            @Override
                            public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
                                System.out.println("authorizedClientService-saveAuthorizedClient");
                            }
                            @Override
                            public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
                                System.out.println("authorizedClientService-removeAuthorizedClient");
                            }
                        })
                        .loginPage("/index")
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri("/authorization/login")
                                .authorizationRequestRepository(new HttpSessionOAuth2AuthorizationRequestRepository())
                                .authorizationRequestResolver(defaultOAuth2AuthorizationRequestResolver)
                        )
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri("/auth/*")
                        )
                        .tokenEndpoint(token -> token
                                .accessTokenResponseClient(new OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> (){
                                    private RestOperations restOperations;
                                    {
                                        RestTemplate restTemplate = new RestTemplate();
                                        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
                                        this.restOperations = restTemplate;
                                    }
                                    @Override
                                    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
                                        Assert.notNull(authorizationGrantRequest, "authorizationCodeGrantRequest cannot be null");
                                        OAuth2AuthorizationExchange authorizationExchange = authorizationGrantRequest.getAuthorizationExchange();
                                        ClientRegistration clientRegistration = authorizationGrantRequest.getClientRegistration();
                                        String getByUrl = clientRegistration.getProviderDetails().getTokenUri()+"?grant_type=authorization_code&client_id="+clientRegistration.getClientId()
                                                +"&client_secret="+clientRegistration.getClientSecret()+"&code="+authorizationExchange.getAuthorizationResponse().getCode()+"&redirect_uri="+clientRegistration.getRedirectUriTemplate();
                                        ResponseEntity<String> response;
                                        try {
                                            response = this.restOperations.getForEntity(getByUrl,String.class);
                                            String rp = "";
                                            if("qq".equals(clientRegistration.getRegistrationId())){
                                                rp =  "{"+response.getBody().replaceAll("&", ",")+"}";
                                            }
                                            Map<String,Object> stringObjectMap = UserAccumulate.converterMap(rp);
                                            OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(stringObjectMap.get("access_token").toString());
                                            builder.tokenType(OAuth2AccessToken.TokenType.BEARER);
                                            builder.scopes(clientRegistration.getScopes());
                                            builder.expiresIn(Long.parseLong(stringObjectMap.get("expires_in").toString()));
                                            builder.refreshToken(stringObjectMap.get("refresh_token").toString());
                                            return builder.build();
                                        }catch(Exception ex) {
                                            OAuth2Error oauth2Error = new OAuth2Error("invalid_token_response",
                                                    "An error occurred while attempting to retrieve the OAuth 2.0 Access Token Response: " + ex.getMessage(), null);
                                            throw new OAuth2AuthorizationException(oauth2Error, ex);
                                        }
                                    }
                                })
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                                .userAuthoritiesMapper(new GrantedAuthoritiesMapper(){
                                    @Override
                                    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
                                        System.out.println("mapAuthorities");
                                        return authorities;
                                    }
                                })
                                .userService(new OAuth2UserService<OAuth2UserRequest, OAuth2User>(){
                                    private RestOperations restOperations;
                                    {
                                        RestTemplate restTemplate = new RestTemplate();
                                        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
                                        this.restOperations = restTemplate;
                                    }
                                    @Override
                                    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
                                        Assert.notNull(userRequest, "userRequest cannot be null");
                                        ClientRegistration clientRegistration = userRequest.getClientRegistration();
                                        String userNameAttributeName = clientRegistration.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
                                        String tokenValue = userRequest.getAccessToken().getTokenValue();
                                        ResponseEntity<String> response;
                                        try {
                                            ResponseEntity<String> forEntity = this.restOperations.getForEntity("https://graph.qq.com/oauth2.0/me?access_token="
                                                            + tokenValue, String.class);
                                            String body = forEntity.getBody();
                                            String replace = body.substring(9,body.length()-4);
                                            Map<String,Object> stringObjectMap = JSON.parseObject(replace,Map.class);
                                            response = this.restOperations.getForEntity(clientRegistration.getProviderDetails().getUserInfoEndpoint().getUri()+"?access_token="
                                                    +tokenValue+"&oauth_consumer_key="+clientRegistration.getClientId()+"&openid="+stringObjectMap.get("openid").toString(),String.class);
                                            Map<String,Object> userAttributes = JSON.parseObject(response.getBody(),Map.class);
                                            userAttributes.put("openid",stringObjectMap.get("openid"));
                                            Set<GrantedAuthority> authorities = new LinkedHashSet<>();
                                            OAuth2AccessToken token = userRequest.getAccessToken();
                                            for (String authority : token.getScopes()){
                                                authorities.add(new SimpleGrantedAuthority("SCOPE_" + authority));
                                            }
                                            return new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);
                                        }catch(Exception ex){
                                            OAuth2Error oauth2Error = new OAuth2Error("invalid_user_info_response",
                                                    "An error occurred while attempting to retrieve the UserInfo Resource: " + ex.getMessage(), null);
                                            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(),ex);
                                        }
                                    }
                                })
                                .oidcUserService(new OAuth2UserService<OidcUserRequest, OidcUser> (){
                                    @Override
                                    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
                                        System.out.println("loadUser-2");
                                        return null;
                                    }
                                })
                        )
                )

                .addFilterBefore(new ImageFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/index").loginProcessingUrl("/defaultLogin").permitAll()
                .successHandler(successHandler)
                .failureHandler(faliureHandler)
                .and()
                .logout().logoutUrl("/clearAll").clearAuthentication(true)
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .logoutSuccessHandler(new LogoutHandler())
                .and()
                .rememberMe().tokenRepository(tokenRepository)
                .tokenValiditySeconds(60 * 6000)
                .userDetailsService(myUserDetails).authenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                String path = request.getRequestURI().substring(4);
                request.getRequestDispatcher(path).forward(request,response);
            }
        });

    }
}