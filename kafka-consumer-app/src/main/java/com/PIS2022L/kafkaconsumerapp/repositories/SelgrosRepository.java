package com.PIS2022L.kafkaconsumerapp.repositories;

import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.models.ProductAggregatedModel;
import com.PIS2022L.kafkaconsumerapp.models.PurchaserAggregatedModel;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

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
    List<MongoSelgrosOrder> findByFilters(LocalDateTime dateFrom, LocalDateTime dateTo, Long purchasersCode, String ean);

    @Aggregation(pipeline = {
        "{ $match: {  " +
                "$and: [ " +
                    "{ $or : [ { $expr: { $eq: ['?0', 'null'] } }, { 'receivedAt': {$gt: ?0} } ] }," +
                    "{ $or : [ { $expr: { $eq: ['?1', 'null'] } }, { 'receivedAt': {$lt: ?1} } ] }," +
                "]" +
            "} " +
        "} ",
        "{ $group:  { " +
            "_id: $purchasersCode, " +
            "purchasersCode: { $first: $purchasersCode }, " +
            "deliveryAddress: { $first: $deliveryAddress }, " +
            "contactPhone: { $first: $contactPhone }, " +
            "total: { $sum:  1  }, " +
            "itmesLists: { $push: $items } " +
            "} " +
        "}",
        "{ $project:  { " +
            "purchasersCode: $purchasersCode, " +
            "deliveryAddress: $deliveryAddress, " +
            "contactPhone: $contactPhone, " +
            "total:  $total, " +
            "items: { $reduce: { " +
                "input: $itmesLists, " +
                "initialValue: [], " +
                "in: {$setUnion : ['$$value', '$$this']} " +
            "}} " +
        "}}",
        "{$sort:{ 'total': -1 } }",
        "{" +
            "$limit: ?2" +
        "}"
    })
    List<PurchaserAggregatedModel> findTopPurchasersByOrdersCount(LocalDateTime dateFrom, LocalDateTime dateTo, @Param("limit") Integer limit);

    @Aggregation(pipeline = {
            "{ $match: {  " +
                    "$and: [ " +
                        "{ $or : [ { $expr: { $eq: ['?0', 'null'] } }, { 'receivedAt': {$gt: ?0} } ] }," +
                        "{ $or : [ { $expr: { $eq: ['?1', 'null'] } }, { 'receivedAt': {$lt: ?1} } ] }," +
                    "]" +
                "} " +
            "} ",
            "{$unwind: { " +
                "path: $items, " +
                "preserveNullAndEmptyArrays: false " +
                "} " +
            "} ",
            "{ $group:  { " +
                    "_id: $purchasersCode, " +
                    "purchasersCode: { $first: $purchasersCode }, " +
                    "deliveryAddress: { $first: $deliveryAddress }, " +
                    "contactPhone: { $first: $contactPhone }, " +
                    "total: { $sum:  '$items.quantity'  }, " +
                    "items: { $push: $items } " +
                "} " +
            "}",
            "{$sort:{ 'total': -1 } }",
            "{" +
                    "$limit: ?2" +
            "}"
    })
    List<PurchaserAggregatedModel> findTopPurchasersByProductsCount(LocalDateTime dateFrom, LocalDateTime dateTo, @Param("limit") Integer limit);


    @Aggregation(pipeline = {
            "{ $match: {  " +
                    "$and: [ " +
                        "{ $or : [ { $expr: { $eq: ['?0', 'null'] } }, { 'receivedAt': {$gt: ?0} } ] }," +
                        "{ $or : [ { $expr: { $eq: ['?1', 'null'] } }, { 'receivedAt': {$lt: ?1} } ] }," +
                    "]" +
                "} " +
            "} ",
            "{$unwind: { " +
                    "path: $items, " +
                    "preserveNullAndEmptyArrays: false " +
                "} " +
            "} ",
            "{ $group:  { " +
                    "_id: '$items.ean', " +
                    "ean: { $first: '$items.ean' }," +
                    "itemsTotal: { $sum:  '$items.quantity'  }, " +
                    "ordersTotal: { $sum:  1  }, " +
                    "orders: { $push: " +
                        "{ " +
                            "purchasersCode: $purchasersCode, " +
                            "deliveryAddress: $deliveryAddress, " +
                            "contactPhone: $contactPhone, " +
                            "createdAt: $createdAt, " +
                            "receivedAt: $receivedAt, " +
                            "remarks: $remarks, " +
                        "} " +
                    "}," +
                "}, "+
            "}",
            "{$sort:{ 'itemsTotal': -1 } }",
            "{" +
                "$limit: ?2" +
            "}"
    })
    List<ProductAggregatedModel> findTopProducts(LocalDateTime dateFrom, LocalDateTime dateTo, @Param("limit") Integer limit);
}
