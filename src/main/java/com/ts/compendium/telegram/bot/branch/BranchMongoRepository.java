package com.ts.compendium.telegram.bot.branch;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

interface BranchMongoRepository extends MongoRepository<BranchEntity, String> {

    Optional<BranchEntity> findByPartnerIdAndCode(String partnerId, String code);

    List<BranchEntity> findByPartnerId(String partnerId);

    List<BranchEntity> findByPartnerIdAndOkpo(String partnerId, String okpo);

    void deleteByPartnerIdAndCode(String partnerId, String code);
}
