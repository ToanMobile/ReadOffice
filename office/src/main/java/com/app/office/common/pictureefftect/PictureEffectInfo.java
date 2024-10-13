package com.app.office.common.pictureefftect;

public class PictureEffectInfo {
    private Integer alpha;
    private Float bright;
    private Float contrast;
    private PictureCroppedInfo croppedRect;
    private Boolean grayscl;
    private Float sat;
    private PictureStretchInfo stretch;
    private Float threshold;
    private Integer transparentColor;

    public void setPictureCroppedInfor(PictureCroppedInfo pictureCroppedInfo) {
        this.croppedRect = pictureCroppedInfo;
    }

    public PictureCroppedInfo getPictureCroppedInfor() {
        return this.croppedRect;
    }

    public PictureStretchInfo getPictureStretchInfo() {
        return this.stretch;
    }

    public void setPictureStretchInfo(PictureStretchInfo pictureStretchInfo) {
        this.stretch = pictureStretchInfo;
    }

    public void setGrayScale(boolean z) {
        this.grayscl = Boolean.valueOf(z);
    }

    public Boolean isGrayScale() {
        return this.grayscl;
    }

    public void setBlackWhiteThreshold(float f) {
        this.threshold = Float.valueOf(f);
    }

    public Float getBlackWhiteThreshold() {
        return this.threshold;
    }

    public void setSaturation(float f) {
        this.sat = Float.valueOf(f);
    }

    public Float getSaturation() {
        return this.sat;
    }

    public void setBrightness(float f) {
        this.bright = Float.valueOf(f);
    }

    public Float getBrightness() {
        return this.bright;
    }

    public void setContrast(float f) {
        this.contrast = Float.valueOf(f);
    }

    public Float getContrast() {
        return this.contrast;
    }

    public void setTransparentColor(int i) {
        this.transparentColor = Integer.valueOf(i);
    }

    public Integer getTransparentColor() {
        return this.transparentColor;
    }

    public Integer getAlpha() {
        return this.alpha;
    }

    public void setAlpha(Integer num) {
        this.alpha = num;
    }

    public void dispose() {
        this.croppedRect = null;
        this.grayscl = null;
        this.threshold = null;
        this.sat = null;
        this.bright = null;
        this.contrast = null;
        this.alpha = null;
    }
}
