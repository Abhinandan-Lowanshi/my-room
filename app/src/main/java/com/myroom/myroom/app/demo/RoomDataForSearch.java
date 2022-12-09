package com.myroom.myroom.app.demo;

public class RoomDataForSearch {

    String size,rent,ratting,name,address;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getRatting() {
        return ratting;
    }

    public void setRatting(String ratting) {
        this.ratting = ratting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RoomDataForSearch(String size, String rent, String ratting, String name, String address) {
        this.size = size;
        this.rent = rent;
        this.ratting = ratting;
        this.name = name;
        this.address = address;
    }
}
