package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Color;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SetTextColor extends EMFTag {
    private Color color;

    public SetTextColor() {
        super(24, 1);
    }

    public SetTextColor(Color color2) {
        this();
        this.color = color2;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SetTextColor(eMFInputStream.readCOLORREF());
    }

    public String toString() {
        return super.toString() + "\n  color: " + this.color;
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.setTextColor(this.color);
    }
}
