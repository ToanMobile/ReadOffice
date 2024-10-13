package com.app.office.pg.animate;

public class ShapeAnimation {
    public static final int Para_All = -2;
    public static final int Para_BG = -1;
    public static final byte SA_EMPH = 1;
    public static final byte SA_ENTR = 0;
    public static final byte SA_EXIT = 2;
    public static final byte SA_MEDIACALL = 5;
    public static final byte SA_PATH = 3;
    public static final byte SA_VERB = 4;
    public static final int Slide = -3;
    private byte animType = -1;
    private int paraBegin = -2;
    private int paraEnd = -2;
    private int shapeID;

    public ShapeAnimation(int i, byte b) {
        this.shapeID = i;
        this.animType = b;
    }

    public ShapeAnimation(int i, byte b, int i2, int i3) {
        this.shapeID = i;
        this.animType = b;
        this.paraBegin = i2;
        this.paraEnd = i3;
    }

    public int getShapeID() {
        return this.shapeID;
    }

    public byte getAnimationType() {
        return this.animType;
    }

    public int getParagraphBegin() {
        return this.paraBegin;
    }

    public int getParagraphEnd() {
        return this.paraEnd;
    }
}
