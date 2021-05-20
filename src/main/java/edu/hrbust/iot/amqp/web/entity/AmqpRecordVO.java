package edu.hrbust.iot.amqp.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmqpRecordVO implements Serializable {

    private Long id;
    private Date createdTime;
    private String userName;

    /**
     * body.services
     */
    private String serviceId;
    private Date eventTime;
    private int luminance;
    private int newinttest;
    private String newstr;
}
