package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.model.InternalWorkbook;
import com.app.office.fc.hssf.record.ExtendedFormatRecord;
import com.app.office.fc.hssf.record.FontRecord;
import com.app.office.fc.hssf.record.StyleRecord;
import com.app.office.fc.hssf.util.HSSFColor;
import com.app.office.fc.ss.usermodel.ICellStyle;
import com.app.office.fc.ss.usermodel.IFont;
import com.app.office.fc.ss.usermodel.Workbook;

public final class HSSFCellStyle implements ICellStyle {
    private ExtendedFormatRecord _format;
    private short _index;
    private InternalWorkbook _workbook;

    protected HSSFCellStyle(short s, ExtendedFormatRecord extendedFormatRecord, HSSFWorkbook hSSFWorkbook) {
        this(s, extendedFormatRecord, hSSFWorkbook.getWorkbook());
    }

    protected HSSFCellStyle(short s, ExtendedFormatRecord extendedFormatRecord, InternalWorkbook internalWorkbook) {
        this._format = null;
        this._index = 0;
        this._workbook = null;
        this._workbook = internalWorkbook;
        this._index = s;
        this._format = extendedFormatRecord;
    }

    public short getIndex() {
        return this._index;
    }

    public HSSFCellStyle getParentStyle() {
        short parentIndex = this._format.getParentIndex();
        if (parentIndex == 0 || parentIndex == 4095) {
            return null;
        }
        return new HSSFCellStyle(parentIndex, this._workbook.getExFormatAt(parentIndex), this._workbook);
    }

    public void setDataFormat(short s) {
        this._format.setFormatIndex(s);
    }

    public short getDataFormat() {
        return this._format.getFormatIndex();
    }

    public String getDataFormatString() {
        return getDataFormatString(this._workbook);
    }

    public String getDataFormatString(Workbook workbook) {
        HSSFDataFormat hSSFDataFormat = new HSSFDataFormat(((HSSFWorkbook) workbook).getWorkbook());
        if (getDataFormat() == -1) {
            return "General";
        }
        return hSSFDataFormat.getFormat(getDataFormat());
    }

    public String getDataFormatString(InternalWorkbook internalWorkbook) {
        return HSSFDataFormat.getFormatCode(internalWorkbook, this._format.getFormatIndex());
    }

    public void setFont(IFont iFont) {
        setFont((HSSFFont) iFont);
    }

    public void setFont(HSSFFont hSSFFont) {
        this._format.setIndentNotParentFont(true);
        this._format.setFontIndex(hSSFFont.getIndex());
    }

    public short getFontIndex() {
        return this._format.getFontIndex();
    }

    public HSSFFont getFont(Workbook workbook) {
        return ((HSSFWorkbook) workbook).getFontAt(getFontIndex());
    }

    public void setHidden(boolean z) {
        this._format.setIndentNotParentCellOptions(true);
        this._format.setHidden(z);
    }

    public boolean getHidden() {
        return this._format.isHidden();
    }

    public void setLocked(boolean z) {
        this._format.setIndentNotParentCellOptions(true);
        this._format.setLocked(z);
    }

    public boolean getLocked() {
        return this._format.isLocked();
    }

    public void setAlignment(short s) {
        this._format.setIndentNotParentAlignment(true);
        this._format.setAlignment(s);
    }

    public short getAlignment() {
        return this._format.getAlignment();
    }

    public void setWrapText(boolean z) {
        this._format.setIndentNotParentAlignment(true);
        this._format.setWrapText(z);
    }

    public boolean getWrapText() {
        return this._format.getWrapText();
    }

    public void setVerticalAlignment(short s) {
        this._format.setVerticalAlignment(s);
    }

    public short getVerticalAlignment() {
        return this._format.getVerticalAlignment();
    }

    public void setRotation(short s) {
        if (s != 255) {
            if (s < 0 && s >= -90) {
                s = (short) (90 - s);
            } else if (s < -90 || s > 90) {
                throw new IllegalArgumentException("The rotation must be between -90 and 90 degrees, or 0xff");
            }
        }
        this._format.setRotation(s);
    }

    public short getRotation() {
        short rotation = this._format.getRotation();
        return (rotation != 255 && rotation > 90) ? (short) (90 - rotation) : rotation;
    }

    public void setIndention(short s) {
        this._format.setIndent(s);
    }

    public short getIndention() {
        return this._format.getIndent();
    }

    public void setBorderLeft(short s) {
        this._format.setIndentNotParentBorder(true);
        this._format.setBorderLeft(s);
    }

    public short getBorderLeft() {
        return this._format.getBorderLeft();
    }

    public void setBorderRight(short s) {
        this._format.setIndentNotParentBorder(true);
        this._format.setBorderRight(s);
    }

    public short getBorderRight() {
        return this._format.getBorderRight();
    }

    public void setBorderTop(short s) {
        this._format.setIndentNotParentBorder(true);
        this._format.setBorderTop(s);
    }

    public short getBorderTop() {
        return this._format.getBorderTop();
    }

    public void setBorderBottom(short s) {
        this._format.setIndentNotParentBorder(true);
        this._format.setBorderBottom(s);
    }

