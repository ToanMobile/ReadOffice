package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Color;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SetPixelV extends EMFTag {
    private Color color;
    private Point point;

    public SetPixelV() {
        super(15, 1);
    }

    public SetPixelV(Point point2, Color color2) {
        this();
        this.point = point2;
        this.color = color2;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SetPixelV(eMFInputStream.readPOINTL(), eMFInputStream.readCOLORREF());
    }

    public String toString() {
        return super.toString() + "\n  point: " + this.point + "\n  color: " + this.color;
    }
}
