package edu.hrbust.iot.amqp.web.utils.common;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDTO {
    protected Long id;

    protected Date createdTime;
    protected String username;
}
