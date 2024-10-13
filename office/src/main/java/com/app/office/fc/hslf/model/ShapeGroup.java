package com.app.office.fc.hslf.model;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherChildAnchorRecord;
import com.app.office.fc.ddf.EscherClientAnchorRecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherRecordFactory;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.ddf.EscherSpgrRecord;
import com.app.office.fc.util.LittleEndian;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

public class ShapeGroup extends Shape {
    public Hyperlink getHyperlink() {
        return null;
    }

    public ShapeGroup() {
        this((EscherContainerRecord) null, (Shape) null);
        this._escherContainer = createSpContainer(false);
    }

    protected ShapeGroup(EscherContainerRecord escherContainerRecord, Shape shape) {
        super(escherContainerRecord, shape);
    }

    public int getShapeId() {
        Iterator<EscherRecord> childIterator = this._escherContainer.getChildIterator();
        if (childIterator.hasNext()) {
            EscherRecord next = childIterator.next();
            if (next instanceof EscherContainerRecord) {
                return ((EscherSpRecord) ((EscherContainerRecord) next).getChildById(EscherSpRecord.RECORD_ID)).getShapeId();
            }
        }
        return 0;
    }

    public Shape[] getShapes() {
        Iterator<EscherRecord> childIterator = this._escherContainer.getChildIterator();
        if (childIterator.hasNext()) {
            childIterator.next();
        }
        ArrayList arrayList = new ArrayList();
        while (childIterator.hasNext()) {
            EscherRecord next = childIterator.next();
            if (next instanceof EscherContainerRecord) {
                Shape createShape = ShapeFactory.createShape((EscherContainerRecord) next, this);
                createShape.setSheet(getSheet());
                arrayList.add(createShape);
            }
        }
        return (Shape[]) arrayList.toArray(new Shape[arrayList.size()]);
    }

    public void setAnchor(Rectangle rectangle) {
        EscherContainerRecord escherContainerRecord = (EscherContainerRecord) this._escherContainer.getChild(0);
        EscherClientAnchorRecord escherClientAnchorRecord = (EscherClientAnchorRecord) ShapeKit.getEscherChild(escherContainerRecord, -4080);
        byte[] bArr = new byte[16];
        LittleEndian.putUShort(bArr, 0, 0);
        LittleEndian.putUShort(bArr, 2, 0);
        LittleEndian.putInt(bArr, 4, 8);
        escherClientAnchorRecord.fillFields(bArr, 0, (EscherRecordFactory) null);
        escherClientAnchorRecord.setFlag((short) ((int) (((float) (rectangle.y * ShapeKit.MASTER_DPI)) / 72.0f)));
        escherClientAnchorRecord.setCol1((short) ((int) (((float) (rectangle.x * ShapeKit.MASTER_DPI)) / 72.0f)));
        escherClientAnchorRecord.setDx1((short) ((int) (((float) ((rectangle.width + rectangle.x) * ShapeKit.MASTER_DPI)) / 72.0f)));
        escherClientAnchorRecord.setRow1((short) ((int) (((float) ((rectangle.height + rectangle.y) * ShapeKit.MASTER_DPI)) / 72.0f)));
        EscherSpgrRecord escherSpgrRecord = (EscherSpgrRecord) ShapeKit.getEscherChild(escherContainerRecord, -4087);
        escherSpgrRecord.setRectX1((int) (((float) (rectangle.x * ShapeKit.MASTER_DPI)) / 72.0f));
        escherSpgrRecord.setRectY1((int) (((float) (rectangle.y * ShapeKit.MASTER_DPI)) / 72.0f));
        escherSpgrRecord.setRectX2((int) (((float) ((rectangle.x + rectangle.width) * ShapeKit.MASTER_DPI)) / 72.0f));
        escherSpgrRecord.setRectY2((int) (((float) ((rectangle.y + rectangle.height) * ShapeKit.MASTER_DPI)) / 72.0f));
    }

    public void setCoordinates(Rectangle2D rectangle2D) {
        EscherSpgrRecord escherSpgrRecord = (EscherSpgrRecord) ShapeKit.getEscherChild((EscherContainerRecord) this._escherContainer.getChild(0), -4087);
        int round = (int) Math.round((rectangle2D.getX() * 576.0d) / 72.0d);
        int round2 = (int) Math.round((rectangle2D.getY() * 576.0d) / 72.0d);
        escherSpgrRecord.setRectX1(round);
        escherSpgrRecord.setRectY1(round2);
        escherSpgrRecord.setRectX2((int) Math.round(((rectangle2D.getX() + rectangle2D.getWidth()) * 576.0d) / 72.0d));
        escherSpgrRecord.setRectY2((int) Math.round(((rectangle2D.getY() + rectangle2D.getHeight()) * 576.0d) / 72.0d));
    }

    public Rectangle2D getCoordinates() {
        EscherSpgrRecord escherSpgrRecord = (EscherSpgrRecord) ShapeKit.getEscherChild((EscherContainerRecord) this._escherContainer.getChild(0), -4087);
        Rectangle2D.Float floatR = new Rectangle2D.Float();
        floatR.x = (((float) escherSpgrRecord.getRectX1()) * 72.0f) / 576.0f;
        floatR.y = (((float) escherSpgrRecord.getRectY1()) * 72.0f) / 576.0f;
        floatR.width = (((float) (escherSpgrRecord.getRectX2() - escherSpgrRecord.getRectX1())) * 72.0f) / 576.0f;
        floatR.height = (((float) (escherSpgrRecord.getRectY2() - escherSpgrRecord.getRectY1())) * 72.0f) / 576.0f;
        return floatR;
    }

