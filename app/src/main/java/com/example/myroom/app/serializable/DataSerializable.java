package com.example.myroom.app.serializable;

import android.os.Parcelable;

import com.example.myroom.app.home.RoomDetailsData;

import java.io.Serializable;
import java.util.ArrayList;

public class DataSerializable implements Serializable {
   public transient  ArrayList<RoomDetailsData> data;

    public ArrayList<RoomDetailsData> getData() {
        return data;
    }

    public void setData(ArrayList<RoomDetailsData> data) {
        this.data = data;
    }
}
