
package com.ts.compendium.telegram.bot.geocoding.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Result {

    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents;
    @JsonProperty("formatted_address")
    private String formattedAddress;

    private Geometry geometry;
    @JsonProperty("place_id")
    private String placeId;

    private List<String> types;
}
