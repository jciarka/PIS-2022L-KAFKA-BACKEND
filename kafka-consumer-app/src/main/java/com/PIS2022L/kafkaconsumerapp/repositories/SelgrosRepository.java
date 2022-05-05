package com.PIS2022L.kafkaconsumerapp.repositories;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SelgrosRepository extends MongoRepository<MongoSelgrosOrder, String>
{
    @Query("{purchasersCode: ?0}")
    List<MongoSelgrosOrder> findByPurchasersCode(Integer code);

    @Query("{'receivedAt': {$gt: ?0}}")
    List<MongoSelgrosOrder> test(Integer test);

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
                "{ $or : [ { $where: '?3 == null' } , { 'items': { $elemMatch: { 'ean' :  ?3 } } }] }" +
            "]" +
        "}"
    )
    List<MongoSelgrosOrder> findByFiltes(LocalDateTime dateFrom, LocalDateTime dateTo, Long purchasersCode, String ean);

}
