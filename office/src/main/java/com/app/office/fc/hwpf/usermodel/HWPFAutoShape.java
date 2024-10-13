package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherContainerRecord;

public class HWPFAutoShape extends HWPFShape {
    public HWPFAutoShape(EscherContainerRecord escherContainerRecord, HWPFShape hWPFShape) {
        super(escherContainerRecord, hWPFShape);
    }

    public String getShapeName() {
        return ShapeKit.getShapeName(this.escherContainer);
    }
}
