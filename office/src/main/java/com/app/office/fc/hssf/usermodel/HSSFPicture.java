package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.ddf.EscherBSERecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ss.usermodel.Picture;
import com.app.office.fc.ss.util.ImageUtils;
import com.app.office.java.awt.Dimension;
import com.app.office.ss.model.XLSModel.AWorkbook;
import com.app.office.ss.model.baseModel.Row;
import java.io.ByteArrayInputStream;

public final class HSSFPicture extends HSSFSimpleShape implements Picture {
    public static final int PICTURE_TYPE_DIB = 7;
    public static final int PICTURE_TYPE_EMF = 2;
    public static final int PICTURE_TYPE_JPEG = 5;
    public static final int PICTURE_TYPE_PICT = 4;
    public static final int PICTURE_TYPE_PNG = 6;
    public static final int PICTURE_TYPE_WMF = 3;
    private static final float PX_DEFAULT = 32.0f;
    private static final float PX_MODIFIED = 36.56f;
    private static final int PX_ROW = 15;
    private int _pictureIndex;
    private EscherOptRecord opt;

    private float getPixelWidth(int i) {
        return PX_DEFAULT;
    }

    public HSSFPicture(EscherContainerRecord escherContainerRecord, HSSFShape hSSFShape, HSSFAnchor hSSFAnchor) {
        super(escherContainerRecord, hSSFShape, hSSFAnchor);
        setShapeType(8);
    }

    public HSSFPicture(AWorkbook aWorkbook, EscherContainerRecord escherContainerRecord, HSSFShape hSSFShape, HSSFAnchor hSSFAnchor, EscherOptRecord escherOptRecord) {
        super(escherContainerRecord, hSSFShape, hSSFAnchor);
        setShapeType(8);
        this.opt = escherOptRecord;
        processLineWidth();
        processLine(escherContainerRecord, aWorkbook);
        processSimpleBackground(escherContainerRecord, aWorkbook);
        processRotationAndFlip(escherContainerRecord);
    }

    public int getPictureIndex() {
        return this._pictureIndex;
    }

    public void setPictureIndex(int i) {
        this._pictureIndex = i;
    }

    public EscherOptRecord getEscherOptRecord() {
        return this.opt;
    }

    public void resize(double d) {
        HSSFClientAnchor hSSFClientAnchor = (HSSFClientAnchor) getAnchor();
        hSSFClientAnchor.setAnchorType(2);
        HSSFClientAnchor preferredSize = getPreferredSize(d);
        int row1 = hSSFClientAnchor.getRow1() + (preferredSize.getRow2() - preferredSize.getRow1());
        hSSFClientAnchor.setCol2((short) (hSSFClientAnchor.getCol1() + (preferredSize.getCol2() - preferredSize.getCol1())));
        hSSFClientAnchor.setDx1(0);
        hSSFClientAnchor.setDx2(preferredSize.getDx2());
        hSSFClientAnchor.setRow2(row1);
        hSSFClientAnchor.setDy1(0);
        hSSFClientAnchor.setDy2(preferredSize.getDy2());
    }

    public void resize() {
        resize(1.0d);
    }

    public HSSFClientAnchor getPreferredSize() {
        return getPreferredSize(1.0d);
    }

    public HSSFClientAnchor getPreferredSize(double d) {
        double d2;
        int i;
        double d3;
        HSSFClientAnchor hSSFClientAnchor = (HSSFClientAnchor) getAnchor();
        Dimension imageDimension = getImageDimension();
        double width = imageDimension.getWidth() * d;
        double height = imageDimension.getHeight() * d;
        float columnWidthInPixels = (getColumnWidthInPixels(hSSFClientAnchor.col1) * (1.0f - (((float) hSSFClientAnchor.dx1) / 1024.0f))) + 0.0f;
        short s = (short) (hSSFClientAnchor.col1 + 1);
        while (true) {
            d2 = (double) columnWidthInPixels;
            if (d2 >= width) {
                break;
            }
            columnWidthInPixels += getColumnWidthInPixels(s);
            s = (short) (s + 1);
        }
        int i2 = 0;
        if (d2 > width) {
            s = (short) (s - 1);
            double columnWidthInPixels2 = (double) getColumnWidthInPixels(s);
            i = (int) (((columnWidthInPixels2 - (d2 - width)) / columnWidthInPixels2) * 1024.0d);
        } else {
            i = 0;
        }
        hSSFClientAnchor.col2 = s;
        hSSFClientAnchor.dx2 = i;
        float rowHeightInPixels = ((1.0f - (((float) hSSFClientAnchor.dy1) / 256.0f)) * getRowHeightInPixels(hSSFClientAnchor.row1)) + 0.0f;
        int i3 = hSSFClientAnchor.row1 + 1;
        while (true) {
            d3 = (double) rowHeightInPixels;
            if (d3 >= height) {
                break;
            }
            rowHeightInPixels += getRowHeightInPixels(i3);
            i3++;
        }
        if (d3 > height) {
            i3--;
            double rowHeightInPixels2 = (double) getRowHeightInPixels(i3);
            i2 = (int) (((rowHeightInPixels2 - (d3 - height)) / rowHeightInPixels2) * 256.0d);
        }
        hSSFClientAnchor.row2 = i3;
        hSSFClientAnchor.dy2 = i2;
        return hSSFClientAnchor;
    }

    private float getColumnWidthInPixels(int i) {
        if (checkPatriarch()) {
            return this._patriarch._sheet.getColumnPixelWidth(i);
        }
        return 0.0f;
    }

    private float getRowHeightInPixels(int i) {
        Row row;
        if (!checkPatriarch() || (row = this._patriarch._sheet.getRow(i)) == null) {
            return 18.0f;
        }
        return row.getRowPixelHeight();
    }

    public Dimension getImageDimension() {
        if (!checkPatriarch()) {
            return null;
        }
        EscherBSERecord bSERecord = this._patriarch._sheet.getAWorkbook().getInternalWorkbook().getBSERecord(this._pictureIndex);
        byte[] picturedata = bSERecord.getBlipRecord().getPicturedata();
        return ImageUtils.getImageDimension(new ByteArrayInputStream(picturedata), bSERecord.getBlipTypeWin32());
    }

    public HSSFPictureData getPictureData() {
        if (!checkPatriarch() || this._pictureIndex <= 0) {
            return null;
        }
        return new HSSFPictureData(this._patriarch._sheet.getAWorkbook().getInternalWorkbook().getBSERecord(this._pictureIndex).getBlipRecord());
    }
}
