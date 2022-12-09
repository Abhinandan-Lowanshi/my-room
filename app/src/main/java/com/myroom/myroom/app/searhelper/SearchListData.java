package com.myroom.myroom.app.searhelper;

import java.util.ArrayList;

public class SearchListData {
    public SearchListData(ArrayList<RecentSearchManager> recentSearchManagers) {
        this.recentSearchManagers = recentSearchManagers;
    }

    public ArrayList<RecentSearchManager> getRecentSearchManagers() {
        return recentSearchManagers;
    }

    public void setRecentSearchManagers(ArrayList<RecentSearchManager> recentSearchManagers) {
        this.recentSearchManagers = recentSearchManagers;
    }

    public SearchListData() {
    }

    private ArrayList<RecentSearchManager> recentSearchManagers;
}
