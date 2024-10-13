package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.ss.model.XLSModel.AWorkbook;

public class HSSFLine extends HSSFSimpleShape {
    private Float[] adjusts;

    public HSSFLine(AWorkbook aWorkbook, EscherContainerRecord escherContainerRecord, HSSFShape hSSFShape, HSSFAnchor hSSFAnchor, int i) {
        super(escherContainerRecord, hSSFShape, hSSFAnchor);
        setShapeType(i);
        processLineWidth();
        processLine(escherContainerRecord, aWorkbook);
        processArrow(escherContainerRecord);
        setAdjustmentValue(escherContainerRecord);
        processRotationAndFlip(escherContainerRecord);
    }

    public Float[] getAdjustmentValue() {
        return this.adjusts;
    }

    public void setAdjustmentValue(EscherContainerRecord escherContainerRecord) {
        this.adjusts = ShapeKit.getAdjustmentValue(escherContainerRecord);
    }
}