    public Rectangle2D getClientAnchor2D(Shape shape) {
        Rectangle2D anchor2D = shape.getAnchor2D();
        if (shape == null || shape.getParent() == null) {
            return anchor2D;
        }
        Rectangle2D clientAnchor2D = ((ShapeGroup) shape.getParent()).getClientAnchor2D(shape.getParent());
        Rectangle2D coordinates = ((ShapeGroup) shape.getParent()).getCoordinates();
        double width = coordinates.getWidth() / clientAnchor2D.getWidth();
        double height = coordinates.getHeight() / clientAnchor2D.getHeight();
        return new Rectangle2D.Double(clientAnchor2D.getX() + ((anchor2D.getX() - coordinates.getX()) / width), clientAnchor2D.getY() + ((anchor2D.getY() - coordinates.getY()) / height), anchor2D.getWidth() / width, anchor2D.getHeight() / height);
    }

    /* access modifiers changed from: protected */
    public EscherContainerRecord createSpContainer(boolean z) {
        EscherContainerRecord escherContainerRecord = new EscherContainerRecord();
        escherContainerRecord.setRecordId(EscherContainerRecord.SPGR_CONTAINER);
        escherContainerRecord.setOptions(15);
        EscherContainerRecord escherContainerRecord2 = new EscherContainerRecord();
        escherContainerRecord2.setRecordId(EscherContainerRecord.SP_CONTAINER);
        escherContainerRecord2.setOptions(15);
        EscherSpgrRecord escherSpgrRecord = new EscherSpgrRecord();
        escherSpgrRecord.setOptions(1);
        escherContainerRecord2.addChildRecord(escherSpgrRecord);
        EscherSpRecord escherSpRecord = new EscherSpRecord();
        escherSpRecord.setOptions(2);
        escherSpRecord.setFlags(513);
        escherContainerRecord2.addChildRecord(escherSpRecord);
        escherContainerRecord2.addChildRecord(new EscherClientAnchorRecord());
        escherContainerRecord.addChildRecord(escherContainerRecord2);
        return escherContainerRecord;
    }

    public void addShape(Shape shape) {
        this._escherContainer.addChildRecord(shape.getSpContainer());
        Sheet sheet = getSheet();
        shape.setSheet(sheet);
        shape.setShapeId(sheet.allocateShapeId());
        shape.afterInsert(sheet);
    }

    public void moveTo(int i, int i2) {
        Rectangle anchor = getAnchor();
        int i3 = i - anchor.x;
        int i4 = i2 - anchor.y;
        anchor.translate(i3, i4);
        setAnchor(anchor);
        Shape[] shapes = getShapes();
        for (int i5 = 0; i5 < shapes.length; i5++) {
            Rectangle anchor2 = shapes[i5].getAnchor();
            anchor2.translate(i3, i4);
            shapes[i5].setAnchor(anchor2);
        }
    }

    public Rectangle2D getAnchor2D() {
        EscherContainerRecord escherContainerRecord = (EscherContainerRecord) this._escherContainer.getChild(0);
        EscherClientAnchorRecord escherClientAnchorRecord = (EscherClientAnchorRecord) ShapeKit.getEscherChild(escherContainerRecord, -4080);
        Rectangle2D.Float floatR = new Rectangle2D.Float();
        if (escherClientAnchorRecord == null) {
            EscherChildAnchorRecord escherChildAnchorRecord = (EscherChildAnchorRecord) ShapeKit.getEscherChild(escherContainerRecord, -4081);
            return new Rectangle2D.Float((((float) escherChildAnchorRecord.getDx1()) * 72.0f) / 576.0f, (((float) escherChildAnchorRecord.getDy1()) * 72.0f) / 576.0f, (((float) (escherChildAnchorRecord.getDx2() - escherChildAnchorRecord.getDx1())) * 72.0f) / 576.0f, (((float) (escherChildAnchorRecord.getDy2() - escherChildAnchorRecord.getDy1())) * 72.0f) / 576.0f);
        }
        floatR.x = (((float) escherClientAnchorRecord.getCol1()) * 72.0f) / 576.0f;
        floatR.y = (((float) escherClientAnchorRecord.getFlag()) * 72.0f) / 576.0f;
        floatR.width = (((float) (escherClientAnchorRecord.getDx1() - escherClientAnchorRecord.getCol1())) * 72.0f) / 576.0f;
        floatR.height = (((float) (escherClientAnchorRecord.getRow1() - escherClientAnchorRecord.getFlag())) * 72.0f) / 576.0f;
        return floatR;
    }

    public int getShapeType() {
        return ((EscherSpRecord) ((EscherContainerRecord) this._escherContainer.getChild(0)).getChildById(EscherSpRecord.RECORD_ID)).getOptions() >> 4;
    }

    public boolean getFlipHorizontal() {
        return ShapeKit.getGroupFlipHorizontal(getSpContainer());
    }

    public boolean getFlipVertical() {
        return ShapeKit.getGroupFlipVertical(getSpContainer());
    }

    public int getRotation() {
        return ShapeKit.getGroupRotation(getSpContainer());
    }

    public void dispose() {
        super.dispose();
    }
}
