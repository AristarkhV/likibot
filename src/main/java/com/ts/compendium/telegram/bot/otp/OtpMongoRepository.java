package com.ts.compendium.telegram.bot.otp;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OtpMongoRepository extends MongoRepository<OtpEntity, String> {

    List<OtpEntity> findByPhoneAndOtp(String phoneNumber, String otp);

    List<OtpEntity> findByChatId(Long chatId);
}
