package com.app.office.fc.hssf.usermodel;

import androidx.core.view.ViewCompat;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Line;
import com.app.office.common.shape.Arrow;
import com.app.office.constant.AutoShapeConstant;
import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.AbstractEscherOptRecord;
import com.app.office.fc.ddf.EscherBSERecord;
import com.app.office.fc.ddf.EscherBlipRecord;
import com.app.office.fc.ddf.EscherChildAnchorRecord;
import com.app.office.fc.ddf.EscherClientAnchorRecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.java.awt.Color;
import com.app.office.ss.model.XLSModel.AWorkbook;

public abstract class HSSFShape {
    private int _fillColor = 134217737;
    private int _fillType;
    private int _lineStyle = 0;
    private int _lineStyleColor = 134217792;
    private int _lineWidth = AutoShapeConstant.LINEWIDTH_DEFAULT;
    private boolean _noBorder = false;
    private boolean _noFill = false;
    HSSFPatriarch _patriarch;
    private HSSFAnchor anchor;
    private int angle;
    private byte[] bgPictureData;
    private Arrow endArrow;
    protected EscherContainerRecord escherContainer;
    private boolean flipH;
    private boolean flipV;
    final HSSFShape parent;
    private int shapeType = 0;
    private Arrow startArrow;

    public int countOfAllChildren() {
        return 1;
    }

