package com.ts.compendium.telegram.bot.order;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface OrderMongoRepository extends MongoRepository<OrderEntity, String> {

    List<OrderEntity> findByUserPhone(String userPhone);
}
