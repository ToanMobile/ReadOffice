package com.app.office.java.awt.geom;

import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.Shape;
import com.app.office.java.awt.geom.AreaOp;
import com.app.office.java.awt.geom.Rectangle2D;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Vector;

public class Area implements Shape, Cloneable {
    private static Vector EmptyCurves = new Vector();
    private Rectangle2D cachedBounds;
    private Vector curves;

    public Area() {
        this.curves = EmptyCurves;
    }

    public Area(Shape shape) {
        if (shape instanceof Area) {
            this.curves = ((Area) shape).curves;
        } else {
            this.curves = pathToCurves(shape.getPathIterator((AffineTransform) null));
        }
    }

    private static Vector pathToCurves(PathIterator pathIterator) {
        AreaOp areaOp;
        double d;
        double d2;
        Vector vector = new Vector();
        int windingRule = pathIterator.getWindingRule();
        double[] dArr = new double[23];
        double d3 = 0.0d;
        double d4 = 0.0d;
        double d5 = 0.0d;
        double d6 = 0.0d;
        while (!pathIterator.isDone()) {
            int currentSegment = pathIterator.currentSegment(dArr);
            if (currentSegment == 0) {
                Curve.insertLine(vector, d6, d3, d4, d5);
                d6 = dArr[0];
                d3 = dArr[1];
                Curve.insertMove(vector, d6, d3);
                d4 = d6;
                d5 = d3;
            } else if (currentSegment != 1) {
                if (currentSegment == 2) {
                    d2 = dArr[2];
                    d = dArr[3];
                    Curve.insertQuad(vector, d6, d3, dArr);
                } else if (currentSegment == 3) {
                    d2 = dArr[4];
                    d = dArr[5];
                    Curve.insertCubic(vector, d6, d3, dArr);
                } else if (currentSegment == 4) {
                    Curve.insertLine(vector, d6, d3, d4, d5);
                    d6 = d4;
                    d3 = d5;
                }
                d6 = d2;
                d3 = d;
            } else {
                double d7 = dArr[0];
                double d8 = dArr[1];
                Curve.insertLine(vector, d6, d3, d7, d8);
                d6 = d7;
                d3 = d8;
            }
            pathIterator.next();
        }
        Curve.insertLine(vector, d6, d3, d4, d5);
        if (windingRule == 0) {
            areaOp = new AreaOp.EOWindOp();
        } else {
            areaOp = new AreaOp.NZWindOp();
        }
        return areaOp.calculate(vector, EmptyCurves);
    }

    public void add(Area area) {
        this.curves = new AreaOp.AddOp().calculate(this.curves, area.curves);
        invalidateBounds();
    }

    public void subtract(Area area) {
        this.curves = new AreaOp.SubOp().calculate(this.curves, area.curves);
        invalidateBounds();
    }

    public void intersect(Area area) {
        this.curves = new AreaOp.IntOp().calculate(this.curves, area.curves);
        invalidateBounds();
    }

    public void exclusiveOr(Area area) {
        this.curves = new AreaOp.XorOp().calculate(this.curves, area.curves);
        invalidateBounds();
    }

    public void reset() {
        this.curves = new Vector();
        invalidateBounds();
    }

    public boolean isEmpty() {
        return this.curves.size() == 0;
    }

    public boolean isPolygonal() {
        Enumeration elements = this.curves.elements();
        while (elements.hasMoreElements()) {
            if (((Curve) elements.nextElement()).getOrder() > 1) {
                return false;
            }
        }
        return true;
    }

    public boolean isRectangular() {
        int size = this.curves.size();
        if (size == 0) {
            return true;
        }
        if (size > 3) {
            return false;
        }
        Curve curve = (Curve) this.curves.get(1);
        Curve curve2 = (Curve) this.curves.get(2);
        return curve.getOrder() == 1 && curve2.getOrder() == 1 && curve.getXTop() == curve.getXBot() && curve2.getXTop() == curve2.getXBot() && curve.getYTop() == curve2.getYTop() && curve.getYBot() == curve2.getYBot();
    }

