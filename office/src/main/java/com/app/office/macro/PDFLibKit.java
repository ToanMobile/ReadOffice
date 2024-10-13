package com.app.office.macro;

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.app.office.fc.pdf.PDFLib;

public class PDFLibKit {
    private static PDFLibKit kit = new PDFLibKit();
    private static PDFLib lib = PDFLib.getPDFLib();

    public static PDFLibKit instance() {
        return kit;
    }

    public synchronized void openFileSync(String str) throws Exception {
        lib.openFileSync(str);
    }

    public int getPageCountSync() {
        return lib.getPageCountSync();
    }

    public Rect[] getAllPagesSize() {
        return lib.getAllPagesSize();
    }

    public synchronized void drawPageSync(Bitmap bitmap, int i, float f, float f2, int i2, int i3, int i4, int i5, int i6) {
        lib.drawPageSync(bitmap, i, f, f2, i2, i3, i4, i5, i6);
    }

    public synchronized boolean hasPasswordSync() {
        return lib.hasPasswordSync();
    }

    public synchronized boolean authenticatePasswordSync(String str) {
        return lib.authenticatePasswordSync(str);
    }

    public void setStopFlagSync(int i) {
        lib.setStopFlagSync(i);
    }

    public synchronized void dispose() {
        lib = null;
    }
}
