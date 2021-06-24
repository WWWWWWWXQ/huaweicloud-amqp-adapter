package edu.hrbust.iot.amqp.security.controller;

import edu.hrbust.iot.amqp.web.utils.common.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登陆页面
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public WebResponse<Map<?,?>> info(){
        Map<String, String> res = new HashMap<>();
        res.put("roles","[admin]");
        res.put("name", "admin");
        res.put("avatar", "https://gitee.com/WWWWWWWXQ/images/raw/master/Gif/grass.gif");
        log.info("res:{}", res);
        return WebResponse.success(res);

    }

}
