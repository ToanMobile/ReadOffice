package com.app.office.fc.hslf.model.textproperties;

public class AutoNumberTextProp {
    private int numberingType = -1;
    private int start = 0;

    public void dispose() {
    }

    public AutoNumberTextProp() {
    }

    public AutoNumberTextProp(int i, int i2) {
        this.numberingType = i;
        this.start = i2;
    }

    public int getNumberingType() {
        return this.numberingType;
    }

    public void setNumberingType(int i) {
        this.numberingType = i;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int i) {
        this.start = i;
    }
}
