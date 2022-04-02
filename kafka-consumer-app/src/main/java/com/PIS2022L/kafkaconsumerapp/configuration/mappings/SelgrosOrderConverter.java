package com.PIS2022L.kafkaconsumerapp.configuration.mappings;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosItem;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface SelgrosOrderConverter extends Converter<MongoSelgrosOrder, KafkaSelgrosOrder> {
    public SelgrosOrderConverter INSTANCE = Mappers.getMapper(SelgrosOrderConverter.class);

    @Override
//    @Mapping(source = "purchasersCode",target = "purchasersCode")
    MongoSelgrosOrder convert(KafkaSelgrosOrder sourceOrder);
}
