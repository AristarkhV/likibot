
package com.ts.compendium.telegram.bot.geocoding.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Bounds {

    @JsonProperty
    private Northeast northeast;

    @JsonProperty
    private Southwest southwest;
}
