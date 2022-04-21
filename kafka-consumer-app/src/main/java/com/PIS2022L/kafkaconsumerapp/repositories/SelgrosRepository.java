package com.PIS2022L.kafkaconsumerapp.repositories;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosItem;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SelgrosRepository extends MongoRepository<MongoSelgrosOrder, String>
{
    @Query("{purchasersCode: ?0}")
    List<MongoSelgrosOrder> findByPurchasersCode(Integer code);

    List<MongoSelgrosItem> getAllItemsForTimePeriod(Long dateFrom, Long dateTo);

}
