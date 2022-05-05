package com.PIS2022L.kafkaconsumerapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MongoSelgrosItem {
    private String ean;
    private Integer quantity;
}
