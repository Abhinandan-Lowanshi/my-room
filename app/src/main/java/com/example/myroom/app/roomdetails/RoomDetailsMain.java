package com.example.myroom.app.roomdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomDetailsMain {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private RoomDetailsMainData data;

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

    public RoomDetailsMainData getData() {
        return data;
    }

    public void setData(RoomDetailsMainData data) {
        this.data = data;
    }


}
