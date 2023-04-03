package com.ts.compendium.telegram.bot.utils;

public class Haversine {

    private Haversine() {

    }

    public static double distance(String lat1, String lon1, String lat2, String lon2) {
        return distance(formatCoordinate(lat1), formatCoordinate(lon1), formatCoordinate(lat2), formatCoordinate(lon2));
    }

    private static double formatCoordinate(String coordinate) {
        return Double.parseDouble(coordinate.replaceAll(",", "."));
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }
}
