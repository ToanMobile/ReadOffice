package com.app.office.fc.hslf.model;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.java.awt.Shape;
import com.app.office.java.awt.geom.Line2D;
import com.app.office.java.awt.geom.Rectangle2D;

public final class Line extends SimpleShape {
    protected Line(EscherContainerRecord escherContainerRecord, Shape shape) {
        super(escherContainerRecord, shape);
    }

    public Float[] getAdjustmentValue() {
        return ShapeKit.getAdjustmentValue(getSpContainer());
    }

    public Line(Shape shape) {
        super((EscherContainerRecord) null, shape);
        this._escherContainer = createSpContainer(shape instanceof ShapeGroup);
    }

    public Line() {
        this((Shape) null);
    }

    /* access modifiers changed from: protected */
    public EscherContainerRecord createSpContainer(boolean z) {
        this._escherContainer = super.createSpContainer(z);
        ((EscherSpRecord) this._escherContainer.getChildById(EscherSpRecord.RECORD_ID)).setOptions(EscherProperties.GEOMETRY__RIGHT);
        EscherOptRecord escherOptRecord = (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085);
        setEscherProperty(escherOptRecord, EscherProperties.GEOMETRY__SHAPEPATH, 4);
        setEscherProperty(escherOptRecord, EscherProperties.GEOMETRY__FILLOK, 65536);
        setEscherProperty(escherOptRecord, EscherProperties.FILL__NOFILLHITTEST, 1048576);
        setEscherProperty(escherOptRecord, EscherProperties.LINESTYLE__COLOR, 134217729);
        setEscherProperty(escherOptRecord, EscherProperties.LINESTYLE__NOLINEDRAWDASH, 655368);
        setEscherProperty(escherOptRecord, 513, 134217730);
        return this._escherContainer;
    }

    public Shape getOutline() {
        Rectangle2D logicalAnchor2D = getLogicalAnchor2D();
        return new Line2D.Double(logicalAnchor2D.getX(), logicalAnchor2D.getY(), logicalAnchor2D.getX() + logicalAnchor2D.getWidth(), logicalAnchor2D.getY() + logicalAnchor2D.getHeight());
    }

    public void dispose() {
        super.dispose();
    }
}
