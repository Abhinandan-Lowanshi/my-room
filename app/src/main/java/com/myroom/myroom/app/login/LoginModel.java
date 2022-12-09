package com.myroom.myroom.app.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("code")
    @Expose
    private Integer code;

//    public String getOrignalError() {
//        return orignalError;
//    }
//
//    public void setOrignalError(String orignalError) {
//        this.orignalError = orignalError;
//    }

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose

//     private String orignalError;
//    @SerializedName("orignalError")
//    @Expose
    private LoginData data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }
}
