package com.app.office.macro;

import android.graphics.Bitmap;

public interface OfficeToPictureListener {
    void callBack(Bitmap bitmap);

    Bitmap getBitmap(int i, int i2);
}
