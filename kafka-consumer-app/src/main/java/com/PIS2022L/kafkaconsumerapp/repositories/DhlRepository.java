package com.PIS2022L.kafkaconsumerapp.repositories;

import com.PIS2022L.kafkaconsumerapp.domain.MongoDhlOrder;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.models.ProductAggregatedModel;
import com.PIS2022L.kafkaconsumerapp.models.PurchaserAggregatedModel;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DhlRepository extends MongoRepository<MongoDhlOrder, String>
{
    @Query("{purchasersCode: ?0}")
    List<MongoSelgrosOrder> findByPurchasersCode(Integer code);

    @Query(
        "{ " +
            "$and: [ " +
                "{ $or : [ { $expr: { $eq: ['?0', 'null'] } }, { 'receivedAt': {$gt: ?0} } ] }," +
                "{ $or : [ { $expr: { $eq: ['?1', 'null'] } }, { 'receivedAt': {$lt: ?1} } ] }," +
            "]" +
        "}"
    )
    List<MongoSelgrosOrder> findByReceivedAtBetween(LocalDateTime dateFrom, LocalDateTime dateTo);

    @Query(
        "{ " +
            "$and: [ " +
                "{ $or : [ { $expr: { $eq: ['?0', 'null'] } }, { 'receivedAt': {$gt: ?0} } ] }," +
                "{ $or : [ { $expr: { $eq: ['?1', 'null'] } }, { 'receivedAt': {$lt: ?1} } ] }," +
                "{ $or : [ { $where: '?2 == null' } , {'purchasersCode':  ?2} ] }," +
            "]" +
        "}"
    )
    List<MongoSelgrosOrder> findByFilters(LocalDateTime dateFrom, LocalDateTime dateTo, Long purchasersCode);
}
