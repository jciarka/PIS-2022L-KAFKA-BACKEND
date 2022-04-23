package com.PIS2022L.kafkaproducerapp.mappings;

import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;
import com.PIS2022L.kafkaproducerapp.models.dtos.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper
{
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    KafkaSelgrosOrder orderDTOToKafkaSelgrosOrder(OrderDTO orderDTO);
}
