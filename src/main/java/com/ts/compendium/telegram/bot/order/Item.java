package com.ts.compendium.telegram.bot.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private String restCode;

    private Double quantity;

    private Double price;

    private Double discount;

    private String drugName;

    private String drugId;

    private String drugCategoryId;
}