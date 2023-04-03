package com.ts.compendium.telegram.bot.city;

import com.ts.compendium.telegram.bot.drug.Locale;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.ts.compendium.telegram.bot.drug.Locale.UK;


@Data
@Document
@CompoundIndexes(@CompoundIndex(name = "uaName_uaDistrictName", def = "{'uaName' : 1, 'uaDistrictName': 1}", unique = true))
public class CityEntity {

    @Id
    private String id;

    @Indexed(unique = true, sparse = true)
    private String externalId;

    @Indexed
    private String uaName;

    @Indexed
    private String ruName;

    private String uaDistrictName;

    private String ruDistrictName;

    private String uaRegionName;

    private String ruRegionName;

    /**
     * "місто"
     * "село"
     * "селище"
     * "селище міського типу"
     */
    private String uaType;

    private String ruType;

    private String type;

    /**
     * Города, который отображаются в первую очередь
     */
    private Boolean main;

    @GeoSpatialIndexed
    private double[] location;

    public double latitude;

    public double longitude;

    public String localizedName(Locale locale) {
        return UK.equals(locale) ? uaName : ruName;
    }

    public String localizedDistrictName(Locale locale) {
        return UK.equals(locale) ? uaDistrictName : ruDistrictName;
    }
}
