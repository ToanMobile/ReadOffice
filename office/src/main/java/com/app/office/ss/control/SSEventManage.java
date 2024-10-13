package com.app.office.ss.control;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import com.app.office.constant.EventConstant;
import com.app.office.ss.model.CellRangeAddress;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Row;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.other.DrawingCell;
import com.app.office.ss.other.FocusCell;
import com.app.office.ss.view.SheetView;
import com.app.office.system.IControl;
import com.app.office.system.ITimerListener;
import com.app.office.system.beans.AEventManage;
import com.app.office.system.beans.ATimer;

public class SSEventManage extends AEventManage implements ITimerListener {
    private static final int MINDISTANCE = 10;
    private boolean actionDown;
    private boolean longPress;
    private FocusCell newHeaderArea;
    private FocusCell oldHeaderArea;
    private int oldX;
    private int oldY;
    private boolean scrolling;
    private Spreadsheet spreadsheet;
    private ATimer timer = new ATimer(1000, this);

    public SSEventManage(Spreadsheet spreadsheet2, IControl iControl) {
        super(spreadsheet2.getContext(), iControl);
        this.spreadsheet = spreadsheet2;
    }

    public void actionPerformed() {
        this.timer.stop();
        this.control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
    }

    private void changingHeader(MotionEvent motionEvent) {
        if (this.newHeaderArea != null) {
            this.scrolling = true;
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int type = this.newHeaderArea.getType();
            if (type == 1) {
                Rect rect = this.newHeaderArea.getRect();
                rect.bottom = Math.round(((float) this.oldHeaderArea.getRect().bottom) + (y - ((float) this.oldY)));
                if (rect.bottom <= rect.top + 10) {
                    rect.bottom = rect.top + 10;
                }
            } else if (type == 2) {
                Rect rect2 = this.newHeaderArea.getRect();
                rect2.right = Math.round(((float) this.oldHeaderArea.getRect().right) + (x - ((float) this.oldX)));
                if (rect2.right <= rect2.left + 10) {
                    rect2.right = rect2.left + 10;
                }
            }
            this.spreadsheet.getSheetView().changeHeaderArea(this.newHeaderArea);
        }
    }

    private boolean checkClickedCell(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (((float) this.spreadsheet.getSheetView().getColumnHeaderHeight()) > y || ((float) this.spreadsheet.getSheetView().getRowHeaderWidth()) > x) {
            return false;
        }
        SheetView sheetView = this.spreadsheet.getSheetView();
        DrawingCell drawingCell = new DrawingCell();
        drawingCell.setLeft((float) sheetView.getRowHeaderWidth());
        drawingCell.setTop((float) sheetView.getColumnHeaderHeight());
        drawingCell.setRowIndex(sheetView.getMinRowAndColumnInformation().getMinRowIndex());
        drawingCell.setColumnIndex(sheetView.getMinRowAndColumnInformation().getMinColumnIndex());
        int i = sheetView.getCurrentSheet().getWorkbook().isBefore07Version() ? 65536 : 1048576;
        while (drawingCell.getTop() <= y && drawingCell.getRowIndex() <= i) {
            Row row = sheetView.getCurrentSheet().getRow(drawingCell.getRowIndex());
            if (row == null || !row.isZeroHeight()) {
                drawingCell.setHeight(row == null ? (float) sheetView.getCurrentSheet().getDefaultRowHeight() : row.getRowPixelHeight());
                drawingCell.setHeight((float) Math.round(drawingCell.getHeight() * sheetView.getZoom()));
                if (drawingCell.getRowIndex() != sheetView.getMinRowAndColumnInformation().getMinRowIndex() || sheetView.getMinRowAndColumnInformation().isRowAllVisible()) {
                    drawingCell.setVisibleHeight(drawingCell.getHeight());
                } else {
                    drawingCell.setVisibleHeight((float) Math.round(sheetView.getMinRowAndColumnInformation().getVisibleRowHeight() * ((double) sheetView.getZoom())));
                }
                drawingCell.setTop(drawingCell.getTop() + drawingCell.getVisibleHeight());
                drawingCell.setRowIndex(drawingCell.getRowIndex() + 1);
            } else {
                drawingCell.setRowIndex(drawingCell.getRowIndex() + 1);
            }
        }
        int i2 = sheetView.getCurrentSheet().getWorkbook().isBefore07Version() ? 256 : 16384;
        while (drawingCell.getLeft() <= x && drawingCell.getColumnIndex() <= i2) {
            if (sheetView.getCurrentSheet().isColumnHidden(drawingCell.getColumnIndex())) {
                drawingCell.setColumnIndex(drawingCell.getColumnIndex() + 1);
            } else {
                drawingCell.setWidth((float) Math.round(sheetView.getCurrentSheet().getColumnPixelWidth(drawingCell.getColumnIndex()) * sheetView.getZoom()));
                if (drawingCell.getColumnIndex() != sheetView.getMinRowAndColumnInformation().getMinColumnIndex() || sheetView.getMinRowAndColumnInformation().isColumnAllVisible()) {
                    drawingCell.setVisibleWidth(drawingCell.getWidth());
                } else {
                    drawingCell.setVisibleWidth((float) Math.round(sheetView.getMinRowAndColumnInformation().getVisibleColumnWidth() * ((double) sheetView.getZoom())));
                }
                drawingCell.setLeft(drawingCell.getLeft() + drawingCell.getVisibleWidth());
                drawingCell.setColumnIndex(drawingCell.getColumnIndex() + 1);
            }
        }
        this.spreadsheet.getSheetView().getCurrentSheet().setActiveCellType(0);
        this.spreadsheet.getSheetView().selectedCell(drawingCell.getRowIndex() - 1, drawingCell.getColumnIndex() - 1);
        this.spreadsheet.getControl().actionEvent(EventConstant.APP_CONTENT_SELECTED, (Object) null);
        this.spreadsheet.abortDrawing();
        this.spreadsheet.postInvalidate();
        return true;
    }

