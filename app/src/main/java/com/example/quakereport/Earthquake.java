package com.example.quakereport;

public class Earthquake {

    double magnitudeToShow;
    String placeNameToShow;
    long timeDateToShow;
    String jsonurl;

    public Earthquake(double magnitude, String placeName , long timeDate, String url) {
        magnitudeToShow = magnitude;
        placeNameToShow = placeName;
        timeDateToShow = timeDate;
        jsonurl = url;

    }

        public double getMagnitudeToShow() {
        return magnitudeToShow;
    }

    public String getplaceNameToShow() {
        return placeNameToShow;
    }

    public  long getTimeDateToShow() {
        return timeDateToShow;
    }

    public String getJsonurl() {
        return jsonurl;
    }
}

