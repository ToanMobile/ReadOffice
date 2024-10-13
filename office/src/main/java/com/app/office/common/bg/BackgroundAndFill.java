package com.app.office.common.bg;

import com.app.office.common.picture.Picture;
import com.app.office.common.pictureefftect.PictureStretchInfo;
import com.app.office.system.IControl;

public class BackgroundAndFill {
    public static final byte FILL_BACKGROUND = 9;
    public static final byte FILL_NO = -1;
    public static final byte FILL_PATTERN = 1;
    public static final byte FILL_PICTURE = 3;
    public static final byte FILL_SHADE_LINEAR = 7;
    public static final byte FILL_SHADE_RADIAL = 4;
    public static final byte FILL_SHADE_RECT = 5;
    public static final byte FILL_SHADE_SHAPE = 6;
    public static final byte FILL_SHADE_TILE = 2;
    public static final byte FILL_SOLID = 0;
    public static final byte FILL_TEXTURE = 8;
    private int bgColor;
    private int fgColor;
    private byte fillType;
    private boolean isSlideBackgroundFill;
    private int pictureIndex;
    private AShader shader;
    private PictureStretchInfo stretch;

    public short getType() {
        return 3;
    }

    public boolean isSlideBackgroundFill() {
        return this.isSlideBackgroundFill;
    }

    public void setSlideBackgroundFill(boolean z) {
        this.isSlideBackgroundFill = z;
    }

    public byte getFillType() {
        return this.fillType;
    }

    public void setFillType(byte b) {
        this.fillType = b;
    }

    public int getForegroundColor() {
        return this.fgColor;
    }

    public void setForegroundColor(int i) {
        this.fgColor = i;
    }

    public int getBackgoundColor() {
        return this.bgColor;
    }

    public void setBackgoundColor(int i) {
        this.bgColor = i;
    }

    public int getPictureIndex() {
        return this.pictureIndex;
    }

    public void setPictureIndex(int i) {
        this.pictureIndex = i;
    }

    public Picture getPicture(IControl iControl) {
        return iControl.getSysKit().getPictureManage().getPicture(this.pictureIndex);
    }

    public AShader getShader() {
        return this.shader;
    }

    public void setShader(AShader aShader) {
        this.shader = aShader;
    }

    public PictureStretchInfo getStretch() {
        return this.stretch;
    }

    public void setStretch(PictureStretchInfo pictureStretchInfo) {
        this.stretch = pictureStretchInfo;
    }

    public void dispose() {
        this.stretch = null;
        AShader aShader = this.shader;
        if (aShader != null) {
            aShader.dispose();
            this.shader = null;
        }
    }
}