    HSSFShape(EscherContainerRecord escherContainerRecord, HSSFShape hSSFShape, HSSFAnchor hSSFAnchor) {
        this.escherContainer = escherContainerRecord;
        this.parent = hSSFShape;
        this.anchor = hSSFAnchor;
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x0013 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0015 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean checkPatriarch() {
        /*
            r2 = this;
            com.app.office.fc.hssf.usermodel.HSSFShape r0 = r2.parent
        L_0x0002:
            com.app.office.fc.hssf.usermodel.HSSFPatriarch r1 = r2._patriarch
            if (r1 != 0) goto L_0x0011
            if (r0 == 0) goto L_0x0011
            com.app.office.fc.hssf.usermodel.HSSFPatriarch r1 = r0._patriarch
            r2._patriarch = r1
            com.app.office.fc.hssf.usermodel.HSSFShape r0 = r0.getParent()
            goto L_0x0002
        L_0x0011:
            if (r1 == 0) goto L_0x0015
            r0 = 1
            goto L_0x0016
        L_0x0015:
            r0 = 0
        L_0x0016:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.usermodel.HSSFShape.checkPatriarch():boolean");
    }

    public void processLineWidth() {
        this._lineWidth = ShapeKit.getLineWidth(this.escherContainer);
    }

    public HSSFShape getParent() {
        return this.parent;
    }

    public HSSFAnchor getAnchor() {
        return this.anchor;
    }

    public void setAnchor(HSSFAnchor hSSFAnchor) {
        if (this.parent == null) {
            if (hSSFAnchor instanceof HSSFChildAnchor) {
                throw new IllegalArgumentException("Must use client anchors for shapes directly attached to sheet.");
            }
        } else if (hSSFAnchor instanceof HSSFClientAnchor) {
            throw new IllegalArgumentException("Must use child anchors for shapes attached to groups.");
        }
        this.anchor = hSSFAnchor;
    }

    public boolean isNoBorder() {
        return this._noBorder;
    }

    public void setNoBorder(boolean z) {
        this._noBorder = z;
    }

    public int getLineStyleColor() {
        return this._lineStyleColor;
    }

    public void setLineStyleColor(int i) {
        this._lineStyleColor = i;
        this._lineStyleColor = (i & ViewCompat.MEASURED_SIZE_MASK) | -16777216;
    }

    public void setLineStyleColor(int i, int i2, int i3) {
        this._lineStyleColor = ((i & 255) << 16) | -16777216 | ((i2 & 255) << 8) | ((i3 & 255) << 0);
    }

    public int getFillColor() {
        return this._fillColor;
    }

    public void setFillColor(int i, int i2) {
        this._fillColor = i;
        this._fillColor = (i & ViewCompat.MEASURED_SIZE_MASK) | (i2 << 24);
    }

    public void setFillColor(int i, int i2, int i3, int i4) {
        this._fillColor = ((i & 255) << 16) | ((i4 & 255) << 24) | ((i2 & 255) << 8) | ((i3 & 255) << 0);
    }

    public int getLineWidth() {
        return this._lineWidth;
    }

    public void setLineWidth(int i) {
        this._lineWidth = i;
    }

    public int getLineStyle() {
        return this._lineStyle;
    }

    public void setLineStyle(int i) {
        this._lineStyle = i;
    }

    public boolean isNoFill() {
        return this._noFill;
    }

    public void setNoFill(boolean z) {
        this._noFill = z;
    }

    public int getFillType() {
        return this._fillType;
    }

    public void setFillType(int i) {
        this._fillType = i;
    }

    public byte[] getBGPictureData() {
        return this.bgPictureData;
    }

    public void setBGPictureData(byte[] bArr) {
        this.bgPictureData = bArr;
    }

    public int getShapeType() {
        return this.shapeType;
    }

    public void setShapeType(int i) {
        this.shapeType = i;
    }

    public int getRotation() {
        return this.angle;
    }

    public void setRotation(int i) {
        this.angle = i;
    }

    public boolean getFlipH() {
        return this.flipH;
    }

    public void setFilpH(boolean z) {
        this.flipH = z;
    }

    public boolean getFlipV() {
        return this.flipV;
    }

    public void setFlipV(boolean z) {
        this.flipV = z;
    }

    public void setStartArrow(byte b, int i, int i2) {
        this.startArrow = new Arrow(b, i, i2);
    }

    public void setEndArrow(byte b, int i, int i2) {
        this.endArrow = new Arrow(b, i, i2);
    }

    public int getStartArrowType() {
        return this.startArrow.getType();
    }

    public int getStartArrowWidth() {
        return this.startArrow.getWidth();
    }

    public int getStartArrowLength() {
        return this.startArrow.getLength();
    }

    public int getEndArrowType() {
        return this.endArrow.getType();
    }

    public int getEndArrowWidth() {
        return this.endArrow.getWidth();
    }

    public int getEndArrowLength() {
        return this.endArrow.getLength();
    }

    public static HSSFClientAnchor toClientAnchor(EscherClientAnchorRecord escherClientAnchorRecord) {
        HSSFClientAnchor hSSFClientAnchor = new HSSFClientAnchor();
        hSSFClientAnchor.setAnchorType(escherClientAnchorRecord.getFlag());
        hSSFClientAnchor.setCol1(escherClientAnchorRecord.getCol1());
        hSSFClientAnchor.setCol2(escherClientAnchorRecord.getCol2());
        hSSFClientAnchor.setDx1(escherClientAnchorRecord.getDx1());
        hSSFClientAnchor.setDx2(escherClientAnchorRecord.getDx2());
        hSSFClientAnchor.setDy1(escherClientAnchorRecord.getDy1());
        hSSFClientAnchor.setDy2(escherClientAnchorRecord.getDy2());
        hSSFClientAnchor.setRow1(escherClientAnchorRecord.getRow1());
        hSSFClientAnchor.setRow2(escherClientAnchorRecord.getRow2());
        return hSSFClientAnchor;
    }

    public static HSSFChildAnchor toChildAnchor(EscherChildAnchorRecord escherChildAnchorRecord) {
        return new HSSFChildAnchor(escherChildAnchorRecord.getDx1(), escherChildAnchorRecord.getDy1(), escherChildAnchorRecord.getDx2(), escherChildAnchorRecord.getDy2());
    }

    public void processSimpleBackground(EscherContainerRecord escherContainerRecord, AWorkbook aWorkbook) {
        EscherBSERecord bSERecord;
        EscherBlipRecord blipRecord;
        EscherOptRecord escherOptRecord = (EscherOptRecord) ShapeKit.getEscherChild(escherContainerRecord, -4085);
        int fillType = ShapeKit.getFillType(escherContainerRecord);
        if (fillType == 3) {
            EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) ShapeKit.getEscherProperty((AbstractEscherOptRecord) escherOptRecord, 390);
            if (!(escherSimpleProperty == null || (bSERecord = aWorkbook.getInternalWorkbook().getBSERecord(escherSimpleProperty.getPropertyValue())) == null || (blipRecord = bSERecord.getBlipRecord()) == null)) {
                setFillType(3);
                setBGPictureData(blipRecord.getPicturedata());
                return;
            }
        } else if (fillType == 1) {
            Color fillbackColor = ShapeKit.getFillbackColor(escherContainerRecord, aWorkbook, 1);
            if (fillbackColor != null) {
                setFillType(0);
                setFillColor(fillbackColor.getRGB(), 255);
                return;
            }
        } else if (isGradientTile()) {
            setFillType(fillType);
            return;
        } else {
            Color foregroundColor = ShapeKit.getForegroundColor(escherContainerRecord, aWorkbook, 1);
            if (foregroundColor != null) {
                setFillType(0);
                setFillColor(foregroundColor.getRGB(), 255);
                return;
            }
        }
        setNoFill(true);
    }

    public boolean isGradientTile() {
        int fillType = ShapeKit.getFillType(this.escherContainer);
        return fillType == 7 || fillType == 4 || fillType == 5 || fillType == 6 || fillType == 2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0086, code lost:
        if (r11 != 0) goto L_0x0091;
     */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00fc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.app.office.common.bg.BackgroundAndFill getGradientTileBackground(com.app.office.ss.model.XLSModel.AWorkbook r18, com.app.office.system.IControl r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            int r2 = r17.getFillType()
            r3 = 0
            r4 = 6
            r5 = 5
            r6 = 4
            r7 = 7
            r8 = 2
            if (r2 == r7) goto L_0x006f
            if (r2 == r6) goto L_0x006f
            if (r2 == r5) goto L_0x006f
            if (r2 != r4) goto L_0x0017
            goto L_0x006f
        L_0x0017:
            if (r2 != r8) goto L_0x006c
            com.app.office.common.bg.BackgroundAndFill r9 = new com.app.office.common.bg.BackgroundAndFill
            r9.<init>()
            com.app.office.fc.ddf.EscherContainerRecord r2 = r0.escherContainer
            r4 = -4085(0xfffffffffffff00b, float:NaN)
            com.app.office.fc.ddf.EscherRecord r2 = com.app.office.fc.ShapeKit.getEscherChild(r2, r4)
            com.app.office.fc.ddf.EscherOptRecord r2 = (com.app.office.fc.ddf.EscherOptRecord) r2
            r4 = 390(0x186, float:5.47E-43)
            com.app.office.fc.ddf.EscherProperty r2 = com.app.office.fc.ShapeKit.getEscherProperty((com.app.office.fc.ddf.AbstractEscherOptRecord) r2, (int) r4)
            com.app.office.fc.ddf.EscherSimpleProperty r2 = (com.app.office.fc.ddf.EscherSimpleProperty) r2
            if (r2 == 0) goto L_0x0107
            com.app.office.fc.hssf.model.InternalWorkbook r1 = r18.getInternalWorkbook()
            int r2 = r2.getPropertyValue()
            com.app.office.fc.ddf.EscherBSERecord r1 = r1.getBSERecord(r2)
            if (r1 == 0) goto L_0x0107
            com.app.office.fc.ddf.EscherBlipRecord r1 = r1.getBlipRecord()
            if (r1 == 0) goto L_0x0107
            r9.setFillType(r8)
            com.app.office.common.picture.Picture r2 = new com.app.office.common.picture.Picture
            r2.<init>()
            byte[] r1 = r1.getPicturedata()
            r2.setData(r1)
            com.app.office.system.SysKit r1 = r19.getSysKit()
            com.app.office.common.picture.PictureManage r1 = r1.getPictureManage()
            r1.addPicture((com.app.office.common.picture.Picture) r2)
            com.app.office.common.bg.TileShader r1 = new com.app.office.common.bg.TileShader
            r4 = 1065353216(0x3f800000, float:1.0)
            r1.<init>(r2, r3, r4, r4)
            r9.setShader(r1)
            goto L_0x0107
        L_0x006c:
            r9 = 0
            goto L_0x0107
        L_0x006f:
            com.app.office.common.bg.BackgroundAndFill r10 = new com.app.office.common.bg.BackgroundAndFill
            r10.<init>()
            com.app.office.fc.ddf.EscherContainerRecord r11 = r0.escherContainer
            int r11 = com.app.office.fc.ShapeKit.getFillAngle(r11)
            r12 = -135(0xffffffffffffff79, float:NaN)
            if (r11 == r12) goto L_0x008f
            r12 = -90
            if (r11 == r12) goto L_0x008c
            r12 = -45
            if (r11 == r12) goto L_0x0089
            if (r11 == 0) goto L_0x008c
            goto L_0x0091
        L_0x0089:
            r11 = 135(0x87, float:1.89E-43)
            goto L_0x0091
        L_0x008c:
            int r11 = r11 + 90
            goto L_0x0091
        L_0x008f:
            r11 = 45
        L_0x0091:
            com.app.office.fc.ddf.EscherContainerRecord r12 = r0.escherContainer
            int r12 = com.app.office.fc.ShapeKit.getFillFocus(r12)
            com.app.office.fc.ddf.EscherContainerRecord r13 = r0.escherContainer
            r14 = 1
            com.app.office.java.awt.Color r13 = com.app.office.fc.ShapeKit.getForegroundColor(r13, r1, r14)
            com.app.office.fc.ddf.EscherContainerRecord r15 = r0.escherContainer
            com.app.office.java.awt.Color r1 = com.app.office.fc.ShapeKit.getFillbackColor(r15, r1, r14)
            com.app.office.fc.ddf.EscherContainerRecord r15 = r0.escherContainer
            boolean r15 = com.app.office.fc.ShapeKit.isShaderPreset(r15)
            if (r15 == 0) goto L_0x00b9
            com.app.office.fc.ddf.EscherContainerRecord r15 = r0.escherContainer
            int[] r15 = com.app.office.fc.ShapeKit.getShaderColors(r15)
            com.app.office.fc.ddf.EscherContainerRecord r9 = r0.escherContainer
            float[] r9 = com.app.office.fc.ShapeKit.getShaderPositions(r9)
            goto L_0x00bb
        L_0x00b9:
            r9 = 0
            r15 = 0
        L_0x00bb:
            if (r15 != 0) goto L_0x00d4
            int[] r15 = new int[r8]
            r16 = -1
            if (r13 != 0) goto L_0x00c5
            r13 = -1
            goto L_0x00c9
        L_0x00c5:
            int r13 = r13.getRGB()
        L_0x00c9:
            r15[r3] = r13
            if (r1 != 0) goto L_0x00ce
            goto L_0x00d2
        L_0x00ce:
            int r16 = r1.getRGB()
        L_0x00d2:
            r15[r14] = r16
        L_0x00d4:
            if (r9 != 0) goto L_0x00db
            float[] r9 = new float[r8]
            r9 = {0, 1065353216} // fill-array
        L_0x00db:
            if (r2 != r7) goto L_0x00e5
            com.app.office.common.bg.LinearGradientShader r1 = new com.app.office.common.bg.LinearGradientShader
            float r3 = (float) r11
            r1.<init>(r3, r15, r9)
        L_0x00e3:
            r9 = r1
            goto L_0x00fa
        L_0x00e5:
            if (r2 == r6) goto L_0x00ee
            if (r2 == r5) goto L_0x00ee
            if (r2 != r4) goto L_0x00ec
            goto L_0x00ee
        L_0x00ec:
            r9 = 0
            goto L_0x00fa
        L_0x00ee:
            com.app.office.common.bg.RadialGradientShader r1 = new com.app.office.common.bg.RadialGradientShader
            com.app.office.fc.ddf.EscherContainerRecord r3 = r0.escherContainer
            int r3 = com.app.office.fc.ShapeKit.getRadialGradientPositionType(r3)
            r1.<init>(r3, r15, r9)
            goto L_0x00e3
        L_0x00fa:
            if (r9 == 0) goto L_0x00ff
            r9.setFocus(r12)
        L_0x00ff:
            byte r1 = (byte) r2
            r10.setFillType(r1)
            r10.setShader(r9)
            r9 = r10
        L_0x0107:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.usermodel.HSSFShape.getGradientTileBackground(com.app.office.ss.model.XLSModel.AWorkbook, com.app.office.system.IControl):com.app.office.common.bg.BackgroundAndFill");
    }

    public Line getLine() {
        BackgroundAndFill backgroundAndFill = new BackgroundAndFill();
        backgroundAndFill.setForegroundColor(this._lineStyleColor);
        Line line = new Line();
        line.setBackgroundAndFill(backgroundAndFill);
        line.setLineWidth(this._lineWidth);
        line.setDash(this._lineStyle > 0);
        return line;
    }
}
