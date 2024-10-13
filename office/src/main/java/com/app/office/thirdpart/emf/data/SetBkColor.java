package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Color;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SetBkColor extends EMFTag {
    private Color color;

    public void render(EMFRenderer eMFRenderer) {
    }

    public SetBkColor() {
        super(25, 1);
    }

    public SetBkColor(Color color2) {
        this();
        this.color = color2;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SetBkColor(eMFInputStream.readCOLORREF());
    }

    public String toString() {
        return super.toString() + "\n  color: " + this.color;
    }
}
