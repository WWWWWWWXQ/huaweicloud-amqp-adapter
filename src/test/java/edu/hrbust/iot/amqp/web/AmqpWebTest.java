package edu.hrbust.iot.amqp.web;

import edu.hrbust.iot.amqp.HuaWeiCloudAmqpAdapterApplication;
import edu.hrbust.iot.amqp.web.controller.AmqpAdapterController;
import edu.hrbust.iot.amqp.web.entity.AmqpQuery;
import edu.hrbust.iot.amqp.web.entity.AmqpRecordDTO;
import edu.hrbust.iot.amqp.web.entity.AmqpRecordVO;
import edu.hrbust.iot.amqp.web.service.AmqpAdapterService;
import edu.hrbust.iot.amqp.web.utils.common.page.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.Date;

@SpringBootTest(classes = HuaWeiCloudAmqpAdapterApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AmqpWebTest {

    @Autowired
    AmqpAdapterController amqpAdapterController;

    @Test
    public void pageTest(){
        AmqpQuery amqpQuery = new AmqpQuery();
        amqpQuery.setIndex(1);
        amqpQuery.setPageSize(20);
        Page<AmqpRecordVO> data = amqpAdapterController.queryPage(amqpQuery).getData();
        data.getData().forEach(System.out::println);
    }

}
