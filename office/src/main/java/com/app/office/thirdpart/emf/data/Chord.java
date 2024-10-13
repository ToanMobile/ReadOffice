package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class Chord extends AbstractArc {
    private Rectangle bounds;
    private Point end;
    private Point start;

    public Chord() {
        super(46, 1, (Rectangle) null, (Point) null, (Point) null);
    }

    public Chord(Rectangle rectangle, Point point, Point point2) {
        super(46, 1, rectangle, point, point2);
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new Chord(eMFInputStream.readRECTL(), eMFInputStream.readPOINTL(), eMFInputStream.readPOINTL());
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.fillAndDrawOrAppend(getShape(eMFRenderer, 1));
    }
}
