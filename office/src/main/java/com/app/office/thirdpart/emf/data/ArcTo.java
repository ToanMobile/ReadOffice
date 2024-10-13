package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class ArcTo extends AbstractArc {
    public ArcTo() {
        super(55, 1, (Rectangle) null, (Point) null, (Point) null);
    }

    public ArcTo(Rectangle rectangle, Point point, Point point2) {
        super(55, 1, rectangle, point, point2);
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new ArcTo(eMFInputStream.readRECTL(), eMFInputStream.readPOINTL(), eMFInputStream.readPOINTL());
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.getFigure().append(getShape(eMFRenderer, 0), true);
    }
}
