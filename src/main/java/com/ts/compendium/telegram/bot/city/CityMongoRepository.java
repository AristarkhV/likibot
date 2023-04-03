package com.ts.compendium.telegram.bot.city;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface CityMongoRepository extends MongoRepository<CityEntity, String> {

    List<CityEntity> findByUaName(String uaName);

    List<CityEntity> findByRuName(String ruName);
}
