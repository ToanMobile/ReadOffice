package com.app.office.thirdpart.achartengine.chart;

public enum PointStyle {
    X("x"),
    CIRCLE("circle"),
    TRIANGLE("triangle"),
    SQUARE("square"),
    DIAMOND("diamond"),
    POINT("point");
    
    private String mName;

    private PointStyle(String str) {
        this.mName = str;
    }

    public String getName() {
        return this.mName;
    }

    public String toString() {
        return getName();
    }

    public static PointStyle getPointStyleForName(String str) {
        PointStyle[] values = values();
        int length = values.length;
        PointStyle pointStyle = null;
        for (int i = 0; i < length && pointStyle == null; i++) {
            if (values[i].mName.equals(str)) {
                pointStyle = values[i];
            }
        }
        return pointStyle;
    }

    public static int getIndexForName(String str) {
        PointStyle[] values = values();
        int length = values.length;
        int i = -1;
        for (int i2 = 0; i2 < length && i < 0; i2++) {
            if (values[i2].mName.equals(str)) {
                i = i2;
            }
        }
        return Math.max(0, i);
    }
}
