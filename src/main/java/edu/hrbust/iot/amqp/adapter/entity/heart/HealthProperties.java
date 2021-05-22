package edu.hrbust.iot.amqp.adapter.entity.heart;

import edu.hrbust.iot.amqp.adapter.entity.common.Properties;
import lombok.Data;

@Data
public class HealthProperties extends Properties {
    private String heaDataUser;
    private int HeaDataHeartRateMin;
    private int HeaDataHeartRateMax;
    private int HeaDataHeartRateMedian;
    private long HeadRateTestDateStart;
    private long HeadRateTestDateEnd;
    private long HeadRateTestTimeStart;
    private long HeadRateTestTimeEnd;
}
