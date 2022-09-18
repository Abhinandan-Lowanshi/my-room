package com.example.myroom.app.myaccount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MyAccountModelData  implements Serializable {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
