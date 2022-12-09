package com.myroom.myroom.app;

public class FavModelTmp {
    String id ;
   boolean status;

    public FavModelTmp(String id, boolean status, boolean isUpdated) {
        this.id = id;
        this.status = status;
        this.isUpdated = isUpdated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    boolean isUpdated;

}
