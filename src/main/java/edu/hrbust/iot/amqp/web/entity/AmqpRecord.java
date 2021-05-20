package edu.hrbust.iot.amqp.web.entity;

import edu.hrbust.iot.amqp.adapter.entity.Properties;
import edu.hrbust.iot.amqp.web.utils.common.base.BasePO;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class AmqpRecord extends BasePO {

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
