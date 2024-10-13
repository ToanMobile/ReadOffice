package com.app.office.ss.model.sheetProperty;

public class PaneInformation {
    public static final byte PANE_LOWER_LEFT = 2;
    public static final byte PANE_LOWER_RIGHT = 0;
    public static final byte PANE_UPPER_LEFT = 3;
    public static final byte PANE_UPPER_RIGHT = 1;
    private boolean frozen = true;
    private short leftColumn;
    private short topRow;

    public PaneInformation() {
    }

    public PaneInformation(short s, short s2, boolean z) {
        this.topRow = s;
        this.leftColumn = s2;
        this.frozen = z;
    }

    public void setHorizontalSplitTopRow(short s) {
        this.topRow = s;
    }

    public short getHorizontalSplitTopRow() {
        return this.topRow;
    }

    public void setVerticalSplitLeftColumn(short s) {
        this.leftColumn = s;
    }

    public short getVerticalSplitLeftColumn() {
        return this.leftColumn;
    }

    public void setFreePane(boolean z) {
        this.frozen = z;
    }

    public boolean isFreezePane() {
        return this.frozen;
    }
}
