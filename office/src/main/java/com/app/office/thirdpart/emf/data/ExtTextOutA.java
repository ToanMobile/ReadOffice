package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class ExtTextOutA extends AbstractExtTextOut implements EMFConstants {
    private TextA text;

    public ExtTextOutA() {
        super(83, 1, (Rectangle) null, 0, 1.0f, 1.0f);
    }

    public ExtTextOutA(Rectangle rectangle, int i, float f, float f2, TextA textA) {
        super(83, 1, rectangle, i, f, f2);
        this.text = textA;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new ExtTextOutA(eMFInputStream.readRECTL(), eMFInputStream.readDWORD(), eMFInputStream.readFLOAT(), eMFInputStream.readFLOAT(), TextA.read(eMFInputStream));
    }

    public Text getText() {
        return this.text;
    }
}
