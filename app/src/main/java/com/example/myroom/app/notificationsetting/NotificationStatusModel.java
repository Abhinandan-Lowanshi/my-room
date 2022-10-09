package com.example.myroom.app.notificationsetting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationStatusModel {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private NotificationStatusModelData data;

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

    public NotificationStatusModelData getData() {
        return data;
    }

    public void setData(NotificationStatusModelData data) {
        this.data = data;
    }

}
