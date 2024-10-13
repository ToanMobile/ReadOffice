package com.app.office.common.pictureefftect;

public class PictureCroppedInfo {
    private float bottomOff;
    private float leftOff;
    private float rightOff;
    private float topOff;

    public PictureCroppedInfo(float f, float f2, float f3, float f4) {
        this.leftOff = f;
        this.topOff = f2;
        this.rightOff = f3;
        this.bottomOff = f4;
    }

    public float getLeftOff() {
        return this.leftOff;
    }

    public void setLeftOff(float f) {
        this.leftOff = f;
    }

    public float getTopOff() {
        return this.topOff;
    }

    public void setTopOff(float f) {
        this.topOff = f;
    }

    public float getRightOff() {
        return this.rightOff;
    }

    public void setRightOff(float f) {
        this.rightOff = f;
    }

    public float getBottomOff() {
        return this.bottomOff;
    }

    public void setBottomOff(float f) {
        this.bottomOff = f;
    }
}
