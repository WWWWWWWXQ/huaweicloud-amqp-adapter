package edu.hrbust.iot.amqp.security.controller;

import edu.hrbust.iot.amqp.security.entity.RegistrationForm;
import edu.hrbust.iot.amqp.security.entity.User;
import edu.hrbust.iot.amqp.security.dao.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

/**
 * 注册页面
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody  RegistrationForm form){
        User user = userRepository.findByUsername(form.getUsername());
        if (user != null) return "用户名已存在";

        log.info("{}", form);

        userRepository.save(form.toUser(passwordEncoder));
        return userRepository.findUserIdByUsername(form.getUsername()).toString();
    }


}