    private boolean changeHeaderEnd() {
        Row row;
        this.scrolling = false;
        if (this.oldHeaderArea == null) {
            return false;
        }
        Sheet currentSheet = this.spreadsheet.getSheetView().getCurrentSheet();
        int type = this.oldHeaderArea.getType();
        boolean z = true;
        if (type == 1) {
            int row2 = this.newHeaderArea.getRow();
            if (currentSheet.getRow(row2) == null) {
                row = new Row(0);
                row.setRowNumber(row2);
                row.setSheet(currentSheet);
                currentSheet.addRow(row);
            } else {
                while (currentSheet.getRow(row2) != null && currentSheet.getRow(row2).isZeroHeight()) {
                    row2--;
                }
                row = currentSheet.getRow(row2);
                if (row == null) {
                    row = new Row(0);
                    row.setRowNumber(row2);
                    row.setSheet(currentSheet);
                    currentSheet.addRow(row);
                }
            }
            row.setRowPixelHeight((float) Math.round(row.getRowPixelHeight() + ((((float) (this.newHeaderArea.getRect().bottom - this.newHeaderArea.getRect().top)) - ((float) (this.oldHeaderArea.getRect().bottom - this.oldHeaderArea.getRect().top))) / this.spreadsheet.getSheetView().getZoom())));
            int rowNumber = row.getRowNumber();
            while (rowNumber <= currentSheet.getLastRowNum()) {
                int i = rowNumber + 1;
                Row row3 = currentSheet.getRow(rowNumber);
                if (row3 != null) {
                    for (int firstCol = row3.getFirstCol(); firstCol <= row3.getLastCol(); firstCol++) {
                        Cell cell = row3.getCell(firstCol);
                        if (cell != null) {
                            if (cell.getRangeAddressIndex() >= 0) {
                                CellRangeAddress mergeRange = currentSheet.getMergeRange(cell.getRangeAddressIndex());
                                cell = currentSheet.getRow(mergeRange.getFirstRow()).getCell(mergeRange.getFirstColumn());
                            }
                            cell.removeSTRoot();
                        }
                    }
                    row3.setInitExpandedRangeAddress(false);
                }
                rowNumber = i;
            }
        } else if (type != 2) {
            z = false;
        } else {
            float f = (float) ((this.newHeaderArea.getRect().right - this.newHeaderArea.getRect().left) - (this.oldHeaderArea.getRect().right - this.oldHeaderArea.getRect().left));
            int column = this.newHeaderArea.getColumn();
            while (currentSheet.isColumnHidden(column)) {
                column--;
            }
            currentSheet.setColumnPixelWidth(column, Math.round(currentSheet.getColumnPixelWidth(column) + (f / this.spreadsheet.getSheetView().getZoom())));
            int firstRowNum = currentSheet.getFirstRowNum();
            while (firstRowNum <= currentSheet.getLastRowNum()) {
                int i2 = firstRowNum + 1;
                Row row4 = currentSheet.getRow(firstRowNum);
                if (row4 != null) {
                    for (int max = Math.max(row4.getFirstCol(), this.oldHeaderArea.getColumn()); max <= row4.getLastCol(); max++) {
                        Cell cell2 = row4.getCell(max);
                        if (cell2 != null) {
                            if (cell2.getRangeAddressIndex() >= 0) {
                                CellRangeAddress mergeRange2 = currentSheet.getMergeRange(cell2.getRangeAddressIndex());
                                cell2 = currentSheet.getRow(mergeRange2.getFirstRow()).getCell(mergeRange2.getFirstColumn());
                            }
                            cell2.removeSTRoot();
                        }
                    }
                    row4.setInitExpandedRangeAddress(false);
                }
                firstRowNum = i2;
            }
        }
        this.spreadsheet.getSheetView().updateMinRowAndColumnInfo();
        this.spreadsheet.getSheetView().setDrawMovingHeaderLine(false);
        this.oldHeaderArea = null;
        this.newHeaderArea = null;
        return z;
    }

