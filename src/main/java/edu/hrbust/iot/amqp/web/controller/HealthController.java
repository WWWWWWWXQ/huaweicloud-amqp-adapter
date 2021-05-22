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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private HealthService healthService;

    @Autowired
    private HealthDVConverter converter;

    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    public WebResponse<Page<HealthDataVO>> queryPage(@RequestBody(required = false) AmqpQuery amqpQuery){
        PageDTO<HealthDataDTO> pageDTO = healthService.queryPage(amqpQuery);
        return WebResponse.success(Page.from(pageDTO,dto ->converter.toTarget(dto)));
    }
}
