package edu.hrbust.iot.amqp.web.entity;

import edu.hrbust.iot.amqp.web.utils.common.base.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmqpRecordDTO extends BaseDTO {
    /**
     * header
     */
    private String appId;
    private String deviceId;
    private String nodeId;
    private String productId;
    private String gatewayId;


    /**
     * body.services
     */
    private String serviceId;
    private int luminance;
    private int newinttest;
    private String newstr;
    private Date eventTime;
}
