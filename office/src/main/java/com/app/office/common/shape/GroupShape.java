package com.app.office.common.shape;

import java.util.ArrayList;
import java.util.List;

public class GroupShape extends AbstractShape {
    private int offX;
    private int offY;
    private List<IShape> shapes = new ArrayList();

    public short getType() {
        return 7;
    }

    public int getOffX() {
        return this.offX;
    }

    public void setOffX(int i) {
        this.offX = i;
    }

    public int getOffY() {
        return this.offY;
    }

    public void setOffY(int i) {
        this.offY = i;
    }

    public void setOffPostion(int i, int i2) {
        this.offX = i;
        this.offY = i2;
    }

    public void appendShapes(IShape iShape) {
        this.shapes.add(iShape);
    }

    public IShape[] getShapes() {
        List<IShape> list = this.shapes;
        return (IShape[]) list.toArray(new IShape[list.size()]);
    }
}
