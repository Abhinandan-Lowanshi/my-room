package com.myroom.myroom.app.searhelper;

import com.myroom.myroom.app.home.RoomDetailsData;

import java.util.ArrayList;

public class RecentSearchManager {
    public RecentSearchManager(String key, String creatTime, Double lat, Double lon, int id, ArrayList<RoomDetailsData> roomDetailsData) {
        this.key = key;
        this.creatTime = creatTime;
        this.lat = lat;
        this.lon = lon;
        this.id = id;
        this.roomDetailsData = roomDetailsData;
    }

    public RecentSearchManager() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<RoomDetailsData> getRoomDetailsData() {
        return roomDetailsData;
    }

    public void setRoomDetailsData(ArrayList<RoomDetailsData> roomDetailsData) {
        this.roomDetailsData = roomDetailsData;
    }

    private  String key , creatTime;
    private  Double lat,lon;
    private  int id ;
    private ArrayList<RoomDetailsData> roomDetailsData;


}
