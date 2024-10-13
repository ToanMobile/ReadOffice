package com.app.office.fc.hslf.model.textproperties;

public class TextProp implements Cloneable {
    protected int dataValue = 0;
    protected int maskInHeader;
    protected String propName;
    protected int sizeOfDataBlock;

    public TextProp(int i, int i2, String str) {
        this.sizeOfDataBlock = i;
        this.maskInHeader = i2;
        this.propName = str;
    }

    public String getName() {
        return this.propName;
    }

    public int getSize() {
        return this.sizeOfDataBlock;
    }

    public int getMask() {
        return this.maskInHeader;
    }

    public int getWriteMask() {
        return getMask();
    }

    public int getValue() {
        return this.dataValue;
    }

    public void setValue(int i) {
        this.dataValue = i;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
    }

    public void dispose() {
        this.propName = null;
    }
}
