package com.app.office.fc.hssf.record;

import androidx.core.view.MotionEventCompat;
import com.app.office.common.shape.ShapeTypes;
import com.app.office.fc.hslf.record.RecordTypes;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.LittleEndianOutput;

public final class ExtendedFormatRecord extends StandardRecord {
    public static final short ALT_BARS = 3;
    public static final short BIG_SPOTS = 9;
    public static final short BRICKS = 10;
    public static final short CENTER = 2;
    public static final short CENTER_SELECTION = 6;
    public static final short DASHED = 3;
    public static final short DASH_DOT = 9;
    public static final short DASH_DOT_DOT = 11;
    public static final short DIAMONDS = 16;
    public static final short DOTTED = 4;
    public static final short DOUBLE = 6;
    public static final short FILL = 4;
    public static final short FINE_DOTS = 2;
    public static final short GENERAL = 0;
    public static final short HAIR = 7;
    public static final short JUSTIFY = 5;
    public static final short LEFT = 1;
    public static final short MEDIUM = 2;
    public static final short MEDIUM_DASHED = 8;
    public static final short MEDIUM_DASH_DOT = 10;
    public static final short MEDIUM_DASH_DOT_DOT = 12;
    public static final short NONE = 0;
    public static final short NO_FILL = 0;
    public static final short NULL = -16;
    public static final short RIGHT = 3;
    public static final short SLANTED_DASH_DOT = 13;
    public static final short SOLID_FILL = 1;
    public static final short SPARSE_DOTS = 4;
    public static final short SQUARES = 15;
    public static final short THICK = 5;
    public static final short THICK_BACKWARD_DIAG = 7;
    public static final short THICK_FORWARD_DIAG = 8;
    public static final short THICK_HORZ_BANDS = 5;
    public static final short THICK_VERT_BANDS = 6;
    public static final short THIN = 1;
    public static final short THIN_BACKWARD_DIAG = 13;
    public static final short THIN_FORWARD_DIAG = 14;
    public static final short THIN_HORZ_BANDS = 11;
    public static final short THIN_VERT_BANDS = 12;
    public static final short VERTICAL_BOTTOM = 2;
    public static final short VERTICAL_CENTER = 1;
    public static final short VERTICAL_JUSTIFY = 3;
    public static final short VERTICAL_TOP = 0;
    public static final short XF_CELL = 0;
    public static final short XF_STYLE = 1;
    private static final BitField _123_prefix = BitFieldFactory.getInstance(8);
    private static final BitField _adtl_diag = BitFieldFactory.getInstance(2080768);
    private static final BitField _adtl_diag_line_style = BitFieldFactory.getInstance(31457280);
    private static final BitField _adtl_fill_pattern = BitFieldFactory.getInstance(-67108864);
    private static final BitField _alignment = BitFieldFactory.getInstance(7);
    private static final BitField _border_bottom = BitFieldFactory.getInstance(RecordTypes.EscherDggContainer);
    private static final BitField _border_left = BitFieldFactory.getInstance(15);
    private static final BitField _border_right = BitFieldFactory.getInstance(ShapeTypes.Funnel);
    private static final BitField _border_top = BitFieldFactory.getInstance(3840);
    private static final BitField _bottom_border_palette_idx = BitFieldFactory.getInstance(16256);
    private static final BitField _diag = BitFieldFactory.getInstance(49152);
    private static final BitField _fill_background = BitFieldFactory.getInstance(16256);
    private static final BitField _fill_foreground = BitFieldFactory.getInstance(127);
    private static final BitField _hidden = BitFieldFactory.getInstance(2);
    private static final BitField _indent = BitFieldFactory.getInstance(15);
    private static final BitField _indent_not_parent_alignment = BitFieldFactory.getInstance(4096);
    private static final BitField _indent_not_parent_border = BitFieldFactory.getInstance(8192);
    private static final BitField _indent_not_parent_cell_options = BitFieldFactory.getInstance(32768);
    private static final BitField _indent_not_parent_font = BitFieldFactory.getInstance(2048);
    private static final BitField _indent_not_parent_format = BitFieldFactory.getInstance(1024);
    private static final BitField _indent_not_parent_pattern = BitFieldFactory.getInstance(16384);
    private static final BitField _justify_last = BitFieldFactory.getInstance(128);
    private static final BitField _left_border_palette_idx = BitFieldFactory.getInstance(127);
    private static final BitField _locked = BitFieldFactory.getInstance(1);
    private static final BitField _merge_cells = BitFieldFactory.getInstance(32);
    private static final BitField _parent_index = BitFieldFactory.getInstance(65520);
    private static final BitField _reading_order = BitFieldFactory.getInstance(ShapeTypes.ActionButtonInformation);
    private static final BitField _right_border_palette_idx = BitFieldFactory.getInstance(16256);
    private static final BitField _rotation = BitFieldFactory.getInstance(MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    private static final BitField _shrink_to_fit = BitFieldFactory.getInstance(16);
    private static final BitField _top_border_palette_idx = BitFieldFactory.getInstance(127);
    private static final BitField _vertical_alignment = BitFieldFactory.getInstance(112);
    private static final BitField _wrap_text = BitFieldFactory.getInstance(8);
    private static final BitField _xf_type = BitFieldFactory.getInstance(4);
    public static final short sid = 224;
    private short field_1_font_index;
    private short field_2_format_index;
    private short field_3_cell_options;
    private short field_4_alignment_options;
    private short field_5_indention_options;
    private short field_6_border_options;
    private short field_7_palette_options;
    private int field_8_adtl_palette_options;
    private short field_9_fill_palette_options;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 20;
    }

