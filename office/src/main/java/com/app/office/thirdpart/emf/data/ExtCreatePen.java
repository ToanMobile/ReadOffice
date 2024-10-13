package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class ExtCreatePen extends EMFTag {
    private int index;
    private ExtLogPen pen;

    public ExtCreatePen() {
        super(95, 1);
    }

    public ExtCreatePen(int i, ExtLogPen extLogPen) {
        this();
        this.index = i;
        this.pen = extLogPen;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        int readDWORD = eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        return new ExtCreatePen(readDWORD, new ExtLogPen(eMFInputStream, i2));
    }

    public String toString() {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(this.index) + "\n" + this.pen.toString();
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.storeGDIObject(this.index, this.pen);
    }
}
