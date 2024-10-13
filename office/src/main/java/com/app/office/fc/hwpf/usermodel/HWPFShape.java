package com.app.office.fc.hwpf.usermodel;

import android.graphics.Path;
import android.graphics.PointF;
import com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Line;
import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.AbstractEscherOptRecord;
import com.app.office.fc.ddf.EscherChildAnchorRecord;
import com.app.office.fc.ddf.EscherClientAnchorRecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.java.awt.Color;
import com.app.office.java.awt.Rectangle;

public abstract class HWPFShape {
    public static final byte POSH_ABS = 0;
    public static final byte POSH_CENTER = 2;
    public static final byte POSH_INSIDE = 4;
    public static final byte POSH_LEFT = 1;
    public static final byte POSH_OUTSIDE = 5;
    public static final byte POSH_RIGHT = 3;
    public static final byte POSRELH_CHAR = 3;
    public static final byte POSRELH_COLUMN = 2;
    public static final byte POSRELH_MARGIN = 0;
    public static final byte POSRELH_PAGE = 1;
    public static final byte POSRELV_LINE = 3;
    public static final byte POSRELV_MARGIN = 0;
    public static final byte POSRELV_PAGE = 1;
    public static final byte POSRELV_TEXT = 2;
    public static final byte POSV_ABS = 0;
    public static final byte POSV_BOTTOM = 3;
    public static final byte POSV_CENTER = 2;
    public static final byte POSV_INSIDE = 4;
    public static final byte POSV_OUTSIDE = 5;
    public static final byte POSV_TOP = 1;
    protected EscherContainerRecord escherContainer;
    protected HWPFShape parent;

    public HWPFShape(EscherContainerRecord escherContainerRecord, HWPFShape hWPFShape) {
        this.escherContainer = escherContainerRecord;
        this.parent = hWPFShape;
    }

    public EscherContainerRecord getSpContainer() {
        return this.escherContainer;
    }

    public HWPFShape getParent() {
        return this.parent;
    }

    public int getShapeType() {
        return ShapeKit.getShapeType(this.escherContainer);
    }

    public Rectangle getAnchor(Rectangle rectangle, float f, float f2) {
        EscherSpRecord escherSpRecord = (EscherSpRecord) this.escherContainer.getChildById(EscherSpRecord.RECORD_ID);
        Rectangle rectangle2 = null;
        if (escherSpRecord != null) {
            if ((escherSpRecord.getFlags() & 2) != 0) {
                EscherChildAnchorRecord escherChildAnchorRecord = (EscherChildAnchorRecord) ShapeKit.getEscherChild(this.escherContainer, -4081);
                if (escherChildAnchorRecord == null) {
                    EscherClientAnchorRecord escherClientAnchorRecord = (EscherClientAnchorRecord) ShapeKit.getEscherChild(this.escherContainer, -4080);
                    if (escherClientAnchorRecord != null) {
                        rectangle2 = new Rectangle();
                        rectangle2.x = (int) (((((float) escherClientAnchorRecord.getCol1()) * f) * 96.0f) / 914400.0f);
                        rectangle2.y = (int) (((((float) escherClientAnchorRecord.getFlag()) * f2) * 96.0f) / 914400.0f);
                        rectangle2.width = (int) (((((float) (escherClientAnchorRecord.getDx1() - escherClientAnchorRecord.getCol1())) * f) * 96.0f) / 914400.0f);
                        rectangle2.height = (int) (((((float) (escherClientAnchorRecord.getRow1() - escherClientAnchorRecord.getFlag())) * f2) * 96.0f) / 914400.0f);
                    }
                } else {
                    rectangle2 = new Rectangle();
                    rectangle2.x = (int) (((((float) escherChildAnchorRecord.getDx1()) * f) * 96.0f) / 914400.0f);
                    rectangle2.y = (int) (((((float) escherChildAnchorRecord.getDy1()) * f2) * 96.0f) / 914400.0f);
                    rectangle2.width = (int) (((((float) (escherChildAnchorRecord.getDx2() - escherChildAnchorRecord.getDx1())) * f) * 96.0f) / 914400.0f);
                    rectangle2.height = (int) (((((float) (escherChildAnchorRecord.getDy2() - escherChildAnchorRecord.getDy1())) * f2) * 96.0f) / 914400.0f);
                }
            } else {
                EscherClientAnchorRecord escherClientAnchorRecord2 = (EscherClientAnchorRecord) ShapeKit.getEscherChild(this.escherContainer, -4080);
                if (escherClientAnchorRecord2 != null) {
                    rectangle2 = new Rectangle();
                    rectangle2.x = (int) (((((float) escherClientAnchorRecord2.getCol1()) * f) * 96.0f) / 914400.0f);
                    rectangle2.y = (int) (((((float) escherClientAnchorRecord2.getFlag()) * f2) * 96.0f) / 914400.0f);
                    rectangle2.width = (int) (((((float) (escherClientAnchorRecord2.getDx1() - escherClientAnchorRecord2.getCol1())) * f) * 96.0f) / 914400.0f);
                    rectangle2.height = (int) (((((float) (escherClientAnchorRecord2.getRow1() - escherClientAnchorRecord2.getFlag())) * f2) * 96.0f) / 914400.0f);
                }
            }
        }
        if (rectangle != null) {
            rectangle2.x -= rectangle.x;
            rectangle2.y -= rectangle.y;
        }
        return rectangle2;
    }

