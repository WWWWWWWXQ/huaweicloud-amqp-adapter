package edu.hrbust.iot.amqp.web.service.impl;

//import edu.hrbust.iot.amqp.adapter.QpidJmsTemplate;
import edu.hrbust.iot.amqp.web.dao.AmqpAdapterRepository;
import edu.hrbust.iot.amqp.web.entity.AmqpQuery;
import edu.hrbust.iot.amqp.web.utils.common.page.PageDTO;
import edu.hrbust.iot.amqp.web.utils.converter.AmqpConverter;
import edu.hrbust.iot.amqp.web.entity.AmqpRecordDTO;
import edu.hrbust.iot.amqp.web.entity.AmqpRecord;
import edu.hrbust.iot.amqp.web.service.AmqpAdapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
@Service
public class DefaultAmqpAdapterService implements AmqpAdapterService {

    @Autowired
    private AmqpAdapterRepository amqpAdapterRepository;

    @Autowired
    private AmqpConverter amqpConverter;

    @Override
    public List<AmqpRecordDTO> queryAll() {
        List<AmqpRecord> amqpRecords = amqpAdapterRepository.findAll();
        log.info("{},amqpRecords:{}", LocalDateTime.now(), amqpRecords);
        if (amqpRecords.isEmpty()){
            return Collections.emptyList();
        }
        return amqpConverter.toTargetList(amqpRecords);
    }

    @Override
    public PageDTO<AmqpRecordDTO> queryPage(AmqpQuery amqpQuery) {
        PageDTO<AmqpRecordDTO> res = PageDTO.emptyPage();

        Specification<AmqpRecord> queryCondition = buildSpecification(amqpQuery);
        PageRequest pageRequest = PageRequest.of(amqpQuery.getIndex()-1, amqpQuery.getPageSize());
        log.info("{}, index:{}, pageSize:{}", LocalDateTime.now(), amqpQuery.getIndex(), amqpQuery.getPageSize());

        Page<AmqpRecord> amqpRecordPage = amqpAdapterRepository.findAll(queryCondition, pageRequest);
        res.setTotalPage(amqpRecordPage.getTotalPages());
        res.setTotal(amqpRecordPage.getTotalElements());
        res.setData(amqpConverter.toTargetList(amqpRecordPage.getContent()));

//        log.info("{},page:{}", LocalDateTime.now(), res);
        return res;
    }

    private Specification<AmqpRecord> buildSpecification(AmqpQuery amqpQuery) {
        return (root, criteriaQuery, criteriaBuilder) ->{
            List<Predicate> predicateList = new ArrayList<>();
            return criteriaBuilder.and(predicateList.toArray(new javax.persistence.criteria.Predicate[0]));
        };
    }

    @Override
    public void save(AmqpRecordDTO record){
        if (record.getUserName() == null) record.setUserName("吴肖琪");
        record.setId(null);
        record.setCreatedTime(new Date());
        amqpAdapterRepository.save(amqpConverter.toSource(record));
    }


}
