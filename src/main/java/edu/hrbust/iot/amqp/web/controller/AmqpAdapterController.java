package edu.hrbust.iot.amqp.web.controller;

import edu.hrbust.iot.amqp.web.utils.common.BaseController;
import edu.hrbust.iot.amqp.web.utils.common.WebResponse;
import edu.hrbust.iot.amqp.web.entity.AmqpRecordDTO;
import edu.hrbust.iot.amqp.web.entity.AmqpRecordVO;
import edu.hrbust.iot.amqp.web.service.AmqpAdapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/amqp")
public class AmqpAdapterController extends BaseController<AmqpRecordDTO, AmqpRecordVO> {

    @Autowired
    private AmqpAdapterService amqpAdapterService;

//    @RequestMapping(value = "/receive", method = RequestMethod.GET)
//    public WebResponse<String> receive(){
//        amqpAdapterService.receive();
//        return WebResponse.success("success");
//    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public WebResponse<List<AmqpRecordVO>> queryAll(){
        List<AmqpRecordDTO> amqpRecordDTOS = amqpAdapterService.queryAll();
        return WebResponse.success(controllerConverter.toTargetList(amqpRecordDTOS));
    }
}
