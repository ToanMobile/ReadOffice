package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFRenderer;

public abstract class AbstractPolyPolyline extends AbstractPolyPolygon {
    protected AbstractPolyPolyline(int i, int i2, Rectangle rectangle, int[] iArr, Point[][] pointArr) {
        super(i, i2, rectangle, iArr, pointArr);
    }

    public void render(EMFRenderer eMFRenderer) {
        render(eMFRenderer, false);
    }
}
