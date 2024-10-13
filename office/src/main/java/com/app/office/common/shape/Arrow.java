package com.app.office.common.shape;

public class Arrow {
    public static final byte Arrow_Arrow = 5;
    public static final byte Arrow_Diamond = 3;
    public static final byte Arrow_None = 0;
    public static final byte Arrow_Oval = 4;
    public static final byte Arrow_Stealth = 2;
    public static final byte Arrow_Triangle = 1;
    private int length = 1;
    private byte type;
    private int width = 1;

    public Arrow(byte b, int i, int i2) {
        this.type = b;
        this.width = i;
        this.length = i2;
    }

    public static int getArrowSize(String str) {
        if (str != null && !str.equals("med")) {
            if (str.equals("sm")) {
                return 0;
            }
            if (str.equals("lg")) {
                return 2;
            }
        }
        return 1;
    }

    public static byte getArrowType(String str) {
        if (str == null || str.length() <= 0) {
            return 0;
        }
        if ("triangle".equalsIgnoreCase(str)) {
            return 1;
        }
        if ("arrow".equalsIgnoreCase(str)) {
            return 5;
        }
        if ("diamond".equalsIgnoreCase(str)) {
            return 3;
        }
        if ("stealth".equalsIgnoreCase(str)) {
            return 2;
        }
        return "oval".equalsIgnoreCase(str) ? (byte) 4 : 0;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int i) {
        this.length = i;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte b) {
        this.type = b;
    }
}
