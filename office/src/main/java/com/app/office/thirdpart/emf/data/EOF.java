package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class EOF extends EMFTag {
    public void render(EMFRenderer eMFRenderer) {
    }

    public EOF() {
        super(14, 1);
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        eMFInputStream.readUnsignedByte(i2);
        return new EOF();
    }
}
