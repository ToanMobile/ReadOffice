package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import java.io.IOException;

public class BlendFunction implements EMFConstants {
    public static final int size = 4;
    private int alphaFormat;
    private int blendFlags;
    private int blendOp;
    private int sourceConstantAlpha;

    public String toString() {
        return "BlendFunction";
    }

    public BlendFunction(int i, int i2, int i3, int i4) {
        this.blendOp = i;
        this.blendFlags = i2;
        this.sourceConstantAlpha = i3;
        this.alphaFormat = i4;
    }

    public BlendFunction(EMFInputStream eMFInputStream) throws IOException {
        this.blendOp = eMFInputStream.readUnsignedByte();
        this.blendFlags = eMFInputStream.readUnsignedByte();
        this.sourceConstantAlpha = eMFInputStream.readUnsignedByte();
        this.alphaFormat = eMFInputStream.readUnsignedByte();
    }

    public int getSourceConstantAlpha() {
        return this.sourceConstantAlpha;
    }

    public int getAlphaFormat() {
        return this.alphaFormat;
    }
}
