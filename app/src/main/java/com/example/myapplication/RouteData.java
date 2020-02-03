package com.example.myapplication;

public class RouteData {
    String totalCount;
    String BSTOPNM;
    String BSTOPSEQ;
    boolean currentLocation;
    int Time;
    boolean Draw;

    public RouteData(String BSTOPNM, String BSTOPSEQ, boolean currentLocation, int time, boolean Draw) {
        this.BSTOPNM = BSTOPNM;
        this.BSTOPSEQ = BSTOPSEQ;
        this.currentLocation = currentLocation;
        Time = time;
        this.Draw = Draw;
    }

    public boolean isDraw() {
        return Draw;
    }

    public void setDraw(boolean setDraw) {
        this.Draw = setDraw;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getBSTOPNM() {
        return BSTOPNM;
    }

    public void setBSTOPNM(String BSTOPNM) {
        this.BSTOPNM = BSTOPNM;
    }

    public String getBSTOPSEQ() {
        return BSTOPSEQ;
    }

    public void setBSTOPSEQ(String BSTOPSEQ) {
        this.BSTOPSEQ = BSTOPSEQ;
    }

    public boolean isCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(boolean currentLocation) {
        this.currentLocation = currentLocation;
    }
}
