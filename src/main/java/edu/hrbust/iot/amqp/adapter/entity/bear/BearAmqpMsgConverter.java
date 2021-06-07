package edu.hrbust.iot.amqp.adapter.entity.bear;

import edu.hrbust.iot.amqp.adapter.entity.common.utils.Body;
import edu.hrbust.iot.amqp.adapter.entity.common.utils.Header;
import edu.hrbust.iot.amqp.adapter.entity.common.utils.Services;
import edu.hrbust.iot.amqp.web.entity.bear.BearRecordDTO;
import org.springframework.stereotype.Component;

@Component
public class BearAmqpMsgConverter {
    public BearRecordDTO convertToDTO(BearAmqpMsg bear){
        BearRecordDTO dto = new BearRecordDTO();

        // build header
        Header header = bear.getNotifyData().getHeader();
        dto.setAppId(header.getAppId());
        dto.setDeviceId(header.getDeviceId());
        dto.setNodeId(header.getNodeId());
        dto.setProductId(header.getProductId());
        dto.setGatewayId(header.getGatewayId());

        // build body
        Body<BearProperties> body =  bear.getNotifyData().getBody();
        Services<BearProperties> services = body.getServices().get(0);

        // build properties
        dto.setServiceId(services.getServiceId());
        BearProperties bearProperties = services.getProperties();
        dto.setLuminance(bearProperties.getLuminance());
        dto.setNewinttest(bearProperties.getNewinttest());
        dto.setNewstr(bearProperties.getNewstr());
        dto.setEventTime(services.getEventTime());

        return dto;
    }
}
