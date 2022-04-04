package com.PIS2022L.kafkaconsumerapp.domain;


import com.PIS2022L.kafkaordermodels.domain.Adress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("selgros")
@NoArgsConstructor
@AllArgsConstructor
public class MongoSelgrosOrder {
    @Id
    private String id;
    private long purchasersCode;
    private Adress deliveryAddress;
    private Long contactPhone;
    private LocalDateTime createdAt;
    private LocalDateTime receivedAt;
    private List<MongoSelgrosItem> items;
    private String remarks;
}
