package edu.hrbust.iot.amqp.adapter;

import edu.hrbust.iot.amqp.adapter.service.AmqpAdapterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HuaweiCloudAmqpAdapterApplicationTests {

    @Autowired
    AmqpAdapterService amqpAdapterService;

    @Test
    void receive(){
        amqpAdapterService.receive();
    }

}
