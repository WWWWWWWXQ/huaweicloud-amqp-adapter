package edu.hrbust.iot.amqp.web.entity.bear;

import edu.hrbust.iot.amqp.web.utils.common.base.BasePO;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "bear_record")
public class BearRecord extends BasePO {

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

    private String userName;
}
