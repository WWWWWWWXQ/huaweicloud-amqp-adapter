package edu.hrbust.iot.amqp.web;

import edu.hrbust.iot.amqp.HuaWeiCloudAmqpAdapterApplication;
import edu.hrbust.iot.amqp.adapter.consumer.QpidJmsTemplate;
import edu.hrbust.iot.amqp.adapter.consumer.HealthConsumer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = HuaWeiCloudAmqpAdapterApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
class HuaweiCloudAmqpAdapterApplicationTests {

    @Autowired
    HealthConsumer qpidConsumer;

    @Test
    public void pureStringTest(){
        System.out.println("pureStringTest");
        qpidConsumer.receiveHealthData();
    }

//    @Test
//    public void pureTest(){
//        System.out.println("pureTest");
//        qpidConsumer.receive();
//    }
//
//    @Test
//    public void parseTest(){
//        String s = "{\"resource\":\"device.property\",\"event\":\"report\",\"event_time\":\"20210518T025047Z\",\"notify_data\":{\"header\":{\"app_id\":\"e6c196d40df84846bacbd9573dabb685\",\"device_id\":\"5fd6df9937f2a30303b55693_863434047705329\",\"node_id\":\"863434047705329\",\"product_id\":\"5fd6df9937f2a30303b55693\",\"gateway_id\":\"5fd6df9937f2a30303b55693_863434047705329\"},\"body\":{\"services\":[{\"service_id\":\"Sensor\",\"properties\":{\"luminance\":215,\"newinttest\":520,\"newstr\":\"ONOK\"},\"event_time\":\"20210518T025047Z\"}]}}}";
//        Bear bear = JSON.parseObject(s, Bear.class);
//        System.out.println(s);
//        System.out.println(bear);
//        qpidConsumer.save(bear);
//    }

}
