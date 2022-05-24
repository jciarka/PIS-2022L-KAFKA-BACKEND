package com.PIS2022L.kafkaconsumerapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DhlAggregatedItemsDTO
{
    private List<DhlAggregatedItemDTO> items;
}
