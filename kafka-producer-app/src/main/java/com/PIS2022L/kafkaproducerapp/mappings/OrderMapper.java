package com.PIS2022L.kafkaproducerapp.mappings;

import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;
import com.PIS2022L.kafkaproducerapp.models.dtos.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

@Mapper
public interface OrderMapper
{
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "remarks", target = "remarks", qualifiedByName = "checkRemarks")
    KafkaSelgrosOrder orderDTOToKafkaSelgrosOrder(OrderDTO orderDTO);

    @Named("checkRemarks")
    static String checkRemarks(final String remarksFromDTO)
    {
        return Objects.nonNull(remarksFromDTO) ? remarksFromDTO : "None";
    }
}
