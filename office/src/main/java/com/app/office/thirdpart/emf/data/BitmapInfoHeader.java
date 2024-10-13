package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import java.io.IOException;

public class BitmapInfoHeader implements EMFConstants {
    public static final int size = 40;
    private int bitCount;
    private int clrImportant;
    private int clrUsed;
    private int compression;
    private int height;
    private int planes;
    private int sizeImage;
    private int width;
    private int xPelsPerMeter;
    private int yPelsPerMeter;

    public BitmapInfoHeader(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        this.width = i;
        this.height = i2;
        this.planes = 1;
        this.bitCount = i3;
        this.compression = i4;
        this.sizeImage = i5;
        this.xPelsPerMeter = i6;
        this.yPelsPerMeter = i7;
        this.clrUsed = i8;
        this.clrImportant = i9;
    }

    public BitmapInfoHeader(EMFInputStream eMFInputStream) throws IOException {
        eMFInputStream.readDWORD();
        this.width = eMFInputStream.readLONG();
        this.height = eMFInputStream.readLONG();
        this.planes = eMFInputStream.readWORD();
        this.bitCount = eMFInputStream.readWORD();
        this.compression = eMFInputStream.readDWORD();
        this.sizeImage = eMFInputStream.readDWORD();
        this.xPelsPerMeter = eMFInputStream.readLONG();
        this.yPelsPerMeter = eMFInputStream.readLONG();
        this.clrUsed = eMFInputStream.readDWORD();
        this.clrImportant = eMFInputStream.readDWORD();
    }

    public String toString() {
        return "    size: 40\n    width: " + this.width + "\n    height: " + this.height + "\n    planes: " + this.planes + "\n    bitCount: " + this.bitCount + "\n    compression: " + this.compression + "\n    sizeImage: " + this.sizeImage + "\n    xPelsPerMeter: " + this.xPelsPerMeter + "\n    yPelsPerMeter: " + this.yPelsPerMeter + "\n    clrUsed: " + this.clrUsed + "\n    clrImportant: " + this.clrImportant;
    }

    public int getBitCount() {
        return this.bitCount;
    }

    public int getCompression() {
        return this.compression;
    }

    public int getClrUsed() {
        return this.clrUsed;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
