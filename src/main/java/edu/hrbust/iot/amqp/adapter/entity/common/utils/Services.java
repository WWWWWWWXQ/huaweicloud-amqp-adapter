package edu.hrbust.iot.amqp.adapter.entity.common.utils;

import edu.hrbust.iot.amqp.adapter.entity.common.Properties;
import lombok.Data;

import java.util.Date;

@Data
public class Services<T extends Properties> {
    private String serviceId;
    private  T properties;
    private Date eventTime;
}
