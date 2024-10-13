package com.app.office.common.shape;

import com.app.office.common.pictureefftect.PictureEffectInfo;

public class WatermarkShape extends WPAutoShape {
    public static final byte Watermark_Picture = 1;
    public static final byte Watermark_Text = 0;
    private final float OPACITY = 0.2f;
    private float blacklevel;
    private PictureEffectInfo effect;
    private int fontColor = -16777216;
    private int fontSize = 36;
    private float gain;
    private boolean isAutoFontSize = false;
    private float opacity = 0.2f;
    private int pictureIndex = -1;
    private byte watermarkType;
    private String watermartString;

    public boolean isWatermarkShape() {
        return true;
    }

    public short getType() {
        return this.watermarkType == 0 ? (short) 2 : 0;
    }

    public void setWatermarkType(byte b) {
        this.watermarkType = b;
    }

    public byte getWatermarkType() {
        return this.watermarkType;
    }

    public void setWatermartString(String str) {
        this.watermartString = str;
    }

    public String getWatermartString() {
        return this.watermartString;
    }

    public boolean isAutoFontSize() {
        return this.isAutoFontSize;
    }

    public void setAutoFontSize(boolean z) {
        this.isAutoFontSize = z;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int i) {
        this.fontSize = i;
    }

    public int getFontColor() {
        return this.fontColor;
    }

    public void setFontColor(int i) {
        this.fontColor = i;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public void setOpacity(float f) {
        this.opacity = f * 0.2f;
    }

    public int getPictureIndex() {
        return this.pictureIndex;
    }

    public void setPictureIndex(int i) {
        this.pictureIndex = i;
    }

    public float getBlacklevel() {
        return this.blacklevel;
    }

    public void setBlacklevel(float f) {
        this.blacklevel = f;
    }

    public float getGain() {
        return this.gain;
    }

    public void setGain(float f) {
        this.gain = f;
    }

    public PictureEffectInfo getEffectInfor() {
        if (this.watermarkType != 1) {
            return null;
        }
        if (this.effect == null) {
            PictureEffectInfo pictureEffectInfo = new PictureEffectInfo();
            this.effect = pictureEffectInfo;
            pictureEffectInfo.setAlpha(Integer.valueOf(Math.round(this.opacity * 255.0f)));
            this.effect.setBrightness((float) Math.round(this.blacklevel * 255.0f));
        }
        return this.effect;
    }

    public void dispose() {
        this.watermartString = null;
        PictureEffectInfo pictureEffectInfo = this.effect;
        if (pictureEffectInfo != null) {
            pictureEffectInfo.dispose();
            this.effect = null;
        }
    }
}
