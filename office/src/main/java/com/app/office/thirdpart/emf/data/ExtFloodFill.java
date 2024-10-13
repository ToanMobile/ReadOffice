package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Color;
import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class ExtFloodFill extends EMFTag implements EMFConstants {
    private Color color;
    private int mode;
    private Point start;

    public ExtFloodFill() {
        super(53, 1);
    }

    public ExtFloodFill(Point point, Color color2, int i) {
        this();
        this.start = point;
        this.color = color2;
        this.mode = i;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new ExtFloodFill(eMFInputStream.readPOINTL(), eMFInputStream.readCOLORREF(), eMFInputStream.readDWORD());
    }

    public String toString() {
        return super.toString() + "\n  start: " + this.start + "\n  color: " + this.color + "\n  mode: " + this.mode;
    }
}
