package com.app.office.officereader.filelist;

public class FileSortType {
    private int recentAscending;
    private int recentType = -1;
    private int sdcardAscending;
    private int sdcardType;
    private int starAscending;
    private int starType;

    public void dispos() {
    }

    public int getSdcardType() {
        return this.sdcardType;
    }

    public int getRecentType() {
        return this.recentType;
    }

    public int getStarType() {
        return this.starType;
    }

    public int getSdcardAscending() {
        return this.sdcardAscending;
    }

    public int getRecentAscending() {
        return this.recentAscending;
    }

    public int getStarAscending() {
        return this.starAscending;
    }

    public void setSdcardType(int i, int i2) {
        this.sdcardType = i;
        this.sdcardAscending = i2;
    }

    public void setRecentType(int i, int i2) {
        this.recentType = i;
        this.recentAscending = i2;
    }

    public void setStarType(int i, int i2) {
        this.starType = i;
        this.starAscending = i2;
    }
}
