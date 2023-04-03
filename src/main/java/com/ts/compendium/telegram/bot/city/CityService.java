package com.ts.compendium.telegram.bot.city;

import com.ts.compendium.telegram.bot.drug.Locale;
import com.ts.compendium.telegram.bot.geocoding.GoogleGeocodingClient;
import com.ts.compendium.telegram.bot.geocoding.dto.GoogleGeocodingResponse;
import com.ts.compendium.telegram.bot.geocoding.dto.Result;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Iterables.isEmpty;

@Service
@AllArgsConstructor
@Slf4j
public class CityService {

    private final GoogleGeocodingClient googleGeocodingClient;

    public City cityByCoordinates(Locale locale, Double latitude, Double longitude) {
        City city = new City();
        GoogleGeocodingResponse geocodingResponse = googleGeocodingClient.getInfo(latitude, longitude, "UK");
        List<Result> results = geocodingResponse.getResults();
        if(!isEmpty(results)) {
            city.setName(results.get(0).getAddressComponents().get(0).getShortName());
            city.setLatitude(String.valueOf(latitude));
            city.setLongitude(String.valueOf(longitude));
        }
        return city;
    }
}
