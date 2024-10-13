package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import java.io.IOException;

public class BitmapInfo {
    private BitmapInfoHeader header;

    public BitmapInfo(BitmapInfoHeader bitmapInfoHeader) {
        this.header = bitmapInfoHeader;
    }

    public BitmapInfo(EMFInputStream eMFInputStream) throws IOException {
        this.header = new BitmapInfoHeader(eMFInputStream);
    }

    public String toString() {
        return "  BitmapInfo\n" + this.header.toString();
    }

    public BitmapInfoHeader getHeader() {
        return this.header;
    }
}
