package edu.hrbust.iot.amqp.web.controller;

import edu.hrbust.iot.amqp.web.entity.AmqpQuery;
import edu.hrbust.iot.amqp.web.entity.heart.HealthDataDTO;
import edu.hrbust.iot.amqp.web.entity.heart.HealthDataVO;
import edu.hrbust.iot.amqp.web.service.HealthService;
import edu.hrbust.iot.amqp.web.utils.common.WebResponse;
import edu.hrbust.iot.amqp.web.utils.common.page.Page;
import edu.hrbust.iot.amqp.web.utils.common.page.PageDTO;
import edu.hrbust.iot.amqp.web.utils.converter.HealthDVConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Health控制类
 */
@CrossOrigin
@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private HealthService healthService;

    @Autowired
    private HealthDVConverter converter;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public WebResponse<List<HealthDataVO>> query(){
        List<HealthDataDTO> healthDataDTOS = healthService.queryAll();
        return WebResponse.success(converter.toTargetList(healthDataDTOS));
    }

    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    public WebResponse<Page<HealthDataVO>> queryPage(@RequestBody(required = false) AmqpQuery amqpQuery){
        PageDTO<HealthDataDTO> pageDTO = healthService.queryPage(amqpQuery);
        return WebResponse.success(Page.from(pageDTO,dto ->converter.toTarget(dto)));
    }
}
