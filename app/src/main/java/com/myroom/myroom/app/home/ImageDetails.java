package com.myroom.myroom.app.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageDetails {
    @SerializedName("img_pkey")
    @Expose
    private Integer imgPkey;
    @SerializedName("img_rm_fkey")
    @Expose
    private Integer imgRmFkey;
    @SerializedName("img_name")
    @Expose
    private String imgName;
    @SerializedName("img_dscptin")
    @Expose
    private String imgDscptin;

    public Integer getImgPkey() {
        return imgPkey;
    }

    public void setImgPkey(Integer imgPkey) {
        this.imgPkey = imgPkey;
    }

    public Integer getImgRmFkey() {
        return imgRmFkey;
    }

    public void setImgRmFkey(Integer imgRmFkey) {
        this.imgRmFkey = imgRmFkey;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgDscptin() {
        return imgDscptin;
    }

    public void setImgDscptin(String imgDscptin) {
        this.imgDscptin = imgDscptin;
    }

}
