package edu.hrbust.iot.amqp.web;

import edu.hrbust.iot.amqp.HuaWeiCloudAmqpAdapterApplication;
import edu.hrbust.iot.amqp.adapter.AmqpAdapter;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = HuaWeiCloudAmqpAdapterApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
class HuaweiCloudAmqpAdapterApplicationTests {

    @Resource
    AmqpAdapter amqpAdapter;

    @Test
    public void pureTest(){
        try {
            amqpAdapter.pureReceive();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
