package edu.hrbust.iot.amqp.adapter.base;

import edu.hrbust.iot.amqp.adapter.entity.Bear;
import edu.hrbust.iot.amqp.adapter.entity.Header;
import edu.hrbust.iot.amqp.adapter.entity.Services;
import edu.hrbust.iot.amqp.web.entity.AmqpRecordDTO;
import org.springframework.stereotype.Component;

@Component
public class AmqpMsgConverter {
    public AmqpRecordDTO convertToDTO(Bear bear){
        AmqpRecordDTO dto = new AmqpRecordDTO();
        // build header
        Header header = bear.getNotifyData().getHeader();
        dto.setAppId(header.getAppId());
        dto.setDeviceId(header.getDeviceId());
        dto.setNodeId(header.getNodeId());
        dto.setProductId(header.getProductId());
        dto.setGatewayId(header.getGatewayId());

        //build body
        Services services = bear.getNotifyData().getBody().getServices().get(0);
        dto.setServiceId(services.getServiceId());
        dto.setLuminance(services.getProperties().getLuminance());
        dto.setNewinttest(services.getProperties().getNewinttest());
        dto.setNewstr(services.getProperties().getNewstr());
        dto.setEventTime(services.getEventTime());

        return dto;
    }
}
