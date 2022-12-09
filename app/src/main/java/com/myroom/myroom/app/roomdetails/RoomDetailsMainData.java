package com.myroom.myroom.app.roomdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RoomDetailsMainData {
    @SerializedName("rm_pkey")
    @Expose
    private Integer rmPkey;
    @SerializedName("rm_usr_fkey")
    @Expose
    private Integer rmUsrFkey;
    @SerializedName("rm_own_Fullname")
    @Expose
    private String rmOwnFullname;
    @SerializedName("rm_own_mble_num")
    @Expose
    private String rmOwnMbleNum;
    @SerializedName("rm_size")
    @Expose
    private String rmSize;
    @SerializedName("rm_furnisd_status")
    @Expose
    private String rmFurnisdStatus;
    @SerializedName("rm_availble")
    @Expose
    private String rmAvailble;
    @SerializedName("rm_prking_avblity")
    @Expose
    private String rmPrkingAvblity;
    @SerializedName("rm_depndecy")
    @Expose
    private String rmDepndecy;
    @SerializedName("rm_flor")
    @Expose
    private String rmFlor;
    @SerializedName("rm_rent")
    @Expose
    private String rmRent;
    @SerializedName("rm_house_no")
    @Expose
    private String rmHouseNo;
    @SerializedName("rm_colny")
    @Expose
    private String rmColny;
    @SerializedName("rm_city")
    @Expose
    private String rmCity;
    @SerializedName("rm_state")
    @Expose
    private String rmState;
    @SerializedName("rm_latitude")
    @Expose
    private String rmLatitude;
    @SerializedName("rm_longitude")
    @Expose
    private String rmLongitude;
    @SerializedName("rm_description")
    @Expose
    private String rmDescription;
    @SerializedName("rm_status")
    @Expose
    private String rmStatus;
    @SerializedName("favorite_key")
    @Expose
    private Boolean favoriteKey;

    @SerializedName("images")
    @Expose
    private ArrayList<RoomDetailsImage> images = null;

    public Integer getRmPkey() {
        return rmPkey;
    }

    public void setRmPkey(Integer rmPkey) {
        this.rmPkey = rmPkey;
    }

    public Integer getRmUsrFkey() {
        return rmUsrFkey;
    }

    public void setRmUsrFkey(Integer rmUsrFkey) {
        this.rmUsrFkey = rmUsrFkey;
    }

    public String getRmOwnFullname() {
        return rmOwnFullname;
    }

    public void setRmOwnFullname(String rmOwnFullname) {
        this.rmOwnFullname = rmOwnFullname;
    }

    public String getRmOwnMbleNum() {
        return rmOwnMbleNum;
    }

    public void setRmOwnMbleNum(String rmOwnMbleNum) {
        this.rmOwnMbleNum = rmOwnMbleNum;
    }

    public String getRmSize() {
        return rmSize;
    }

    public void setRmSize(String rmSize) {
        this.rmSize = rmSize;
    }

    public String getRmFurnisdStatus() {
        return rmFurnisdStatus;
    }

    public void setRmFurnisdStatus(String rmFurnisdStatus) {
        this.rmFurnisdStatus = rmFurnisdStatus;
    }

    public String getRmAvailble() {
        return rmAvailble;
    }

    public void setRmAvailble(String rmAvailble) {
        this.rmAvailble = rmAvailble;
    }

    public String getRmPrkingAvblity() {
        return rmPrkingAvblity;
    }

    public void setRmPrkingAvblity(String rmPrkingAvblity) {
        this.rmPrkingAvblity = rmPrkingAvblity;
    }
    public Boolean getFavoriteKey() {
        return favoriteKey;
    }

    public void setFavoriteKey(Boolean favoriteKey) {
        this.favoriteKey = favoriteKey;
    }


    public String getRmDepndecy() {
        return rmDepndecy;
    }

    public void setRmDepndecy(String rmDepndecy) {
        this.rmDepndecy = rmDepndecy;
    }

    public String getRmFlor() {
        return rmFlor;
    }

    public void setRmFlor(String rmFlor) {
        this.rmFlor = rmFlor;
    }

    public String getRmRent() {
        return rmRent;
    }

    public void setRmRent(String rmRent) {
        this.rmRent = rmRent;
    }

    public String getRmHouseNo() {
        return rmHouseNo;
    }

    public void setRmHouseNo(String rmHouseNo) {
        this.rmHouseNo = rmHouseNo;
    }

    public String getRmColny() {
        return rmColny;
    }

    public void setRmColny(String rmColny) {
        this.rmColny = rmColny;
    }

    public String getRmCity() {
        return rmCity;
    }

    public void setRmCity(String rmCity) {
        this.rmCity = rmCity;
    }

    public String getRmState() {
        return rmState;
    }

    public void setRmState(String rmState) {
        this.rmState = rmState;
    }

    public String getRmLatitude() {
        return rmLatitude;
    }

    public void setRmLatitude(String rmLatitude) {
        this.rmLatitude = rmLatitude;
    }

    public String getRmLongitude() {
        return rmLongitude;
    }

    public void setRmLongitude(String rmLongitude) {
        this.rmLongitude = rmLongitude;
    }

    public String getRmDescription() {
        return rmDescription;
    }

    public void setRmDescription(String rmDescription) {
        this.rmDescription = rmDescription;
    }

    public String getRmStatus() {
        return rmStatus;
    }

    public void setRmStatus(String rmStatus) {
        this.rmStatus = rmStatus;
    }

    public ArrayList<RoomDetailsImage> getImages() {
        return images;
    }

    public void setImages(ArrayList<RoomDetailsImage> images) {
        this.images = images;
    }



}