    public boolean isHidden() {
        return ShapeKit.isHidden(getSpContainer());
    }

    public boolean hasLine() {
        return ShapeKit.hasLine(getSpContainer());
    }

    public double getLineWidth() {
        return (double) ShapeKit.getLineWidth(getSpContainer());
    }

    public Color getLineColor() {
        return ShapeKit.getLineColor(getSpContainer(), (Object) null, 0);
    }

    public int getLineDashing() {
        return ShapeKit.getLineDashing(getSpContainer());
    }

    public int getFillType() {
        return ShapeKit.getFillType(getSpContainer());
    }

    public Color getForegroundColor() {
        return ShapeKit.getForegroundColor(getSpContainer(), (Object) null, 0);
    }

    public Color getFillbackColor() {
        return ShapeKit.getFillbackColor(getSpContainer(), (Object) null, 0);
    }

    public int getBackgroundPictureIdx() {
        EscherSimpleProperty escherSimpleProperty;
        EscherOptRecord escherOptRecord = (EscherOptRecord) ShapeKit.getEscherChild(getSpContainer(), -4085);
        int fillType = ShapeKit.getFillType(this.escherContainer);
        if ((fillType == 3 || fillType == 2 || fillType == 1) && (escherSimpleProperty = (EscherSimpleProperty) ShapeKit.getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 390)) != null) {
            return escherSimpleProperty.getPropertyValue();
        }
        return -1;
    }

    public int getRotation() {
        return ShapeKit.getRotation(getSpContainer());
    }

    public boolean getFlipHorizontal() {
        return ShapeKit.getFlipHorizontal(getSpContainer());
    }

    public boolean getFlipVertical() {
        return ShapeKit.getFlipVertical(getSpContainer());
    }

    public Float[] getAdjustmentValue() {
        return ShapeKit.getAdjustmentValue(getSpContainer());
    }

    public int getStartArrowType() {
        return ShapeKit.getStartArrowType(getSpContainer());
    }

    public int getStartArrowWidth() {
        return ShapeKit.getStartArrowWidth(getSpContainer());
    }

    public int getStartArrowLength() {
        return ShapeKit.getStartArrowLength(getSpContainer());
    }

    public int getEndArrowType() {
        return ShapeKit.getEndArrowType(getSpContainer());
    }

    public int getEndArrowWidth() {
        return ShapeKit.getEndArrowWidth(getSpContainer());
    }

    public int getEndArrowLength() {
        return ShapeKit.getEndArrowLength(getSpContainer());
    }

    public Path[] getFreeformPath(Rectangle rectangle, PointF pointF, byte b, PointF pointF2, byte b2) {
        return ShapeKit.getFreeformPath(getSpContainer(), rectangle, pointF, b, pointF2, b2);
    }

    public ArrowPathAndTail getStartArrowPath(Rectangle rectangle) {
        return ShapeKit.getStartArrowPathAndTail(getSpContainer(), rectangle);
    }

    public ArrowPathAndTail getEndArrowPath(Rectangle rectangle) {
        return ShapeKit.getEndArrowPathAndTail(getSpContainer(), rectangle);
    }

    public int getFillAngle() {
        return ShapeKit.getFillAngle(getSpContainer());
    }

    public int getFillFocus() {
        return ShapeKit.getFillFocus(getSpContainer());
    }

    public boolean isShaderPreset() {
        return ShapeKit.isShaderPreset(getSpContainer());
    }

    public int[] getShaderColors() {
        return ShapeKit.getShaderColors(getSpContainer());
    }

    public float[] getShaderPositions() {
        return ShapeKit.getShaderPositions(getSpContainer());
    }

    public int getRadialGradientPositionType() {
        return ShapeKit.getRadialGradientPositionType(getSpContainer());
    }

    public Line getLine(boolean z) {
        if (!z && !hasLine()) {
            return null;
        }
        int round = (int) Math.round(getLineWidth() * 1.3333333730697632d);
        boolean z2 = getLineDashing() > 0;
        Color lineColor = getLineColor();
        if (lineColor == null) {
            return null;
        }
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setForegroundColor(lineColor.getRGB());
        Line line = new Line();
        line.setBackgroundAndFill(backgroundAndFill);
        line.setLineWidth(round);
        line.setDash(z2);
        return line;
    }

    public int getPosition_H() {
        return ShapeKit.getPosition_H(getSpContainer());
    }

    public int getPositionRelTo_H() {
        return ShapeKit.getPositionRelTo_H(getSpContainer());
    }

    public int getPosition_V() {
        return ShapeKit.getPosition_V(getSpContainer());
    }

    public int getPositionRelTo_V() {
        return ShapeKit.getPositionRelTo_V(getSpContainer());
    }

    public void dispose() {
        this.parent = null;
        EscherContainerRecord escherContainerRecord = this.escherContainer;
        if (escherContainerRecord != null) {
            escherContainerRecord.dispose();
            this.escherContainer = null;
        }
    }
}
