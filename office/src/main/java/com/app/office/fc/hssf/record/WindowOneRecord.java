package com.app.office.fc.hssf.record;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.LittleEndianOutput;

public final class WindowOneRecord extends StandardRecord {
    private static final BitField hidden = BitFieldFactory.getInstance(1);
    private static final BitField hscroll = BitFieldFactory.getInstance(8);
    private static final BitField iconic = BitFieldFactory.getInstance(2);
    private static final BitField reserved = BitFieldFactory.getInstance(4);
    public static final short sid = 61;
    private static final BitField tabs = BitFieldFactory.getInstance(32);
    private static final BitField vscroll = BitFieldFactory.getInstance(16);
    private short field_1_h_hold;
    private short field_2_v_hold;
    private short field_3_width;
    private short field_4_height;
    private short field_5_options;
    private int field_6_active_sheet;
    private int field_7_first_visible_tab;
    private short field_8_num_selected_tabs;
    private short field_9_tab_width_ratio;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 18;
    }

    public short getSid() {
        return 61;
    }

    public WindowOneRecord() {
    }

    public WindowOneRecord(RecordInputStream recordInputStream) {
        this.field_1_h_hold = recordInputStream.readShort();
        this.field_2_v_hold = recordInputStream.readShort();
        this.field_3_width = recordInputStream.readShort();
        this.field_4_height = recordInputStream.readShort();
        this.field_5_options = recordInputStream.readShort();
        this.field_6_active_sheet = recordInputStream.readShort();
        this.field_7_first_visible_tab = recordInputStream.readShort();
        this.field_8_num_selected_tabs = recordInputStream.readShort();
        this.field_9_tab_width_ratio = recordInputStream.readShort();
    }

    public void setHorizontalHold(short s) {
        this.field_1_h_hold = s;
    }

    public void setVerticalHold(short s) {
        this.field_2_v_hold = s;
    }

    public void setWidth(short s) {
        this.field_3_width = s;
    }

    public void setHeight(short s) {
        this.field_4_height = s;
    }

    public void setOptions(short s) {
        this.field_5_options = s;
    }

    public void setHidden(boolean z) {
        this.field_5_options = hidden.setShortBoolean(this.field_5_options, z);
    }

    public void setIconic(boolean z) {
        this.field_5_options = iconic.setShortBoolean(this.field_5_options, z);
    }

    public void setDisplayHorizonalScrollbar(boolean z) {
        this.field_5_options = hscroll.setShortBoolean(this.field_5_options, z);
    }

    public void setDisplayVerticalScrollbar(boolean z) {
        this.field_5_options = vscroll.setShortBoolean(this.field_5_options, z);
    }

    public void setDisplayTabs(boolean z) {
        this.field_5_options = tabs.setShortBoolean(this.field_5_options, z);
    }

    public void setActiveSheetIndex(int i) {
        this.field_6_active_sheet = i;
    }

    public void setSelectedTab(short s) {
        setActiveSheetIndex(s);
    }

    public void setFirstVisibleTab(int i) {
        this.field_7_first_visible_tab = i;
    }

    public void setDisplayedTab(short s) {
        setFirstVisibleTab(s);
    }

    public void setNumSelectedTabs(short s) {
        this.field_8_num_selected_tabs = s;
    }

    public void setTabWidthRatio(short s) {
        this.field_9_tab_width_ratio = s;
    }

    public short getHorizontalHold() {
        return this.field_1_h_hold;
    }

    public short getVerticalHold() {
        return this.field_2_v_hold;
    }

    public short getWidth() {
        return this.field_3_width;
    }

    public short getHeight() {
        return this.field_4_height;
    }

    public short getOptions() {
        return this.field_5_options;
    }

    public boolean getHidden() {
        return hidden.isSet(this.field_5_options);
    }

    public boolean getIconic() {
        return iconic.isSet(this.field_5_options);
    }

    public boolean getDisplayHorizontalScrollbar() {
        return hscroll.isSet(this.field_5_options);
    }

    public boolean getDisplayVerticalScrollbar() {
        return vscroll.isSet(this.field_5_options);
    }

    public boolean getDisplayTabs() {
        return tabs.isSet(this.field_5_options);
    }

    public int getActiveSheetIndex() {
        return this.field_6_active_sheet;
    }

    public short getSelectedTab() {
        return (short) getActiveSheetIndex();
    }

    public int getFirstVisibleTab() {
        return this.field_7_first_visible_tab;
    }

    public short getDisplayedTab() {
        return (short) getFirstVisibleTab();
    }

    public short getNumSelectedTabs() {
        return this.field_8_num_selected_tabs;
    }

    public short getTabWidthRatio() {
        return this.field_9_tab_width_ratio;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[WINDOW1]\n");
        stringBuffer.append("    .h_hold          = ");
        stringBuffer.append(Integer.toHexString(getHorizontalHold()));
        stringBuffer.append("\n");
        stringBuffer.append("    .v_hold          = ");
        stringBuffer.append(Integer.toHexString(getVerticalHold()));
        stringBuffer.append("\n");
        stringBuffer.append("    .width           = ");
        stringBuffer.append(Integer.toHexString(getWidth()));
        stringBuffer.append("\n");
        stringBuffer.append("    .height          = ");
        stringBuffer.append(Integer.toHexString(getHeight()));
        stringBuffer.append("\n");
        stringBuffer.append("    .options         = ");
        stringBuffer.append(Integer.toHexString(getOptions()));
        stringBuffer.append("\n");
        stringBuffer.append("        .hidden      = ");
        stringBuffer.append(getHidden());
        stringBuffer.append("\n");
        stringBuffer.append("        .iconic      = ");
        stringBuffer.append(getIconic());
        stringBuffer.append("\n");
        stringBuffer.append("        .hscroll     = ");
        stringBuffer.append(getDisplayHorizontalScrollbar());
        stringBuffer.append("\n");
        stringBuffer.append("        .vscroll     = ");
        stringBuffer.append(getDisplayVerticalScrollbar());
        stringBuffer.append("\n");
        stringBuffer.append("        .tabs        = ");
        stringBuffer.append(getDisplayTabs());
        stringBuffer.append("\n");
        stringBuffer.append("    .activeSheet     = ");
        stringBuffer.append(Integer.toHexString(getActiveSheetIndex()));
        stringBuffer.append("\n");
        stringBuffer.append("    .firstVisibleTab    = ");
        stringBuffer.append(Integer.toHexString(getFirstVisibleTab()));
        stringBuffer.append("\n");
        stringBuffer.append("    .numselectedtabs = ");
        stringBuffer.append(Integer.toHexString(getNumSelectedTabs()));
        stringBuffer.append("\n");
        stringBuffer.append("    .tabwidthratio   = ");
        stringBuffer.append(Integer.toHexString(getTabWidthRatio()));
        stringBuffer.append("\n");
        stringBuffer.append("[/WINDOW1]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getHorizontalHold());
        littleEndianOutput.writeShort(getVerticalHold());
        littleEndianOutput.writeShort(getWidth());
        littleEndianOutput.writeShort(getHeight());
        littleEndianOutput.writeShort(getOptions());
        littleEndianOutput.writeShort(getActiveSheetIndex());
        littleEndianOutput.writeShort(getFirstVisibleTab());
        littleEndianOutput.writeShort(getNumSelectedTabs());
        littleEndianOutput.writeShort(getTabWidthRatio());
    }
}
