package com.app.office.fc.sl.usermodel;

import com.app.office.java.awt.geom.Rectangle2D;

public interface Shape {
    Rectangle2D getAnchor();

    Shape getParent();

    int getShapeType();

    void moveTo(float f, float f2);

    void setAnchor(Rectangle2D rectangle2D);
}
