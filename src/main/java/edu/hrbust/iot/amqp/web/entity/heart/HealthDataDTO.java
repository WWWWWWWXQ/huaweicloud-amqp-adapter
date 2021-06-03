package edu.hrbust.iot.amqp.web.entity.heart;

import edu.hrbust.iot.amqp.web.utils.common.base.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthDataDTO extends BaseDTO {
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
