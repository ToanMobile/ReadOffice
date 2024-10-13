package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class DeleteObject extends EMFTag {
    private int index;

    public DeleteObject() {
        super(40, 1);
    }

    public DeleteObject(int i) {
        this();
        this.index = i;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new DeleteObject(eMFInputStream.readDWORD());
    }

    public String toString() {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(this.index);
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.storeGDIObject(this.index, (GDIObject) null);
    }
}
