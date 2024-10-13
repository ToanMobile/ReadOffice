package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SetMiterLimit extends EMFTag {
    private int limit;

    public SetMiterLimit() {
        super(58, 1);
    }

    public SetMiterLimit(int i) {
        this();
        this.limit = i;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SetMiterLimit(eMFInputStream.readDWORD());
    }

    public String toString() {
        return super.toString() + "\n  limit: " + this.limit;
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.setMeterLimit(this.limit);
    }
}
