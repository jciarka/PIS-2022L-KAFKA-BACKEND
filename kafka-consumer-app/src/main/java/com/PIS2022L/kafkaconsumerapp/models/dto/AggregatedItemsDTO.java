package com.PIS2022L.kafkaconsumerapp.models.dto;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AggregatedItemsDTO
{
    private List<AggregatedItemDTO> items;
}
