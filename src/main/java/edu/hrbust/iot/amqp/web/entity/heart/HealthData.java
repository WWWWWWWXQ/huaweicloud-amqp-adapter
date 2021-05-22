package edu.hrbust.iot.amqp.web.entity.heart;

import edu.hrbust.iot.amqp.web.utils.common.base.BasePO;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class HealthData extends BasePO {

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
    private String uid;
    private String startTime;
    private String endTime;
    private int maxHeartRate;
    private int minHeartRate;
    private int averHeartRate;
    private String startDate;
    private String endDate;
    private Date eventTime;
}
