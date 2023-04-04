package com.ts.compendium.telegram.bot.order;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Note {

    private String text;

    private String author;

    private LocalDateTime updateTime;

    private OrderStatus orderStatus;

    private List<Item> items;
}