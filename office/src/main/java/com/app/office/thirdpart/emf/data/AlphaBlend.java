package com.app.office.thirdpart.emf.data;

import android.graphics.Bitmap;
import com.app.office.java.awt.Color;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.geom.AffineTransform;
import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFImageLoader;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class AlphaBlend extends EMFTag implements EMFConstants {
    private static final int size = 108;
    private Color bkg;
    private BitmapInfo bmi;
    private Rectangle bounds;
    private BlendFunction dwROP;
    private int height;
    private Bitmap image;
    private AffineTransform transform;
    private int usage;
    private int width;
    private int x;
    private int xSrc;
    private int y;
    private int ySrc;

    public AlphaBlend() {
        super(114, 1);
    }

    public AlphaBlend(Rectangle rectangle, int i, int i2, int i3, int i4, AffineTransform affineTransform, Bitmap bitmap, Color color) {
        this();
        this.bounds = rectangle;
        this.x = i;
        this.y = i2;
        this.width = i3;
        this.height = i4;
        this.dwROP = new BlendFunction(0, 0, 255, 1);
        this.xSrc = 0;
        this.ySrc = 0;
        this.transform = affineTransform;
        this.bkg = color == null ? new Color(0, 0, 0, 0) : color;
        this.usage = 0;
        this.image = bitmap;
        this.bmi = null;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        AlphaBlend alphaBlend = new AlphaBlend();
        alphaBlend.bounds = eMFInputStream.readRECTL();
        alphaBlend.x = eMFInputStream.readLONG();
        alphaBlend.y = eMFInputStream.readLONG();
        alphaBlend.width = eMFInputStream.readLONG();
        alphaBlend.height = eMFInputStream.readLONG();
        alphaBlend.dwROP = new BlendFunction(eMFInputStream);
        alphaBlend.xSrc = eMFInputStream.readLONG();
        alphaBlend.ySrc = eMFInputStream.readLONG();
        alphaBlend.transform = eMFInputStream.readXFORM();
        alphaBlend.bkg = eMFInputStream.readCOLORREF();
        alphaBlend.usage = eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        int readDWORD = eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        eMFInputStream.readLONG();
        eMFInputStream.readLONG();
        BitmapInfo bitmapInfo = readDWORD > 0 ? new BitmapInfo(eMFInputStream) : null;
        alphaBlend.bmi = bitmapInfo;
        alphaBlend.image = EMFImageLoader.readImage(bitmapInfo.getHeader(), alphaBlend.width, alphaBlend.height, eMFInputStream, (i2 - 100) - 40, alphaBlend.dwROP);
        return alphaBlend;
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
        sb.append("\n  dwROP: ");
        sb.append(this.dwROP);
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
            eMFRenderer.drawImage(bitmap, this.x, this.y, this.width, this.height);
        }
    }
}
