package com.app.office.fc.hslf.model;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.java.awt.Shape;
import com.app.office.java.awt.geom.AffineTransform;
import com.app.office.java.awt.geom.Arc2D;
import com.app.office.java.awt.geom.Ellipse2D;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.java.awt.geom.Line2D;
import com.app.office.java.awt.geom.Rectangle2D;
import com.app.office.java.awt.geom.RoundRectangle2D;

public final class AutoShapes {
    protected static ShapeOutline[] shapes;

    public static ShapeOutline getShapeOutline(int i) {
        return shapes[i];
    }

    public static Shape transform(Shape shape, Rectangle2D rectangle2D) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(rectangle2D.getX(), rectangle2D.getY());
        affineTransform.scale(rectangle2D.getWidth() * 4.6296296204673126E-5d, rectangle2D.getHeight() * 4.6296296204673126E-5d);
        return affineTransform.createTransformedShape(shape);
    }

    static {
        ShapeOutline[] shapeOutlineArr = new ShapeOutline[255];
        shapes = shapeOutlineArr;
        shapeOutlineArr[1] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                return new Rectangle2D.Float(0.0f, 0.0f, 21600.0f, 21600.0f);
            }
        };
        shapes[2] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                float escherProperty = (float) ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUSTVALUE, 5400);
                return new RoundRectangle2D.Float(0.0f, 0.0f, 21600.0f, 21600.0f, escherProperty, escherProperty);
            }
        };
        shapes[3] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                return new Ellipse2D.Float(0.0f, 0.0f, 21600.0f, 21600.0f);
            }
        };
        shapes[4] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                GeneralPath generalPath = new GeneralPath();
                generalPath.moveTo(10800.0f, 0.0f);
                generalPath.lineTo(21600.0f, 10800.0f);
                generalPath.lineTo(10800.0f, 21600.0f);
                generalPath.lineTo(0.0f, 10800.0f);
                generalPath.closePath();
                return generalPath;
            }
        };
        shapes[203] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                int escherProperty = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUSTVALUE, 10800);
                GeneralPath generalPath = new GeneralPath();
                generalPath.moveTo((float) escherProperty, 0.0f);
                generalPath.lineTo(0.0f, 21600.0f);
                generalPath.lineTo(21600.0f, 21600.0f);
                generalPath.closePath();
                return generalPath;
            }
        };
        shapes[204] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                GeneralPath generalPath = new GeneralPath();
                generalPath.moveTo(0.0f, 0.0f);
                generalPath.lineTo(21600.0f, 21600.0f);
                generalPath.lineTo(0.0f, 21600.0f);
                generalPath.closePath();
                return generalPath;
            }
        };
        shapes[7] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                int escherProperty = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUSTVALUE, 5400);
                GeneralPath generalPath = new GeneralPath();
                generalPath.moveTo((float) escherProperty, 0.0f);
                generalPath.lineTo(21600.0f, 0.0f);
                generalPath.lineTo((float) (21600 - escherProperty), 21600.0f);
                generalPath.lineTo(0.0f, 21600.0f);
                generalPath.closePath();
                return generalPath;
            }
        };
        shapes[8] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                int escherProperty = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUSTVALUE, 5400);
                GeneralPath generalPath = new GeneralPath();
                generalPath.moveTo(0.0f, 0.0f);
                generalPath.lineTo((float) escherProperty, 21600.0f);
                generalPath.lineTo((float) (21600 - escherProperty), 21600.0f);
                generalPath.lineTo(21600.0f, 0.0f);
                generalPath.closePath();
                return generalPath;
            }
        };
        shapes[9] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                int escherProperty = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUSTVALUE, 5400);
                GeneralPath generalPath = new GeneralPath();
                float f = (float) escherProperty;
                generalPath.moveTo(f, 0.0f);
                float f2 = (float) (21600 - escherProperty);
                generalPath.lineTo(f2, 0.0f);
                generalPath.lineTo(21600.0f, 10800.0f);
                generalPath.lineTo(f2, 21600.0f);
                generalPath.lineTo(f, 21600.0f);
                generalPath.lineTo(0.0f, 10800.0f);
                generalPath.closePath();
                return generalPath;
            }
        };
        shapes[10] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                int escherProperty = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUSTVALUE, 6326);
                GeneralPath generalPath = new GeneralPath();
                float f = (float) escherProperty;
                generalPath.moveTo(f, 0.0f);
                float f2 = (float) (21600 - escherProperty);
                generalPath.lineTo(f2, 0.0f);
                generalPath.lineTo(21600.0f, f);
                generalPath.lineTo(21600.0f, f2);
                generalPath.lineTo(f2, 21600.0f);
                generalPath.lineTo(f, 21600.0f);
                generalPath.lineTo(0.0f, f2);
                generalPath.lineTo(0.0f, f);
                generalPath.closePath();
                return generalPath;
            }
        };
        shapes[11] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                int escherProperty = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUSTVALUE, 5400);
                GeneralPath generalPath = new GeneralPath();
                float f = (float) escherProperty;
                generalPath.moveTo(f, 0.0f);
                float f2 = (float) (21600 - escherProperty);
                generalPath.lineTo(f2, 0.0f);
                generalPath.lineTo(f2, f);
                generalPath.lineTo(21600.0f, f);
                generalPath.lineTo(21600.0f, f2);
                generalPath.lineTo(f2, f2);
                generalPath.lineTo(f2, 21600.0f);
                generalPath.lineTo(f, 21600.0f);
                generalPath.lineTo(f, f2);
                generalPath.lineTo(0.0f, f2);
                generalPath.lineTo(0.0f, f);
                generalPath.lineTo(f, f);
                generalPath.closePath();
                return generalPath;
            }
        };
        shapes[56] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                GeneralPath generalPath = new GeneralPath();
                generalPath.moveTo(10800.0f, 0.0f);
                generalPath.lineTo(21600.0f, 8259.0f);
                generalPath.lineTo(17400.0f, 21600.0f);
                generalPath.lineTo(4200.0f, 21600.0f);
                generalPath.lineTo(0.0f, 8259.0f);
                generalPath.closePath();
                return generalPath;
            }
        };
        shapes[67] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                int escherProperty = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUSTVALUE, 16200);
                int escherProperty2 = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUST2VALUE, 5400);
                GeneralPath generalPath = new GeneralPath();
                float f = (float) escherProperty;
                generalPath.moveTo(0.0f, f);
                float f2 = (float) escherProperty2;
                generalPath.lineTo(f2, f);
                generalPath.lineTo(f2, 0.0f);
                float f3 = (float) (21600 - escherProperty2);
                generalPath.lineTo(f3, 0.0f);
                generalPath.lineTo(f3, f);
                generalPath.lineTo(21600.0f, f);
                generalPath.lineTo(10800.0f, 21600.0f);
                generalPath.closePath();
                return generalPath;
            }
        };
        shapes[68] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                int escherProperty = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUSTVALUE, 5400);
                int escherProperty2 = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUST2VALUE, 5400);
                GeneralPath generalPath = new GeneralPath();
                float f = (float) escherProperty;
                generalPath.moveTo(0.0f, f);
                float f2 = (float) escherProperty2;
                generalPath.lineTo(f2, f);
                generalPath.lineTo(f2, 21600.0f);
                float f3 = (float) (21600 - escherProperty2);
                generalPath.lineTo(f3, 21600.0f);
                generalPath.lineTo(f3, f);
                generalPath.lineTo(21600.0f, f);
                generalPath.lineTo(10800.0f, 0.0f);
                generalPath.closePath();
                return generalPath;
            }
        };
        shapes[205] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                int escherProperty = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUSTVALUE, 16200);
                int escherProperty2 = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUST2VALUE, 5400);
                GeneralPath generalPath = new GeneralPath();
                float f = (float) escherProperty;
                generalPath.moveTo(f, 0.0f);
                float f2 = (float) escherProperty2;
                generalPath.lineTo(f, f2);
                generalPath.lineTo(0.0f, f2);
                float f3 = (float) (21600 - escherProperty2);
                generalPath.lineTo(0.0f, f3);
                generalPath.lineTo(f, f3);
                generalPath.lineTo(f, 21600.0f);
                generalPath.lineTo(21600.0f, 10800.0f);
                generalPath.closePath();
                return generalPath;
            }
        };
        shapes[66] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                int escherProperty = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUSTVALUE, 5400);
                int escherProperty2 = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUST2VALUE, 5400);
                GeneralPath generalPath = new GeneralPath();
                float f = (float) escherProperty;
                generalPath.moveTo(f, 0.0f);
                float f2 = (float) escherProperty2;
                generalPath.lineTo(f, f2);
                generalPath.lineTo(21600.0f, f2);
                float f3 = (float) (21600 - escherProperty2);
                generalPath.lineTo(21600.0f, f3);
                generalPath.lineTo(f, f3);
                generalPath.lineTo(f, 21600.0f);
                generalPath.lineTo(0.0f, 10800.0f);
                generalPath.closePath();
                return generalPath;
            }
        };
        shapes[22] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                int escherProperty = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUSTVALUE, 5400);
                GeneralPath generalPath = new GeneralPath();
                float f = (float) escherProperty;
                generalPath.append((Shape) new Arc2D.Float(0.0f, 0.0f, 21600.0f, f, 0.0f, 180.0f, 0), false);
                int i = escherProperty / 2;
                float f2 = (float) i;
                generalPath.moveTo(0.0f, f2);
                float f3 = (float) (21600 - i);
                generalPath.lineTo(0.0f, f3);
                generalPath.closePath();
                generalPath.append((Shape) new Arc2D.Float(0.0f, (float) (21600 - escherProperty), 21600.0f, f, 180.0f, 180.0f, 0), false);
                generalPath.moveTo(21600.0f, f3);
                generalPath.lineTo(21600.0f, f2);
                generalPath.append((Shape) new Arc2D.Float(0.0f, 0.0f, 21600.0f, f, 180.0f, 180.0f, 0), false);
                generalPath.moveTo(0.0f, f2);
                generalPath.closePath();
                return generalPath;
            }
        };
        shapes[87] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                int escherProperty = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUSTVALUE, 1800);
                int escherProperty2 = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUST2VALUE, 10800);
                GeneralPath generalPath = new GeneralPath();
                generalPath.moveTo(21600.0f, 0.0f);
                int i = escherProperty * 2;
                float f = (float) i;
                generalPath.append((Shape) new Arc2D.Float(10800.0f, 0.0f, 21600.0f, f, 90.0f, 90.0f, 0), false);
                generalPath.moveTo(10800.0f, (float) escherProperty);
                generalPath.lineTo(10800.0f, (float) (escherProperty2 - escherProperty));
                generalPath.append((Shape) new Arc2D.Float(-10800.0f, (float) (escherProperty2 - i), 21600.0f, f, 270.0f, 90.0f, 0), false);
                float f2 = (float) escherProperty2;
                generalPath.moveTo(0.0f, f2);
                generalPath.append((Shape) new Arc2D.Float(-10800.0f, f2, 21600.0f, f, 0.0f, 90.0f, 0), false);
                generalPath.moveTo(10800.0f, (float) (escherProperty2 + escherProperty));
                generalPath.lineTo(10800.0f, (float) (21600 - escherProperty));
                generalPath.append((Shape) new Arc2D.Float(10800.0f, (float) (21600 - i), 21600.0f, f, 180.0f, 90.0f, 0), false);
                return generalPath;
            }
        };
        shapes[88] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                int escherProperty = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUSTVALUE, 1800);
                int escherProperty2 = ShapeKit.getEscherProperty(shape.getSpContainer(), EscherProperties.GEOMETRY__ADJUST2VALUE, 10800);
                GeneralPath generalPath = new GeneralPath();
                generalPath.moveTo(0.0f, 0.0f);
                int i = escherProperty * 2;
                float f = (float) i;
                generalPath.append((Shape) new Arc2D.Float(-10800.0f, 0.0f, 21600.0f, f, 0.0f, 90.0f, 0), false);
                generalPath.moveTo(10800.0f, (float) escherProperty);
                generalPath.lineTo(10800.0f, (float) (escherProperty2 - escherProperty));
                generalPath.append((Shape) new Arc2D.Float(10800.0f, (float) (escherProperty2 - i), 21600.0f, f, 180.0f, 90.0f, 0), false);
                float f2 = (float) escherProperty2;
                generalPath.moveTo(21600.0f, f2);
                generalPath.append((Shape) new Arc2D.Float(10800.0f, f2, 21600.0f, f, 90.0f, 90.0f, 0), false);
                generalPath.moveTo(10800.0f, (float) (escherProperty2 + escherProperty));
                generalPath.lineTo(10800.0f, (float) (21600 - escherProperty));
                generalPath.append((Shape) new Arc2D.Float(-10800.0f, (float) (21600 - i), 21600.0f, f, 270.0f, 90.0f, 0), false);
                return generalPath;
            }
        };
        shapes[32] = new ShapeOutline() {
            public Shape getOutline(Shape shape) {
                return new Line2D.Float(0.0f, 0.0f, 21600.0f, 21600.0f);
            }
        };
    }
}
