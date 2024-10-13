package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class ExtTextOutW extends AbstractExtTextOut implements EMFConstants {
    private TextW text;

    public ExtTextOutW() {
        super(84, 1, (Rectangle) null, 0, 1.0f, 1.0f);
    }

    public ExtTextOutW(Rectangle rectangle, int i, float f, float f2, TextW textW) {
        super(84, 1, rectangle, i, f, f2);
        this.text = textW;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new ExtTextOutW(eMFInputStream.readRECTL(), eMFInputStream.readDWORD(), eMFInputStream.readFLOAT(), eMFInputStream.readFLOAT(), TextW.read(eMFInputStream));
    }

    public Text getText() {
        return this.text;
    }
}