    private int findClickedRowHeader(MotionEvent motionEvent) {
        float y = motionEvent.getY();
        SheetView sheetView = this.spreadsheet.getSheetView();
        DrawingCell drawingCell = new DrawingCell();
        drawingCell.setTop((float) sheetView.getColumnHeaderHeight());
        drawingCell.setRowIndex(sheetView.getMinRowAndColumnInformation().getMinRowIndex());
        int i = sheetView.getCurrentSheet().getWorkbook().isBefore07Version() ? 65536 : 1048576;
        while (drawingCell.getTop() <= y && drawingCell.getRowIndex() <= i) {
            Row row = sheetView.getCurrentSheet().getRow(drawingCell.getRowIndex());
            if (row == null || !row.isZeroHeight()) {
                drawingCell.setHeight(row == null ? (float) sheetView.getCurrentSheet().getDefaultRowHeight() : row.getRowPixelHeight());
                drawingCell.setHeight((float) Math.round(drawingCell.getHeight() * sheetView.getZoom()));
                if (drawingCell.getRowIndex() != sheetView.getMinRowAndColumnInformation().getMinRowIndex() || sheetView.getMinRowAndColumnInformation().isRowAllVisible()) {
                    drawingCell.setVisibleHeight(drawingCell.getHeight());
                } else {
                    drawingCell.setVisibleHeight((float) Math.round(sheetView.getMinRowAndColumnInformation().getVisibleRowHeight() * ((double) sheetView.getZoom())));
                }
                drawingCell.setTop(drawingCell.getTop() + drawingCell.getVisibleHeight());
                drawingCell.setRowIndex(drawingCell.getRowIndex() + 1);
            } else {
                drawingCell.setRowIndex(drawingCell.getRowIndex() + 1);
            }
        }
        return drawingCell.getRowIndex() - 1;
    }

    private int findClickedColumnHeader(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        SheetView sheetView = this.spreadsheet.getSheetView();
        DrawingCell drawingCell = new DrawingCell();
        drawingCell.setLeft((float) sheetView.getRowHeaderWidth());
        drawingCell.setColumnIndex(sheetView.getMinRowAndColumnInformation().getMinColumnIndex());
        int i = sheetView.getCurrentSheet().getWorkbook().isBefore07Version() ? 256 : 16384;
        while (drawingCell.getLeft() <= x && drawingCell.getColumnIndex() <= i) {
            if (sheetView.getCurrentSheet().isColumnHidden(drawingCell.getColumnIndex())) {
                drawingCell.setColumnIndex(drawingCell.getColumnIndex() + 1);
            } else {
                drawingCell.setWidth((float) Math.round(sheetView.getCurrentSheet().getColumnPixelWidth(drawingCell.getColumnIndex()) * sheetView.getZoom()));
                if (drawingCell.getColumnIndex() != sheetView.getMinRowAndColumnInformation().getMinColumnIndex() || sheetView.getMinRowAndColumnInformation().isColumnAllVisible()) {
                    drawingCell.setVisibleWidth(drawingCell.getWidth());
                } else {
                    drawingCell.setVisibleWidth((float) Math.round(sheetView.getMinRowAndColumnInformation().getVisibleColumnWidth() * ((double) sheetView.getZoom())));
                }
                drawingCell.setLeft(drawingCell.getLeft() + drawingCell.getVisibleWidth());
                drawingCell.setColumnIndex(drawingCell.getColumnIndex() + 1);
            }
        }
        return drawingCell.getColumnIndex() - 1;
    }

