package com.app.office.java.awt;

import com.app.office.java.awt.geom.AffineTransform;
import com.app.office.java.awt.geom.PathIterator;
import com.app.office.java.awt.geom.Point2D;
import com.app.office.java.awt.geom.Rectangle2D;

public interface Shape {
    boolean contains(double d, double d2);

    boolean contains(double d, double d2, double d3, double d4);

    boolean contains(Point2D point2D);

    boolean contains(Rectangle2D rectangle2D);

    Rectangle getBounds();

    Rectangle2D getBounds2D();

    PathIterator getPathIterator(AffineTransform affineTransform);

    PathIterator getPathIterator(AffineTransform affineTransform, double d);

    boolean intersects(double d, double d2, double d3, double d4);

    boolean intersects(Rectangle2D rectangle2D);
}
