package com.example.myroom.app.demo;

public class RoomData {
    double lat,log;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public RoomData(double lat, double log, String city, String colony, String owner_name, String mobile, String user_id) {
        this.lat = lat;
        this.log = log;
        this.city = city;
        this.colony = colony;
        this.owner_name = owner_name;
        this.mobile = mobile;
        this.room_id = user_id;
    }

    String city,colony,owner_name,mobile, room_id;
}
