package com.app.office.fc.hslf.model;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherChildAnchorRecord;
import com.app.office.fc.ddf.EscherClientAnchorRecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperty;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.hslf.record.ColorSchemeAtom;
import com.app.office.java.awt.Color;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.geom.Rectangle2D;
import java.util.Iterator;

public abstract class Shape {
    protected EscherContainerRecord _escherContainer;
    protected Fill _fill;
    protected Shape _parent;
    protected Sheet _sheet;

    /* access modifiers changed from: protected */
    public void afterInsert(Sheet sheet) {
    }

    /* access modifiers changed from: protected */
    public abstract EscherContainerRecord createSpContainer(boolean z);

    protected Shape(EscherContainerRecord escherContainerRecord, Shape shape) {
        this._escherContainer = escherContainerRecord;
        this._parent = shape;
    }

    public Shape getParent() {
        return this._parent;
    }

    public int getShapeType() {
        return ShapeKit.getShapeType(this._escherContainer);
    }

    public int getShapeId() {
        return ShapeKit.getShapeId(this._escherContainer);
    }

    public boolean isHidden() {
        return ShapeKit.isHidden(this._escherContainer);
    }

    public int getMasterShapeID() {
        return ShapeKit.getMasterShapeID(this._escherContainer);
    }

    public Rectangle2D getAnchor2D() {
        if ((((EscherSpRecord) this._escherContainer.getChildById(EscherSpRecord.RECORD_ID)).getFlags() & 2) != 0) {
            EscherChildAnchorRecord escherChildAnchorRecord = (EscherChildAnchorRecord) ShapeKit.getEscherChild(this._escherContainer, -4081);
            if (escherChildAnchorRecord != null) {
                return new Rectangle2D.Float((((float) escherChildAnchorRecord.getDx1()) * 72.0f) / 576.0f, (((float) escherChildAnchorRecord.getDy1()) * 72.0f) / 576.0f, (((float) (escherChildAnchorRecord.getDx2() - escherChildAnchorRecord.getDx1())) * 72.0f) / 576.0f, (((float) (escherChildAnchorRecord.getDy2() - escherChildAnchorRecord.getDy1())) * 72.0f) / 576.0f);
            }
            EscherClientAnchorRecord escherClientAnchorRecord = (EscherClientAnchorRecord) ShapeKit.getEscherChild(this._escherContainer, -4080);
            return new Rectangle2D.Float((((float) escherClientAnchorRecord.getCol1()) * 72.0f) / 576.0f, (((float) escherClientAnchorRecord.getFlag()) * 72.0f) / 576.0f, (((float) (escherClientAnchorRecord.getDx1() - escherClientAnchorRecord.getCol1())) * 72.0f) / 576.0f, (((float) (escherClientAnchorRecord.getRow1() - escherClientAnchorRecord.getFlag())) * 72.0f) / 576.0f);
        }
        EscherClientAnchorRecord escherClientAnchorRecord2 = (EscherClientAnchorRecord) ShapeKit.getEscherChild(this._escherContainer, -4080);
        return new Rectangle2D.Float((((float) escherClientAnchorRecord2.getCol1()) * 72.0f) / 576.0f, (((float) escherClientAnchorRecord2.getFlag()) * 72.0f) / 576.0f, (((float) (escherClientAnchorRecord2.getDx1() - escherClientAnchorRecord2.getCol1())) * 72.0f) / 576.0f, (((float) (escherClientAnchorRecord2.getRow1() - escherClientAnchorRecord2.getFlag())) * 72.0f) / 576.0f);
    }

    public Rectangle getAnchor() {
        return getAnchor2D().getBounds();
    }

    public EscherContainerRecord getSpContainer() {
        return this._escherContainer;
    }

    public Sheet getSheet() {
        return this._sheet;
    }

    public void setSheet(Sheet sheet) {
        this._sheet = sheet;
    }

    /* access modifiers changed from: protected */
    public Color getColor(int i, int i2) {
        if (i >= 134217728) {
            int i3 = i - 134217728;
            ColorSchemeAtom colorScheme = getSheet().getColorScheme();
            if (i3 >= 0 && i3 <= 7) {
                i = colorScheme.getColor(i3);
            }
        }
        Color color = new Color(i, true);
        return new Color(color.getBlue(), color.getGreen(), color.getRed(), i2);
    }

    public Fill getFill() {
        if (this._fill == null) {
            this._fill = new Fill(this);
        }
        return this._fill;
    }

    public Hyperlink getHyperlink() {
        return Hyperlink.find(this);
    }

    public static EscherRecord getEscherChild(EscherContainerRecord escherContainerRecord, int i) {
        Iterator<EscherRecord> childIterator = escherContainerRecord.getChildIterator();
        while (childIterator.hasNext()) {
            EscherRecord next = childIterator.next();
            if (next.getRecordId() == i) {
                return next;
            }
        }
        return null;
    }

    public static EscherProperty getEscherProperty(EscherOptRecord escherOptRecord, int i) {
        if (escherOptRecord == null) {
            return null;
        }
        for (EscherProperty next : escherOptRecord.getEscherProperties()) {
            if (next.getPropertyNumber() == i) {
                return next;
            }
        }
        return null;
    }

    public static void setEscherProperty(EscherOptRecord escherOptRecord, short s, int i) {
        Iterator<EscherProperty> it = escherOptRecord.getEscherProperties().iterator();
        while (it.hasNext()) {
            if (it.next().getId() == s) {
                it.remove();
            }
        }
        if (i != -1) {
            escherOptRecord.addEscherProperty(new EscherSimpleProperty(s, i));
            escherOptRecord.sortProperties();
        }
    }

