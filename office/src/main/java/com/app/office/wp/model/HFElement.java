package com.app.office.wp.model;

import com.app.office.simpletext.model.AbstractElement;

public class HFElement extends AbstractElement {
    private short elemType;
    private byte hfType;

    public HFElement(short s, byte b) {
        this.hfType = b;
        this.elemType = s;
    }

    public short getType() {
        return this.elemType;
    }

    public byte getHFType() {
        return this.hfType;
    }
}
