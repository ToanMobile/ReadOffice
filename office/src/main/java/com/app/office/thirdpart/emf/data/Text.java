package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFConstants;

public abstract class Text implements EMFConstants {
    Rectangle bounds;
    int options;
    Point pos;
    String string;
    int[] widths;

    protected Text(Point point, String str, int i, Rectangle rectangle, int[] iArr) {
        this.pos = point;
        this.string = str;
        this.options = i;
        this.bounds = rectangle;
        this.widths = iArr;
    }

    public Point getPos() {
        return this.pos;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public String getString() {
        return this.string;
    }
}
