package com.PIS2022L.kafkaconsumerapp.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;

@Data
@Document("selgros")
@NoArgsConstructor
@AllArgsConstructor
public class MongoSelgrosOrder {
    @Id
    private String id;

    private long purchasersCode;
    private List<MongoSelgrosItem> items;
}
