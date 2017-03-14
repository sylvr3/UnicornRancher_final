package com.example.user.myadapter;

import java.util.ArrayList;

public class LocationInfo {

    private String location;
    private ArrayList<UnicornInfo> list = new ArrayList<UnicornInfo>();

    public String getLoc() {
        return location;
    }

    public void setLoc(String location) {
        this.location = location;
    }

    public ArrayList<UnicornInfo> getUnicornLocation() {
        return list;
    }

    public void setLocation(ArrayList<UnicornInfo> location) {
        this.list = location;
    }

}
