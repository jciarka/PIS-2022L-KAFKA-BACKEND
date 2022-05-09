package com.PIS2022L.kafkaproducerapp.mappings;

import com.PIS2022L.kafkaordermodels.domain.Address;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosItem;
import com.PIS2022L.kafkaordermodels.domain.selgros.KafkaSelgrosOrder;
import com.PIS2022L.kafkaproducerapp.models.dtos.OrderDTO;
import com.PIS2022L.kafkaproducerapp.utils.AbstractUnitTestTemplate;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderMapperTest extends AbstractUnitTestTemplate
{
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;
    private final LocalDateTime MOCK_DATE = LocalDateTime.parse("2000-01-01T00:00:00");

    @Test
    void orderDTOToKafkaSelgrosOrder()
    {
        final OrderDTO mockOrderDTO = new OrderDTO(
                12L,
                new Address(),
                600700800L,
                MOCK_DATE,
                List.of(new KafkaSelgrosItem("123", 2)),
                null
        );

        final KafkaSelgrosOrder selgrosOrder = orderMapper.orderDTOToKafkaSelgrosOrder(mockOrderDTO);

        assertEquals(12L, selgrosOrder.getPurchasersCode());
        assertEquals(new Address(), selgrosOrder.getDeliveryAddress());
        assertEquals(600700800L, selgrosOrder.getContactPhone());
        assertEquals(MOCK_DATE, selgrosOrder.getCreatedAt());
        assertEquals(List.of(new KafkaSelgrosItem("123", 2)), selgrosOrder.getItems());
        assertEquals("None", selgrosOrder.getRemarks());
    }
}

