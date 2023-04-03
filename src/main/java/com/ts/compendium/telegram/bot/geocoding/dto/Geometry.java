
package com.ts.compendium.telegram.bot.geocoding.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Geometry {

    private Bounds bounds;

    private Location location;

    @JsonProperty("location_type")
    private String locationType;

    private Viewport viewport;
}
