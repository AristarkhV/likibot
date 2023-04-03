package com.ts.compendium.telegram.bot.city;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toCollection;

@Service
@RequiredArgsConstructor
public class CityRepository {

    private final CityMongoRepository cityMongoRepository;

    private final MongoOperations mongoOperations;

    public CityEntity save(CityEntity cityEntity) {
        return cityMongoRepository.save(cityEntity);
    }

    public List<CityEntity> findCitiesByName(String cityName) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("ruName").regex(Pattern.compile(Pattern.quote(cityName), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where("uaName").regex(Pattern.compile(Pattern.quote(cityName), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
        ));
        return mongoOperations.find(query, CityEntity.class);
    }

    public CityEntity findByUaName(String uaName) {
        List<CityEntity> cityEntities = cityMongoRepository.findByUaName(uaName);
        if (!cityEntities.isEmpty()) {
            return cityEntities.get(0);
        }
        return null;
    }

    public CityEntity findByRuName(String uaName) {
        List<CityEntity> cityEntities = cityMongoRepository.findByRuName(uaName);
        if (!cityEntities.isEmpty()) {
            return cityEntities.get(0);
        }
        return null;
    }

    @Cacheable("CityRepository.cachedFindAll")
    public List<CityEntity> cachedFindAll() {
        return cityMongoRepository.findAll();
    }

    @Cacheable("CityRepository.findAllCityUaNames")
    public List<String> findAllCityUaNames() {
        Query query = new Query();
        query.fields().include("uaName");
        List<CityEntity> cityEntities = mongoOperations.find(query, CityEntity.class);
        return cityEntities.stream().map(CityEntity::getUaName).collect(toCollection(ArrayList::new));
    }
}
