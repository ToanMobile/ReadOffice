package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class AbortPath extends EMFTag {
    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return this;
    }

    public AbortPath() {
        super(68, 1);
    }
}
