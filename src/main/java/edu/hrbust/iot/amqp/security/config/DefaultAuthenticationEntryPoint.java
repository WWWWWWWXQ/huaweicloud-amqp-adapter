package edu.hrbust.iot.amqp.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 配置Spring Security 登陆页面入口
 */
@Slf4j
@Component("entryPoint")
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Value("${security.entry-point}")
    private String entryPointURL;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.info("[登陆拦截]接收到[{}]请求", httpServletRequest.getRequestURI());
        httpServletResponse.sendRedirect(entryPointURL);
    }
}
