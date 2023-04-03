package com.ts.compendium.telegram.bot.city;

import lombok.Data;

@Data
public class City {

    private String name;

    private String districtName;

    private String latitude;

    private String longitude;

    private boolean nearest;

    private Double distance;
}
