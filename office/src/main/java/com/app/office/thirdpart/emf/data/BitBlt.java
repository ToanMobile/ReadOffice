package com.app.office.thirdpart.emf.data;

import android.graphics.Bitmap;
import com.app.office.java.awt.Color;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.geom.AffineTransform;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFImageLoader;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class BitBlt extends EMFTag implements EMFConstants {
    private static final int size = 100;
    private Color bkg;
    private BitmapInfo bmi;
    private Rectangle bounds;
    private int dwROP;
    private int height;
    private Bitmap image;
    private AffineTransform transform;
    private int usage;
    private int width;
    private int x;
    private int xSrc;
    private int y;
    private int ySrc;

    public BitBlt() {
        super(76, 1);
    }

    public BitBlt(Rectangle rectangle, int i, int i2, int i3, int i4, AffineTransform affineTransform, Bitmap bitmap, Color color) {
        this();
        this.bounds = rectangle;
        this.x = i;
        this.y = i2;
        this.width = i3;
        this.height = i4;
        this.dwROP = EMFConstants.SRCCOPY;
        this.xSrc = 0;
        this.ySrc = 0;
        this.transform = affineTransform;
        this.bkg = color;
        this.usage = 0;
        this.image = bitmap;
        this.bmi = null;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        BitmapInfo bitmapInfo;
        BitBlt bitBlt = new BitBlt();
        bitBlt.bounds = eMFInputStream.readRECTL();
        bitBlt.x = eMFInputStream.readLONG();
        bitBlt.y = eMFInputStream.readLONG();
        bitBlt.width = eMFInputStream.readLONG();
        bitBlt.height = eMFInputStream.readLONG();
        bitBlt.dwROP = eMFInputStream.readDWORD();
        bitBlt.xSrc = eMFInputStream.readLONG();
        bitBlt.ySrc = eMFInputStream.readLONG();
        bitBlt.transform = eMFInputStream.readXFORM();
        bitBlt.bkg = eMFInputStream.readCOLORREF();
        bitBlt.usage = eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        int readDWORD = eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        int readDWORD2 = eMFInputStream.readDWORD();
        if (readDWORD > 0) {
            bitBlt.bmi = new BitmapInfo(eMFInputStream);
        } else {
            bitBlt.bmi = null;
        }
        if (readDWORD2 <= 0 || (bitmapInfo = bitBlt.bmi) == null) {
            bitBlt.image = null;
        } else {
            bitBlt.image = EMFImageLoader.readImage(bitmapInfo.getHeader(), bitBlt.width, bitBlt.height, eMFInputStream, readDWORD2, (BlendFunction) null);
        }
        return bitBlt;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\n  bounds: ");
        sb.append(this.bounds);
        sb.append("\n  x, y, w, h: ");
        sb.append(this.x);
        sb.append(" ");
        sb.append(this.y);
        sb.append(" ");
        sb.append(this.width);
        sb.append(" ");
        sb.append(this.height);
        sb.append("\n  dwROP: 0x");
        sb.append(Integer.toHexString(this.dwROP));
        sb.append("\n  xSrc, ySrc: ");
        sb.append(this.xSrc);
        sb.append(" ");
        sb.append(this.ySrc);
        sb.append("\n  transform: ");
        sb.append(this.transform);
        sb.append("\n  bkg: ");
        sb.append(this.bkg);
        sb.append("\n  usage: ");
        sb.append(this.usage);
        sb.append("\n");
        BitmapInfo bitmapInfo = this.bmi;
        sb.append(bitmapInfo != null ? bitmapInfo.toString() : "  bitmap: null");
        return sb.toString();
    }

    public void render(EMFRenderer eMFRenderer) {
        Bitmap bitmap = this.image;
        if (bitmap != null) {
            eMFRenderer.drawImage(bitmap, this.transform);
        } else if (!this.bounds.isEmpty() && this.dwROP == 15728673) {
            this.bounds.x = this.x;
            this.bounds.y = this.y;
            eMFRenderer.fillShape(this.bounds);
        }
        GeneralPath figure = eMFRenderer.getFigure();
        if (figure != null) {
            eMFRenderer.fillAndDrawShape(figure);
        }
    }
}
