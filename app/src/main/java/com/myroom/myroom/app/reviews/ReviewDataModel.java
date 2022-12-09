package com.myroom.myroom.app.reviews;

public class ReviewDataModel {
    String userID , roomID , review , date , userName;

    public ReviewDataModel() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ReviewDataModel(String userID, String roomID, String review, String date, String userName) {
        this.userID = userID;
        this.roomID = roomID;
        this.review = review;
        this.date = date;
        this.userName = userName;
    }
}
