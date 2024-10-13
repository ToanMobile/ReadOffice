package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class OffsetClipRgn extends EMFTag {
    private Point offset;

    public OffsetClipRgn() {
        super(26, 1);
    }

    public OffsetClipRgn(Point point) {
        this();
        this.offset = point;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new OffsetClipRgn(eMFInputStream.readPOINTL());
    }

    public String toString() {
        return super.toString() + "\n  offset: " + this.offset;
    }
}
