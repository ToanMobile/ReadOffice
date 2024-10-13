package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Color;
import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import java.io.IOException;
import java.util.logging.Logger;

public class LogBrush32 implements EMFConstants, GDIObject {
    private Color color;
    private int hatch;
    private int style;

    public LogBrush32(int i, Color color2, int i2) {
        this.style = i;
        this.color = color2;
        this.hatch = i2;
    }

    public LogBrush32(EMFInputStream eMFInputStream) throws IOException {
        this.style = eMFInputStream.readUINT();
        this.color = eMFInputStream.readCOLORREF();
        this.hatch = eMFInputStream.readULONG();
    }

    public String toString() {
        return "  LogBrush32\n    style: " + this.style + "\n    color: " + this.color + "\n    hatch: " + this.hatch;
    }

    public void render(EMFRenderer eMFRenderer) {
        int i = this.style;
        if (i == 0) {
            eMFRenderer.setBrushPaint(this.color);
        } else if (i == 1) {
            eMFRenderer.setBrushPaint(new Color(0, 0, 0, 0));
        } else {
            Logger logger = Logger.getLogger("org.freehep.graphicsio.emf");
            logger.warning("LogBrush32 style not supported: " + toString());
            eMFRenderer.setBrushPaint(this.color);
        }
    }
}