    public short getSid() {
        return sid;
    }

    public ExtendedFormatRecord() {
    }

    public ExtendedFormatRecord(RecordInputStream recordInputStream) {
        this.field_1_font_index = recordInputStream.readShort();
        this.field_2_format_index = recordInputStream.readShort();
        this.field_3_cell_options = recordInputStream.readShort();
        this.field_4_alignment_options = recordInputStream.readShort();
        this.field_5_indention_options = recordInputStream.readShort();
        this.field_6_border_options = recordInputStream.readShort();
        this.field_7_palette_options = recordInputStream.readShort();
        this.field_8_adtl_palette_options = recordInputStream.readInt();
        this.field_9_fill_palette_options = recordInputStream.readShort();
    }

    public void setFontIndex(short s) {
        this.field_1_font_index = s;
    }

    public void setFormatIndex(short s) {
        this.field_2_format_index = s;
    }

    public void setCellOptions(short s) {
        this.field_3_cell_options = s;
    }

    public void setLocked(boolean z) {
        this.field_3_cell_options = _locked.setShortBoolean(this.field_3_cell_options, z);
    }

    public void setHidden(boolean z) {
        this.field_3_cell_options = _hidden.setShortBoolean(this.field_3_cell_options, z);
    }

    public void setXFType(short s) {
        this.field_3_cell_options = _xf_type.setShortValue(this.field_3_cell_options, s);
    }

    public void set123Prefix(boolean z) {
        this.field_3_cell_options = _123_prefix.setShortBoolean(this.field_3_cell_options, z);
    }

    public void setParentIndex(short s) {
        this.field_3_cell_options = _parent_index.setShortValue(this.field_3_cell_options, s);
    }

    public void setAlignmentOptions(short s) {
        this.field_4_alignment_options = s;
    }

    public void setAlignment(short s) {
        this.field_4_alignment_options = _alignment.setShortValue(this.field_4_alignment_options, s);
    }

    public void setWrapText(boolean z) {
        this.field_4_alignment_options = _wrap_text.setShortBoolean(this.field_4_alignment_options, z);
    }

    public void setVerticalAlignment(short s) {
        this.field_4_alignment_options = _vertical_alignment.setShortValue(this.field_4_alignment_options, s);
    }

    public void setJustifyLast(short s) {
        this.field_4_alignment_options = _justify_last.setShortValue(this.field_4_alignment_options, s);
    }

    public void setRotation(short s) {
        this.field_4_alignment_options = _rotation.setShortValue(this.field_4_alignment_options, s);
    }

    public void setIndentionOptions(short s) {
        this.field_5_indention_options = s;
    }

    public void setIndent(short s) {
        this.field_5_indention_options = _indent.setShortValue(this.field_5_indention_options, s);
    }

    public void setShrinkToFit(boolean z) {
        this.field_5_indention_options = _shrink_to_fit.setShortBoolean(this.field_5_indention_options, z);
    }

    public void setMergeCells(boolean z) {
        this.field_5_indention_options = _merge_cells.setShortBoolean(this.field_5_indention_options, z);
    }

    public void setReadingOrder(short s) {
        this.field_5_indention_options = _reading_order.setShortValue(this.field_5_indention_options, s);
    }

