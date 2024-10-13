package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SelectObject extends EMFTag {
    private int index;

    public SelectObject() {
        super(37, 1);
    }

    public SelectObject(int i) {
        this();
        this.index = i;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SelectObject(eMFInputStream.readDWORD());
    }

    public String toString() {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(this.index);
    }

    public void render(EMFRenderer eMFRenderer) {
        GDIObject gDIObject;
        int i = this.index;
        if (i < 0) {
            gDIObject = StockObjects.getStockObject(i);
        } else {
            gDIObject = eMFRenderer.getGDIObject(i);
        }
        if (gDIObject != null) {
            gDIObject.render(eMFRenderer);
        }
    }
}
