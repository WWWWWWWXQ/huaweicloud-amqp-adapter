package edu.hrbust.iot.amqp.web.utils.common;


public abstract class BaseController<DTO, VO> {

    protected class ControllerConverter implements BaseConverter<DTO, VO>{};

    protected ControllerConverter controllerConverter = new ControllerConverter();

}
