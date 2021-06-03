package edu.hrbust.iot.amqp.web;

import edu.hrbust.iot.amqp.security.controller.RegistrationController;
import edu.hrbust.iot.amqp.security.entity.RegistrationForm;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class AuthTest {
    @Resource
    RegistrationController controller;

//    @Test
    void test(){
        RegistrationForm form = RegistrationForm.builder().username("admin").password("testtest").build();
        controller.register(form);
    }
}
