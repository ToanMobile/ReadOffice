package com.app.office.fc.hssf.usermodel;

import androidx.core.view.MotionEventCompat;

public final class HSSFClientAnchor extends HSSFAnchor implements IClientAnchor {
    int anchorType;
    short col1;
    short col2;
    int row1;
    int row2;

    public HSSFClientAnchor() {
    }

    public HSSFClientAnchor(int i, int i2, int i3, int i4, short s, int i5, short s2, int i6) {
        super(i, i2, i3, i4);
        checkRange(i, 0, IEEEDouble.EXPONENT_BIAS, "dx1");
        checkRange(i3, 0, IEEEDouble.EXPONENT_BIAS, "dx2");
        checkRange(i2, 0, 255, "dy1");
        checkRange(i4, 0, 255, "dy2");
        checkRange(s, 0, 255, "col1");
        checkRange(s2, 0, 255, "col2");
        checkRange(i5, 0, MotionEventCompat.ACTION_POINTER_INDEX_MASK, "row1");
        checkRange(i6, 0, MotionEventCompat.ACTION_POINTER_INDEX_MASK, "row2");
        this.col1 = s;
        this.row1 = i5;
        this.col2 = s2;
        this.row2 = i6;
    }

    public float getAnchorHeightInPoints(HSSFSheet hSSFSheet) {
        int dy1 = getDy1();
        int dy2 = getDy2();
        int min = Math.min(getRow1(), getRow2());
        int max = Math.max(getRow1(), getRow2());
        if (min == max) {
            return (((float) (dy2 - dy1)) / 256.0f) * getRowHeightInPoints(hSSFSheet, max);
        }
        float rowHeightInPoints = ((256.0f - ((float) dy1)) / 256.0f) * getRowHeightInPoints(hSSFSheet, min);
        float f = 0.0f;
        while (true) {
            rowHeightInPoints += f;
            min++;
            if (min >= max) {
                return rowHeightInPoints + ((((float) dy2) / 256.0f) * getRowHeightInPoints(hSSFSheet, max));
            }
            f = getRowHeightInPoints(hSSFSheet, min);
        }
    }

    private float getRowHeightInPoints(HSSFSheet hSSFSheet, int i) {
        HSSFRow row = hSSFSheet.getRow(i);
        if (row == null) {
            return hSSFSheet.getDefaultRowHeightInPoints();
        }
        return row.getHeightInPoints();
    }

    public short getCol1() {
        return this.col1;
    }

    public void setCol1(short s) {
        checkRange(s, 0, 255, "col1");
        this.col1 = s;
    }

    public void setCol1(int i) {
        setCol1((short) i);
    }

    public short getCol2() {
        return this.col2;
    }

    public void setCol2(short s) {
        checkRange(s, 0, 255, "col2");
        this.col2 = s;
    }

    public void setCol2(int i) {
        setCol2((short) i);
    }

    public int getRow1() {
        return this.row1;
    }

    public void setRow1(int i) {
        checkRange(i, 0, 65536, "row1");
        this.row1 = i;
    }

    public int getRow2() {
        return this.row2;
    }

    public void setRow2(int i) {
        checkRange(i, 0, 65536, "row2");
        this.row2 = i;
    }

    public void setAnchor(short s, int i, int i2, int i3, short s2, int i4, int i5, int i6) {
        checkRange(this.dx1, 0, IEEEDouble.EXPONENT_BIAS, "dx1");
        checkRange(this.dx2, 0, IEEEDouble.EXPONENT_BIAS, "dx2");
        checkRange(this.dy1, 0, 255, "dy1");
        checkRange(this.dy2, 0, 255, "dy2");
        checkRange(s, 0, 255, "col1");
        checkRange(s2, 0, 255, "col2");
        checkRange(i, 0, MotionEventCompat.ACTION_POINTER_INDEX_MASK, "row1");
        checkRange(i4, 0, MotionEventCompat.ACTION_POINTER_INDEX_MASK, "row2");
        this.col1 = s;
        this.row1 = i;
        this.dx1 = i2;
        this.dy1 = i3;
        this.col2 = s2;
        this.row2 = i4;
        this.dx2 = i5;
        this.dy2 = i6;
    }

    public boolean isHorizontallyFlipped() {
        short s = this.col1;
        short s2 = this.col2;
        if (s == s2) {
            if (this.dx1 > this.dx2) {
                return true;
            }
            return false;
        } else if (s > s2) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isVerticallyFlipped() {
        int i = this.row1;
        int i2 = this.row2;
        if (i == i2) {
            if (this.dy1 > this.dy2) {
                return true;
            }
            return false;
        } else if (i > i2) {
            return true;
        } else {
            return false;
        }
    }

    public int getAnchorType() {
        return this.anchorType;
    }

    public void setAnchorType(int i) {
        this.anchorType = i;
    }

    private void checkRange(int i, int i2, int i3, String str) {
        if (i < i2 || i > i3) {
            throw new IllegalArgumentException(str + " must be between " + i2 + " and " + i3);
        }
    }
}
