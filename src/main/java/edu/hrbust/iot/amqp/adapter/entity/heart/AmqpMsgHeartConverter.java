package edu.hrbust.iot.amqp.adapter.entity.heart;

import edu.hrbust.iot.amqp.adapter.entity.common.Body;
import edu.hrbust.iot.amqp.adapter.entity.common.Header;
import edu.hrbust.iot.amqp.adapter.entity.common.Services;
import edu.hrbust.iot.amqp.web.entity.heart.HealthDataDTO;
import org.springframework.stereotype.Component;

@Component
public class AmqpMsgHeartConverter {
    public HealthDataDTO convertToDTO(AmqpHealth healthData){
        HealthDataDTO dto = new HealthDataDTO();
        // build header
        Header header = healthData.getNotifyData().getHeader();
        dto.setAppId(header.getAppId());
        dto.setDeviceId(header.getDeviceId());
        dto.setNodeId(header.getNodeId());
        dto.setProductId(header.getProductId());
        dto.setGatewayId(header.getGatewayId());

        //build body
        Body<HealthProperties> body = healthData.getNotifyData().getBody();
        Services<HealthProperties> services =  body.getServices().get(0);
        dto.setServiceId(services.getServiceId());
        HealthProperties healthProperties = services.getProperties();

        dto.setUid(healthProperties.getHeaDataUser());
        dto.setStartTime(String.valueOf(healthProperties.getHeadRateTestTimeStart()));
        dto.setEndTime(String.valueOf(healthProperties.getHeadRateTestTimeEnd()));
        dto.setMaxHeartRate(healthProperties.getHeaDataHeartRateMax());
        dto.setMinHeartRate(healthProperties.getHeaDataHeartRateMin());
        dto.setAverHeartRate(healthProperties.getHeaDataHeartRateMedian());
        dto.setStartDate(String.valueOf(healthProperties.getHeadRateTestDateStart()));
        dto.setEndDate(String.valueOf(healthProperties.getHeadRateTestDateEnd()));

        dto.setEventTime(services.getEventTime());

        return dto;
    }
}
