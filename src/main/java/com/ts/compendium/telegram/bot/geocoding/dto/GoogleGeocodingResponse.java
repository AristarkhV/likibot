
package com.ts.compendium.telegram.bot.geocoding.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GoogleGeocodingResponse {

    @JsonProperty("plus_code")
    private PlusCode plusCode;

    private List<Result> results;

    private String status;
}
