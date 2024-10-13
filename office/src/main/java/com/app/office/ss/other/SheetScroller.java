package com.app.office.ss.other;

import com.app.office.ss.model.baseModel.Row;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.sheetProperty.PaneInformation;

public class SheetScroller {
    private float columnWidth;
    private boolean isColumnAllVisible = true;
    private boolean isRowAllVisible = true;
    private int minColumnIndex;
    private int minRowIndex;
    private float rowHeight;
    private double visibleColumnWidth;
    private double visibleRowHeight;

    public void dispose() {
    }

    public void reset() {
        setMinRowIndex(0);
        setMinColumnIndex(0);
        setRowHeight(0.0f);
        setColumnWidth(0.0f);
        setVisibleRowHeight(0.0d);
        setVisibleColumnWidth(0.0d);
        setRowAllVisible(true);
        setColumnAllVisible(true);
    }

    public void update(Sheet sheet, int i, int i2) {
        int i3;
        int i4;
        float f;
        Sheet sheet2 = sheet;
        int i5 = i;
        int i6 = i2;
        reset();
        setVisibleRowHeight((double) i6);
        setVisibleColumnWidth((double) i5);
        PaneInformation paneInformation = sheet.getPaneInformation();
        if (paneInformation != null) {
            setMinRowIndex(paneInformation.getHorizontalSplitTopRow());
            setMinColumnIndex(paneInformation.getVerticalSplitLeftColumn());
        }
        int i7 = sheet.getWorkbook().isBefore07Version() ? 65536 : 1048576;
        int i8 = sheet.getWorkbook().isBefore07Version() ? 256 : 16384;
        if (i6 > 0) {
            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            int defaultRowHeight = sheet.getDefaultRowHeight();
            while (this.visibleRowHeight >= 1.0d && (i4 = this.minRowIndex) <= i7) {
                Row row = (i4 < firstRowNum || i4 > lastRowNum) ? null : sheet2.getRow(i4);
                if (row == null || (row != null && !row.isZeroHeight())) {
                    if (row == null) {
                        f = (float) defaultRowHeight;
                    } else {
                        f = row.getRowPixelHeight();
                    }
                    this.rowHeight = f;
                    this.visibleRowHeight -= (double) f;
                }
                this.minRowIndex++;
            }
            int i9 = this.minRowIndex;
            if (i9 != i7) {
                this.minRowIndex = i9 - 1;
                setVisibleRowHeight(Math.abs(getVisibleRowHeight()));
                if (getVisibleRowHeight() < 1.0d) {
                    this.minRowIndex++;
                    setVisibleRowHeight(0.0d);
                } else {
                    setRowAllVisible(false);
                }
            } else {
                int i10 = i9 - 1;
                this.minRowIndex = i10;
                Row row2 = sheet2.getRow(i10);
                while (row2 != null && row2.isZeroHeight()) {
                    this.minRowIndex--;
                    row2 = sheet2.getRow(getMinRowIndex());
                }
                setVisibleRowHeight(0.0d);
            }
        }
        if (i5 > 0) {
            while (this.visibleColumnWidth >= 1.0d && (i3 = this.minColumnIndex) <= i8) {
                if (!sheet2.isColumnHidden(i3)) {
                    float columnPixelWidth = sheet2.getColumnPixelWidth(this.minColumnIndex);
                    this.columnWidth = columnPixelWidth;
                    this.visibleColumnWidth -= (double) columnPixelWidth;
                }
                this.minColumnIndex++;
            }
            int i11 = this.minColumnIndex;
            if (i11 != i8) {
                this.minColumnIndex = i11 - 1;
                setVisibleColumnWidth(Math.abs(getVisibleColumnWidth()));
                if (getVisibleColumnWidth() < 1.0d) {
                    this.minColumnIndex++;
                    setVisibleColumnWidth(0.0d);
                    return;
                }
                setColumnAllVisible(false);
                return;
            }
            this.minColumnIndex = i11 - 1;
            while (sheet2.isColumnHidden(this.minColumnIndex)) {
                this.minColumnIndex--;
            }
            setVisibleColumnWidth(0.0d);
        }
    }

    public int getMinRowIndex() {
        return this.minRowIndex;
    }

    public void setMinRowIndex(int i) {
        this.minRowIndex = i;
    }

    public int getMinColumnIndex() {
        return this.minColumnIndex;
    }

    public void setMinColumnIndex(int i) {
        this.minColumnIndex = i;
    }

    public float getColumnWidth() {
        return this.columnWidth;
    }

    public void setColumnWidth(float f) {
        this.columnWidth = f;
    }

    public float getRowHeight() {
        return this.rowHeight;
    }

    public void setRowHeight(float f) {
        this.rowHeight = f;
    }

    public boolean isRowAllVisible() {
        return this.isRowAllVisible;
    }

    public void setRowAllVisible(boolean z) {
        this.isRowAllVisible = z;
    }

    public boolean isColumnAllVisible() {
        return this.isColumnAllVisible;
    }

    public void setColumnAllVisible(boolean z) {
        this.isColumnAllVisible = z;
    }

    public double getVisibleRowHeight() {
        return this.visibleRowHeight;
    }

    public void setVisibleRowHeight(double d) {
        this.visibleRowHeight = d;
    }

    public double getVisibleColumnWidth() {
        return this.visibleColumnWidth;
    }

    public void setVisibleColumnWidth(double d) {
        this.visibleColumnWidth = d;
    }
}
