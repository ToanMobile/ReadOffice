package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.ddf.EscherContainerRecord;

public class HSSFPolygon extends HSSFShape {
    int drawAreaHeight = 100;
    int drawAreaWidth = 100;
    int[] xPoints;
    int[] yPoints;

    HSSFPolygon(EscherContainerRecord escherContainerRecord, HSSFShape hSSFShape, HSSFAnchor hSSFAnchor) {
        super(escherContainerRecord, hSSFShape, hSSFAnchor);
    }

    public int[] getXPoints() {
        return this.xPoints;
    }

    public int[] getYPoints() {
        return this.yPoints;
    }

    public void setPoints(int[] iArr, int[] iArr2) {
        this.xPoints = cloneArray(iArr);
        this.yPoints = cloneArray(iArr2);
    }

    private int[] cloneArray(int[] iArr) {
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = iArr[i];
        }
        return iArr2;
    }

    public void setPolygonDrawArea(int i, int i2) {
        this.drawAreaWidth = i;
        this.drawAreaHeight = i2;
    }

    public int getDrawAreaWidth() {
        return this.drawAreaWidth;
    }

    public int getDrawAreaHeight() {
        return this.drawAreaHeight;
    }
}