    public short getBorderBottom() {
        return this._format.getBorderBottom();
    }

    public void setLeftBorderColor(short s) {
        this._format.setLeftBorderPaletteIdx(s);
    }

    public short getLeftBorderColor() {
        return this._format.getLeftBorderPaletteIdx();
    }

    public void setRightBorderColor(short s) {
        this._format.setRightBorderPaletteIdx(s);
    }

    public short getRightBorderColor() {
        return this._format.getRightBorderPaletteIdx();
    }

    public void setTopBorderColor(short s) {
        this._format.setTopBorderPaletteIdx(s);
    }

    public short getTopBorderColor() {
        return this._format.getTopBorderPaletteIdx();
    }

    public void setBottomBorderColor(short s) {
        this._format.setBottomBorderPaletteIdx(s);
    }

    public short getBottomBorderColor() {
        return this._format.getBottomBorderPaletteIdx();
    }

    public void setFillPattern(short s) {
        this._format.setAdtlFillPattern(s);
    }

    public short getFillPattern() {
        return this._format.getAdtlFillPattern();
    }

    private void checkDefaultBackgroundFills() {
        if (this._format.getFillForeground() == 64) {
            if (this._format.getFillBackground() != 65) {
                setFillBackgroundColor(65);
            }
        } else if (this._format.getFillBackground() == 65 && this._format.getFillForeground() != 64) {
            setFillBackgroundColor(64);
        }
    }

    public void setFillBackgroundColor(short s) {
        this._format.setFillBackground(s);
        checkDefaultBackgroundFills();
    }

    public short getFillBackgroundColor() {
        short fillBackground = this._format.getFillBackground();
        if (fillBackground == 65) {
            return 64;
        }
        return fillBackground;
    }

    public HSSFColor getFillBackgroundColorColor() {
        return new HSSFPalette(this._workbook.getCustomPalette()).getColor(getFillBackgroundColor());
    }

    public void setFillForegroundColor(short s) {
        this._format.setFillForeground(s);
        checkDefaultBackgroundFills();
    }

    public short getFillForegroundColor() {
        return this._format.getFillForeground();
    }

    public HSSFColor getFillForegroundColorColor() {
        return new HSSFPalette(this._workbook.getCustomPalette()).getColor(getFillForegroundColor());
    }

    public String getUserStyleName() {
        StyleRecord styleRecord = this._workbook.getStyleRecord(this._index);
        if (styleRecord != null && !styleRecord.isBuiltin()) {
            return styleRecord.getName();
        }
        return null;
    }

    public void setUserStyleName(String str) {
        StyleRecord styleRecord = this._workbook.getStyleRecord(this._index);
        if (styleRecord == null) {
            styleRecord = this._workbook.createStyleRecord(this._index);
        }
        if (!styleRecord.isBuiltin() || this._index > 20) {
            styleRecord.setName(str);
            return;
        }
        throw new IllegalArgumentException("Unable to set user specified style names for built in styles!");
    }

    public void verifyBelongsToWorkbook(HSSFWorkbook hSSFWorkbook) {
        if (hSSFWorkbook.getWorkbook() != this._workbook) {
            throw new IllegalArgumentException("This Style does not belong to the supplied Workbook. Are you trying to assign a style from one workbook to the cell of a differnt workbook?");
        }
    }

    public void cloneStyleFrom(ICellStyle iCellStyle) {
        if (iCellStyle instanceof HSSFCellStyle) {
            cloneStyleFrom((HSSFCellStyle) iCellStyle);
            return;
        }
        throw new IllegalArgumentException("Can only clone from one HSSFCellStyle to another, not between HSSFCellStyle and XSSFCellStyle");
    }

    public void cloneStyleFrom(HSSFCellStyle hSSFCellStyle) {
        this._format.cloneStyleFrom(hSSFCellStyle._format);
        InternalWorkbook internalWorkbook = this._workbook;
        if (internalWorkbook != hSSFCellStyle._workbook) {
            setDataFormat((short) internalWorkbook.createFormat(hSSFCellStyle.getDataFormatString()));
            FontRecord createNewFont = this._workbook.createNewFont();
            createNewFont.cloneStyleFrom(hSSFCellStyle._workbook.getFontRecordAt(hSSFCellStyle.getFontIndex()));
            setFont(new HSSFFont((short) this._workbook.getFontIndex(createNewFont), createNewFont));
        }
    }

    public int hashCode() {
        ExtendedFormatRecord extendedFormatRecord = this._format;
        return (((extendedFormatRecord == null ? 0 : extendedFormatRecord.hashCode()) + 31) * 31) + this._index;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof HSSFCellStyle)) {
            return false;
        }
        HSSFCellStyle hSSFCellStyle = (HSSFCellStyle) obj;
        ExtendedFormatRecord extendedFormatRecord = this._format;
        if (extendedFormatRecord == null) {
            if (hSSFCellStyle._format != null) {
                return false;
            }
        } else if (!extendedFormatRecord.equals(hSSFCellStyle._format)) {
            return false;
        }
        return this._index == hSSFCellStyle._index;
    }
}
