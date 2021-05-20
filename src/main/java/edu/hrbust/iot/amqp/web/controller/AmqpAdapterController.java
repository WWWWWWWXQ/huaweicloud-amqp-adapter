package edu.hrbust.iot.amqp.web.controller;

import edu.hrbust.iot.amqp.web.entity.AmqpQuery;
import edu.hrbust.iot.amqp.web.utils.common.WebResponse;
import edu.hrbust.iot.amqp.web.entity.AmqpRecordDTO;
import edu.hrbust.iot.amqp.web.entity.AmqpRecordVO;
import edu.hrbust.iot.amqp.web.service.AmqpAdapterService;
import edu.hrbust.iot.amqp.web.utils.common.page.Page;
import edu.hrbust.iot.amqp.web.utils.common.page.PageDTO;
import edu.hrbust.iot.amqp.web.utils.converter.AmqpWebConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/amqp")
public class AmqpAdapterController  {

    @Autowired
    private AmqpAdapterService amqpAdapterService;

    @Autowired
    private AmqpWebConverter amqpWebConverter;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public WebResponse<List<AmqpRecordVO>> queryAll(){
        List<AmqpRecordDTO> amqpRecordDTOS = amqpAdapterService.queryAll();
        if (amqpRecordDTOS.isEmpty()){
            return WebResponse.success(Collections.emptyList());
        }
        return WebResponse.success(amqpWebConverter.toTargetList(amqpRecordDTOS));
    }

    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    public WebResponse<Page<AmqpRecordVO>> queryPage(@RequestBody(required = false) AmqpQuery amqpQuery){
        PageDTO<AmqpRecordDTO> res = amqpAdapterService.queryPage(amqpQuery);
        return WebResponse.success(Page.from(res, dto -> amqpWebConverter.toTarget(dto)));
    }
}