    private boolean checkClickedHeader(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        SheetView sheetView = this.spreadsheet.getSheetView();
        boolean z = true;
        if (((float) sheetView.getRowHeaderWidth()) > x && ((float) sheetView.getColumnHeaderHeight()) < y) {
            sheetView.getCurrentSheet().setActiveCellType(1);
            sheetView.getCurrentSheet().setActiveCellRow(findClickedRowHeader(motionEvent));
        } else if (((float) sheetView.getRowHeaderWidth()) >= x || ((float) sheetView.getColumnHeaderHeight()) <= y) {
            z = false;
        } else {
            sheetView.getCurrentSheet().setActiveCellType(2);
            sheetView.getCurrentSheet().setActiveCellColumn(findClickedColumnHeader(motionEvent));
        }
        this.spreadsheet.getControl().actionEvent(EventConstant.APP_CONTENT_SELECTED, (Object) null);
        return z;
    }

    private void actionUp(MotionEvent motionEvent) {
        boolean z;
        if (this.actionDown) {
            this.actionDown = false;
            if (this.scrolling) {
                z = changeHeaderEnd();
            } else if (this.longPress || !checkClickedCell(motionEvent)) {
                z = checkClickedHeader(motionEvent);
            } else {
                z = true;
            }
            this.longPress = false;
            if (!z) {
                return;
            }
            if (!this.timer.isRunning()) {
                this.timer.start();
            } else {
                this.timer.restart();
            }
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.spreadsheet == null) {
            return false;
        }
        super.onTouch(view, motionEvent);
        int action = motionEvent.getAction();
        if (motionEvent.getPointerCount() == 2) {
            this.scrolling = true;
            this.actionDown = false;
            if (this.newHeaderArea != null) {
                this.spreadsheet.getSheetView().setDrawMovingHeaderLine(false);
                this.oldHeaderArea = null;
                this.newHeaderArea = null;
            }
            return true;
        }
        if (action == 0) {
            this.actionDown = true;
        } else if (action == 1) {
            actionUp(motionEvent);
            this.scrolling = false;
            this.actionDown = false;
            this.spreadsheet.postInvalidate();
        } else if (action == 2) {
            changingHeader(motionEvent);
            this.spreadsheet.abortDrawing();
            this.spreadsheet.postInvalidate();
        }
        return false;
    }

    private void findChangingRowHeader(MotionEvent motionEvent) {
        float y = motionEvent.getY();
        SheetView sheetView = this.spreadsheet.getSheetView();
        DrawingCell drawingCell = new DrawingCell();
        drawingCell.setTop((float) sheetView.getColumnHeaderHeight());
        drawingCell.setRowIndex(sheetView.getMinRowAndColumnInformation().getMinRowIndex());
        int round = Math.round(drawingCell.getTop());
        Rect rect = new Rect();
        rect.bottom = round;
        rect.top = round;
        int i = sheetView.getCurrentSheet().getWorkbook().isBefore07Version() ? 65536 : 1048576;
        while (drawingCell.getTop() <= y && drawingCell.getRowIndex() <= i) {
            Row row = sheetView.getCurrentSheet().getRow(drawingCell.getRowIndex());
            if (row == null || !row.isZeroHeight()) {
                drawingCell.setHeight(row == null ? (float) sheetView.getCurrentSheet().getDefaultRowHeight() : row.getRowPixelHeight());
                drawingCell.setHeight((float) Math.round(drawingCell.getHeight() * sheetView.getZoom()));
                if (drawingCell.getRowIndex() != sheetView.getMinRowAndColumnInformation().getMinRowIndex() || sheetView.getMinRowAndColumnInformation().isRowAllVisible()) {
                    drawingCell.setVisibleHeight(drawingCell.getHeight());
                } else {
                    drawingCell.setVisibleHeight((float) Math.round(sheetView.getMinRowAndColumnInformation().getVisibleRowHeight() * ((double) sheetView.getZoom())));
                }
                rect.top = rect.bottom;
                rect.bottom = Math.round(drawingCell.getTop());
                round = Math.round(drawingCell.getTop());
                drawingCell.setTop(drawingCell.getTop() + drawingCell.getVisibleHeight());
                drawingCell.setRowIndex(drawingCell.getRowIndex() + 1);
            } else {
                drawingCell.setRowIndex(drawingCell.getRowIndex() + 1);
            }
        }
        if (this.oldHeaderArea == null) {
            this.oldHeaderArea = new FocusCell();
        }
        this.oldHeaderArea.setType(1);
        if (y > (((float) round) + drawingCell.getTop()) / 2.0f) {
            this.oldHeaderArea.setRow(drawingCell.getRowIndex() - 1);
            rect.top = rect.bottom;
            rect.bottom = Math.round(drawingCell.getTop());
            this.oldHeaderArea.setRect(rect);
            return;
        }
        int rowIndex = drawingCell.getRowIndex() - 2;
        FocusCell focusCell = this.oldHeaderArea;
        if (rowIndex < 0) {
            rowIndex = 0;
        }
        focusCell.setRow(rowIndex);
        this.oldHeaderArea.setRect(rect);
    }

