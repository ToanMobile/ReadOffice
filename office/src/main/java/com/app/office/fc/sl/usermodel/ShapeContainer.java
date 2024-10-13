package com.app.office.fc.sl.usermodel;

public interface ShapeContainer {
    void addShape(Shape shape);

    Shape[] getShapes();

    boolean removeShape(Shape shape);
}
