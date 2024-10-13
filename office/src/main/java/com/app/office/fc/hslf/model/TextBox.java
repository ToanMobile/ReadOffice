package com.app.office.fc.hslf.model;

import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherProperties;

public class TextBox extends TextShape {
    protected TextBox(EscherContainerRecord escherContainerRecord, Shape shape) {
        super(escherContainerRecord, shape);
    }

    public TextBox(Shape shape) {
        super(shape);
    }

    public TextBox() {
        this((Shape) null);
    }

    /* access modifiers changed from: protected */
    public EscherContainerRecord createSpContainer(boolean z) {
        this._escherContainer = super.createSpContainer(z);
        setShapeType(202);
        setEscherProperty(EscherProperties.FILL__FILLCOLOR, 134217732);
        setEscherProperty(EscherProperties.FILL__FILLBACKCOLOR, 134217728);
        setEscherProperty(EscherProperties.FILL__NOFILLHITTEST, 1048576);
        setEscherProperty(EscherProperties.LINESTYLE__COLOR, 134217729);
        setEscherProperty(EscherProperties.LINESTYLE__NOLINEDRAWDASH, 524288);
        setEscherProperty(513, 134217730);
        this._txtrun = createTextRun();
        return this._escherContainer;
    }

    /* access modifiers changed from: protected */
    public void setDefaultTextProperties(TextRun textRun) {
        setVerticalAlignment(0);
        setEscherProperty(EscherProperties.TEXT__SIZE_TEXT_TO_FIT_SHAPE, 131074);
    }
}