    public boolean isSingular() {
        if (this.curves.size() < 3) {
            return true;
        }
        Enumeration elements = this.curves.elements();
        elements.nextElement();
        while (elements.hasMoreElements()) {
            if (((Curve) elements.nextElement()).getOrder() == 0) {
                return false;
            }
        }
        return true;
    }

    private void invalidateBounds() {
        this.cachedBounds = null;
    }

    private Rectangle2D getCachedBounds() {
        Rectangle2D rectangle2D = this.cachedBounds;
        if (rectangle2D != null) {
            return rectangle2D;
        }
        Rectangle2D.Double doubleR = new Rectangle2D.Double();
        if (this.curves.size() > 0) {
            Curve curve = (Curve) this.curves.get(0);
            doubleR.setRect(curve.getX0(), curve.getY0(), 0.0d, 0.0d);
            for (int i = 1; i < this.curves.size(); i++) {
                ((Curve) this.curves.get(i)).enlarge(doubleR);
            }
        }
        this.cachedBounds = doubleR;
        return doubleR;
    }

    public Rectangle2D getBounds2D() {
        return getCachedBounds().getBounds2D();
    }

    public Rectangle getBounds() {
        return getCachedBounds().getBounds();
    }

    public Object clone() {
        return new Area(this);
    }

    public boolean equals(Area area) {
        if (area == this) {
            return true;
        }
        if (area == null) {
            return false;
        }
        return new AreaOp.XorOp().calculate(this.curves, area.curves).isEmpty();
    }

    public void transform(AffineTransform affineTransform) {
        Objects.requireNonNull(affineTransform, "transform must not be null");
        this.curves = pathToCurves(getPathIterator(affineTransform));
        invalidateBounds();
    }

    public Area createTransformedArea(AffineTransform affineTransform) {
        Area area = new Area(this);
        area.transform(affineTransform);
        return area;
    }

    public boolean contains(double d, double d2) {
        if (!getCachedBounds().contains(d, d2)) {
            return false;
        }
        Enumeration elements = this.curves.elements();
        int i = 0;
        while (elements.hasMoreElements()) {
            i += ((Curve) elements.nextElement()).crossingsFor(d, d2);
        }
        if ((i & 1) == 1) {
            return true;
        }
        return false;
    }

    public boolean contains(Point2D point2D) {
        return contains(point2D.getX(), point2D.getY());
    }

    public boolean contains(double d, double d2, double d3, double d4) {
        double d5 = d2;
        if (d3 < 0.0d || d4 < 0.0d) {
            return false;
        } else if (!getCachedBounds().contains(d, d2, d3, d4)) {
            return false;
        } else {
            double d6 = d5 + d4;
            Crossings findCrossings = Crossings.findCrossings(this.curves, d, d2, d + d3, d6);
            if (findCrossings == null || !findCrossings.covers(d5, d6)) {
                return false;
            }
            return true;
        }
    }

    public boolean contains(Rectangle2D rectangle2D) {
        return contains(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public boolean intersects(double d, double d2, double d3, double d4) {
        if (d3 < 0.0d || d4 < 0.0d) {
            return false;
        } else if (!getCachedBounds().intersects(d, d2, d3, d4)) {
            return false;
        } else {
            Crossings findCrossings = Crossings.findCrossings(this.curves, d, d2, d + d3, d2 + d4);
            if (findCrossings == null || !findCrossings.isEmpty()) {
                return true;
            }
            return false;
        }
    }

    public boolean intersects(Rectangle2D rectangle2D) {
        return intersects(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public PathIterator getPathIterator(AffineTransform affineTransform) {
        return new AreaIterator(this.curves, affineTransform);
    }

    public PathIterator getPathIterator(AffineTransform affineTransform, double d) {
        return new FlatteningPathIterator(getPathIterator(affineTransform), d);
    }
}
