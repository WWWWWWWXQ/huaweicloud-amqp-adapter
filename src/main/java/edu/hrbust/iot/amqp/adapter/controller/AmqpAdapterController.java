package edu.hrbust.iot.amqp.adapter.controller;

import edu.hrbust.iot.amqp.adapter.service.AmqpAdapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/amqp")
public class AmqpAdapterController {

    @Autowired
    private AmqpAdapterService amqpAdapterService;

    @RequestMapping(value = "/receive", method = RequestMethod.GET)
    public String receive(){
        amqpAdapterService.receive();
        return "test";
    }
}
