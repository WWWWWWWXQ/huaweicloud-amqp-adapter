package edu.hrbust.iot.amqp.web.controller;

import edu.hrbust.iot.amqp.web.utils.common.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/user")
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public WebResponse<Map<?,?>> login(){
        Map<String, String> res = new HashMap<>();
        res.put("token", "admin");
        return WebResponse.success(res);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public WebResponse<Map<?,?>> info(){
        Map<String, String> res = new HashMap<>();
        res.put("roles","[admin]");
        res.put("name", "admin");
        res.put("avatar", "https://gitee.com/WWWWWWWXQ/images/raw/master/Gif/grass.gif");
        return WebResponse.success(res);

    }
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public WebResponse<Map<?,?>> logout(){
        Map<String, String> res = new HashMap<>();
        res.put("token", "admin");
        res.put("roles", "[admin]");
        return WebResponse.success(res);
    }
}
