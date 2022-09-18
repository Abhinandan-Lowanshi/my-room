package com.example.myroom.app.customlatModel;

public class CustomeLatLon {
    double lat = 0.0 ;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    double lon = 0.0;
    String city = "";

    public CustomeLatLon() {
    }

    public CustomeLatLon(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }


}
