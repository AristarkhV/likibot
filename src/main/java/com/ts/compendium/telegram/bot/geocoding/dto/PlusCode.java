
package com.ts.compendium.telegram.bot.geocoding.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlusCode {

    @JsonProperty("compound_code")
    private String compoundCode;

    @JsonProperty("global_code")
    private String globalCode;
}
