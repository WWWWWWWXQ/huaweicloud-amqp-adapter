package edu.hrbust.iot.amqp.web;

import edu.hrbust.iot.amqp.HuaWeiCloudAmqpAdapterApplication;
import edu.hrbust.iot.amqp.web.controller.BearController;
import edu.hrbust.iot.amqp.web.controller.HealthController;
import edu.hrbust.iot.amqp.web.entity.AmqpQuery;
import edu.hrbust.iot.amqp.web.entity.bear.BearRecordVO;
import edu.hrbust.iot.amqp.web.entity.heart.HealthData;
import edu.hrbust.iot.amqp.web.entity.heart.HealthDataVO;
import edu.hrbust.iot.amqp.web.utils.common.page.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = HuaWeiCloudAmqpAdapterApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AmqpWebTest {

    @Autowired
    HealthController controller;

    @Test
    public void pageTest(){
        AmqpQuery amqpQuery = new AmqpQuery();
        amqpQuery.setIndex(1);
        amqpQuery.setPageSize(20);
        Page<HealthDataVO> data = controller.queryPage(amqpQuery).getData();
        data.getData().forEach(System.out::println);
    }

}
