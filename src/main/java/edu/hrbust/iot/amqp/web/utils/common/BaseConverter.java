package edu.hrbust.iot.amqp.web.utils.common;

import com.sun.istack.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface BaseConverter<SOURCE, TARGET>{

    @Nullable
    default SOURCE toSource(TARGET target){
        if (target == null) return null;

        SOURCE source = null;
        try {
            Type type = this.getClass().getGenericInterfaces()[0];
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class tempClass = (Class) parameterizedType.getActualTypeArguments()[0];
            source = (SOURCE) tempClass.getConstructor().newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        BeanUtils.copyProperties(target, source);
        return source;
    }

    @Nullable
    default TARGET toTarget(SOURCE source){
        if (source == null) return null;

        TARGET target = null;
        try {
            Type type = this.getClass().getGenericInterfaces()[0];
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class tempClass = (Class) parameterizedType.getActualTypeArguments()[0];
            target = (TARGET) tempClass.getConstructor().newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        BeanUtils.copyProperties(source, target);
        return target;
    }

    @Nullable
    default List<TARGET> toTargetList(List<SOURCE> sources){
        if (CollectionUtils.isEmpty(sources)){
            return Collections.emptyList();
        }
        return sources.stream().map(this::toTarget).collect(Collectors.toList());
    }

    @Nullable
    default List<SOURCE> toSourceList(List<TARGET> targets){
        if (CollectionUtils.isEmpty(targets)){
            return Collections.emptyList();
        }
        return targets.stream().map(this::toSource).collect(Collectors.toList());
    }
}
