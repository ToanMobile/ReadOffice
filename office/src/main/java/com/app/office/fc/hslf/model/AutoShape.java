package com.app.office.fc.hslf.model;

import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.java.awt.Shape;
import com.app.office.java.awt.geom.Rectangle2D;

public class AutoShape extends TextShape {
    protected AutoShape(EscherContainerRecord escherContainerRecord, Shape shape) {
        super(escherContainerRecord, shape);
    }

    /* access modifiers changed from: protected */
    public void setDefaultTextProperties(TextRun textRun) {
        setVerticalAlignment(1);
        setHorizontalAlignment(1);
        setWordWrap(2);
    }

    public AutoShape(int i, Shape shape) {
        super((EscherContainerRecord) null, shape);
        this._escherContainer = createSpContainer(i, shape instanceof ShapeGroup);
    }

    public AutoShape(int i) {
        this(i, (Shape) null);
    }

    /* access modifiers changed from: protected */
    public EscherContainerRecord createSpContainer(int i, boolean z) {
        this._escherContainer = super.createSpContainer(z);
        setShapeType(i);
        setEscherProperty(EscherProperties.PROTECTION__LOCKAGAINSTGROUPING, 262144);
        setEscherProperty(EscherProperties.FILL__FILLCOLOR, 134217732);
        setEscherProperty(EscherProperties.FILL__FILLCOLOR, 134217732);
        setEscherProperty(EscherProperties.FILL__FILLBACKCOLOR, 134217728);
        setEscherProperty(EscherProperties.FILL__NOFILLHITTEST, 1048592);
        setEscherProperty(EscherProperties.LINESTYLE__COLOR, 134217729);
        setEscherProperty(EscherProperties.LINESTYLE__NOLINEDRAWDASH, 524296);
        setEscherProperty(513, 134217730);
        return this._escherContainer;
    }

    public int getAdjustmentValue(int i) {
        if (i >= 0 && i <= 9) {
            return ShapeKit.getEscherProperty(this._escherContainer, (short) (i + TIFFConstants.TIFFTAG_CLEANFAXDATA));
        }
        throw new IllegalArgumentException("The index of an adjustment value must be in the [0, 9] range");
    }

    public void setAdjustmentValue(int i, int i2) {
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException("The index of an adjustment value must be in the [0, 9] range");
        }
        setEscherProperty((short) (i + TIFFConstants.TIFFTAG_CLEANFAXDATA), i2);
    }

    public Shape getOutline() {
        ShapeOutline shapeOutline = AutoShapes.getShapeOutline(getShapeType());
        Rectangle2D logicalAnchor2D = getLogicalAnchor2D();
        if (shapeOutline == null) {
            return logicalAnchor2D;
        }
        return AutoShapes.transform(shapeOutline.getOutline(this), logicalAnchor2D);
    }

    public void dispose() {
        super.dispose();
    }
}
