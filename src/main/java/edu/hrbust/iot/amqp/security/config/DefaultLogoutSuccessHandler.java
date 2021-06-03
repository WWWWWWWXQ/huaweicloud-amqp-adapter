package edu.hrbust.iot.amqp.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hrbust.iot.amqp.web.utils.common.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security
 * 登出成功处理类
 */
@Slf4j
@Component("logoutSuccessHandler")
public class DefaultLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登出成功");
        WebResponse<String> res = WebResponse.success("登出成功");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(res));
    }
}
