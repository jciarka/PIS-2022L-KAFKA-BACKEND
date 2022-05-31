package com.PIS2022L.kafkaconsumerapp.repositories;

import com.PIS2022L.kafkaconsumerapp.domain.MongoDhlOrder;
import com.PIS2022L.kafkaconsumerapp.domain.MongoSelgrosOrder;
import com.PIS2022L.kafkaconsumerapp.models.ProductAggregatedModel;
import com.PIS2022L.kafkaconsumerapp.models.PurchaserAggregatedModel;
import com.PIS2022L.kafkaconsumerapp.models.dto.DhlAggregatedItemDTO;
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

    @Aggregation(pipeline = {
            "{$unwind: { " +
                    "path: $items, " +
                    "preserveNullAndEmptyArrays: false " +
                "}" +
            "}",
            "{ $match: {  " +
                    "$and: [ " +
                        "{ $or : [ { $expr: { $eq: ['?0', 'null'] } }, { 'receivedAt': {$gt: ?0} } ] }," +
                        "{ $or : [ { $expr: { $eq: ['?1', 'null'] } }, { 'receivedAt': {$lt: ?1} } ] }," +
                        "{ $or : [ { $expr: { $eq: ['?2', 'null'] } } , {'purchasersCode':  ?2} ] }," +
                        "{ $or : [ { $expr: { $eq: ['?3', 'null'] } }, { 'items.weight': {$gt: ?3} } ] }," +
                        "{ $or : [ { $expr: { $eq: ['?4', 'null'] } }, { 'items.weight': {$lt: ?4} } ] }," +
                        "{ $or : [ { $expr: { $eq: ['?5', 'null'] } }, { 'items.width': {$gt: ?5} } ] }," +
                        "{ $or : [ { $expr: { $eq: ['?6', 'null'] } }, { 'items.width': {$lt: ?6} } ] }," +
                        "{ $or : [ { $expr: { $eq: ['?7', 'null'] } }, { 'items.length': {$gt: ?7} } ] }," +
                        "{ $or : [ { $expr: { $eq: ['?8', 'null'] } }, { 'items.length': {$lt: ?8 } } ] }," +
                        "{ $or : [ { $expr: { $eq: ['?9', 'null'] } }, { 'items.height': {$gt: ?9} } ] }," +
                        "{ $or : [ { $expr: { $eq: ['?10', 'null'] } }, { 'items.height': {$lt: ?10 } } ] }," +
                    "]" +
                "}" +
            "}",
            "{ $project:  " +
                "{ " +
                    "id: $_id, " +
                    "purchasersCode: $purchasersCode, " +
                    "pickupAddress: $pickupAddress, " +
                    "deliveryAddress: $deliveryAddress, " +
                    "contactPhone: $contactPhone, " +
                    "createdAt: $createdAt, " +
                    "receivedAt: $receivedAt, " +
                    "requiredDeliveryDate: $requiredDeliveryDate, " +
                    "remarks: $remarks, " +
                    "weight: '$items.weight', " +
                    "width: '$items.width', " +
                    "length: '$items.length', " +
                    "height: '$items.height', " +
                "}" +
            "}",

    })
    List<DhlAggregatedItemDTO> findByFilters(LocalDateTime dateFrom, LocalDateTime dateTo, Long purchasersCode,
         Double weightFrom, Double weightTo, Double widthFrom, Double widthTo, Double lengthFrom, Double lengthTo, Double heightFrom, Double heightTo);
}