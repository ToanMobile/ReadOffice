package com.app.office.macro;

import android.graphics.Bitmap;
import com.app.office.common.IOfficeToPicture;

class MacroOfficeToPicture implements IOfficeToPicture {
    private byte modeType = 1;
    private OfficeToPictureListener officeToPictureListener;

    public boolean isZoom() {
        return true;
    }

    protected MacroOfficeToPicture(OfficeToPictureListener officeToPictureListener2) {
        this.officeToPictureListener = officeToPictureListener2;
    }

    public void setModeType(byte b) {
        this.modeType = b;
    }

    public byte getModeType() {
        return this.modeType;
    }

    public Bitmap getBitmap(int i, int i2) {
        OfficeToPictureListener officeToPictureListener2 = this.officeToPictureListener;
        if (officeToPictureListener2 != null) {
            return officeToPictureListener2.getBitmap(i, i2);
        }
        return null;
    }

    public void callBack(Bitmap bitmap) {
        OfficeToPictureListener officeToPictureListener2 = this.officeToPictureListener;
        if (officeToPictureListener2 != null) {
            officeToPictureListener2.callBack(bitmap);
        }
    }

    public void dispose() {
        this.officeToPictureListener = null;
    }
}