    private void findChangingColumnHeader(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        SheetView sheetView = this.spreadsheet.getSheetView();
        DrawingCell drawingCell = new DrawingCell();
        drawingCell.setLeft((float) sheetView.getRowHeaderWidth());
        drawingCell.setColumnIndex(sheetView.getMinRowAndColumnInformation().getMinColumnIndex());
        int round = Math.round(drawingCell.getLeft());
        Rect rect = new Rect();
        int round2 = Math.round(drawingCell.getLeft());
        rect.right = round2;
        rect.left = round2;
        int i = sheetView.getCurrentSheet().getWorkbook().isBefore07Version() ? 256 : 16384;
        while (drawingCell.getLeft() <= x && drawingCell.getColumnIndex() <= i) {
            if (sheetView.getCurrentSheet().isColumnHidden(drawingCell.getColumnIndex())) {
                drawingCell.setColumnIndex(drawingCell.getColumnIndex() + 1);
            } else {
                drawingCell.setWidth((float) Math.round(sheetView.getCurrentSheet().getColumnPixelWidth(drawingCell.getColumnIndex()) * sheetView.getZoom()));
                if (drawingCell.getColumnIndex() != sheetView.getMinRowAndColumnInformation().getMinColumnIndex() || sheetView.getMinRowAndColumnInformation().isColumnAllVisible()) {
                    drawingCell.setVisibleWidth(drawingCell.getWidth());
                } else {
                    drawingCell.setVisibleWidth((float) Math.round(sheetView.getMinRowAndColumnInformation().getVisibleColumnWidth() * ((double) sheetView.getZoom())));
                }
                rect.left = rect.right;
                rect.right = Math.round(drawingCell.getLeft());
                round = Math.round(drawingCell.getLeft());
                drawingCell.setLeft(drawingCell.getLeft() + drawingCell.getVisibleWidth());
                drawingCell.setColumnIndex(drawingCell.getColumnIndex() + 1);
            }
        }
        if (this.oldHeaderArea == null) {
            this.oldHeaderArea = new FocusCell();
        }
        this.oldHeaderArea.setType(2);
        if (x > (((float) round) + drawingCell.getLeft()) / 2.0f) {
            this.oldHeaderArea.setColumn(drawingCell.getColumnIndex() - 1);
            rect.left = rect.right;
            rect.right = Math.round(drawingCell.getLeft());
            this.oldHeaderArea.setRect(rect);
            return;
        }
        int columnIndex = drawingCell.getColumnIndex() - 2;
        FocusCell focusCell = this.oldHeaderArea;
        if (columnIndex < 0) {
            columnIndex = 0;
        }
        focusCell.setColumn(columnIndex);
        this.oldHeaderArea.setRect(rect);
    }

