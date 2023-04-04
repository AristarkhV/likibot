package com.ts.compendium.telegram.bot.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchRepository {

    private final BranchMongoRepository branchMongoRepository;

    private final MongoOperations mongoOperations;

    public BranchEntity save(BranchEntity branchEntity) {
        return branchMongoRepository.save(branchEntity);
    }

    public List<BranchEntity> save(List<BranchEntity> branchEntities) {
        return branchMongoRepository.saveAll(branchEntities);
    }

    public Optional<BranchEntity> findByPartnerIdAndCode(String partnerId, String code) {
        return branchMongoRepository.findByPartnerIdAndCode(partnerId, code);
    }

    @Cacheable(value = "BranchEntity.findByPartnerIdAndCode", unless = "#result == null")
    public Optional<BranchEntity> cacheableFindByPartnerIdAndCode(String partnerId, String code) {
        return branchMongoRepository.findByPartnerIdAndCode(partnerId, code);
    }

    public List<BranchEntity> findByPartnerId(String partnerId) {
        return branchMongoRepository.findByPartnerId(partnerId);
    }
}