    public void setIndentNotParentFormat(boolean z) {
        this.field_5_indention_options = _indent_not_parent_format.setShortBoolean(this.field_5_indention_options, z);
    }

    public void setIndentNotParentFont(boolean z) {
        this.field_5_indention_options = _indent_not_parent_font.setShortBoolean(this.field_5_indention_options, z);
    }

    public void setIndentNotParentAlignment(boolean z) {
        this.field_5_indention_options = _indent_not_parent_alignment.setShortBoolean(this.field_5_indention_options, z);
    }

    public void setIndentNotParentBorder(boolean z) {
        this.field_5_indention_options = _indent_not_parent_border.setShortBoolean(this.field_5_indention_options, z);
    }

    public void setIndentNotParentPattern(boolean z) {
        this.field_5_indention_options = _indent_not_parent_pattern.setShortBoolean(this.field_5_indention_options, z);
    }

    public void setIndentNotParentCellOptions(boolean z) {
        this.field_5_indention_options = _indent_not_parent_cell_options.setShortBoolean(this.field_5_indention_options, z);
    }

    public void setBorderOptions(short s) {
        this.field_6_border_options = s;
    }

    public void setBorderLeft(short s) {
        this.field_6_border_options = _border_left.setShortValue(this.field_6_border_options, s);
    }

    public void setBorderRight(short s) {
        this.field_6_border_options = _border_right.setShortValue(this.field_6_border_options, s);
    }

    public void setBorderTop(short s) {
        this.field_6_border_options = _border_top.setShortValue(this.field_6_border_options, s);
    }

    public void setBorderBottom(short s) {
        this.field_6_border_options = _border_bottom.setShortValue(this.field_6_border_options, s);
    }

    public void setPaletteOptions(short s) {
        this.field_7_palette_options = s;
    }

    public void setLeftBorderPaletteIdx(short s) {
        this.field_7_palette_options = _left_border_palette_idx.setShortValue(this.field_7_palette_options, s);
    }

    public void setRightBorderPaletteIdx(short s) {
        this.field_7_palette_options = _right_border_palette_idx.setShortValue(this.field_7_palette_options, s);
    }

    public void setDiag(short s) {
        this.field_7_palette_options = _diag.setShortValue(this.field_7_palette_options, s);
    }

    public void setAdtlPaletteOptions(short s) {
        this.field_8_adtl_palette_options = s;
    }

    public void setTopBorderPaletteIdx(short s) {
        this.field_8_adtl_palette_options = _top_border_palette_idx.setValue(this.field_8_adtl_palette_options, s);
    }

    public void setBottomBorderPaletteIdx(short s) {
        this.field_8_adtl_palette_options = _bottom_border_palette_idx.setValue(this.field_8_adtl_palette_options, s);
    }

    public void setAdtlDiag(short s) {
        this.field_8_adtl_palette_options = _adtl_diag.setValue(this.field_8_adtl_palette_options, s);
    }

    public void setAdtlDiagLineStyle(short s) {
        this.field_8_adtl_palette_options = _adtl_diag_line_style.setValue(this.field_8_adtl_palette_options, s);
    }

    public void setAdtlFillPattern(short s) {
        this.field_8_adtl_palette_options = _adtl_fill_pattern.setValue(this.field_8_adtl_palette_options, s);
    }

    public void setFillPaletteOptions(short s) {
        this.field_9_fill_palette_options = s;
    }

    public void setFillForeground(short s) {
        this.field_9_fill_palette_options = _fill_foreground.setShortValue(this.field_9_fill_palette_options, s);
    }

    public void setFillBackground(short s) {
        this.field_9_fill_palette_options = _fill_background.setShortValue(this.field_9_fill_palette_options, s);
    }

    public short getFontIndex() {
        return this.field_1_font_index;
    }

    public short getFormatIndex() {
        return this.field_2_format_index;
    }

    public short getCellOptions() {
        return this.field_3_cell_options;
    }

    public boolean isLocked() {
        return _locked.isSet(this.field_3_cell_options);
    }

    public boolean isHidden() {
        return _hidden.isSet(this.field_3_cell_options);
    }

    public short getXFType() {
        return _xf_type.getShortValue(this.field_3_cell_options);
    }

    public boolean get123Prefix() {
        return _123_prefix.isSet(this.field_3_cell_options);
    }

    public short getParentIndex() {
        return _parent_index.getShortValue(this.field_3_cell_options);
    }

    public short getAlignmentOptions() {
        return this.field_4_alignment_options;
    }

