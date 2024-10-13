package com.app.office.simpletext.model;

public class AttributeSetImpl implements IAttributeSet {
    public static final int CAPACITY = 5;
    private int ID;
    private short[] arrayID = new short[10];
    private int[] arrayValue = new int[10];
    private int size = 0;

    public int getID() {
        return this.ID;
    }

    public void setAttribute(short s, int i) {
        if (this.size >= this.arrayID.length) {
            ensureCapacity();
        }
        int iDIndex = getIDIndex(s);
        if (iDIndex >= 0) {
            this.arrayValue[iDIndex] = i;
            return;
        }
        short[] sArr = this.arrayID;
        int i2 = this.size;
        sArr[i2] = s;
        this.arrayValue[i2] = i;
        this.size = i2 + 1;
    }

    public void removeAttribute(short s) {
        int iDIndex = getIDIndex(s);
        if (iDIndex >= 0) {
            while (true) {
                iDIndex++;
                int i = this.size;
                if (iDIndex < i) {
                    short[] sArr = this.arrayID;
                    int i2 = iDIndex - 1;
                    sArr[i2] = sArr[iDIndex];
                    int[] iArr = this.arrayValue;
                    iArr[i2] = iArr[iDIndex];
                } else {
                    this.size = i - 1;
                    return;
                }
            }
        }
    }

    public int getAttribute(short s) {
        return getAttribute(s, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0014, code lost:
        r5 = getIDIndex(0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int getAttribute(short r4, boolean r5) {
        /*
            r3 = this;
            int r0 = r3.getIDIndex(r4)
            if (r0 < 0) goto L_0x000b
            int[] r4 = r3.arrayValue
            r4 = r4[r0]
            return r4
        L_0x000b:
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r5 != 0) goto L_0x0010
            return r0
        L_0x0010:
            r5 = 4095(0xfff, float:5.738E-42)
            if (r4 >= r5) goto L_0x002c
            r5 = 0
            int r5 = r3.getIDIndex(r5)
            if (r5 < 0) goto L_0x002c
            com.app.office.simpletext.model.StyleManage r1 = com.app.office.simpletext.model.StyleManage.instance()
            int[] r2 = r3.arrayValue
            r5 = r2[r5]
            com.app.office.simpletext.model.Style r5 = r1.getStyle(r5)
            int r5 = r3.getAttributeForStyle(r5, r4)
            goto L_0x002e
        L_0x002c:
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
        L_0x002e:
            if (r5 == r0) goto L_0x0031
            return r5
        L_0x0031:
            r0 = 4096(0x1000, float:5.74E-42)
            int r0 = r3.getIDIndex(r0)
            if (r0 < 0) goto L_0x0049
            com.app.office.simpletext.model.StyleManage r5 = com.app.office.simpletext.model.StyleManage.instance()
            int[] r1 = r3.arrayValue
            r0 = r1[r0]
            com.app.office.simpletext.model.Style r5 = r5.getStyle(r0)
            int r5 = r3.getAttributeForStyle(r5, r4)
        L_0x0049:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.simpletext.model.AttributeSetImpl.getAttribute(short, boolean):int");
    }

    private int getAttributeForStyle(Style style, short s) {
        int attribute = ((AttributeSetImpl) style.getAttrbuteSet()).getAttribute(s, false);
        if (attribute != Integer.MIN_VALUE) {
            return attribute;
        }
        if (style.getBaseID() >= 0) {
            return getAttributeForStyle(StyleManage.instance().getStyle(style.getBaseID()), s);
        }
        return Integer.MIN_VALUE;
    }

    public void mergeAttribute(IAttributeSet iAttributeSet) {
        if (iAttributeSet instanceof AttributeSetImpl) {
            AttributeSetImpl attributeSetImpl = (AttributeSetImpl) iAttributeSet;
            int length = attributeSetImpl.arrayID.length;
            for (int i = 0; i < length; i++) {
                int iDIndex = getIDIndex(attributeSetImpl.arrayID[i]);
                if (iDIndex > 0) {
                    this.arrayValue[iDIndex] = attributeSetImpl.arrayValue[i];
                } else {
                    if (this.size >= this.arrayID.length) {
                        ensureCapacity();
                    }
                    short[] sArr = this.arrayID;
                    int i2 = this.size;
                    sArr[i2] = attributeSetImpl.arrayID[i];
                    this.arrayValue[i2] = attributeSetImpl.arrayValue[i];
                    this.size = i2 + 1;
                }
            }
        }
    }

    public IAttributeSet clone() {
        AttributeSetImpl attributeSetImpl = new AttributeSetImpl();
        attributeSetImpl.size = this.size;
        int i = this.size;
        short[] sArr = new short[i];
        System.arraycopy(this.arrayID, 0, sArr, 0, i);
        attributeSetImpl.arrayID = sArr;
        int i2 = this.size;
        int[] iArr = new int[i2];
        System.arraycopy(this.arrayValue, 0, iArr, 0, i2);
        attributeSetImpl.arrayValue = iArr;
        return attributeSetImpl;
    }

    private int getIDIndex(int i) {
        for (int i2 = 0; i2 < this.size; i2++) {
            if (this.arrayID[i2] == i) {
                return i2;
            }
        }
        return -1;
    }

    private void ensureCapacity() {
        int i = this.size;
        int i2 = i + 5;
        short[] sArr = new short[i2];
        System.arraycopy(this.arrayID, 0, sArr, 0, i);
        this.arrayID = sArr;
        int[] iArr = new int[i2];
        System.arraycopy(this.arrayValue, 0, iArr, 0, this.size);
        this.arrayValue = iArr;
    }

    public void dispose() {
        this.arrayID = null;
        this.arrayValue = null;
    }
}
