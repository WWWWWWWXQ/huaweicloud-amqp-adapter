package edu.hrbust.iot.amqp.web.utils.common.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Long id;

    protected Date createdTime;

}
