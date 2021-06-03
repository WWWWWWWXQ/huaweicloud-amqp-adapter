package edu.hrbust.iot.amqp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Spring Security
 * 核心配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.loginProcessingUrl}")
    private String loginProcessingUrl;

    @Value("${security.logoutUrl}")
    private String logoutUrl;

    private final UserDetailsService userDetailsService;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final AuthenticationEntryPoint entryPoint;
    private final LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService
            , AuthenticationSuccessHandler successHandler
            , AuthenticationFailureHandler failureHandler
            , AuthenticationEntryPoint entryPoint
            , LogoutSuccessHandler logoutSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.entryPoint = entryPoint;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Bean
    PasswordEncoder createPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置Spring Security 使用的自定义处理类和密码转码工具类
     * @param auth 认证工具对象
     * @throws Exception Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(createPasswordEncoder());
    }

    /**
     * 配置Spring Security 的安全性需求
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                // 对登录注册要允许匿名访问;
                .antMatchers(loginProcessingUrl, "/register/register").permitAll()
                .antMatchers(HttpMethod.GET).permitAll()
                // 其他的路径都要登录之后具备USER角色
//                .antMatchers("/**").hasRole("role")
                //这里配置的loginProcessingUrl为页面中对应表单的 action ，该请求为 post，并设置可匿名访问
                .and().formLogin().loginProcessingUrl(loginProcessingUrl).permitAll()
                //登录成功后的返回结果
                .successHandler(successHandler)
                //登录失败后的返回结果
                .failureHandler(failureHandler)
                //这里配置的logoutUrl为登出接口，并设置可匿名访问
                .and().logout().logoutUrl(logoutUrl).permitAll()
                //登出后的返回结果
                .logoutSuccessHandler(logoutSuccessHandler)
                //这里配置的为当未登录访问受保护资源时，返回json，并且让spring security自带的登录界面失效
                .and().exceptionHandling().authenticationEntryPoint(entryPoint)
        ;

    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String password = passwordEncoder.encode("admin");
//        auth.inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("admin")
//                .password(password)
//                .roles("admin");
//    }

}
