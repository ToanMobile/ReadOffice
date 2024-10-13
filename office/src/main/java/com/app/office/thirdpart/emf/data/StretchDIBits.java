package com.app.office.thirdpart.emf.data;

import android.graphics.Bitmap;
import com.app.office.java.awt.Color;
import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFImageLoader;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class StretchDIBits extends EMFTag implements EMFConstants {
    public static final int size = 80;
    private Color bkg;
    private BitmapInfo bmi;
    private Rectangle bounds;
    private int dwROP;
    private int height;
    private int heightSrc;
    private Bitmap image;
    private int usage;
    private int width;
    private int widthSrc;
    private int x;
    private int xSrc;
    private int y;
    private int ySrc;

    public StretchDIBits() {
        super(81, 1);
    }

    public StretchDIBits(Rectangle rectangle, int i, int i2, int i3, int i4, Bitmap bitmap, Color color) {
        this();
        this.bounds = rectangle;
        this.x = i;
        this.y = i2;
        this.width = i3;
        this.height = i4;
        this.xSrc = 0;
        this.ySrc = 0;
        this.widthSrc = bitmap.getWidth();
        this.heightSrc = bitmap.getHeight();
        this.usage = 0;
        this.dwROP = EMFConstants.SRCCOPY;
        this.bkg = color;
        this.image = bitmap;
        this.bmi = null;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        StretchDIBits stretchDIBits = new StretchDIBits();
        stretchDIBits.bounds = eMFInputStream.readRECTL();
        stretchDIBits.x = eMFInputStream.readLONG();
        stretchDIBits.y = eMFInputStream.readLONG();
        stretchDIBits.xSrc = eMFInputStream.readLONG();
        stretchDIBits.ySrc = eMFInputStream.readLONG();
        stretchDIBits.width = eMFInputStream.readLONG();
        stretchDIBits.height = eMFInputStream.readLONG();
        eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        stretchDIBits.usage = eMFInputStream.readDWORD();
        stretchDIBits.dwROP = eMFInputStream.readDWORD();
        stretchDIBits.widthSrc = eMFInputStream.readLONG();
        stretchDIBits.heightSrc = eMFInputStream.readLONG();
        BitmapInfo bitmapInfo = new BitmapInfo(eMFInputStream);
        stretchDIBits.bmi = bitmapInfo;
        stretchDIBits.image = EMFImageLoader.readImage(bitmapInfo.getHeader(), stretchDIBits.width, stretchDIBits.height, eMFInputStream, (i2 - 72) - 40, (BlendFunction) null);
        return stretchDIBits;
    }

    public String toString() {
        return super.toString() + "\n  bounds: " + this.bounds + "\n  x, y, w, h: " + this.x + " " + this.y + " " + this.width + " " + this.height + "\n  xSrc, ySrc, widthSrc, heightSrc: " + this.xSrc + " " + this.ySrc + " " + this.widthSrc + " " + this.heightSrc + "\n  usage: " + this.usage + "\n  dwROP: " + this.dwROP + "\n  bkg: " + this.bkg + "\n" + this.bmi.toString();
    }

    public void render(EMFRenderer eMFRenderer) {
        Bitmap bitmap = this.image;
        if (bitmap != null) {
            eMFRenderer.drawImage(bitmap, this.x, this.y, this.widthSrc, this.heightSrc);
        }
    }
}
