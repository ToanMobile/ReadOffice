package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class CreatePen extends EMFTag {
    private int index;
    private LogPen pen;

    public CreatePen() {
        super(38, 1);
    }

    public CreatePen(int i, LogPen logPen) {
        this();
        this.index = i;
        this.pen = logPen;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new CreatePen(eMFInputStream.readDWORD(), new LogPen(eMFInputStream));
    }

    public String toString() {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(this.index) + "\n" + this.pen.toString();
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.storeGDIObject(this.index, this.pen);
    }
}
