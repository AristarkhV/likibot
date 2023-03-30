package com.ts.compendium.telegram.bot.otp;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OtpRepository {

    private final OtpMongoRepository otpMongoRepository;
    private final MongoOperations mongoOperations;

    public void save(OtpEntity otpEntity) {
        otpMongoRepository.save(otpEntity);
    }

    public List<OtpEntity> find(String phoneNumber, String otp) {
        return otpMongoRepository.findByPhoneAndOtp(phoneNumber, otp);
    }

    public List<OtpEntity> findLastByChatId(Long chatId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("chatId").is(chatId))
                .with(Sort.by(Sort.Direction.DESC, "lastUpdateDate"))
                .limit(1);
        return mongoOperations.find(query, OtpEntity.class);
    }

    public long countByPhoneInLastHour(String phoneNumber) {
        return mongoOperations.count(
                new Query()
                        .addCriteria(Criteria.where("lastUpdateDate").gte(LocalDateTime.now().minusHours(1)))
                        .addCriteria(Criteria.where("phone").is(phoneNumber)),
                OtpEntity.class
        );
    }
}
