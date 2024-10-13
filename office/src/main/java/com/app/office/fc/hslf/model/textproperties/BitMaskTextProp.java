package com.app.office.fc.hslf.model.textproperties;

public class BitMaskTextProp extends TextProp implements Cloneable {
    private int[] subPropMasks;
    private boolean[] subPropMatches;
    private String[] subPropNames;

    public String[] getSubPropNames() {
        return this.subPropNames;
    }

    public boolean[] getSubPropMatches() {
        return this.subPropMatches;
    }

    public BitMaskTextProp(int i, int i2, String str, String[] strArr) {
        super(i, i2, "bitmask");
        this.subPropNames = strArr;
        this.propName = str;
        this.subPropMasks = new int[strArr.length];
        this.subPropMatches = new boolean[strArr.length];
        int i3 = 0;
        while (true) {
            int[] iArr = this.subPropMasks;
            if (i3 < iArr.length) {
                iArr[i3] = 1 << i3;
                i3++;
            } else {
                return;
            }
        }
    }

    public int getWriteMask() {
        return this.dataValue;
    }

    public void setValue(int i) {
        this.dataValue = i;
        int i2 = 0;
        while (true) {
            boolean[] zArr = this.subPropMatches;
            if (i2 < zArr.length) {
                zArr[i2] = false;
                if ((this.dataValue & this.subPropMasks[i2]) != 0) {
                    this.subPropMatches[i2] = true;
                }
                i2++;
            } else {
                return;
            }
        }
    }

    public boolean getSubValue(int i) {
        return this.subPropMatches[i];
    }

    public void setSubValue(boolean z, int i) {
        if (this.subPropMatches[i] != z) {
            if (z) {
                this.dataValue += this.subPropMasks[i];
            } else {
                this.dataValue -= this.subPropMasks[i];
            }
            this.subPropMatches[i] = z;
        }
    }

    public Object clone() {
        BitMaskTextProp bitMaskTextProp = (BitMaskTextProp) super.clone();
        bitMaskTextProp.subPropMatches = new boolean[this.subPropMatches.length];
        return bitMaskTextProp;
    }
}
