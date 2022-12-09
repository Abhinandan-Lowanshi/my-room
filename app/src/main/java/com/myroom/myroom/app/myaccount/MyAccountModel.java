package com.myroom.myroom.app.myaccount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyAccountModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private MyAccountModelData data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyAccountModelData getData() {
        return data;
    }

    public void setData(MyAccountModelData data) {
        this.data = data;
    }

}
