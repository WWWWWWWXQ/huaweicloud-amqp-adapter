package edu.hrbust.iot.amqp.web.service.impl;

import edu.hrbust.iot.amqp.web.dao.HealthDataRepository;
import edu.hrbust.iot.amqp.web.entity.AmqpQuery;
import edu.hrbust.iot.amqp.web.entity.heart.HealthData;
import edu.hrbust.iot.amqp.web.entity.heart.HealthDataDTO;
import edu.hrbust.iot.amqp.web.service.HealthService;
import edu.hrbust.iot.amqp.web.utils.common.page.PageDTO;
import edu.hrbust.iot.amqp.web.utils.converter.HealthPDConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
@Component
public class DefaultHealthService implements HealthService {

    @Autowired
    private HealthDataRepository repository;

    @Autowired
    private HealthPDConverter converter;


    @Override
    public PageDTO<HealthDataDTO> queryPage(AmqpQuery amqpQuery) {
        PageDTO<HealthDataDTO> res = PageDTO.emptyPage();
        Specification<HealthData> queryCondition = buildSpecification(amqpQuery);
        PageRequest pageRequest = PageRequest.of(amqpQuery.getIndex()-1, amqpQuery.getPageSize());
        log.info("{}, index:{}, pageSize:{}", LocalDateTime.now(), amqpQuery.getIndex(), amqpQuery.getPageSize());

        Page<HealthData> amqpRecordPage = repository.findAll(queryCondition, pageRequest);
        res.setTotalPage(amqpRecordPage.getTotalPages());
        res.setTotal(amqpRecordPage.getTotalElements());
        res.setData(converter.toTargetList(amqpRecordPage.getContent()));

        log.info("{},page:{}", LocalDateTime.now(), res);
        return res;
    }

    private Specification<HealthData> buildSpecification(AmqpQuery amqpQuery) {
        return (root, criteriaQuery, criteriaBuilder) ->{
            List<Predicate> predicateList = new ArrayList<>();
            return criteriaBuilder.and(predicateList.toArray(new javax.persistence.criteria.Predicate[0]));
        };
    }

    @Override
    public void save(HealthDataDTO healthDataDTO) {
        if (healthDataDTO.getUid() == null) healthDataDTO.setUid("1714030119");
        healthDataDTO.setId(null);
        healthDataDTO.setCreatedTime(new Date());
        repository.save(converter.toSource(healthDataDTO));
    }


}
