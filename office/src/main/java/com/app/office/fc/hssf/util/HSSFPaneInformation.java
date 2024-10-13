package com.app.office.fc.hssf.util;

public class HSSFPaneInformation {
    public static final byte PANE_LOWER_LEFT = 2;
    public static final byte PANE_LOWER_RIGHT = 0;
    public static final byte PANE_UPPER_LEFT = 3;
    public static final byte PANE_UPPER_RIGHT = 1;
    private byte activePane;
    private boolean frozen = false;
    private short leftColumn;
    private short topRow;
    private short x;
    private short y;

    public HSSFPaneInformation(short s, short s2, short s3, short s4, byte b, boolean z) {
        this.x = s;
        this.y = s2;
        this.topRow = s3;
        this.leftColumn = s4;
        this.activePane = b;
        this.frozen = z;
    }

    public short getVerticalSplitPosition() {
        return this.x;
    }

    public short getHorizontalSplitPosition() {
        return this.y;
    }

    public short getHorizontalSplitTopRow() {
        return this.topRow;
    }

    public short getVerticalSplitLeftColumn() {
        return this.leftColumn;
    }

    public byte getActivePane() {
        return this.activePane;
    }

    public boolean isFreezePane() {
        return this.frozen;
    }
}
