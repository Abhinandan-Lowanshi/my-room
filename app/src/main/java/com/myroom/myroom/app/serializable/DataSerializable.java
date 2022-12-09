package com.myroom.myroom.app.serializable;

import com.myroom.myroom.app.home.RoomDetailsData;

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