    public void setEscherProperty(short s, int i) {
        setEscherProperty((EscherOptRecord) getEscherChild(this._escherContainer, -4085), s, i);
    }

    public int getEscherProperty(short s) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((EscherOptRecord) getEscherChild(this._escherContainer, -4085), (int) s);
        if (escherSimpleProperty == null) {
            return 0;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    public int getEscherProperty(short s, int i) {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) getEscherProperty((EscherOptRecord) getEscherChild(this._escherContainer, -4085), (int) s);
        return escherSimpleProperty == null ? i : escherSimpleProperty.getPropertyValue();
    }

    public Float[] getAdjustmentValue() {
        return ShapeKit.getAdjustmentValue(this._escherContainer);
    }

    public int getStartArrowType() {
        EscherSimpleProperty escherSimpleProperty;
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(this._escherContainer, -4085);
        if (escherOptRecord == null || (escherSimpleProperty = (EscherSimpleProperty) getEscherProperty(escherOptRecord, 464)) == null || escherSimpleProperty.getPropertyValue() <= 0) {
            return 0;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    public int getStartArrowWidth() {
        return ShapeKit.getStartArrowWidth(this._escherContainer);
    }

    public int getStartArrowLength() {
        return ShapeKit.getStartArrowLength(this._escherContainer);
    }

    public int getEndArrowType() {
        EscherSimpleProperty escherSimpleProperty;
        EscherOptRecord escherOptRecord = (EscherOptRecord) getEscherChild(this._escherContainer, -4085);
        if (escherOptRecord == null || (escherSimpleProperty = (EscherSimpleProperty) getEscherProperty(escherOptRecord, 465)) == null || escherSimpleProperty.getPropertyValue() <= 0) {
            return 0;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    public int getEndArrowWidth() {
        return ShapeKit.getEndArrowWidth(this._escherContainer);
    }

    public int getEndArrowLength() {
        return ShapeKit.getEndArrowLength(this._escherContainer);
    }

    public boolean hasLine() {
        return ShapeKit.hasLine(getSpContainer());
    }

    public double getLineWidth() {
        return (double) ShapeKit.getLineWidth(getSpContainer());
    }

    public Color getLineColor() {
        return ShapeKit.getLineColor(getSpContainer(), getSheet(), 2);
    }

    public Color getFillColor() {
        return getFill().getForegroundColor();
    }

    public boolean getFlipHorizontal() {
        return ShapeKit.getFlipHorizontal(getSpContainer());
    }

    public boolean getFlipVertical() {
        return ShapeKit.getFlipVertical(getSpContainer());
    }

    public int getRotation() {
        return ShapeKit.getRotation(getSpContainer());
    }

    public void dispose() {
        this._parent = null;
        this._sheet = null;
        EscherContainerRecord escherContainerRecord = this._escherContainer;
        if (escherContainerRecord != null) {
            escherContainerRecord.dispose();
            this._escherContainer = null;
        }
        Fill fill = this._fill;
        if (fill != null) {
            fill.dispose();
            this._fill = null;
        }
    }

    public String getShapeName() {
        return ShapeTypes.typeName(getShapeType());
    }

    public void setShapeType(int i) {
        ((EscherSpRecord) this._escherContainer.getChildById(EscherSpRecord.RECORD_ID)).setOptions((short) ((i << 4) | 2));
    }

    public Rectangle2D getLogicalAnchor2D() {
        return getAnchor2D();
    }

    public void setAnchor(Rectangle2D rectangle2D) {
        if ((((EscherSpRecord) this._escherContainer.getChildById(EscherSpRecord.RECORD_ID)).getFlags() & 2) != 0) {
            EscherChildAnchorRecord escherChildAnchorRecord = (EscherChildAnchorRecord) ShapeKit.getEscherChild(this._escherContainer, -4081);
            escherChildAnchorRecord.setDx1((int) ((rectangle2D.getX() * 576.0d) / 72.0d));
            escherChildAnchorRecord.setDy1((int) ((rectangle2D.getY() * 576.0d) / 72.0d));
            escherChildAnchorRecord.setDx2((int) (((rectangle2D.getWidth() + rectangle2D.getX()) * 576.0d) / 72.0d));
            escherChildAnchorRecord.setDy2((int) (((rectangle2D.getHeight() + rectangle2D.getY()) * 576.0d) / 72.0d));
            return;
        }
        EscherClientAnchorRecord escherClientAnchorRecord = (EscherClientAnchorRecord) ShapeKit.getEscherChild(this._escherContainer, -4080);
        escherClientAnchorRecord.setFlag((short) ((int) ((rectangle2D.getY() * 576.0d) / 72.0d)));
        escherClientAnchorRecord.setCol1((short) ((int) ((rectangle2D.getX() * 576.0d) / 72.0d)));
        escherClientAnchorRecord.setDx1((short) ((int) (((rectangle2D.getWidth() + rectangle2D.getX()) * 576.0d) / 72.0d)));
        escherClientAnchorRecord.setRow1((short) ((int) (((rectangle2D.getHeight() + rectangle2D.getY()) * 576.0d) / 72.0d)));
    }

    public void moveTo(float f, float f2) {
        Rectangle2D anchor2D = getAnchor2D();
        anchor2D.setRect((double) f, (double) f2, anchor2D.getWidth(), anchor2D.getHeight());
        setAnchor(anchor2D);
    }

    public void setShapeId(int i) {
        EscherSpRecord escherSpRecord = (EscherSpRecord) this._escherContainer.getChildById(EscherSpRecord.RECORD_ID);
        if (escherSpRecord != null) {
            escherSpRecord.setShapeId(i);
        }
    }

    public com.app.office.java.awt.Shape getOutline() {
        return getLogicalAnchor2D();
    }
}
