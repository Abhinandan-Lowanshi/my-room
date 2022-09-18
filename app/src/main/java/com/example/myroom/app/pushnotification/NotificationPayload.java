package com.example.myroom.app.pushnotification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationPayload {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rm_pkey")
    @Expose
    private Integer rmPkey;
    @SerializedName("rm_size")
    @Expose
    private String rmSize;
    @SerializedName("rm_furnisd_status")
    @Expose
    private String rmFurnisdStatus;
    @SerializedName("rm_usr_fkey")
    @Expose
    private String rmUsrFkey;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRmPkey() {
        return rmPkey;
    }

    public void setRmPkey(Integer rmPkey) {
        this.rmPkey = rmPkey;
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

    public String getRmUsrFkey() {
        return rmUsrFkey;
    }

    public void setRmUsrFkey(String rmUsrFkey) {
        this.rmUsrFkey = rmUsrFkey;
    }


}
