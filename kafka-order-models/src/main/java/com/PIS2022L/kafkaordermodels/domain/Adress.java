package com.PIS2022L.kafkaordermodels.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adress {
    private String countryCode;
    private String city;
    private String postalCode;
    private String street;
    private String buildingNumber;
    private String flatNumber;
}
