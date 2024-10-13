package com.app.office.ss.other;

import com.app.office.ss.model.CellRangeAddress;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.sheetProperty.PaneInformation;
import com.app.office.ss.view.SheetView;

public class MergeCellMgr {
    private CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 0);
    private MergeCell mergedCell = new MergeCell();

    public CellRangeAddress getVisibleCellRangeAddress(Sheet sheet, CellRangeAddress cellRangeAddress2) {
        int firstColumn = cellRangeAddress2.getFirstColumn();
        int lastColumn = cellRangeAddress2.getLastColumn();
        int firstRow = cellRangeAddress2.getFirstRow();
        int lastRow = cellRangeAddress2.getLastRow();
        while (firstColumn <= lastColumn && sheet.isColumnHidden(firstColumn)) {
            firstColumn++;
        }
        while (lastColumn >= firstColumn && sheet.isColumnHidden(lastColumn)) {
            lastColumn--;
        }
        while (firstRow <= lastRow && sheet.getRow(firstRow).isZeroHeight()) {
            firstRow++;
        }
        while (lastRow >= firstRow && sheet.getRow(lastRow).isZeroHeight()) {
            lastRow--;
        }
        this.cellRangeAddress.setFirstColumn(firstColumn);
        this.cellRangeAddress.setFirstRow(firstRow);
        this.cellRangeAddress.setLastColumn(lastColumn);
        this.cellRangeAddress.setLastRow(lastRow);
        return this.cellRangeAddress;
    }

    public boolean isDrawMergeCell(SheetView sheetView, CellRangeAddress cellRangeAddress2, int i, int i2) {
        int minColumnIndex = sheetView.getMinRowAndColumnInformation().getMinColumnIndex();
        int minRowIndex = sheetView.getMinRowAndColumnInformation().getMinRowIndex();
        PaneInformation paneInformation = sheetView.getCurrentSheet().getPaneInformation();
        getVisibleCellRangeAddress(sheetView.getCurrentSheet(), cellRangeAddress2);
        if (paneInformation != null) {
            if (i < paneInformation.getHorizontalSplitTopRow() && cellRangeAddress2.getLastRow() >= paneInformation.getHorizontalSplitTopRow()) {
                this.cellRangeAddress.setLastRow(paneInformation.getHorizontalSplitTopRow() - 1);
                minRowIndex = 0;
            } else if (i >= paneInformation.getHorizontalSplitTopRow() && cellRangeAddress2.getFirstRow() < paneInformation.getHorizontalSplitTopRow()) {
                this.cellRangeAddress.setFirstRow(paneInformation.getHorizontalSplitTopRow());
            }
            if (i2 < paneInformation.getVerticalSplitLeftColumn() && cellRangeAddress2.getLastColumn() >= paneInformation.getVerticalSplitLeftColumn()) {
                this.cellRangeAddress.setLastColumn(paneInformation.getVerticalSplitLeftColumn() - 1);
                minColumnIndex = 0;
            } else if (i2 >= paneInformation.getVerticalSplitLeftColumn() && cellRangeAddress2.getFirstColumn() < paneInformation.getVerticalSplitLeftColumn()) {
                this.cellRangeAddress.setFirstColumn(paneInformation.getVerticalSplitLeftColumn());
            }
        }
        if (this.cellRangeAddress.getFirstColumn() == i2 && this.cellRangeAddress.getFirstRow() == i) {
            return true;
        }
        if (i != this.cellRangeAddress.getFirstRow() || i2 <= this.cellRangeAddress.getFirstColumn()) {
            if (i2 != this.cellRangeAddress.getFirstColumn() || i <= this.cellRangeAddress.getFirstRow()) {
                if (i <= this.cellRangeAddress.getFirstRow() || i2 <= this.cellRangeAddress.getFirstColumn() || i2 != minColumnIndex || i != minRowIndex) {
                    return false;
                }
                return true;
            } else if (i == minRowIndex) {
                return true;
            }
        } else if (i2 == minColumnIndex) {
            return true;
        }
        return false;
    }

    public MergeCell getMergedCellSize(SheetView sheetView, CellRangeAddress cellRangeAddress2, int i, int i2) {
        this.mergedCell.reset();
        int minColumnIndex = sheetView.getMinRowAndColumnInformation().getMinColumnIndex();
        int minRowIndex = sheetView.getMinRowAndColumnInformation().getMinRowIndex();
        PaneInformation paneInformation = sheetView.getCurrentSheet().getPaneInformation();
        if (paneInformation == null) {
            for (int firstColumn = cellRangeAddress2.getFirstColumn(); firstColumn <= cellRangeAddress2.getLastColumn(); firstColumn++) {
                if (!sheetView.getCurrentSheet().isColumnHidden(firstColumn)) {
                    float columnPixelWidth = sheetView.getCurrentSheet().getColumnPixelWidth(firstColumn) * sheetView.getZoom();
                    MergeCell mergeCell = this.mergedCell;
                    mergeCell.setWidth(mergeCell.getWidth() + columnPixelWidth);
                    if (firstColumn < minColumnIndex) {
                        MergeCell mergeCell2 = this.mergedCell;
                        mergeCell2.setNovisibleWidth(mergeCell2.getNovisibleWidth() + columnPixelWidth);
                    }
                }
            }
            for (int firstRow = cellRangeAddress2.getFirstRow(); firstRow <= cellRangeAddress2.getLastRow(); firstRow++) {
                if (!sheetView.getCurrentSheet().getRow(firstRow).isZeroHeight()) {
                    float rowPixelHeight = sheetView.getCurrentSheet().getRow(firstRow).getRowPixelHeight() * sheetView.getZoom();
                    MergeCell mergeCell3 = this.mergedCell;
                    mergeCell3.setHeight(mergeCell3.getHeight() + rowPixelHeight);
                    if (firstRow < minRowIndex) {
                        MergeCell mergeCell4 = this.mergedCell;
                        mergeCell4.setNoVisibleHeight(mergeCell4.getNoVisibleHeight() + rowPixelHeight);
                    }
                }
            }
        } else {
            if (i2 >= paneInformation.getVerticalSplitLeftColumn()) {
                for (int firstColumn2 = cellRangeAddress2.getFirstColumn(); firstColumn2 <= cellRangeAddress2.getLastColumn(); firstColumn2++) {
                    if (!sheetView.getCurrentSheet().isColumnHidden(firstColumn2)) {
                        float columnPixelWidth2 = sheetView.getCurrentSheet().getColumnPixelWidth(firstColumn2) * sheetView.getZoom();
                        MergeCell mergeCell5 = this.mergedCell;
                        mergeCell5.setWidth(mergeCell5.getWidth() + columnPixelWidth2);
                        if (firstColumn2 < minColumnIndex) {
                            MergeCell mergeCell6 = this.mergedCell;
                            mergeCell6.setNovisibleWidth(mergeCell6.getNovisibleWidth() + columnPixelWidth2);
                        }
                    }
                }
            } else {
                this.mergedCell.setFrozenColumn(true);
                for (int firstColumn3 = cellRangeAddress2.getFirstColumn(); firstColumn3 <= cellRangeAddress2.getLastColumn(); firstColumn3++) {
                    if (!sheetView.getCurrentSheet().isColumnHidden(firstColumn3)) {
                        float columnPixelWidth3 = sheetView.getCurrentSheet().getColumnPixelWidth(firstColumn3) * sheetView.getZoom();
                        MergeCell mergeCell7 = this.mergedCell;
                        mergeCell7.setWidth(mergeCell7.getWidth() + columnPixelWidth3);
                        if (firstColumn3 >= paneInformation.getVerticalSplitLeftColumn()) {
                            MergeCell mergeCell8 = this.mergedCell;
                            mergeCell8.setNovisibleWidth(mergeCell8.getNovisibleWidth() + columnPixelWidth3);
                        }
                    }
                }
            }
            if (i >= paneInformation.getHorizontalSplitTopRow()) {
                for (int firstRow2 = cellRangeAddress2.getFirstRow(); firstRow2 <= cellRangeAddress2.getLastRow(); firstRow2++) {
                    if (!sheetView.getCurrentSheet().getRow(firstRow2).isZeroHeight()) {
                        float rowPixelHeight2 = sheetView.getCurrentSheet().getRow(firstRow2).getRowPixelHeight() * sheetView.getZoom();
                        MergeCell mergeCell9 = this.mergedCell;
                        mergeCell9.setHeight(mergeCell9.getHeight() + rowPixelHeight2);
                        if (firstRow2 < minRowIndex) {
                            MergeCell mergeCell10 = this.mergedCell;
                            mergeCell10.setNoVisibleHeight(mergeCell10.getNoVisibleHeight() + rowPixelHeight2);
                        }
                    }
                }
            } else {
                this.mergedCell.setFrozenRow(true);
                for (int firstRow3 = cellRangeAddress2.getFirstRow(); firstRow3 <= cellRangeAddress2.getLastRow(); firstRow3++) {
                    if (!sheetView.getCurrentSheet().getRow(firstRow3).isZeroHeight()) {
                        float rowPixelHeight3 = sheetView.getCurrentSheet().getRow(firstRow3).getRowPixelHeight() * sheetView.getZoom();
                        MergeCell mergeCell11 = this.mergedCell;
                        mergeCell11.setHeight(mergeCell11.getHeight() + rowPixelHeight3);
                        if (firstRow3 >= paneInformation.getHorizontalSplitTopRow()) {
                            MergeCell mergeCell12 = this.mergedCell;
                            mergeCell12.setNoVisibleHeight(mergeCell12.getNoVisibleHeight() + rowPixelHeight3);
                        }
                    }
                }
            }
        }
        return this.mergedCell;
    }

    public void dispose() {
        CellRangeAddress cellRangeAddress2 = this.cellRangeAddress;
        if (cellRangeAddress2 != null) {
            cellRangeAddress2.dispose();
            this.cellRangeAddress = null;
        }
        MergeCell mergeCell = this.mergedCell;
        if (mergeCell != null) {
            mergeCell.dispose();
            this.mergedCell = null;
        }
    }
}