    public short getAlignment() {
        return _alignment.getShortValue(this.field_4_alignment_options);
    }

    public boolean getWrapText() {
        return _wrap_text.isSet(this.field_4_alignment_options);
    }

    public short getVerticalAlignment() {
        return _vertical_alignment.getShortValue(this.field_4_alignment_options);
    }

    public short getJustifyLast() {
        return _justify_last.getShortValue(this.field_4_alignment_options);
    }

    public short getRotation() {
        return _rotation.getShortValue(this.field_4_alignment_options);
    }

    public short getIndentionOptions() {
        return this.field_5_indention_options;
    }

    public short getIndent() {
        return _indent.getShortValue(this.field_5_indention_options);
    }

    public boolean getShrinkToFit() {
        return _shrink_to_fit.isSet(this.field_5_indention_options);
    }

    public boolean getMergeCells() {
        return _merge_cells.isSet(this.field_5_indention_options);
    }

    public short getReadingOrder() {
        return _reading_order.getShortValue(this.field_5_indention_options);
    }

    public boolean isIndentNotParentFormat() {
        return _indent_not_parent_format.isSet(this.field_5_indention_options);
    }

    public boolean isIndentNotParentFont() {
        return _indent_not_parent_font.isSet(this.field_5_indention_options);
    }

    public boolean isIndentNotParentAlignment() {
        return _indent_not_parent_alignment.isSet(this.field_5_indention_options);
    }

    public boolean isIndentNotParentBorder() {
        return _indent_not_parent_border.isSet(this.field_5_indention_options);
    }

    public boolean isIndentNotParentPattern() {
        return _indent_not_parent_pattern.isSet(this.field_5_indention_options);
    }

    public boolean isIndentNotParentCellOptions() {
        return _indent_not_parent_cell_options.isSet(this.field_5_indention_options);
    }

    public short getBorderOptions() {
        return this.field_6_border_options;
    }

    public short getBorderLeft() {
        return _border_left.getShortValue(this.field_6_border_options);
    }

    public short getBorderRight() {
        return _border_right.getShortValue(this.field_6_border_options);
    }

    public short getBorderTop() {
        return _border_top.getShortValue(this.field_6_border_options);
    }

    public short getBorderBottom() {
        return _border_bottom.getShortValue(this.field_6_border_options);
    }

    public short getPaletteOptions() {
        return this.field_7_palette_options;
    }

    public short getLeftBorderPaletteIdx() {
        return _left_border_palette_idx.getShortValue(this.field_7_palette_options);
    }

    public short getRightBorderPaletteIdx() {
        return _right_border_palette_idx.getShortValue(this.field_7_palette_options);
    }

    public short getDiag() {
        return _diag.getShortValue(this.field_7_palette_options);
    }

    public int getAdtlPaletteOptions() {
        return this.field_8_adtl_palette_options;
    }

    public short getTopBorderPaletteIdx() {
        return (short) _top_border_palette_idx.getValue(this.field_8_adtl_palette_options);
    }

    public short getBottomBorderPaletteIdx() {
        return (short) _bottom_border_palette_idx.getValue(this.field_8_adtl_palette_options);
    }

    public short getAdtlDiag() {
        return (short) _adtl_diag.getValue(this.field_8_adtl_palette_options);
    }

    public short getAdtlDiagLineStyle() {
        return (short) _adtl_diag_line_style.getValue(this.field_8_adtl_palette_options);
    }

    public short getAdtlFillPattern() {
        return (short) _adtl_fill_pattern.getValue(this.field_8_adtl_palette_options);
    }

    public short getFillPaletteOptions() {
        return this.field_9_fill_palette_options;
    }

    public short getFillForeground() {
        return _fill_foreground.getShortValue(this.field_9_fill_palette_options);
    }

