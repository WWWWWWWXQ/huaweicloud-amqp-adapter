package edu.hrbust.iot.amqp.security.config;

import edu.hrbust.iot.amqp.security.dao.UserRepository;
import edu.hrbust.iot.amqp.security.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security
 * 自定义核心处理类
 */
@Slf4j
@Service("userDetailsService")
public class DefaultUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public DefaultUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("[{}]登陆中...", s);
        User user = userRepository.findByUsername(s);
        if (user == null){
            log.error("未找到用户名[{}]", s);
            throw new UsernameNotFoundException("User [" + s + "] no found");
        }
        log.info("用户名[{}]存在", s);
        return user;
    }

}
