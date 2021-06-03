package edu.hrbust.iot.amqp.web.controller;

import edu.hrbust.iot.amqp.web.entity.AmqpQuery;
import edu.hrbust.iot.amqp.web.utils.common.WebResponse;
import edu.hrbust.iot.amqp.web.entity.bear.BearRecordDTO;
import edu.hrbust.iot.amqp.web.entity.bear.BearRecordVO;
import edu.hrbust.iot.amqp.web.service.BearService;
import edu.hrbust.iot.amqp.web.utils.common.page.Page;
import edu.hrbust.iot.amqp.web.utils.common.page.PageDTO;
import edu.hrbust.iot.amqp.web.utils.converter.BearDVConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/bear")
public class BearController {

    @Autowired
    private BearService amqpAdapterService;

    @Autowired
    private BearDVConverter converter;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public WebResponse<List<BearRecordVO>> queryAll(){
        List<BearRecordDTO> amqpRecordDTOS = amqpAdapterService.queryAll();
        if (amqpRecordDTOS.isEmpty()){
            return WebResponse.success(Collections.emptyList());
        }
        return WebResponse.success(converter.toTargetList(amqpRecordDTOS));
    }

    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    public WebResponse<Page<BearRecordVO>> queryPage(@RequestBody(required = false) AmqpQuery amqpQuery){
        PageDTO<BearRecordDTO> res = amqpAdapterService.queryPage(amqpQuery);
        return WebResponse.success(Page.from(res, dto -> converter.toTarget(dto)));
    }
}
