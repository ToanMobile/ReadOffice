package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.ddf.EscherContainerRecord;
import java.util.ArrayList;
import java.util.List;

public class HSSFShapeGroup extends HSSFShape implements HSSFShapeContainer {
    private List<HSSFShape> shapes = new ArrayList();
    private int x1 = 0;
    private int x2 = IEEEDouble.EXPONENT_BIAS;
    private int y1 = 0;
    private int y2 = 255;

    public HSSFShapeGroup(EscherContainerRecord escherContainerRecord, HSSFShape hSSFShape, HSSFAnchor hSSFAnchor) {
        super(escherContainerRecord, hSSFShape, hSSFAnchor);
    }

    public HSSFShapeGroup createGroup(HSSFChildAnchor hSSFChildAnchor) {
        HSSFShapeGroup hSSFShapeGroup = new HSSFShapeGroup((EscherContainerRecord) null, this, hSSFChildAnchor);
        hSSFShapeGroup.setAnchor(hSSFChildAnchor);
        this.shapes.add(hSSFShapeGroup);
        return hSSFShapeGroup;
    }

    public HSSFSimpleShape createShape(HSSFChildAnchor hSSFChildAnchor) {
        HSSFSimpleShape hSSFSimpleShape = new HSSFSimpleShape((EscherContainerRecord) null, this, hSSFChildAnchor);
        hSSFSimpleShape.setAnchor(hSSFChildAnchor);
        this.shapes.add(hSSFSimpleShape);
        return hSSFSimpleShape;
    }

    public HSSFTextbox createTextbox(HSSFChildAnchor hSSFChildAnchor) {
        HSSFTextbox hSSFTextbox = new HSSFTextbox((EscherContainerRecord) null, this, hSSFChildAnchor);
        hSSFTextbox.setAnchor(hSSFChildAnchor);
        this.shapes.add(hSSFTextbox);
        return hSSFTextbox;
    }

    public HSSFPolygon createPolygon(HSSFChildAnchor hSSFChildAnchor) {
        HSSFPolygon hSSFPolygon = new HSSFPolygon((EscherContainerRecord) null, this, hSSFChildAnchor);
        hSSFPolygon.setAnchor(hSSFChildAnchor);
        this.shapes.add(hSSFPolygon);
        return hSSFPolygon;
    }

    public HSSFPicture createPicture(HSSFChildAnchor hSSFChildAnchor, int i) {
        HSSFPicture hSSFPicture = new HSSFPicture((EscherContainerRecord) null, this, hSSFChildAnchor);
        hSSFPicture.setAnchor(hSSFChildAnchor);
        hSSFPicture.setPictureIndex(i);
        this.shapes.add(hSSFPicture);
        return hSSFPicture;
    }

    public void addChildShape(HSSFShape hSSFShape) {
        this.shapes.add(hSSFShape);
    }

    public List<HSSFShape> getChildren() {
        return this.shapes;
    }

    public void setCoordinates(int i, int i2, int i3, int i4) {
        this.x1 = i;
        this.y1 = i2;
        this.x2 = i3;
        this.y2 = i4;
    }

    public int getX1() {
        return this.x1;
    }

    public int getY1() {
        return this.y1;
    }

    public int getX2() {
        return this.x2;
    }

    public int getY2() {
        return this.y2;
    }

    public int countOfAllChildren() {
        int size = this.shapes.size();
        for (HSSFShape countOfAllChildren : this.shapes) {
            size += countOfAllChildren.countOfAllChildren();
        }
        return size;
    }
}
