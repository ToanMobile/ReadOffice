package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Color;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import java.io.IOException;

public class ExtLogPen extends AbstractPen {
    private int brushStyle;
    private Color color;
    private int hatch;
    private int penStyle;
    private int[] style;
    private int width;

    public ExtLogPen(int i, int i2, int i3, Color color2, int i4, int[] iArr) {
        this.penStyle = i;
        this.width = i2;
        this.brushStyle = i3;
        this.color = color2;
        this.hatch = i4;
        this.style = iArr;
    }

    public ExtLogPen(EMFInputStream eMFInputStream, int i) throws IOException {
        this.penStyle = eMFInputStream.readDWORD();
        this.width = eMFInputStream.readDWORD();
        this.brushStyle = eMFInputStream.readUINT();
        this.color = eMFInputStream.readCOLORREF();
        this.hatch = eMFInputStream.readULONG();
        int readDWORD = eMFInputStream.readDWORD();
        if (readDWORD == 0 && i > 44) {
            eMFInputStream.readDWORD();
        }
        this.style = eMFInputStream.readDWORD(readDWORD);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("  ExtLogPen\n");
        stringBuffer.append("    penStyle: ");
        stringBuffer.append(Integer.toHexString(this.penStyle));
        stringBuffer.append("\n");
        stringBuffer.append("    width: ");
        stringBuffer.append(this.width);
        stringBuffer.append("\n");
        stringBuffer.append("    brushStyle: ");
        stringBuffer.append(this.brushStyle);
        stringBuffer.append("\n");
        stringBuffer.append("    color: ");
        stringBuffer.append(this.color);
        stringBuffer.append("\n");
        stringBuffer.append("    hatch: ");
        stringBuffer.append(this.hatch);
        stringBuffer.append("\n");
        for (int i = 0; i < this.style.length; i++) {
            stringBuffer.append("      style[");
            stringBuffer.append(i);
            stringBuffer.append("]: ");
            stringBuffer.append(this.style[i]);
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.setUseCreatePen(false);
        eMFRenderer.setPenPaint(this.color);
        eMFRenderer.setPenStroke(createStroke(eMFRenderer, this.penStyle, this.style, (float) this.width));
    }
}
