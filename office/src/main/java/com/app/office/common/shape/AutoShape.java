package com.app.office.common.shape;

public class AutoShape extends AbstractShape {
    private boolean shape07 = true;
    private int type;
    private Float[] values;

    public short getType() {
        return 2;
    }

    public AutoShape() {
    }

    public AutoShape(int i) {
        this.type = i;
    }

    public int getShapeType() {
        return this.type;
    }

    public void setShapeType(int i) {
        this.type = i;
    }

    public void setAdjustData(Float[] fArr) {
        this.values = fArr;
    }

    public Float[] getAdjustData() {
        return this.values;
    }

    public void setAuotShape07(boolean z) {
        this.shape07 = z;
    }

    public boolean isAutoShape07() {
        return this.shape07;
    }

    public void dispose() {
        super.dispose();
    }
}
