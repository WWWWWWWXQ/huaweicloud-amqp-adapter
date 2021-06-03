package edu.hrbust.iot.amqp.web.entity.heart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthDataVO implements Serializable {

    private Long id;
    private Date createdTime;

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
