package com.app.office.common;

import android.graphics.Bitmap;

public interface IOfficeToPicture {
    public static final String INSTANCE_CLASS_PATH = "com.app.office.officereader.OfficeToPicture";
    public static final byte VIEW_CHANGE_END = 1;
    public static final byte VIEW_CHANGING = 0;

    void callBack(Bitmap bitmap);

    void dispose();

    Bitmap getBitmap(int i, int i2);

    byte getModeType();

    boolean isZoom();

    void setModeType(byte b);
}
