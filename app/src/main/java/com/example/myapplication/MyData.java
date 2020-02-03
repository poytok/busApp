package com.example.myapplication;

public class MyData {
    String ROUTENO, ORIGIN_BSTOPNM, DEST_BSTOPNM, FBUS_DEPHMS, LBUS_DEPHMS, MIN_ALLOCGAP, MAX_ALLOCGAP, ROUTEID;
    boolean bookMark;


    public MyData(String ROUTENO, String ORIGIN_BSTOPNM, String DEST_BSTOPNM, String FBUS_DEPHMS, String LBUS_DEPHMS, String MIN_ALLOCGAP, String MAX_ALLOCGAP, String ROUTEID, boolean bookMark) {
        this.ROUTENO = ROUTENO;
        this.ORIGIN_BSTOPNM = ORIGIN_BSTOPNM;
        this.DEST_BSTOPNM = DEST_BSTOPNM;
        this.FBUS_DEPHMS = FBUS_DEPHMS;
        this.LBUS_DEPHMS = LBUS_DEPHMS;
        this.MIN_ALLOCGAP = MIN_ALLOCGAP;
        this.MAX_ALLOCGAP = MAX_ALLOCGAP;
        this.ROUTEID = ROUTEID;
        this.bookMark = bookMark;
    }

    public String getROUTEID() {
        return ROUTEID;
    }

    public void setROUTEID(String ROUTEID) {
        this.ROUTEID = ROUTEID;
    }

    public boolean isBookMark() {
        return bookMark;
    }

    public void setBookMark(boolean bookMark) {
        this.bookMark = bookMark;
    }

    public String getDEST_BSTOPNM() {
        return DEST_BSTOPNM;
    }

    public void setDEST_BSTOPNM(String DEST_BSTOPNM) {
        this.DEST_BSTOPNM = DEST_BSTOPNM;
    }

    public String getORIGIN_BSTOPNM() {
        return ORIGIN_BSTOPNM;
    }

    public void setORIGIN_BSTOPNM(String ORIGIN_BSTOPNM) {
        this.ORIGIN_BSTOPNM = ORIGIN_BSTOPNM;
    }

    public String getFBUS_DEPHMS() {
        return FBUS_DEPHMS;
    }

    public void setFBUS_DEPHMS(String FBUS_DEPHMS) {
        this.FBUS_DEPHMS = FBUS_DEPHMS;
    }

    public String getLBUS_DEPHMS() {
        return LBUS_DEPHMS;
    }

    public void setLBUS_DEPHMS(String LBUS_DEPHMS) {
        this.LBUS_DEPHMS = LBUS_DEPHMS;
    }

    public String getMIN_ALLOCGAP() {
        return MIN_ALLOCGAP;
    }

    public void setMIN_ALLOCGAP(String MIN_ALLOCGAP) {
        this.MIN_ALLOCGAP = MIN_ALLOCGAP;
    }

    public String getMAX_ALLOCGAP() {
        return MAX_ALLOCGAP;
    }

    public void setMAX_ALLOCGAP(String MAX_ALLOCGAP) {
        this.MAX_ALLOCGAP = MAX_ALLOCGAP;
    }

    public String getROUTENO() {
        return ROUTENO;
    }

    public void setROUTENO(String ROUTENO) {
        this.ROUTENO = ROUTENO;
    }
}