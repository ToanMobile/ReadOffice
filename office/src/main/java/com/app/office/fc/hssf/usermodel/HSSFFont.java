package com.app.office.fc.hssf.usermodel;

import androidx.core.view.InputDeviceCompat;
import com.app.office.fc.hssf.record.FontRecord;
import com.app.office.fc.ss.usermodel.IFont;

public final class HSSFFont implements IFont {
    public static final String FONT_ARIAL = "Arial";
    private FontRecord font;
    private short index;

    protected HSSFFont(short s, FontRecord fontRecord) {
        this.font = fontRecord;
        this.index = s;
    }

    public void setFontName(String str) {
        this.font.setFontName(str);
    }

    public String getFontName() {
        return this.font.getFontName();
    }

    public short getIndex() {
        return this.index;
    }

    public void setFontHeight(short s) {
        this.font.setFontHeight(s);
    }

    public void setFontHeightInPoints(short s) {
        this.font.setFontHeight((short) (s * 20));
    }

    public short getFontHeight() {
        return this.font.getFontHeight();
    }

    public short getFontHeightInPoints() {
        return (short) (this.font.getFontHeight() / 20);
    }

    public void setItalic(boolean z) {
        this.font.setItalic(z);
    }

    public boolean getItalic() {
        return this.font.isItalic();
    }

    public void setStrikeout(boolean z) {
        this.font.setStrikeout(z);
    }

    public boolean getStrikeout() {
        return this.font.isStruckout();
    }

    public void setColor(short s) {
        this.font.setColorPaletteIndex(s);
    }

    public short getColor() {
        short colorPaletteIndex = this.font.getColorPaletteIndex();
        if (colorPaletteIndex == Short.MAX_VALUE) {
            return 8;
        }
        return colorPaletteIndex;
    }

    public void setBoldweight(short s) {
        this.font.setBoldWeight(s);
    }

    public short getBoldweight() {
        return this.font.getBoldWeight();
    }

    public void setTypeOffset(short s) {
        this.font.setSuperSubScript(s);
    }

    public short getTypeOffset() {
        return this.font.getSuperSubScript();
    }

    public void setUnderline(byte b) {
        this.font.setUnderline(b);
    }

    public byte getUnderline() {
        return this.font.getUnderline();
    }

    public int getCharSet() {
        byte charset = this.font.getCharset();
        return charset >= 0 ? charset : charset + 256;
    }

    public void setCharSet(int i) {
        byte b = (byte) i;
        if (i > 127) {
            b = (byte) (i + InputDeviceCompat.SOURCE_ANY);
        }
        setCharSet(b);
    }

    public void setCharSet(byte b) {
        this.font.setCharset(b);
    }

    public String toString() {
        return "org.apache.poi.hssf.usermodel.HSSFFont{" + this.font + "}";
    }

    public int hashCode() {
        FontRecord fontRecord = this.font;
        return (((fontRecord == null ? 0 : fontRecord.hashCode()) + 31) * 31) + this.index;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof HSSFFont)) {
            return false;
        }
        HSSFFont hSSFFont = (HSSFFont) obj;
        FontRecord fontRecord = this.font;
        if (fontRecord == null) {
            if (hSSFFont.font != null) {
                return false;
            }
        } else if (!fontRecord.equals(hSSFFont.font)) {
            return false;
        }
        return this.index == hSSFFont.index;
    }
}
