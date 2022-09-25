package com.example.myroom.app.reviews;

import java.util.ArrayList;

public class ReviewModel {
    public ArrayList<ReviewDataModel> getReviewDataModels() {
        return reviewDataModels;
    }

    public void setReviewDataModels(ArrayList<ReviewDataModel> reviewDataModels) {
        this.reviewDataModels = reviewDataModels;
    }

    public ReviewModel(ArrayList<ReviewDataModel> reviewDataModels) {
        this.reviewDataModels = reviewDataModels;
    }

    public ReviewModel() {
    }

    ArrayList<ReviewDataModel> reviewDataModels ;
}
