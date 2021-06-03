package edu.hrbust.iot.amqp.security.redundant;//package com.example.demo.config;
//
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Collection;
//
//@Component("provider")
//public class DefaultAuthenticationProvider implements AuthenticationProvider {
//    @Resource
//    private UserDetailsService userDetailService;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String userName = authentication.getName();// 这个获取表单输入中返回的用户名;
//        String password = (String) authentication.getCredentials();// 这个是表单中输入的密码；
//        System.out.println("provider " + "userName: " + userName + " , password:" + password );
//
//        User userInfo = (User) userDetailService.loadUserByUsername(userName); // 这里调用我们的自己写的获取用户的方法；
//        if (userInfo == null) {
//            throw new BadCredentialsException("用户名不存在");
//        }
//        if (!userInfo.getPassword().equals(password)) {
//            throw new BadCredentialsException("密码不正确");
//        }
//        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
//        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return true;
//    }
//}



//in SecurityConfig
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(provider);
//    }