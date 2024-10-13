package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.java.awt.Color;
import com.app.office.ss.model.XLSModel.AWorkbook;

public class HSSFSimpleShape extends HSSFShape {
    public static final short OBJECT_TYPE_COMBO_BOX = 20;
    public static final short OBJECT_TYPE_COMMENT = 25;
    public static final short OBJECT_TYPE_LINE = 1;
    public static final short OBJECT_TYPE_OVAL = 3;
    public static final short OBJECT_TYPE_PICTURE = 8;
    public static final short OBJECT_TYPE_RECTANGLE = 2;

    public HSSFSimpleShape(EscherContainerRecord escherContainerRecord, HSSFShape hSSFShape, HSSFAnchor hSSFAnchor) {
        super(escherContainerRecord, hSSFShape, hSSFAnchor);
    }

    public void processLine(EscherContainerRecord escherContainerRecord, AWorkbook aWorkbook) {
        if (ShapeKit.hasLine(escherContainerRecord)) {
            Color lineColor = ShapeKit.getLineColor(escherContainerRecord, aWorkbook, 1);
            if (lineColor != null) {
                setLineStyleColor(lineColor.getRGB());
            } else {
                setNoBorder(true);
            }
            setLineStyle(ShapeKit.getLineDashing(escherContainerRecord));
            return;
        }
        setNoBorder(true);
    }

    public void processArrow(EscherContainerRecord escherContainerRecord) {
        setStartArrow((byte) ShapeKit.getStartArrowType(escherContainerRecord), ShapeKit.getStartArrowWidth(escherContainerRecord), ShapeKit.getStartArrowLength(escherContainerRecord));
        setEndArrow((byte) ShapeKit.getEndArrowType(escherContainerRecord), ShapeKit.getEndArrowWidth(escherContainerRecord), ShapeKit.getEndArrowLength(escherContainerRecord));
    }

    public void processRotationAndFlip(EscherContainerRecord escherContainerRecord) {
        setRotation(ShapeKit.getRotation(escherContainerRecord));
        setFilpH(ShapeKit.getFlipHorizontal(escherContainerRecord));
        setFlipV(ShapeKit.getFlipVertical(escherContainerRecord));
    }
}
