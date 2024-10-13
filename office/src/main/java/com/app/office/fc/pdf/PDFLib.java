package com.app.office.fc.pdf;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;

public class PDFLib {
    private static PDFLib lib = new PDFLib();
    private int currentPageIndex = -1;
    private boolean isDrawPDFFinished = true;
    private int pageCount = -1;
    public float pageHeight;
    public float pageWidth;

    private static native boolean authenticatePassword(String str);

    private static native int convertFile(String str, String str2, int i, int i2);

    private static native int convertPicture2PNG(String str, String str2, String str3);

    private static native void destroy();

    private static native void drawPage(Bitmap bitmap, float f, float f2, int i, int i2, int i3, int i4);

    private static native int getHyperlinkCount(int i, float f, float f2);

    private static native PDFHyperlinkInfo[] getHyperlinkInfo(int i);

    private static native PDFOutlineItem[] getOutline();

    private static native int getPageCount();

    private static native float getPageHeight();

    private static native float getPageWidth();

    private static native Rect[] getPagesSize();

    private static native boolean hasOutline();

    private static native boolean hasPassword();

    private static native int openFile(String str);

    private static native RectF[] searchContent(String str);

    private static native int setStopFlag(int i);

    private static native void showPage(int i);

    static {
        System.loadLibrary("wxiweiPDF");
    }

    public static PDFLib getPDFLib() {
        return lib;
    }

    public synchronized void openFileSync(String str) throws Exception {
        if (openFile(str) > 0) {
            this.pageCount = -1;
            this.currentPageIndex = -1;
        } else {
            throw new Exception("Format error");
        }
    }

    public int getPageCountSync() {
        if (this.pageCount < 0) {
            this.pageCount = getPageCount();
        }
        return this.pageCount;
    }

    private void showPageSync(int i) {
        if (this.pageCount == -1) {
            this.pageCount = getPageCount();
        }
        int i2 = this.pageCount;
        if (i > i2 - 1) {
            i = i2 - 1;
        } else if (i < 0) {
            i = 0;
        }
        if (this.currentPageIndex != i) {
            this.currentPageIndex = i;
            showPage(i);
            this.pageWidth = getPageWidth();
            this.pageHeight = getPageHeight();
        }
    }

    public Rect[] getAllPagesSize() {
        return getPagesSize();
    }

    public boolean isDrawPageSyncFinished() {
        return this.isDrawPDFFinished;
    }

    public synchronized void drawPageSync(Bitmap bitmap, int i, float f, float f2, int i2, int i3, int i4, int i5, int i6) {
        synchronized (this) {
            this.isDrawPDFFinished = false;
            int i7 = i;
            showPageSync(i);
            drawPage(bitmap, f, f2, i2, i3, i4, i5);
            this.isDrawPDFFinished = true;
        }
    }

    public synchronized int getHyperlinkCountSync(int i, float f, float f2) {
        return getHyperlinkCount(i, f, f2);
    }

    public synchronized PDFHyperlinkInfo[] getHyperlinkInfoSync(int i) {
        return getHyperlinkInfo(i);
    }

    public synchronized RectF[] searchContentSync(int i, String str) {
        showPageSync(i);
        return searchContent(str);
    }

    public synchronized boolean hasOutlineSync() {
        return hasOutline();
    }

    public synchronized PDFOutlineItem[] getOutlineSync() {
        return getOutline();
    }

    public synchronized boolean hasPasswordSync() {
        return hasPassword();
    }

    public synchronized boolean authenticatePasswordSync(String str) {
        return authenticatePassword(str);
    }

    public void setStopFlagSync(int i) {
        setStopFlag(i);
    }

    public int wmf2Jpg(String str, String str2, int i, int i2) {
        return convertFile(str, str2, i, i2);
    }

    public boolean convertToPNG(String str, String str2, String str3) {
        if (("png".equalsIgnoreCase(str3) || "jpeg".equalsIgnoreCase(str3)) && convertPicture2PNG(str, str2, str3.toLowerCase()) != 0) {
            return true;
        }
        return false;
    }

    public synchronized void dispose() {
    }
}
