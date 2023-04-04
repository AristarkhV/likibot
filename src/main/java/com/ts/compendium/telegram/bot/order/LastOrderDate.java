package com.ts.compendium.telegram.bot.order;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LastOrderDate {

    private String userPhone;

    private LocalDateTime createDate;
}
