package com.app.office.fc.hslf.model;

import com.app.office.fc.ddf.EscherContainerRecord;

public final class Placeholder extends TextBox {
    protected Placeholder(EscherContainerRecord escherContainerRecord, Shape shape) {
        super(escherContainerRecord, shape);
    }

    public Placeholder(Shape shape) {
        super(shape);
    }

    public Placeholder() {
    }

    /* access modifiers changed from: protected */
    public EscherContainerRecord createSpContainer(boolean z) {
        this._escherContainer = super.createSpContainer(z);
        return this._escherContainer;
    }
}
