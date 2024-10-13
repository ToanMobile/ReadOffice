package com.app.office.fc.hssf.usermodel;

import android.graphics.Path;
import android.graphics.PointF;
import com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail;
import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.java.awt.Rectangle;
import com.app.office.ss.model.XLSModel.AWorkbook;

public class HSSFFreeform extends HSSFAutoShape {
    public HSSFFreeform(AWorkbook aWorkbook, EscherContainerRecord escherContainerRecord, HSSFShape hSSFShape, HSSFAnchor hSSFAnchor, int i) {
        super(aWorkbook, escherContainerRecord, hSSFShape, hSSFAnchor, i);
        processLineWidth();
        processArrow(escherContainerRecord);
    }

    public Path[] getFreeformPath(Rectangle rectangle, PointF pointF, byte b, PointF pointF2, byte b2) {
        return ShapeKit.getFreeformPath(this.escherContainer, rectangle, pointF, b, pointF2, b2);
    }

    public ArrowPathAndTail getStartArrowPath(Rectangle rectangle) {
        return ShapeKit.getStartArrowPathAndTail(this.escherContainer, rectangle);
    }

    public ArrowPathAndTail getEndArrowPath(Rectangle rectangle) {
        return ShapeKit.getEndArrowPathAndTail(this.escherContainer, rectangle);
    }
}
