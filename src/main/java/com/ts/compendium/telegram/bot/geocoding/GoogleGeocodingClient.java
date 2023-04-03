package com.ts.compendium.telegram.bot.geocoding;

import com.ts.compendium.telegram.bot.geocoding.dto.GoogleGeocodingResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleGeocodingClient {

    public static final String GOOGLE_API_KEY = "AIzaSyAaqMcGHnGpLCsTRb9Kiow3ZBK8ngmzSuo";

    private final RestTemplate restTemplate = new RestTemplate();

    public GoogleGeocodingResponse getInfo(Double latitude, Double longitude, String lang) {
        return restTemplate.getForObject("https://maps.googleapis.com/maps/api/geocode/json?" +
                        "latlng="+latitude+"," + longitude +
                        "&key=" + GOOGLE_API_KEY +
                        "&language=" + lang+
                        "&result_type=locality",
                GoogleGeocodingResponse.class);
    }
}