    public void onLongPress(MotionEvent motionEvent) {
        super.onLongPress(motionEvent);
        this.longPress = true;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        this.oldY = Math.round(y);
        this.oldX = Math.round(x);
        SheetView sheetView = this.spreadsheet.getSheetView();
        if (((float) sheetView.getRowHeaderWidth()) > x && ((float) sheetView.getColumnHeaderHeight()) < y) {
            findChangingRowHeader(motionEvent);
        } else if (((float) sheetView.getRowHeaderWidth()) < x && ((float) sheetView.getColumnHeaderHeight()) > y) {
            findChangingColumnHeader(motionEvent);
        }
        FocusCell focusCell = this.oldHeaderArea;
        if (focusCell != null) {
            this.newHeaderArea = focusCell.clone();
            this.spreadsheet.getSheetView().changeHeaderArea(this.newHeaderArea);
            this.spreadsheet.getSheetView().setDrawMovingHeaderLine(true);
            this.spreadsheet.abortDrawing();
            this.spreadsheet.postInvalidate();
        }
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        boolean z;
        super.onScroll(motionEvent, motionEvent2, f, f2);
        SheetView sheetView = this.spreadsheet.getSheetView();
        if (Math.abs(f) > 2.0f) {
            z = true;
        } else {
            z = false;
            f = 0.0f;
        }
        if (Math.abs(f2) > 2.0f) {
            z = true;
        } else {
            f2 = 0.0f;
        }
        if (z) {
            this.isScroll = true;
            this.scrolling = true;
            sheetView.getRowHeader().calculateRowHeaderWidth(sheetView.getZoom());
            sheetView.scrollBy((float) Math.round(f), (float) Math.round(f2));
            this.spreadsheet.abortDrawing();
            this.spreadsheet.postInvalidate();
        }
        return true;
    }

    public void fling(int i, int i2) {
        super.fling(i, i2);
        float zoom = this.spreadsheet.getSheetView().getZoom();
        int round = Math.round(this.spreadsheet.getSheetView().getScrollX() * zoom);
        int round2 = Math.round(this.spreadsheet.getSheetView().getScrollY() * zoom);
        this.oldY = 0;
        this.oldX = 0;
        if (Math.abs(i2) > Math.abs(i)) {
            this.oldY = round2;
            this.mScroller.fling(round, round2, 0, i2, 0, 0, 0, this.spreadsheet.getSheetView().getMaxScrollY());
        } else {
            this.oldX = round;
            this.mScroller.fling(round, round2, i, 0, 0, this.spreadsheet.getSheetView().getMaxScrollX(), 0, 0);
        }
        this.spreadsheet.abortDrawing();
        this.spreadsheet.postInvalidate();
    }

    public void computeScroll() {
        super.computeScroll();
        if (this.mScroller.computeScrollOffset()) {
            this.isFling = true;
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            if (currX == this.oldX && this.oldY == currY) {
                this.mScroller.abortAnimation();
                this.spreadsheet.abortDrawing();
                this.spreadsheet.postInvalidate();
                return;
            }
            SheetView sheetView = this.spreadsheet.getSheetView();
            boolean z = false;
            int i = this.oldX;
            if (currX != i && this.oldY == 0) {
                if (Math.abs(currX - i) > 2) {
                    z = true;
                } else {
                    this.oldX = currX;
                }
            }
            int i2 = this.oldY;
            if (currY != i2 && this.oldX == 0) {
                if (Math.abs(i2 - currY) > 2) {
                    z = true;
                } else {
                    this.oldY = currY;
                }
            }
            if (z) {
                this.scrolling = true;
                sheetView.getRowHeader().calculateRowHeaderWidth(sheetView.getZoom());
                sheetView.scrollBy((float) Math.round((float) (currX - this.oldX)), (float) Math.round((float) (currY - this.oldY)));
            }
            this.spreadsheet.abortDrawing();
            this.spreadsheet.postInvalidate();
            this.oldX = currX;
            this.oldY = currY;
        }
    }

    public void dispose() {
        super.dispose();
        this.spreadsheet = null;
        FocusCell focusCell = this.oldHeaderArea;
        if (focusCell != null) {
            focusCell.dispose();
            this.oldHeaderArea = null;
        }
        FocusCell focusCell2 = this.newHeaderArea;
        if (focusCell2 != null) {
            focusCell2.dispose();
            this.newHeaderArea = null;
        }
        ATimer aTimer = this.timer;
        if (aTimer != null) {
            aTimer.dispose();
            this.timer = null;
        }
    }
}
