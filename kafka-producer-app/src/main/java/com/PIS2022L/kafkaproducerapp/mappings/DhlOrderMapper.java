package com.PIS2022L.kafkaproducerapp.mappings;

import com.PIS2022L.kafkaordermodels.domain.dhl.KafkaDhlTransportOrder;
import com.PIS2022L.kafkaproducerapp.models.dtos.DhlOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Objects;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface DhlOrderMapper
{
    DhlOrderMapper INSTANCE = Mappers.getMapper(DhlOrderMapper.class);

    @Mapping(source = "remarks", target = "remarks", qualifiedByName = "checkRemarks")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    KafkaDhlTransportOrder orderDTOToKafkaDhlOrder(DhlOrderDto orderDTO);

    @Named("checkRemarks")
    static String checkRemarks(final String remarksFromDTO)
    {
        return Objects.nonNull(remarksFromDTO) ? remarksFromDTO : "None";
    }


}
