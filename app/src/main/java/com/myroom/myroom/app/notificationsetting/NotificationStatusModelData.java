package com.myroom.myroom.app.notificationsetting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationStatusModelData {
    @SerializedName("usr_pkey")
    @Expose
    private Integer usrPkey;
    @SerializedName("usr_firstName")
    @Expose
    private String usrFirstName;
    @SerializedName("usr_lastName")
    @Expose
    private String usrLastName;
    @SerializedName("usr_email")
    @Expose
    private String usrEmail;
    @SerializedName("usr_phone")
    @Expose
    private String usrPhone;
    @SerializedName("usr_parmentAdrss")
    @Expose
    private String usrParmentAdrss;
    @SerializedName("usr_currentAdrss")
    @Expose
    private String usrCurrentAdrss;
    @SerializedName("usr_pasword")
    @Expose
    private String usrPasword;
    @SerializedName("usr_otp")
    @Expose
    private Integer usrOtp;
    @SerializedName("usr_latitude")
    @Expose
    private String usrLatitude;
    @SerializedName("usr_longitude")
    @Expose
    private String usrLongitude;
    @SerializedName("isNotify")
    @Expose
    private String isNotify;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public Integer getUsrPkey() {
        return usrPkey;
    }

    public void setUsrPkey(Integer usrPkey) {
        this.usrPkey = usrPkey;
    }

    public String getUsrFirstName() {
        return usrFirstName;
    }

    public void setUsrFirstName(String usrFirstName) {
        this.usrFirstName = usrFirstName;
    }

    public String getUsrLastName() {
        return usrLastName;
    }

    public void setUsrLastName(String usrLastName) {
        this.usrLastName = usrLastName;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public String getUsrPhone() {
        return usrPhone;
    }

    public void setUsrPhone(String usrPhone) {
        this.usrPhone = usrPhone;
    }

    public String getUsrParmentAdrss() {
        return usrParmentAdrss;
    }

    public void setUsrParmentAdrss(String usrParmentAdrss) {
        this.usrParmentAdrss = usrParmentAdrss;
    }

    public String getUsrCurrentAdrss() {
        return usrCurrentAdrss;
    }

    public void setUsrCurrentAdrss(String usrCurrentAdrss) {
        this.usrCurrentAdrss = usrCurrentAdrss;
    }

    public String getUsrPasword() {
        return usrPasword;
    }

    public void setUsrPasword(String usrPasword) {
        this.usrPasword = usrPasword;
    }

    public Integer getUsrOtp() {
        return usrOtp;
    }

    public void setUsrOtp(Integer usrOtp) {
        this.usrOtp = usrOtp;
    }

    public String getUsrLatitude() {
        return usrLatitude;
    }

    public void setUsrLatitude(String usrLatitude) {
        this.usrLatitude = usrLatitude;
    }

    public String getUsrLongitude() {
        return usrLongitude;
    }

    public void setUsrLongitude(String usrLongitude) {
        this.usrLongitude = usrLongitude;
    }

    public String getIsNotify() {
        return isNotify;
    }

    public void setIsNotify(String isNotify) {
        this.isNotify = isNotify;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
