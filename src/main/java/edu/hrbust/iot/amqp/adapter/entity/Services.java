package edu.hrbust.iot.amqp.adapter.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Services {
    private String serviceId;
    private Properties properties;
    private Date eventTime;
}
