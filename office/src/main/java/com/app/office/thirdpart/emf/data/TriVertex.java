package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Color;
import com.app.office.thirdpart.emf.EMFInputStream;
import java.io.IOException;

public class TriVertex {
    private Color color;
    private int x;
    private int y;

    public TriVertex(int i, int i2, Color color2) {
        this.x = i;
        this.y = i2;
        this.color = color2;
    }

    public TriVertex(EMFInputStream eMFInputStream) throws IOException {
        this.x = eMFInputStream.readLONG();
        this.y = eMFInputStream.readLONG();
        this.color = eMFInputStream.readCOLOR16();
    }

    public String toString() {
        return "[" + this.x + ", " + this.y + "] " + this.color;
    }
}
