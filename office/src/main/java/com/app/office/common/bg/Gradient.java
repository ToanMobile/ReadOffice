package com.app.office.common.bg;

public abstract class Gradient extends AShader {
    public static final int COORDINATE_LENGTH = 100;
    protected int[] colors = null;
    private int focus = 100;
    protected float[] positions = null;
    private int type;

    public Gradient(int[] iArr, float[] fArr) {
        if (iArr != null && iArr.length >= 2) {
            this.colors = iArr;
        }
        this.positions = fArr;
    }

    public int getGradientType() {
        return this.type;
    }

    public void setGradientType(int i) {
        this.type = i;
    }

    public int getFocus() {
        return this.focus;
    }

    public void setFocus(int i) {
        this.focus = i;
    }
}
