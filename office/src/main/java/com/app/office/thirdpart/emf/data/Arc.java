package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class Arc extends AbstractArc {
    public Arc() {
        super(45, 1, (Rectangle) null, (Point) null, (Point) null);
    }

    public Arc(Rectangle rectangle, Point point, Point point2) {
        super(45, 1, rectangle, point, point2);
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new Arc(eMFInputStream.readRECTL(), eMFInputStream.readPOINTL(), eMFInputStream.readPOINTL());
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.fillAndDrawOrAppend(getShape(eMFRenderer, 0));
    }
}
