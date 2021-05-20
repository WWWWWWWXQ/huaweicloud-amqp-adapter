package edu.hrbust.iot.amqp.web.utils.common.base;

import lombok.Data;

import java.util.Date;

@Data
public class BaseVO {

    protected Long id;
    protected String userName;
    protected Date createdTime;
}
