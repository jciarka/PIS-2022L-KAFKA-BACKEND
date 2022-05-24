package com.PIS2022L.kafkaconsumerapp.mappings;

import com.PIS2022L.kafkaconsumerapp.domain.MongoDhlOrder;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaordermodels.domain.dhl.KafkaDhlTransportOrder;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface DhlOrderConverter extends Converter<MongoDhlOrder, KafkaDhlTransportOrder>
{
    DhlOrderConverter INSTANCE = Mappers.getMapper(DhlOrderConverter.class);

    @Override
    @Mapping(target = "receivedAt", expression = "java(LocalDateTime.now())")
    MongoDhlOrder convert(KafkaDhlTransportOrder sourceOrder);
}
