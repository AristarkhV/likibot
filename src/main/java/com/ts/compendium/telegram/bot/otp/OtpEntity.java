package com.ts.compendium.telegram.bot.otp;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class OtpEntity {

    @Id
    private String id;

    @Indexed
    private String phone;

    @Indexed
    private String otp;

    @Indexed
    private Long chatId;

    private LocalDateTime lastUpdateDate;
}
