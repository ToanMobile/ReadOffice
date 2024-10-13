package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SelectClipPath extends AbstractClipPath {
    public SelectClipPath() {
        super(67, 1, 1);
    }

    public SelectClipPath(int i) {
        super(67, 1, i);
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SelectClipPath(eMFInputStream.readDWORD());
    }

    public void render(EMFRenderer eMFRenderer) {
        render(eMFRenderer, eMFRenderer.getPath());
    }
}
