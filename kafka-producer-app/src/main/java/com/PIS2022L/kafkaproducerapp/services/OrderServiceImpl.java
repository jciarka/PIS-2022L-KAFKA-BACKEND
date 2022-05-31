package com.PIS2022L.kafkaproducerapp.services;

import com.PIS2022L.kafkaordermodels.domain.KafkaTopic;
import com.PIS2022L.kafkaordermodels.domain.dhl.KafkaDhlTransportOrder;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;
import com.PIS2022L.kafkaproducerapp.mappings.DhlOrderMapper;
import com.PIS2022L.kafkaproducerapp.mappings.OrderMapper;
import com.PIS2022L.kafkaproducerapp.models.dtos.DhlOrderDto;
import com.PIS2022L.kafkaproducerapp.models.dtos.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService
{
    private final KafkaTemplate<String, KafkaSelgrosOrder> selgrosOrderKafkaTemplate;
    private final KafkaTemplate<String, KafkaDhlTransportOrder> dhlOrderKafkaTemplate;

    @Autowired
    public OrderServiceImpl(final KafkaTemplate<String, KafkaSelgrosOrder> kafkaTemplate,
                            KafkaTemplate<String, KafkaDhlTransportOrder> dhlOrderKafkaTemplate)
    {
        this.selgrosOrderKafkaTemplate = kafkaTemplate;
        this.dhlOrderKafkaTemplate = dhlOrderKafkaTemplate;
    }

    @Override
    public void handleSelgrosOrder(final OrderDTO orderDTO)
    {
        final KafkaSelgrosOrder kafkaSelgrosOrder = OrderMapper.INSTANCE.orderDTOToKafkaSelgrosOrder(orderDTO);
        selgrosOrderKafkaTemplate.send(KafkaTopic.SELGROS, kafkaSelgrosOrder);
    }

    @Override
    public void handleDhlOrder(DhlOrderDto orderDTO) {
        final KafkaDhlTransportOrder kafkaDhlOrder = DhlOrderMapper.INSTANCE.orderDTOToKafkaDhlOrder(orderDTO);
        dhlOrderKafkaTemplate.send(KafkaTopic.DHL, kafkaDhlOrder);
    }
}
