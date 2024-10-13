package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Color;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import java.io.IOException;

public class LogPen extends AbstractPen {
    private Color color;
    private int penStyle;
    private int width;

    public LogPen(int i, int i2, Color color2) {
        this.penStyle = i;
        this.width = i2;
        this.color = color2;
    }

    public LogPen(EMFInputStream eMFInputStream) throws IOException {
        this.penStyle = eMFInputStream.readDWORD();
        this.width = eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        this.color = eMFInputStream.readCOLORREF();
    }

    public String toString() {
        return "  LogPen\n    penstyle: " + this.penStyle + "\n    width: " + this.width + "\n    color: " + this.color;
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.setUseCreatePen(true);
        eMFRenderer.setPenPaint(this.color);
        eMFRenderer.setPenStroke(createStroke(eMFRenderer, this.penStyle, (int[]) null, (float) this.width));
    }
}
