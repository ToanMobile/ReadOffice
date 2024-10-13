package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherChildAnchorRecord;
import com.app.office.fc.ddf.EscherClientAnchorRecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSpgrRecord;
import com.app.office.java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class HWPFShapeGroup extends HWPFShape {
    public HWPFShapeGroup(EscherContainerRecord escherContainerRecord, HWPFShape hWPFShape) {
        super(escherContainerRecord, hWPFShape);
    }

    public Rectangle getCoordinates(float f, float f2) {
        EscherSpgrRecord escherSpgrRecord;
        EscherContainerRecord escherContainerRecord = (EscherContainerRecord) getSpContainer().getChild(0);
        if (escherContainerRecord == null || (escherSpgrRecord = (EscherSpgrRecord) ShapeKit.getEscherChild(escherContainerRecord, -4087)) == null) {
            return null;
        }
        Rectangle rectangle = new Rectangle();
        rectangle.x = (int) (((((float) escherSpgrRecord.getRectX1()) * f) * 96.0f) / 914400.0f);
        rectangle.y = (int) (((((float) escherSpgrRecord.getRectY1()) * f2) * 96.0f) / 914400.0f);
        rectangle.width = (int) (((((float) (escherSpgrRecord.getRectX2() - escherSpgrRecord.getRectX1())) * f) * 96.0f) / 914400.0f);
        rectangle.height = (int) (((((float) (escherSpgrRecord.getRectY2() - escherSpgrRecord.getRectY1())) * f2) * 96.0f) / 914400.0f);
        return rectangle;
    }

    public Rectangle getAnchor(float f, float f2) {
        EscherContainerRecord escherContainerRecord = (EscherContainerRecord) getSpContainer().getChild(0);
        if (escherContainerRecord == null) {
            return null;
        }
        EscherClientAnchorRecord escherClientAnchorRecord = (EscherClientAnchorRecord) ShapeKit.getEscherChild(escherContainerRecord, -4080);
        if (escherClientAnchorRecord == null) {
            EscherChildAnchorRecord escherChildAnchorRecord = (EscherChildAnchorRecord) ShapeKit.getEscherChild(escherContainerRecord, -4081);
            if (escherChildAnchorRecord == null) {
                return null;
            }
            Rectangle rectangle = new Rectangle();
            rectangle.x = (int) (((((float) escherChildAnchorRecord.getDx1()) * f) * 96.0f) / 914400.0f);
            rectangle.y = (int) (((((float) escherChildAnchorRecord.getDy1()) * f2) * 96.0f) / 914400.0f);
            rectangle.width = (int) (((((float) (escherChildAnchorRecord.getDx2() - escherChildAnchorRecord.getDx1())) * f) * 96.0f) / 914400.0f);
            rectangle.height = (int) (((((float) (escherChildAnchorRecord.getDy2() - escherChildAnchorRecord.getDy1())) * f2) * 96.0f) / 914400.0f);
            return rectangle;
        }
        Rectangle rectangle2 = new Rectangle();
        rectangle2.x = (int) (((((float) escherClientAnchorRecord.getCol1()) * f) * 96.0f) / 914400.0f);
        rectangle2.y = (int) (((((float) escherClientAnchorRecord.getFlag()) * f2) * 96.0f) / 914400.0f);
        rectangle2.width = (int) (((((float) (escherClientAnchorRecord.getDx1() - escherClientAnchorRecord.getCol1())) * f) * 96.0f) / 914400.0f);
        rectangle2.height = (int) (((((float) (escherClientAnchorRecord.getRow1() - escherClientAnchorRecord.getFlag())) * f2) * 96.0f) / 914400.0f);
        return rectangle2;
    }

    public float[] getShapeAnchorFit(Rectangle rectangle, float f, float f2) {
        EscherSpgrRecord escherSpgrRecord;
        float[] fArr = {1.0f, 1.0f};
        EscherContainerRecord escherContainerRecord = (EscherContainerRecord) getSpContainer().getChild(0);
        if (!(escherContainerRecord == null || (escherSpgrRecord = (EscherSpgrRecord) ShapeKit.getEscherChild(escherContainerRecord, -4087)) == null)) {
            float rectX2 = (float) (escherSpgrRecord.getRectX2() - escherSpgrRecord.getRectX1());
            float rectY2 = (float) (escherSpgrRecord.getRectY2() - escherSpgrRecord.getRectY1());
            if (!(rectX2 == 0.0f || rectY2 == 0.0f)) {
                fArr[0] = ((((float) (rectangle.width * 914400)) / 96.0f) / f) / rectX2;
                fArr[1] = ((((float) (rectangle.height * 914400)) / 96.0f) / f2) / rectY2;
            }
        }
        return fArr;
    }

    public boolean getFlipHorizontal() {
        return ShapeKit.getGroupFlipHorizontal(getSpContainer());
    }

    public boolean getFlipVertical() {
        return ShapeKit.getGroupFlipVertical(getSpContainer());
    }

    public int getGroupRotation() {
        return ShapeKit.getGroupRotation(getSpContainer());
    }

    public HWPFShape[] getShapes() {
        Iterator<EscherRecord> childIterator = getSpContainer().getChildIterator();
        if (childIterator.hasNext()) {
            childIterator.next();
        }
        ArrayList arrayList = new ArrayList();
        while (childIterator.hasNext()) {
            EscherRecord next = childIterator.next();
            if (next instanceof EscherContainerRecord) {
                arrayList.add(HWPFShapeFactory.createShape((EscherContainerRecord) next, this));
            }
        }
        return (HWPFShape[]) arrayList.toArray(new HWPFShape[arrayList.size()]);
    }

    public String getShapeName() {
        return ShapeKit.getShapeName(this.escherContainer);
    }
}
