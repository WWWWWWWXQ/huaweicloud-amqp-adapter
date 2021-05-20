package edu.hrbust.iot.amqp.web.utils.common.page;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class Page<T> {
    public static final long MAX_TOTAL = 50000;
    public static final int MAX_PAGE = 5000;
    private List<T> data;
    private long total;
    private int totalPage;

    public static <T, R> Page<R> from(PageDTO<T> dtoPage, Function<T, R> function){
        if (Objects.isNull(dtoPage) || CollectionUtils.isEmpty(dtoPage.getData())){
            return new Page<R>().setData(Collections.emptyList());
        }
        List<R> data = dtoPage.getData().stream().map(function).collect(Collectors.toList());
        long total = Math.min(dtoPage.getTotal(), MAX_TOTAL);
        int totalPage = Math.min(dtoPage.getTotalPage(), MAX_PAGE);
        return new Page<R>()
                .setData(data)
                .setTotalPage(totalPage)
                .setTotal(total);
    }
}