    public short getFillBackground() {
        return _fill_background.getShortValue(this.field_9_fill_palette_options);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[EXTENDEDFORMAT]\n");
        if (getXFType() == 1) {
            stringBuffer.append(" STYLE_RECORD_TYPE\n");
        } else if (getXFType() == 0) {
            stringBuffer.append(" CELL_RECORD_TYPE\n");
        }
        stringBuffer.append("    .fontindex       = ");
        stringBuffer.append(Integer.toHexString(getFontIndex()));
        stringBuffer.append("\n");
        stringBuffer.append("    .formatindex     = ");
        stringBuffer.append(Integer.toHexString(getFormatIndex()));
        stringBuffer.append("\n");
        stringBuffer.append("    .celloptions     = ");
        stringBuffer.append(Integer.toHexString(getCellOptions()));
        stringBuffer.append("\n");
        stringBuffer.append("          .islocked  = ");
        stringBuffer.append(isLocked());
        stringBuffer.append("\n");
        stringBuffer.append("          .ishidden  = ");
        stringBuffer.append(isHidden());
        stringBuffer.append("\n");
        stringBuffer.append("          .recordtype= ");
        stringBuffer.append(Integer.toHexString(getXFType()));
        stringBuffer.append("\n");
        stringBuffer.append("          .parentidx = ");
        stringBuffer.append(Integer.toHexString(getParentIndex()));
        stringBuffer.append("\n");
        stringBuffer.append("    .alignmentoptions= ");
        stringBuffer.append(Integer.toHexString(getAlignmentOptions()));
        stringBuffer.append("\n");
        stringBuffer.append("          .alignment = ");
        stringBuffer.append(getAlignment());
        stringBuffer.append("\n");
        stringBuffer.append("          .wraptext  = ");
        stringBuffer.append(getWrapText());
        stringBuffer.append("\n");
        stringBuffer.append("          .valignment= ");
        stringBuffer.append(Integer.toHexString(getVerticalAlignment()));
        stringBuffer.append("\n");
        stringBuffer.append("          .justlast  = ");
        stringBuffer.append(Integer.toHexString(getJustifyLast()));
        stringBuffer.append("\n");
        stringBuffer.append("          .rotation  = ");
        stringBuffer.append(Integer.toHexString(getRotation()));
        stringBuffer.append("\n");
        stringBuffer.append("    .indentionoptions= ");
        stringBuffer.append(Integer.toHexString(getIndentionOptions()));
        stringBuffer.append("\n");
        stringBuffer.append("          .indent    = ");
        stringBuffer.append(Integer.toHexString(getIndent()));
        stringBuffer.append("\n");
        stringBuffer.append("          .shrinktoft= ");
        stringBuffer.append(getShrinkToFit());
        stringBuffer.append("\n");
        stringBuffer.append("          .mergecells= ");
        stringBuffer.append(getMergeCells());
        stringBuffer.append("\n");
        stringBuffer.append("          .readngordr= ");
        stringBuffer.append(Integer.toHexString(getReadingOrder()));
        stringBuffer.append("\n");
        stringBuffer.append("          .formatflag= ");
        stringBuffer.append(isIndentNotParentFormat());
        stringBuffer.append("\n");
        stringBuffer.append("          .fontflag  = ");
        stringBuffer.append(isIndentNotParentFont());
        stringBuffer.append("\n");
        stringBuffer.append("          .prntalgnmt= ");
        stringBuffer.append(isIndentNotParentAlignment());
        stringBuffer.append("\n");
        stringBuffer.append("          .borderflag= ");
        stringBuffer.append(isIndentNotParentBorder());
        stringBuffer.append("\n");
        stringBuffer.append("          .paternflag= ");
        stringBuffer.append(isIndentNotParentPattern());
        stringBuffer.append("\n");
        stringBuffer.append("          .celloption= ");
        stringBuffer.append(isIndentNotParentCellOptions());
        stringBuffer.append("\n");
        stringBuffer.append("    .borderoptns     = ");
        stringBuffer.append(Integer.toHexString(getBorderOptions()));
        stringBuffer.append("\n");
        stringBuffer.append("          .lftln     = ");
        stringBuffer.append(Integer.toHexString(getBorderLeft()));
        stringBuffer.append("\n");
        stringBuffer.append("          .rgtln     = ");
        stringBuffer.append(Integer.toHexString(getBorderRight()));
        stringBuffer.append("\n");
        stringBuffer.append("          .topln     = ");
        stringBuffer.append(Integer.toHexString(getBorderTop()));
        stringBuffer.append("\n");
        stringBuffer.append("          .btmln     = ");
        stringBuffer.append(Integer.toHexString(getBorderBottom()));
        stringBuffer.append("\n");
        stringBuffer.append("    .paleteoptns     = ");
        stringBuffer.append(Integer.toHexString(getPaletteOptions()));
        stringBuffer.append("\n");
        stringBuffer.append("          .leftborder= ");
        stringBuffer.append(Integer.toHexString(getLeftBorderPaletteIdx()));
        stringBuffer.append("\n");
        stringBuffer.append("          .rghtborder= ");
        stringBuffer.append(Integer.toHexString(getRightBorderPaletteIdx()));
        stringBuffer.append("\n");
        stringBuffer.append("          .diag      = ");
        stringBuffer.append(Integer.toHexString(getDiag()));
        stringBuffer.append("\n");
        stringBuffer.append("    .paleteoptn2     = ");
        stringBuffer.append(Integer.toHexString(getAdtlPaletteOptions()));
        stringBuffer.append("\n");
        stringBuffer.append("          .topborder = ");
        stringBuffer.append(Integer.toHexString(getTopBorderPaletteIdx()));
        stringBuffer.append("\n");
        stringBuffer.append("          .botmborder= ");
        stringBuffer.append(Integer.toHexString(getBottomBorderPaletteIdx()));
        stringBuffer.append("\n");
        stringBuffer.append("          .adtldiag  = ");
        stringBuffer.append(Integer.toHexString(getAdtlDiag()));
        stringBuffer.append("\n");
        stringBuffer.append("          .diaglnstyl= ");
        stringBuffer.append(Integer.toHexString(getAdtlDiagLineStyle()));
        stringBuffer.append("\n");
        stringBuffer.append("          .fillpattrn= ");
        stringBuffer.append(Integer.toHexString(getAdtlFillPattern()));
        stringBuffer.append("\n");
        stringBuffer.append("    .fillpaloptn     = ");
        stringBuffer.append(Integer.toHexString(getFillPaletteOptions()));
        stringBuffer.append("\n");
        stringBuffer.append("          .foreground= ");
        stringBuffer.append(Integer.toHexString(getFillForeground()));
        stringBuffer.append("\n");
        stringBuffer.append("          .background= ");
        stringBuffer.append(Integer.toHexString(getFillBackground()));
        stringBuffer.append("\n");
        stringBuffer.append("[/EXTENDEDFORMAT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getFontIndex());
        littleEndianOutput.writeShort(getFormatIndex());
        littleEndianOutput.writeShort(getCellOptions());
        littleEndianOutput.writeShort(getAlignmentOptions());
        littleEndianOutput.writeShort(getIndentionOptions());
        littleEndianOutput.writeShort(getBorderOptions());
        littleEndianOutput.writeShort(getPaletteOptions());
        littleEndianOutput.writeInt(getAdtlPaletteOptions());
        littleEndianOutput.writeShort(getFillPaletteOptions());
    }

    public void cloneStyleFrom(ExtendedFormatRecord extendedFormatRecord) {
        this.field_1_font_index = extendedFormatRecord.field_1_font_index;
        this.field_2_format_index = extendedFormatRecord.field_2_format_index;
        this.field_3_cell_options = extendedFormatRecord.field_3_cell_options;
        this.field_4_alignment_options = extendedFormatRecord.field_4_alignment_options;
        this.field_5_indention_options = extendedFormatRecord.field_5_indention_options;
        this.field_6_border_options = extendedFormatRecord.field_6_border_options;
        this.field_7_palette_options = extendedFormatRecord.field_7_palette_options;
        this.field_8_adtl_palette_options = extendedFormatRecord.field_8_adtl_palette_options;
        this.field_9_fill_palette_options = extendedFormatRecord.field_9_fill_palette_options;
    }

    public int hashCode() {
        return ((((((((((((((((this.field_1_font_index + 31) * 31) + this.field_2_format_index) * 31) + this.field_3_cell_options) * 31) + this.field_4_alignment_options) * 31) + this.field_5_indention_options) * 31) + this.field_6_border_options) * 31) + this.field_7_palette_options) * 31) + this.field_8_adtl_palette_options) * 31) + this.field_9_fill_palette_options;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ExtendedFormatRecord)) {
            return false;
        }
        ExtendedFormatRecord extendedFormatRecord = (ExtendedFormatRecord) obj;
        return this.field_1_font_index == extendedFormatRecord.field_1_font_index && this.field_2_format_index == extendedFormatRecord.field_2_format_index && this.field_3_cell_options == extendedFormatRecord.field_3_cell_options && this.field_4_alignment_options == extendedFormatRecord.field_4_alignment_options && this.field_5_indention_options == extendedFormatRecord.field_5_indention_options && this.field_6_border_options == extendedFormatRecord.field_6_border_options && this.field_7_palette_options == extendedFormatRecord.field_7_palette_options && this.field_8_adtl_palette_options == extendedFormatRecord.field_8_adtl_palette_options && this.field_9_fill_palette_options == extendedFormatRecord.field_9_fill_palette_options;
    }
}
