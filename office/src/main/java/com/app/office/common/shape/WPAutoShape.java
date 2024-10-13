package com.app.office.common.shape;

import com.app.office.java.awt.Rectangle;

public class WPAutoShape extends WPAbstractShape {
    private WPGroupShape groupShape;

    public short getType() {
        return 2;
    }

    public boolean isWatermarkShape() {
        return false;
    }

    public Rectangle getBounds() {
        WPGroupShape wPGroupShape = this.groupShape;
        if (wPGroupShape != null) {
            return wPGroupShape.getBounds();
        }
        return super.getBounds();
    }

    public void addGroupShape(WPGroupShape wPGroupShape) {
        this.groupShape = wPGroupShape;
    }

    public WPGroupShape getGroupShape() {
        return this.groupShape;
    }

    public void dispose() {
        super.dispose();
        WPGroupShape wPGroupShape = this.groupShape;
        if (wPGroupShape != null) {
            wPGroupShape.dispose();
            this.groupShape = null;
        }
    }
}
