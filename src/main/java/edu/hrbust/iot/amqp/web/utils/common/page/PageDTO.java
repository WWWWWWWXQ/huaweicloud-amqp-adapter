package edu.hrbust.iot.amqp.web.utils.common.page;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class PageDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<T> data;
    private long total;
    private int totalPage;

    public static <T> PageDTO<T> emptyPage(){
        PageDTO<T> pageDTO = new PageDTO<>();
        pageDTO.setData(Collections.emptyList());
        return pageDTO;
    }
}
