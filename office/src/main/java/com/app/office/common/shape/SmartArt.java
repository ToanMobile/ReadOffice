package com.app.office.common.shape;

import java.util.ArrayList;
import java.util.List;

public class SmartArt extends AbstractShape {
    private int offX;
    private int offY;
    private List<IShape> shapes = new ArrayList();

    public short getType() {
        return 8;
    }

    public void appendShapes(IShape iShape) {
        this.shapes.add(iShape);
    }

    public IShape[] getShapes() {
        List<IShape> list = this.shapes;
        return (IShape[]) list.toArray(new IShape[list.size()]);
    }
}
