package com.app.office.ss.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.exifinterface.media.ExifInterface;
import com.app.office.common.PaintKit;
import com.app.office.constant.SSConstant;
import com.app.office.fc.ppt.attribute.ParaAttr;
import com.app.office.fc.ppt.attribute.RunAttr;
import com.app.office.fc.xls.Reader.SchemeColorUtil;
import com.app.office.simpletext.font.FontKit;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.AttributeSetImpl;
import com.app.office.simpletext.model.CellLeafElement;
import com.app.office.simpletext.model.IAttributeSet;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.LeafElement;
import com.app.office.simpletext.model.ParagraphElement;
import com.app.office.simpletext.model.STDocument;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.simpletext.view.STRoot;
import com.app.office.ss.model.CellRangeAddress;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Row;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.model.style.CellStyle;
import com.app.office.ss.model.table.SSTable;
import com.app.office.ss.model.table.SSTableCellStyle;
import com.app.office.ss.model.table.SSTableStyle;
import com.app.office.ss.model.table.TableStyleKit;
import com.app.office.ss.other.DrawingCell;
import com.app.office.ss.other.MergeCell;
import com.app.office.ss.other.MergeCellMgr;
import com.app.office.ss.util.ModelUtil;

public class CellView {
    private CellBorderView cellBorderView;
    private DrawingCell cellInfor;
    private RectF cellRect;
    private float left;
    private MergeCellMgr mergedCellMgr = new MergeCellMgr();
    private MergeCell mergedCellSize = new MergeCell();
    private boolean numericCellAlignRight;
    private SheetView sheetView = null;
    private StringBuilder strBuilder = new StringBuilder();
    private TableStyleKit tableStyleKit = new TableStyleKit();
    private float top;

    public CellView(SheetView sheetView2) {
        this.sheetView = sheetView2;
        this.cellBorderView = new CellBorderView(sheetView2);
        this.cellRect = new RectF();
    }

    private void redrawMergedCellBordersAndBackground(Canvas canvas, CellRangeAddress cellRangeAddress) {
        canvas.save();
        float f = this.left;
        canvas.clipRect(f, this.top, ((this.mergedCellSize.getWidth() + f) - this.mergedCellSize.getNovisibleWidth()) - (this.cellInfor.getWidth() - this.cellInfor.getVisibleWidth()), ((this.top + this.mergedCellSize.getHeight()) - this.mergedCellSize.getNoVisibleHeight()) - (this.cellInfor.getHeight() - this.cellInfor.getVisibleHeight()));
        canvas.drawColor(-1);
        Paint paint = PaintKit.instance().getPaint();
        int color = paint.getColor();
        Paint.Style style = paint.getStyle();
        paint.setColor(SSConstant.GRIDLINE_COLOR);
        paint.setStyle(Paint.Style.STROKE);
        float f2 = this.left;
        canvas.drawRect(f2, this.top, (this.mergedCellSize.getWidth() + f2) - this.mergedCellSize.getNovisibleWidth(), (this.top + this.mergedCellSize.getHeight()) - this.mergedCellSize.getNoVisibleHeight(), paint);
        paint.setStyle(style);
        int minRowIndex = this.sheetView.getMinRowAndColumnInformation().getMinRowIndex();
        int minColumnIndex = this.sheetView.getMinRowAndColumnInformation().getMinColumnIndex();
        if (this.mergedCellSize.isFrozenRow()) {
            minRowIndex = 0;
        }
        int i = this.mergedCellSize.isFrozenColumn() ? 0 : minColumnIndex;
        if (minRowIndex >= cellRangeAddress.getFirstRow()) {
            paint.setColor(SSConstant.HEADER_GRIDLINE_COLOR);
            float f3 = this.left;
            canvas.drawRect(f3, this.top, (this.mergedCellSize.getWidth() + f3) - this.mergedCellSize.getNovisibleWidth(), this.top + 1.0f, paint);
        }
        if (i >= cellRangeAddress.getFirstColumn()) {
            paint.setColor(SSConstant.HEADER_GRIDLINE_COLOR);
            float f4 = this.left;
            float f5 = this.top;
            canvas.drawRect(f4, f5, f4 + 1.0f, (this.mergedCellSize.getHeight() + f5) - this.mergedCellSize.getNoVisibleHeight(), paint);
        }
        paint.setColor(color);
        canvas.restore();
    }

    public void draw(Canvas canvas, Cell cell, DrawingCell drawingCell) {
        if (cell != null && !this.sheetView.getSpreadsheet().isAbortDrawing()) {
            Sheet currentSheet = this.sheetView.getCurrentSheet();
            this.cellInfor = drawingCell;
            this.left = drawingCell.getLeft();
            this.top = drawingCell.getTop();
            this.mergedCellSize.setWidth(drawingCell.getWidth());
            this.mergedCellSize.setHeight(drawingCell.getHeight());
            this.mergedCellSize.setNovisibleWidth(0.0f);
            this.mergedCellSize.setNoVisibleHeight(0.0f);
            if (cell.getRangeAddressIndex() >= 0) {
                CellRangeAddress mergeRange = currentSheet.getMergeRange(cell.getRangeAddressIndex());
                if (this.mergedCellMgr.isDrawMergeCell(this.sheetView, mergeRange, drawingCell.getRowIndex(), drawingCell.getColumnIndex())) {
                    this.mergedCellSize = this.mergedCellMgr.getMergedCellSize(this.sheetView, mergeRange, drawingCell.getRowIndex(), drawingCell.getColumnIndex());
                    Cell cell2 = currentSheet.getRow(mergeRange.getFirstRow()).getCell(mergeRange.getFirstColumn());
                    redrawMergedCellBordersAndBackground(canvas, mergeRange);
                    cell = cell2;
                } else {
                    return;
                }
            }
            RectF rectF = this.cellRect;
            float f = this.left;
            rectF.set(f, this.top, ((this.mergedCellSize.getWidth() + f) - this.mergedCellSize.getNovisibleWidth()) - (drawingCell.getWidth() - drawingCell.getVisibleWidth()), ((this.top + this.mergedCellSize.getHeight()) - this.mergedCellSize.getNoVisibleHeight()) - (drawingCell.getHeight() - drawingCell.getVisibleHeight()));
            SSTable tableInfo = cell.getTableInfo();
            SSTableCellStyle sSTableCellStyle = null;
            if (tableInfo != null) {
                sSTableCellStyle = getTableCellStyle(tableInfo, this.sheetView.getCurrentSheet().getWorkbook(), cell.getRowNumber(), cell.getColNumber());
            }
            drawCellBackgroundAndBorder(canvas, cell, sSTableCellStyle);
            drawCellContents(canvas, cell, drawingCell, sSTableCellStyle);
        }
    }

    public void drawActiveCellBorder(Canvas canvas, RectF rectF, short s) {
        this.cellBorderView.drawActiveCellBorder(canvas, rectF, s);
    }

    public void drawCellBackgroundAndBorder(Canvas canvas, Cell cell, SSTableCellStyle sSTableCellStyle) {
        Paint paint = PaintKit.instance().getPaint();
        int color = paint.getColor();
        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle != null && cellStyle.getFillPatternType() == 0) {
            paint.setColor(cellStyle.getFgColor());
            if (Math.abs(this.cellRect.left - ((float) this.sheetView.getRowHeaderWidth())) < 1.0f) {
                canvas.drawRect(1.0f + this.cellRect.left, this.cellRect.top, this.cellRect.right, this.cellRect.bottom, paint);
            } else {
                canvas.drawRect(this.cellRect, paint);
            }
        } else if (cellStyle != null && (cellStyle.getFillPatternType() == 7 || cellStyle.getFillPatternType() == 4)) {
            drawGradientAndTile(canvas, this.sheetView.getSpreadsheet().getControl(), this.sheetView.getSheetIndex(), cellStyle.getFillPattern(), this.cellRect, this.sheetView.getZoom(), paint);
        } else if (!(sSTableCellStyle == null || sSTableCellStyle.getFillColor() == null)) {
            paint.setColor(sSTableCellStyle.getFillColor().intValue());
            if (Math.abs(this.cellRect.left - ((float) this.sheetView.getRowHeaderWidth())) < 1.0f) {
                canvas.drawRect(1.0f + this.cellRect.left, this.cellRect.top, this.cellRect.right, this.cellRect.bottom, paint);
            } else {
                canvas.drawRect(this.cellRect, paint);
            }
        }
        paint.setColor(color);
        this.cellBorderView.draw(canvas, cell, this.cellRect, sSTableCellStyle);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0091, code lost:
        if (r1 != 100) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x009e, code lost:
        if (r1 != 100) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00a3, code lost:
        r5 = 0.0f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawGradientAndTile(android.graphics.Canvas r17, com.app.office.system.IControl r18, int r19, com.app.office.common.bg.BackgroundAndFill r20, android.graphics.RectF r21, float r22, android.graphics.Paint r23) {
        /*
            r16 = this;
            r0 = r21
            r1 = r22
            r2 = r23
            com.app.office.common.bg.AShader r3 = r20.getShader()
            if (r3 == 0) goto L_0x00dd
            android.graphics.Shader r4 = r3.getShader()
            r5 = 1065353216(0x3f800000, float:1.0)
            if (r4 != 0) goto L_0x0046
            float r4 = r5 / r1
            android.graphics.Rect r6 = new android.graphics.Rect
            float r7 = r0.left
            float r7 = r7 * r4
            int r7 = java.lang.Math.round(r7)
            float r8 = r0.top
            float r8 = r8 * r4
            int r8 = java.lang.Math.round(r8)
            float r9 = r0.right
            float r9 = r9 * r4
            int r9 = java.lang.Math.round(r9)
            float r10 = r0.bottom
            float r10 = r10 * r4
            int r4 = java.lang.Math.round(r10)
            r6.<init>(r7, r8, r9, r4)
            r4 = r18
            r7 = r19
            android.graphics.Shader r4 = r3.createShader(r4, r7, r6)
            if (r4 != 0) goto L_0x0046
            return
        L_0x0046:
            android.graphics.Matrix r6 = new android.graphics.Matrix
            r6.<init>()
            float r7 = r0.left
            float r8 = r0.top
            boolean r9 = r3 instanceof com.app.office.common.bg.TileShader
            if (r9 == 0) goto L_0x0069
            com.app.office.common.bg.TileShader r3 = (com.app.office.common.bg.TileShader) r3
            int r5 = r3.getOffsetX()
            float r5 = (float) r5
            float r5 = r5 * r1
            float r7 = r7 + r5
            int r3 = r3.getOffsetY()
            float r3 = (float) r3
            float r3 = r3 * r1
            float r8 = r8 + r3
            r6.postScale(r1, r1)
            goto L_0x00cb
        L_0x0069:
            boolean r1 = r3 instanceof com.app.office.common.bg.PatternShader
            if (r1 == 0) goto L_0x006e
            goto L_0x00cb
        L_0x006e:
            boolean r1 = r3 instanceof com.app.office.common.bg.LinearGradientShader
            if (r1 == 0) goto L_0x00bc
            com.app.office.common.bg.LinearGradientShader r3 = (com.app.office.common.bg.LinearGradientShader) r3
            int r1 = r3.getAngle()
            r9 = 90
            r10 = 100
            r11 = 50
            r12 = -50
            r13 = 0
            r14 = 1056964608(0x3f000000, float:0.5)
            r15 = -1090519040(0xffffffffbf000000, float:-0.5)
            if (r1 != r9) goto L_0x0094
            int r1 = r3.getFocus()
            if (r1 == r12) goto L_0x00a5
            if (r1 == 0) goto L_0x00a0
            if (r1 == r11) goto L_0x00aa
            if (r1 == r10) goto L_0x00a3
            goto L_0x00a0
        L_0x0094:
            int r1 = r3.getFocus()
            if (r1 == r12) goto L_0x00aa
            if (r1 == 0) goto L_0x00a0
            if (r1 == r11) goto L_0x00a5
            if (r1 == r10) goto L_0x00a3
        L_0x00a0:
            r13 = 1065353216(0x3f800000, float:1.0)
            goto L_0x00ae
        L_0x00a3:
            r5 = 0
            goto L_0x00ae
        L_0x00a5:
            r5 = 1056964608(0x3f000000, float:0.5)
            r13 = 1056964608(0x3f000000, float:0.5)
            goto L_0x00ae
        L_0x00aa:
            r5 = -1090519040(0xffffffffbf000000, float:-0.5)
            r13 = -1090519040(0xffffffffbf000000, float:-0.5)
        L_0x00ae:
            float r1 = r21.width()
            float r5 = r5 * r1
            float r7 = r7 + r5
            float r1 = r21.height()
            float r13 = r13 * r1
            float r8 = r8 + r13
        L_0x00bc:
            float r1 = r21.width()
            r3 = 1120403456(0x42c80000, float:100.0)
            float r1 = r1 / r3
            float r5 = r21.height()
            float r5 = r5 / r3
            r6.postScale(r1, r5)
        L_0x00cb:
            r6.postTranslate(r7, r8)
            r4.setLocalMatrix(r6)
            r2.setShader(r4)
            r1 = r17
            r1.drawRect(r0, r2)
            r0 = 0
            r2.setShader(r0)
        L_0x00dd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.view.CellView.drawGradientAndTile(android.graphics.Canvas, com.app.office.system.IControl, int, com.app.office.common.bg.BackgroundAndFill, android.graphics.RectF, float, android.graphics.Paint):void");
    }

    public SSTableCellStyle getTableCellStyle(SSTable sSTable, Workbook workbook, int i, int i2) {
        CellRangeAddress tableReference = sSTable.getTableReference();
        SSTableStyle tableStyle = this.tableStyleKit.getTableStyle(sSTable.getName(), SchemeColorUtil.getSchemeColor(workbook));
        if (tableStyle == null) {
            return null;
        }
        if (sSTable.isHeaderRowShown()) {
            if (i == tableReference.getFirstRow()) {
                return tableStyle.getFirstRow();
            }
            if (!sSTable.isTotalRowShown() || i != tableReference.getLastRow()) {
                if (sSTable.isShowFirstColumn() && i2 == tableReference.getFirstColumn() && tableStyle.getFirstCol() != null) {
                    return tableStyle.getFirstCol();
                }
                if (sSTable.isShowLastColumn() && i2 == tableReference.getLastColumn() && tableStyle.getLastCol() != null) {
                    return tableStyle.getLastCol();
                }
                if (sSTable.isShowRowStripes()) {
                    if ((i - tableReference.getFirstRow()) % 2 == 1) {
                        return tableStyle.getBand1V();
                    }
                    if (!sSTable.isShowColumnStripes() || (i2 - tableReference.getFirstColumn()) % 2 != 0) {
                        return tableStyle.getBand2V();
                    }
                    return tableStyle.getBand1H();
                } else if (!sSTable.isShowColumnStripes()) {
                    return tableStyle.getBand2H();
                } else {
                    if ((i2 - tableReference.getFirstColumn()) % 2 == 0) {
                        return tableStyle.getBand1H();
                    }
                    return tableStyle.getBand2H();
                }
            } else if (tableStyle.getLastRow() != null) {
                return tableStyle.getLastRow();
            } else {
                if (sSTable.isShowFirstColumn() && i2 == tableReference.getFirstColumn() && tableStyle.getFirstCol() != null) {
                    return tableStyle.getFirstCol();
                }
                if (!sSTable.isShowLastColumn() || i2 != tableReference.getLastColumn() || tableStyle.getLastCol() == null) {
                    return tableStyle.getBand2H();
                }
                return tableStyle.getLastCol();
            }
        } else if (!sSTable.isTotalRowShown() || i != tableReference.getLastRow()) {
            if (sSTable.isShowFirstColumn() && i2 == tableReference.getFirstColumn() && tableStyle.getFirstCol() != null) {
                return tableStyle.getFirstCol();
            }
            if (sSTable.isShowLastColumn() && i2 == tableReference.getLastColumn() && tableStyle.getLastCol() != null) {
                return tableStyle.getLastCol();
            }
            if (sSTable.isShowRowStripes()) {
                if ((i - tableReference.getFirstRow()) % 2 == 0) {
                    return tableStyle.getBand1V();
                }
                if (!sSTable.isShowColumnStripes() || (i2 - tableReference.getFirstColumn()) % 2 != 0) {
                    return tableStyle.getBand2V();
                }
                return tableStyle.getBand1H();
            } else if (!sSTable.isShowColumnStripes()) {
                return tableStyle.getBand2H();
            } else {
                if ((i2 - tableReference.getFirstColumn()) % 2 == 0) {
                    return tableStyle.getBand1H();
                }
                return tableStyle.getBand2H();
            }
        } else if (tableStyle.getLastRow() != null) {
            return tableStyle.getLastRow();
        } else {
            if (sSTable.isShowFirstColumn() && i2 == tableReference.getFirstColumn() && tableStyle.getFirstCol() != null) {
                return tableStyle.getFirstCol();
            }
            if (!sSTable.isShowLastColumn() || i2 != tableReference.getLastColumn() || tableStyle.getLastCol() == null) {
                return tableStyle.getBand2H();
            }
            return tableStyle.getLastCol();
        }
    }

    private int getRotatedX(int i, int i2, int i3) {
        double d = ((double) (((float) i3) / 180.0f)) * 3.141592653589793d;
        return (int) ((Math.sin(d) * ((double) i2)) + (Math.cos(d) * ((double) i)));
    }

    private int getRotatedY(int i, int i2, int i3) {
        double d = ((double) (((float) i3) / 180.0f)) * 3.141592653589793d;
        return (int) ((Math.cos(d) * ((double) i2)) - (Math.sin(d) * ((double) i)));
    }

    private void drawCellContents(Canvas canvas, Cell cell, DrawingCell drawingCell, SSTableCellStyle sSTableCellStyle) {
        Sheet currentSheet = this.sheetView.getCurrentSheet();
        if (cell.getExpandedRangeAddressIndex() >= 0) {
            Row row = currentSheet.getRow(drawingCell.getRowIndex());
            CellRangeAddress rangedAddress = row.getExpandedRangeAddress(cell.getExpandedRangeAddressIndex()).getRangedAddress();
            if (this.mergedCellMgr.isDrawMergeCell(this.sheetView, rangedAddress, drawingCell.getRowIndex(), drawingCell.getColumnIndex())) {
                this.mergedCellSize = this.mergedCellMgr.getMergedCellSize(this.sheetView, rangedAddress, drawingCell.getRowIndex(), drawingCell.getColumnIndex());
                cell = row.getExpandedRangeAddress(cell.getExpandedRangeAddressIndex()).getExpandedCell();
                RectF rectF = this.cellRect;
                float f = this.left;
                rectF.set(f, this.top, ((this.mergedCellSize.getWidth() + f) - this.mergedCellSize.getNovisibleWidth()) - (drawingCell.getWidth() - drawingCell.getVisibleWidth()), ((this.top + this.mergedCellSize.getHeight()) - this.mergedCellSize.getNoVisibleHeight()) - (drawingCell.getHeight() - drawingCell.getVisibleHeight()));
            } else {
                return;
            }
        }
        String formatContents = ModelUtil.instance().getFormatContents(currentSheet.getWorkbook(), cell);
        if (formatContents != null && formatContents.length() != 0) {
            Paint cellPaint = FontKit.instance().getCellPaint(cell, currentSheet.getWorkbook(), sSTableCellStyle);
            float textSize = cellPaint.getTextSize();
            cellPaint.setTextSize(this.sheetView.getZoom() * textSize);
            this.numericCellAlignRight = false;
            if (cell.getCellType() == 4 || (cell.getCellType() == 0 && cell.getCellNumericType() != 11)) {
                this.numericCellAlignRight = true;
            }
            if (this.numericCellAlignRight) {
                drawNumericCell(canvas, cell, formatContents, cellPaint);
            } else {
                drawNonNumericCell(canvas, cell, formatContents, cellPaint);
            }
            cellPaint.setTextSize(textSize);
        }
    }

    public static boolean isComplexText(Cell cell) {
        if (cell.getCellType() != 1 || cell.getStringCellValueIndex() < 0) {
            return false;
        }
        return cell.getSheet().getWorkbook().getSharedItem(cell.getStringCellValueIndex()) instanceof SectionElement;
    }

    private void drawNonNumericCell(Canvas canvas, Cell cell, String str, Paint paint) {
        if (isComplexText(cell)) {
            drawComplexTextCell(canvas, cell);
        } else if (!cell.getCellStyle().isWrapText() || (!str.contains("\n") && paint.measureText(str) + 4.0f <= this.mergedCellSize.getWidth())) {
            if (str.length() > 1024) {
                str = str.substring(0, 1024);
            }
            drawNonWrapText(canvas, cell, str, paint);
        } else {
            canvas.save();
            canvas.clipRect(this.cellRect);
            drawWrapText(canvas, cell, str);
            canvas.restore();
        }
    }

    private void drawComplexTextCell(Canvas canvas, Cell cell) {
        if (cell.getCellStyle().isWrapText()) {
            drawWrapComplexTextCell(canvas, cell);
        } else {
            drawNotWrapComplexTextCell(canvas, cell);
        }
    }

    private void drawWrapComplexTextCell(Canvas canvas, Cell cell) {
        canvas.save();
        canvas.clipRect(this.cellRect);
        STRoot sTRoot = cell.getSTRoot();
        if (sTRoot == null) {
            Rect cellAnchor = ModelUtil.instance().getCellAnchor(this.sheetView.getCurrentSheet(), cell.getRowNumber(), cell.getColNumber());
            SectionElement sectionElement = (SectionElement) cell.getSheet().getWorkbook().getSharedItem(cell.getStringCellValueIndex());
            if (sectionElement == null || sectionElement.getEndOffset() - sectionElement.getStartOffset() == 0) {
                canvas.restore();
                return;
            }
            IAttributeSet attribute = sectionElement.getAttribute();
            AttrManage.instance().setPageWidth(attribute, Math.round(((float) cellAnchor.width()) * 15.0f));
            AttrManage.instance().setPageHeight(attribute, Math.round(((float) cellAnchor.height()) * 15.0f));
            STDocument sTDocument = new STDocument();
            sTDocument.appendSection(sectionElement);
            STRoot sTRoot2 = new STRoot(this.sheetView.getSpreadsheet().getEditor(), sTDocument);
            sTRoot2.setWrapLine(true);
            sTRoot2.doLayout();
            cell.setSTRoot(sTRoot2);
            sTRoot = sTRoot2;
        }
        sTRoot.draw(canvas, Math.round((this.left - this.mergedCellSize.getNovisibleWidth()) - (this.cellInfor.getWidth() - this.cellInfor.getVisibleWidth())), Math.round((this.top - this.mergedCellSize.getNoVisibleHeight()) - (this.cellInfor.getHeight() - this.cellInfor.getVisibleHeight())), this.sheetView.getZoom());
        canvas.restore();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00a0, code lost:
        if (r0 != 3) goto L_0x00b4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawNotWrapComplexTextCell(android.graphics.Canvas r8, com.app.office.ss.model.baseModel.Cell r9) {
        /*
            r7 = this;
            r8.save()
            android.graphics.RectF r0 = r7.cellRect
            r8.clipRect(r0)
            com.app.office.ss.model.style.CellStyle r0 = r9.getCellStyle()
            com.app.office.simpletext.view.STRoot r6 = r9.getSTRoot()
            if (r6 != 0) goto L_0x0016
            r8.restore()
            return
        L_0x0016:
            com.app.office.simpletext.view.IView r1 = r6.getChildView()
            com.app.office.simpletext.view.IView r1 = r1.getChildView()
            r2 = 0
            int r2 = r1.getLayoutSpan(r2)
            float r2 = (float) r2
            com.app.office.ss.view.SheetView r3 = r7.sheetView
            float r3 = r3.getZoom()
            float r2 = r2 * r3
            int r2 = (int) r2
            r1.getHeight()
            com.app.office.ss.view.SheetView r1 = r7.sheetView
            r1.getZoom()
            com.app.office.ss.view.SheetView r1 = r7.sheetView
            short r3 = r0.getIndent()
            int r1 = r1.getIndentWidthWithZoom(r3)
            int r3 = r1 + 4
            float r3 = (float) r3
            com.app.office.ss.other.MergeCell r4 = r7.mergedCellSize
            float r4 = r4.getWidth()
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 < 0) goto L_0x0050
            r8.restore()
            return
        L_0x0050:
            short r3 = r0.getHorizontalAlign()
            r4 = 1073741824(0x40000000, float:2.0)
            switch(r3) {
                case 0: goto L_0x0083;
                case 1: goto L_0x0083;
                case 2: goto L_0x0074;
                case 3: goto L_0x005a;
                case 4: goto L_0x0083;
                case 5: goto L_0x0083;
                case 6: goto L_0x0083;
                default: goto L_0x0059;
            }
        L_0x0059:
            goto L_0x0093
        L_0x005a:
            com.app.office.ss.other.MergeCell r3 = r7.mergedCellSize
            float r5 = r3.getWidth()
            float r1 = (float) r1
            float r5 = r5 - r1
            r3.setWidth(r5)
            float r1 = r7.left
            com.app.office.ss.other.MergeCell r3 = r7.mergedCellSize
            float r3 = r3.getWidth()
            float r2 = (float) r2
            float r3 = r3 - r2
            float r3 = r3 - r4
            float r1 = r1 + r3
            r7.left = r1
            goto L_0x0093
        L_0x0074:
            float r1 = r7.left
            com.app.office.ss.other.MergeCell r3 = r7.mergedCellSize
            float r3 = r3.getWidth()
            float r2 = (float) r2
            float r3 = r3 - r2
            float r3 = r3 / r4
            float r1 = r1 + r3
            r7.left = r1
            goto L_0x0093
        L_0x0083:
            float r2 = r7.left
            float r2 = r2 + r4
            r7.left = r2
            com.app.office.ss.other.MergeCell r2 = r7.mergedCellSize
            float r3 = r2.getWidth()
            float r1 = (float) r1
            float r3 = r3 - r1
            r2.setWidth(r3)
        L_0x0093:
            short r0 = r0.getVerticalAlign()
            if (r0 == 0) goto L_0x00af
            r1 = 1
            if (r0 == r1) goto L_0x00a9
            r1 = 2
            if (r0 == r1) goto L_0x00a3
            r1 = 3
            if (r0 == r1) goto L_0x00a9
            goto L_0x00b4
        L_0x00a3:
            float r0 = r7.top
            float r0 = r0 + r4
            r7.top = r0
            goto L_0x00b4
        L_0x00a9:
            float r0 = r7.top
            float r0 = r0 + r4
            r7.top = r0
            goto L_0x00b4
        L_0x00af:
            float r0 = r7.top
            float r0 = r0 + r4
            r7.top = r0
        L_0x00b4:
            float r0 = r7.left
            com.app.office.ss.other.MergeCell r1 = r7.mergedCellSize
            float r1 = r1.getNovisibleWidth()
            float r0 = r0 - r1
            com.app.office.ss.other.DrawingCell r1 = r7.cellInfor
            float r1 = r1.getWidth()
            com.app.office.ss.other.DrawingCell r2 = r7.cellInfor
            float r2 = r2.getVisibleWidth()
            float r1 = r1 - r2
            float r4 = r0 - r1
            float r0 = r7.top
            com.app.office.ss.other.MergeCell r1 = r7.mergedCellSize
            float r1 = r1.getNoVisibleHeight()
            float r0 = r0 - r1
            com.app.office.ss.other.DrawingCell r1 = r7.cellInfor
            float r1 = r1.getHeight()
            com.app.office.ss.other.DrawingCell r2 = r7.cellInfor
            float r2 = r2.getVisibleHeight()
            float r1 = r1 - r2
            float r5 = r0 - r1
            int r0 = r9.getExpandedRangeAddressIndex()
            if (r0 < 0) goto L_0x00f8
            com.app.office.ss.view.SheetView r1 = r7.sheetView
            android.graphics.RectF r3 = new android.graphics.RectF
            android.graphics.RectF r0 = r7.cellRect
            r3.<init>(r0)
            r2 = r9
            r1.addExtendCell(r2, r3, r4, r5, r6)
            goto L_0x0103
        L_0x00f8:
            int r9 = (int) r4
            int r0 = (int) r5
            com.app.office.ss.view.SheetView r1 = r7.sheetView
            float r1 = r1.getZoom()
            r6.draw(r8, r9, r0, r1)
        L_0x0103:
            r8.restore()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.view.CellView.drawNotWrapComplexTextCell(android.graphics.Canvas, com.app.office.ss.model.baseModel.Cell):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x010e, code lost:
        if (r5 != 3) goto L_0x0133;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawAccountCell(android.graphics.Canvas r17, com.app.office.ss.model.baseModel.Cell r18, java.lang.String r19, android.graphics.Paint r20) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r19
            r3 = r20
            android.graphics.Paint$FontMetrics r4 = r20.getFontMetrics()
            float r5 = r3.measureText(r2)
            int r5 = (int) r5
            float r6 = r4.descent
            float r7 = r4.ascent
            float r6 = r6 - r7
            double r6 = (double) r6
            double r6 = java.lang.Math.ceil(r6)
            int r6 = (int) r6
            float r7 = r0.left
            r8 = 1073741824(0x40000000, float:2.0)
            float r7 = r7 + r8
            r0.left = r7
            com.app.office.ss.model.style.CellStyle r7 = r18.getCellStyle()
            com.app.office.ss.view.SheetView r9 = r0.sheetView
            short r10 = r7.getIndent()
            int r9 = r9.getIndentWidthWithZoom(r10)
            r17.save()
            android.graphics.RectF r10 = r0.cellRect
            r1.clipRect(r10)
            int r5 = r5 + r9
            r10 = 2
            int r5 = r5 + r10
            float r5 = (float) r5
            com.app.office.ss.other.MergeCell r11 = r0.mergedCellSize
            float r11 = r11.getWidth()
            r12 = 1082130432(0x40800000, float:4.0)
            r13 = 3
            r14 = 0
            r15 = 1
            int r5 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r5 <= 0) goto L_0x007b
            com.app.office.ss.other.MergeCell r2 = r0.mergedCellSize
            float r2 = r2.getWidth()
            float r2 = r2 - r12
            java.lang.String r5 = "#"
            float r5 = r3.measureText(r5)
            float r2 = r2 / r5
            int r2 = (int) r2
            java.lang.StringBuilder r5 = r0.strBuilder
            int r7 = r5.length()
            r5.delete(r14, r7)
        L_0x0064:
            if (r14 >= r2) goto L_0x0070
            java.lang.StringBuilder r5 = r0.strBuilder
            r7 = 35
            r5.append(r7)
            int r14 = r14 + 1
            goto L_0x0064
        L_0x0070:
            java.lang.StringBuilder r2 = r0.strBuilder
            java.lang.String r2 = r2.toString()
            r3.measureText(r2)
            goto L_0x0100
        L_0x007b:
            short r5 = r7.getHorizontalAlign()
            if (r5 == 0) goto L_0x0092
            if (r5 == r15) goto L_0x0092
            if (r5 == r13) goto L_0x0086
            goto L_0x00a2
        L_0x0086:
            com.app.office.ss.other.MergeCell r5 = r0.mergedCellSize
            float r7 = r5.getWidth()
            float r9 = (float) r9
            float r7 = r7 - r9
            r5.setWidth(r7)
            goto L_0x00a2
        L_0x0092:
            float r5 = r0.left
            float r7 = (float) r9
            float r5 = r5 + r7
            r0.left = r5
            com.app.office.ss.other.MergeCell r5 = r0.mergedCellSize
            float r9 = r5.getWidth()
            float r9 = r9 - r7
            r5.setWidth(r9)
        L_0x00a2:
            java.lang.String r5 = "*"
            int r5 = r2.indexOf(r5)
            r7 = -1
            if (r5 <= r7) goto L_0x0100
            java.lang.String r7 = r2.substring(r14, r5)
            int r5 = r5 + r15
            int r9 = r19.length()
            java.lang.String r2 = r2.substring(r5, r9)
            com.app.office.ss.other.MergeCell r5 = r0.mergedCellSize
            float r5 = r5.getWidth()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r7)
            r9.append(r2)
            java.lang.String r9 = r9.toString()
            float r9 = r3.measureText(r9)
            float r5 = r5 - r9
            float r5 = r5 - r12
            java.lang.String r9 = " "
            float r9 = r3.measureText(r9)
            float r5 = r5 / r9
            int r5 = (int) r5
            java.lang.StringBuilder r9 = r0.strBuilder
            int r11 = r9.length()
            r9.delete(r14, r11)
            java.lang.StringBuilder r9 = r0.strBuilder
            r9.append(r7)
        L_0x00e9:
            if (r14 >= r5) goto L_0x00f5
            java.lang.StringBuilder r7 = r0.strBuilder
            r9 = 32
            r7.append(r9)
            int r14 = r14 + 1
            goto L_0x00e9
        L_0x00f5:
            java.lang.StringBuilder r5 = r0.strBuilder
            r5.append(r2)
            java.lang.StringBuilder r2 = r0.strBuilder
            java.lang.String r2 = r2.toString()
        L_0x0100:
            com.app.office.ss.model.style.CellStyle r5 = r18.getCellStyle()
            short r5 = r5.getVerticalAlign()
            if (r5 == 0) goto L_0x012e
            if (r5 == r15) goto L_0x011f
            if (r5 == r10) goto L_0x0111
            if (r5 == r13) goto L_0x011f
            goto L_0x0133
        L_0x0111:
            float r5 = r0.top
            com.app.office.ss.other.MergeCell r7 = r0.mergedCellSize
            float r7 = r7.getHeight()
            float r6 = (float) r6
            float r7 = r7 - r6
            float r5 = r5 + r7
            r0.top = r5
            goto L_0x0133
        L_0x011f:
            float r5 = r0.top
            com.app.office.ss.other.MergeCell r7 = r0.mergedCellSize
            float r7 = r7.getHeight()
            float r6 = (float) r6
            float r7 = r7 - r6
            float r7 = r7 / r8
            float r5 = r5 + r7
            r0.top = r5
            goto L_0x0133
        L_0x012e:
            float r5 = r0.top
            float r5 = r5 + r8
            r0.top = r5
        L_0x0133:
            float r5 = r0.left
            com.app.office.ss.other.MergeCell r6 = r0.mergedCellSize
            float r6 = r6.getNovisibleWidth()
            float r5 = r5 - r6
            com.app.office.ss.other.DrawingCell r6 = r0.cellInfor
            float r6 = r6.getWidth()
            com.app.office.ss.other.DrawingCell r7 = r0.cellInfor
            float r7 = r7.getVisibleWidth()
            float r6 = r6 - r7
            float r5 = r5 - r6
            float r6 = r0.top
            float r4 = r4.ascent
            float r6 = r6 - r4
            com.app.office.ss.other.MergeCell r4 = r0.mergedCellSize
            float r4 = r4.getNoVisibleHeight()
            float r6 = r6 - r4
            com.app.office.ss.other.DrawingCell r4 = r0.cellInfor
            float r4 = r4.getHeight()
            com.app.office.ss.other.DrawingCell r7 = r0.cellInfor
            float r7 = r7.getVisibleHeight()
            float r4 = r4 - r7
            float r6 = r6 - r4
            r1.drawText(r2, r5, r6, r3)
            r17.restore()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.view.CellView.drawAccountCell(android.graphics.Canvas, com.app.office.ss.model.baseModel.Cell, java.lang.String, android.graphics.Paint):void");
    }

    private String getScientificGeneralContents(String str, Paint paint) {
        String str2;
        boolean z;
        char c;
        int indexOf = str.indexOf(69);
        String substring = str.substring(0, indexOf);
        String substring2 = str.substring(indexOf + 1);
        int parseInt = Integer.parseInt(substring2);
        if (parseInt > 0) {
            if (parseInt < 10) {
                str2 = "E+0".concat(substring2);
            } else {
                str2 = "E+".concat(substring2);
            }
        } else if (parseInt > -10) {
            str2 = "E-0".concat(String.valueOf(-parseInt));
        } else {
            str2 = ExifInterface.LONGITUDE_EAST.concat(substring2);
        }
        if (paint.measureText(str2) + 4.0f < this.mergedCellSize.getWidth()) {
            c = 0;
            while (true) {
                if (((float) (((int) paint.measureText(substring + str2)) + 4)) > this.mergedCellSize.getWidth()) {
                    if (substring.length() < 1) {
                        break;
                    }
                    c = substring.charAt(substring.length() - 1);
                    substring = substring.substring(0, substring.length() - 1);
                } else {
                    z = false;
                    break;
                }
            }
        } else {
            c = 0;
        }
        z = true;
        if (z || substring.length() == 0 || substring.equals("-")) {
            String str3 = "";
            while (true) {
                if (paint.measureText(str3 + "#") + 4.0f >= this.mergedCellSize.getWidth()) {
                    return str3;
                }
                str3 = str3 + "#";
            }
        } else if (substring.charAt(substring.length() - 1) == '.') {
            return substring.substring(0, substring.length() - 1) + str2;
        } else {
            if (c <= '9' && c >= '5') {
                substring = ceilNumeric(substring);
            }
            return substring + str2;
        }
    }

    private String ceilNumeric(String str) {
        int indexOf = str.indexOf(46);
        if (indexOf <= 0) {
            return str.length() == 1 ? String.valueOf(((int) Double.parseDouble(str)) + 1) : str;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length - 1;
        while (length > indexOf && charArray[length] == '9') {
            length--;
        }
        if (length <= indexOf) {
            return String.valueOf(((int) Double.parseDouble(str)) + 1);
        }
        charArray[length] = (char) (charArray[length] + 1);
        return String.valueOf(charArray, 0, length + 1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x017a  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01b6 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getNonScientificGeneralContents(java.lang.String r14, android.graphics.Paint r15) {
        /*
            r13 = this;
            float r0 = r15.measureText(r14)
            int r0 = (int) r0
            int r0 = r0 + 4
            float r0 = (float) r0
            com.app.office.ss.other.MergeCell r1 = r13.mergedCellSize
            float r1 = r1.getWidth()
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L_0x01e7
            int r0 = r14.length()
            java.lang.String r1 = ""
            r2 = 1
            if (r0 != r2) goto L_0x001c
            return r1
        L_0x001c:
            double r3 = java.lang.Double.parseDouble(r14)
            int r0 = (int) r3
            r5 = 53
            r6 = 57
            r7 = 46
            r8 = 0
            if (r0 == 0) goto L_0x008a
            java.lang.String r0 = java.lang.String.valueOf(r0)
            float r0 = r15.measureText(r0)
            int r0 = (int) r0
            int r0 = r0 + 4
            float r0 = (float) r0
            com.app.office.ss.other.MergeCell r9 = r13.mergedCellSize
            float r9 = r9.getWidth()
            int r0 = (r0 > r9 ? 1 : (r0 == r9 ? 0 : -1))
            if (r0 <= 0) goto L_0x0041
            goto L_0x008a
        L_0x0041:
            r0 = 0
        L_0x0042:
            float r1 = r15.measureText(r14)
            int r1 = (int) r1
            int r1 = r1 + 4
            float r1 = (float) r1
            com.app.office.ss.other.MergeCell r3 = r13.mergedCellSize
            float r3 = r3.getWidth()
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 <= 0) goto L_0x0067
            int r0 = r14.length()
            int r0 = r0 - r2
            char r0 = r14.charAt(r0)
            int r1 = r14.length()
            int r1 = r1 - r2
            java.lang.String r14 = r14.substring(r8, r1)
            goto L_0x0042
        L_0x0067:
            int r15 = r14.length()
            int r15 = r15 - r2
            char r15 = r14.charAt(r15)
            if (r15 != r7) goto L_0x007c
            int r15 = r14.length()
            int r15 = r15 - r2
            java.lang.String r14 = r14.substring(r8, r15)
            goto L_0x0084
        L_0x007c:
            if (r0 > r6) goto L_0x0084
            if (r0 < r5) goto L_0x0084
            java.lang.String r14 = r13.ceilNumeric(r14)
        L_0x0084:
            java.lang.String r14 = r13.trimInvalidateZero(r14)
            goto L_0x01e7
        L_0x008a:
            r14 = 0
        L_0x008b:
            r0 = 10
            double r9 = (double) r0
            double r9 = r3 / r9
            double r11 = java.lang.Math.abs(r9)
            int r11 = (int) r11
            if (r11 <= 0) goto L_0x009b
            int r14 = r14 + 1
            r3 = r9
            goto L_0x008b
        L_0x009b:
            if (r14 <= 0) goto L_0x00d2
            java.lang.String r9 = "E+"
            if (r14 >= r0) goto L_0x00ba
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r9)
            java.lang.String r9 = "0"
            r0.append(r9)
            java.lang.String r14 = java.lang.String.valueOf(r14)
            r0.append(r14)
            java.lang.String r14 = r0.toString()
            goto L_0x00cd
        L_0x00ba:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r9)
            java.lang.String r14 = java.lang.String.valueOf(r14)
            r0.append(r14)
            java.lang.String r14 = r0.toString()
        L_0x00cd:
            java.lang.String r0 = java.lang.String.valueOf(r3)
            goto L_0x0119
        L_0x00d2:
            java.lang.String r14 = java.lang.String.valueOf(r3)
            r0 = 69
            int r0 = r14.indexOf(r0)
            if (r0 <= 0) goto L_0x00e8
            java.lang.String r3 = r14.substring(r8, r0)
            java.lang.String r14 = r14.substring(r0)
            r0 = r3
            goto L_0x0119
        L_0x00e8:
            r14 = 0
        L_0x00e9:
            double r9 = java.lang.Math.abs(r3)
            r11 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            int r0 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r0 >= 0) goto L_0x010b
            r9 = 4746794007244308480(0x41dfffffffc00000, double:2.147483647E9)
            double r9 = r9 * r3
            double r9 = java.lang.Math.abs(r9)
            r11 = 0
            int r0 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r0 <= 0) goto L_0x010b
            r9 = 4621819117588971520(0x4024000000000000, double:10.0)
            double r3 = r3 * r9
            int r14 = r14 + 1
            goto L_0x00e9
        L_0x010b:
            java.lang.String r0 = java.lang.String.valueOf(r3)
            java.lang.String r14 = java.lang.String.valueOf(r14)
            java.lang.String r3 = "E-"
            java.lang.String r14 = r3.concat(r14)
        L_0x0119:
            float r3 = r15.measureText(r14)
            r4 = 1082130432(0x40800000, float:4.0)
            float r3 = r3 + r4
            com.app.office.ss.other.MergeCell r9 = r13.mergedCellSize
            float r9 = r9.getWidth()
            int r3 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r3 < 0) goto L_0x012d
            r3 = 0
        L_0x012b:
            r9 = 1
            goto L_0x0169
        L_0x012d:
            r3 = 0
        L_0x012e:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r0)
            r9.append(r14)
            java.lang.String r9 = r9.toString()
            float r9 = r15.measureText(r9)
            int r9 = (int) r9
            int r9 = r9 + 4
            float r9 = (float) r9
            com.app.office.ss.other.MergeCell r10 = r13.mergedCellSize
            float r10 = r10.getWidth()
            int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r9 <= 0) goto L_0x0168
            int r9 = r0.length()
            if (r9 < r2) goto L_0x012b
            int r3 = r0.length()
            int r3 = r3 - r2
            char r3 = r0.charAt(r3)
            int r9 = r0.length()
            int r9 = r9 - r2
            java.lang.String r0 = r0.substring(r8, r9)
            goto L_0x012e
        L_0x0168:
            r9 = 0
        L_0x0169:
            if (r9 != 0) goto L_0x01b6
            int r9 = r0.length()
            if (r9 == 0) goto L_0x01b6
            java.lang.String r9 = "-"
            boolean r9 = r0.equals(r9)
            if (r9 == 0) goto L_0x017a
            goto L_0x01b6
        L_0x017a:
            int r15 = r0.length()
            int r15 = r15 - r2
            char r15 = r0.charAt(r15)
            if (r15 != r7) goto L_0x019e
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            int r1 = r0.length()
            int r1 = r1 - r2
            java.lang.String r0 = r0.substring(r8, r1)
            r15.append(r0)
            r15.append(r14)
            java.lang.String r14 = r15.toString()
            goto L_0x01e7
        L_0x019e:
            if (r3 > r6) goto L_0x01a6
            if (r3 < r5) goto L_0x01a6
            java.lang.String r0 = r13.ceilNumeric(r0)
        L_0x01a6:
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r0)
            r15.append(r14)
            java.lang.String r14 = r15.toString()
            goto L_0x01e7
        L_0x01b6:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r1)
            java.lang.String r0 = "#"
            r14.append(r0)
            java.lang.String r14 = r14.toString()
            float r14 = r15.measureText(r14)
            float r14 = r14 + r4
            com.app.office.ss.other.MergeCell r2 = r13.mergedCellSize
            float r2 = r2.getWidth()
            int r14 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1))
            if (r14 >= 0) goto L_0x01e6
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r1)
            r14.append(r0)
            java.lang.String r1 = r14.toString()
            goto L_0x01b6
        L_0x01e6:
            r14 = r1
        L_0x01e7:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.view.CellView.getNonScientificGeneralContents(java.lang.String, android.graphics.Paint):java.lang.String");
    }

    private String trimInvalidateZero(String str) {
        int indexOf;
        if (str == null || str.length() == 0 || (indexOf = str.indexOf(46)) <= 0) {
            return str;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length - 1;
        while (length > indexOf && charArray[indexOf] == '0') {
            indexOf--;
        }
        char c = charArray[length];
        return String.valueOf(charArray, 0, length);
    }

    private String adjustGeneralCellContent(String str, Paint paint) {
        String scientificToNormal = scientificToNormal(str);
        if (scientificToNormal.indexOf(69) > -1) {
            return getScientificGeneralContents(scientificToNormal, paint);
        }
        return getNonScientificGeneralContents(scientificToNormal, paint);
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    private boolean isNumeric(String str) {
        return isInteger(str) || isDouble(str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x002e, code lost:
        if (r2 != 3) goto L_0x0053;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawGeneralCell(android.graphics.Canvas r7, com.app.office.ss.model.baseModel.Cell r8, java.lang.String r9, android.graphics.Paint r10) {
        /*
            r6 = this;
            java.lang.String r9 = r6.adjustGeneralCellContent(r9, r10)
            android.graphics.Paint$FontMetrics r0 = r10.getFontMetrics()
            float r1 = r0.descent
            float r2 = r0.ascent
            float r1 = r1 - r2
            double r1 = (double) r1
            double r1 = java.lang.Math.ceil(r1)
            int r1 = (int) r1
            r7.save()
            android.graphics.RectF r2 = r6.cellRect
            r7.clipRect(r2)
            com.app.office.ss.model.style.CellStyle r2 = r8.getCellStyle()
            short r2 = r2.getVerticalAlign()
            r3 = 1073741824(0x40000000, float:2.0)
            if (r2 == 0) goto L_0x004e
            r4 = 1
            if (r2 == r4) goto L_0x003f
            r4 = 2
            if (r2 == r4) goto L_0x0031
            r4 = 3
            if (r2 == r4) goto L_0x003f
            goto L_0x0053
        L_0x0031:
            float r2 = r6.top
            com.app.office.ss.other.MergeCell r4 = r6.mergedCellSize
            float r4 = r4.getHeight()
            float r1 = (float) r1
            float r4 = r4 - r1
            float r2 = r2 + r4
            r6.top = r2
            goto L_0x0053
        L_0x003f:
            float r2 = r6.top
            com.app.office.ss.other.MergeCell r4 = r6.mergedCellSize
            float r4 = r4.getHeight()
            float r1 = (float) r1
            float r4 = r4 - r1
            float r4 = r4 / r3
            float r2 = r2 + r4
            r6.top = r2
            goto L_0x0053
        L_0x004e:
            float r1 = r6.top
            float r1 = r1 + r3
            r6.top = r1
        L_0x0053:
            com.app.office.ss.model.style.CellStyle r8 = r8.getCellStyle()
            com.app.office.ss.view.SheetView r1 = r6.sheetView
            short r2 = r8.getIndent()
            int r1 = r1.getIndentWidthWithZoom(r2)
            int r2 = r1 + 4
            float r2 = (float) r2
            com.app.office.ss.other.MergeCell r4 = r6.mergedCellSize
            float r4 = r4.getWidth()
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 < 0) goto L_0x0078
            boolean r2 = r6.isNumeric(r9)
            if (r2 != 0) goto L_0x0078
            r7.restore()
            return
        L_0x0078:
            float r2 = r10.measureText(r9)
            int r2 = (int) r2
            int r4 = r2 + r1
            int r4 = r4 + 4
            float r4 = (float) r4
            com.app.office.ss.other.MergeCell r5 = r6.mergedCellSize
            float r5 = r5.getWidth()
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 <= 0) goto L_0x00c2
            float r8 = r6.left
            float r8 = r8 + r3
            r6.left = r8
            com.app.office.ss.other.MergeCell r8 = r6.mergedCellSize
            float r8 = r8.getWidth()
            r9 = 1082130432(0x40800000, float:4.0)
            float r8 = r8 - r9
            java.lang.String r9 = "#"
            float r9 = r10.measureText(r9)
            float r8 = r8 / r9
            int r8 = (int) r8
            java.lang.StringBuilder r9 = r6.strBuilder
            int r1 = r9.length()
            r2 = 0
            r9.delete(r2, r1)
        L_0x00ac:
            if (r2 >= r8) goto L_0x00b8
            java.lang.StringBuilder r9 = r6.strBuilder
            r1 = 35
            r9.append(r1)
            int r2 = r2 + 1
            goto L_0x00ac
        L_0x00b8:
            java.lang.StringBuilder r8 = r6.strBuilder
            java.lang.String r9 = r8.toString()
            r10.measureText(r9)
            goto L_0x010b
        L_0x00c2:
            short r8 = r8.getHorizontalAlign()
            switch(r8) {
                case 0: goto L_0x00ed;
                case 1: goto L_0x00d9;
                case 2: goto L_0x00ca;
                case 3: goto L_0x00ed;
                case 4: goto L_0x00d9;
                case 5: goto L_0x00d9;
                case 6: goto L_0x00d9;
                default: goto L_0x00c9;
            }
        L_0x00c9:
            goto L_0x010b
        L_0x00ca:
            float r8 = r6.left
            com.app.office.ss.other.MergeCell r1 = r6.mergedCellSize
            float r1 = r1.getWidth()
            float r2 = (float) r2
            float r1 = r1 - r2
            float r1 = r1 / r3
            float r8 = r8 + r1
            r6.left = r8
            goto L_0x010b
        L_0x00d9:
            float r8 = r6.left
            int r2 = r1 + 2
            float r2 = (float) r2
            float r8 = r8 + r2
            r6.left = r8
            com.app.office.ss.other.MergeCell r8 = r6.mergedCellSize
            float r2 = r8.getWidth()
            float r1 = (float) r1
            float r2 = r2 - r1
            r8.setWidth(r2)
            goto L_0x010b
        L_0x00ed:
            com.app.office.ss.other.MergeCell r8 = r6.mergedCellSize
            float r2 = r8.getWidth()
            float r1 = (float) r1
            float r2 = r2 - r1
            r8.setWidth(r2)
            float r8 = r6.left
            com.app.office.ss.other.MergeCell r1 = r6.mergedCellSize
            float r1 = r1.getWidth()
            float r2 = r10.measureText(r9)
            int r2 = (int) r2
            float r2 = (float) r2
            float r1 = r1 - r2
            float r1 = r1 - r3
            float r8 = r8 + r1
            r6.left = r8
        L_0x010b:
            float r8 = r6.left
            com.app.office.ss.other.MergeCell r1 = r6.mergedCellSize
            float r1 = r1.getNovisibleWidth()
            float r8 = r8 - r1
            com.app.office.ss.other.DrawingCell r1 = r6.cellInfor
            float r1 = r1.getWidth()
            com.app.office.ss.other.DrawingCell r2 = r6.cellInfor
            float r2 = r2.getVisibleWidth()
            float r1 = r1 - r2
            float r8 = r8 - r1
            float r1 = r6.top
            float r0 = r0.ascent
            float r1 = r1 - r0
            com.app.office.ss.other.MergeCell r0 = r6.mergedCellSize
            float r0 = r0.getNoVisibleHeight()
            float r1 = r1 - r0
            com.app.office.ss.other.DrawingCell r0 = r6.cellInfor
            float r0 = r0.getHeight()
            com.app.office.ss.other.DrawingCell r2 = r6.cellInfor
            float r2 = r2.getVisibleHeight()
            float r0 = r0 - r2
            float r1 = r1 - r0
            r7.drawText(r9, r8, r1, r10)
            r7.restore()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.view.CellView.drawGeneralCell(android.graphics.Canvas, com.app.office.ss.model.baseModel.Cell, java.lang.String, android.graphics.Paint):void");
    }

    private String scientificToNormal(String str) {
        int indexOf = str.indexOf(69);
        if (indexOf < 0) {
            return str;
        }
        String substring = str.substring(0, indexOf);
        int parseInt = Integer.parseInt(str.substring(indexOf + 1));
        boolean z = Double.parseDouble(substring) < 0.0d;
        if (Math.abs(parseInt) > 10) {
            return str;
        }
        if (parseInt < 0) {
            if (substring.charAt(substring.length() - 1) == '0') {
                substring = substring.substring(0, substring.length() - 1);
            }
            String replace = substring.replace(".", "");
            StringBuilder sb = this.strBuilder;
            sb.delete(0, sb.length());
            while (true) {
                parseInt++;
                if (parseInt >= 0) {
                    break;
                }
                this.strBuilder.append("0");
            }
            if (!z) {
                return "0." + this.strBuilder.toString() + replace;
            }
            return "-0." + this.strBuilder.toString() + replace.replace("-", "");
        } else if (parseInt > 10) {
            return substring;
        } else {
            int indexOf2 = substring.indexOf(46);
            int length = substring.length() - 2;
            if (z) {
                length = substring.length() - 3;
            }
            if (length <= parseInt) {
                String replace2 = substring.replace(".", "");
                for (int i = parseInt - length; i > 0; i--) {
                    replace2 = replace2 + "0";
                }
                return replace2;
            }
            char[] charArray = substring.toCharArray();
            int i2 = parseInt + indexOf2;
            while (indexOf2 < i2) {
                int i3 = indexOf2 + 1;
                charArray[indexOf2] = charArray[i3];
                indexOf2 = i3;
            }
            charArray[indexOf2] = '.';
            return String.valueOf(charArray);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0061, code lost:
        if (r4 != 3) goto L_0x0086;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawNumericCell(android.graphics.Canvas r10, com.app.office.ss.model.baseModel.Cell r11, java.lang.String r12, android.graphics.Paint r13) {
        /*
            r9 = this;
            int r0 = r13.getColor()
            int r1 = r12.length()
            if (r1 <= 0) goto L_0x001b
            double r1 = r11.getNumberValue()
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 >= 0) goto L_0x001b
            int r1 = com.app.office.ss.util.format.NumericFormatter.getNegativeColor(r11)
            r13.setColor(r1)
        L_0x001b:
            short r1 = r11.getCellNumericType()
            r2 = 8
            if (r1 != r2) goto L_0x0027
            r9.drawAccountCell(r10, r11, r12, r13)
            return
        L_0x0027:
            short r1 = r11.getCellNumericType()
            r2 = 6
            if (r1 != r2) goto L_0x0032
            r9.drawGeneralCell(r10, r11, r12, r13)
            return
        L_0x0032:
            android.graphics.Paint$FontMetrics r1 = r13.getFontMetrics()
            float r2 = r1.descent
            float r3 = r1.ascent
            float r2 = r2 - r3
            double r2 = (double) r2
            double r2 = java.lang.Math.ceil(r2)
            int r2 = (int) r2
            float r3 = r13.measureText(r12)
            int r3 = (int) r3
            r10.save()
            android.graphics.RectF r4 = r9.cellRect
            r10.clipRect(r4)
            com.app.office.ss.model.style.CellStyle r4 = r11.getCellStyle()
            short r4 = r4.getVerticalAlign()
            r5 = 2
            r6 = 1073741824(0x40000000, float:2.0)
            if (r4 == 0) goto L_0x0081
            r7 = 1
            if (r4 == r7) goto L_0x0072
            if (r4 == r5) goto L_0x0064
            r7 = 3
            if (r4 == r7) goto L_0x0072
            goto L_0x0086
        L_0x0064:
            float r4 = r9.top
            com.app.office.ss.other.MergeCell r7 = r9.mergedCellSize
            float r7 = r7.getHeight()
            float r2 = (float) r2
            float r7 = r7 - r2
            float r4 = r4 + r7
            r9.top = r4
            goto L_0x0086
        L_0x0072:
            float r4 = r9.top
            com.app.office.ss.other.MergeCell r7 = r9.mergedCellSize
            float r7 = r7.getHeight()
            float r2 = (float) r2
            float r7 = r7 - r2
            float r7 = r7 / r6
            float r4 = r4 + r7
            r9.top = r4
            goto L_0x0086
        L_0x0081:
            float r2 = r9.top
            float r2 = r2 + r6
            r9.top = r2
        L_0x0086:
            com.app.office.ss.view.SheetView r2 = r9.sheetView
            com.app.office.ss.model.style.CellStyle r4 = r11.getCellStyle()
            short r4 = r4.getIndent()
            int r2 = r2.getIndentWidthWithZoom(r4)
            int r4 = r3 + r2
            r7 = 4
            int r4 = r4 + r7
            float r4 = (float) r4
            com.app.office.ss.other.MergeCell r8 = r9.mergedCellSize
            float r8 = r8.getWidth()
            int r4 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r4 <= 0) goto L_0x00d9
            com.app.office.ss.other.MergeCell r11 = r9.mergedCellSize
            float r11 = r11.getWidth()
            r12 = 1082130432(0x40800000, float:4.0)
            float r11 = r11 - r12
            java.lang.String r12 = "#"
            float r12 = r13.measureText(r12)
            float r11 = r11 / r12
            int r11 = (int) r11
            java.lang.StringBuilder r12 = r9.strBuilder
            int r2 = r12.length()
            r3 = 0
            r12.delete(r3, r2)
        L_0x00be:
            if (r3 >= r11) goto L_0x00ca
            java.lang.StringBuilder r12 = r9.strBuilder
            r2 = 35
            r12.append(r2)
            int r3 = r3 + 1
            goto L_0x00be
        L_0x00ca:
            java.lang.StringBuilder r11 = r9.strBuilder
            java.lang.String r12 = r11.toString()
            r13.measureText(r12)
            float r11 = r9.left
            float r11 = r11 + r6
            r9.left = r11
            goto L_0x012b
        L_0x00d9:
            com.app.office.ss.model.style.CellStyle r4 = r11.getCellStyle()
            short r4 = r4.getHorizontalAlign()
            short r11 = r11.getCellType()
            if (r11 != r7) goto L_0x00ea
            if (r4 != 0) goto L_0x00ea
            goto L_0x00eb
        L_0x00ea:
            r5 = r4
        L_0x00eb:
            switch(r5) {
                case 0: goto L_0x0112;
                case 1: goto L_0x00fe;
                case 2: goto L_0x00ef;
                case 3: goto L_0x0112;
                case 4: goto L_0x00fe;
                case 5: goto L_0x00fe;
                case 6: goto L_0x00fe;
                default: goto L_0x00ee;
            }
        L_0x00ee:
            goto L_0x012b
        L_0x00ef:
            float r11 = r9.left
            com.app.office.ss.other.MergeCell r2 = r9.mergedCellSize
            float r2 = r2.getWidth()
            float r3 = (float) r3
            float r2 = r2 - r3
            float r2 = r2 / r6
            float r11 = r11 + r2
            r9.left = r11
            goto L_0x012b
        L_0x00fe:
            float r11 = r9.left
            int r3 = r2 + 2
            float r3 = (float) r3
            float r11 = r11 + r3
            r9.left = r11
            com.app.office.ss.other.MergeCell r11 = r9.mergedCellSize
            float r3 = r11.getWidth()
            float r2 = (float) r2
            float r3 = r3 - r2
            r11.setWidth(r3)
            goto L_0x012b
        L_0x0112:
            com.app.office.ss.other.MergeCell r11 = r9.mergedCellSize
            float r4 = r11.getWidth()
            float r2 = (float) r2
            float r4 = r4 - r2
            r11.setWidth(r4)
            float r11 = r9.left
            com.app.office.ss.other.MergeCell r2 = r9.mergedCellSize
            float r2 = r2.getWidth()
            float r3 = (float) r3
            float r2 = r2 - r3
            float r2 = r2 - r6
            float r11 = r11 + r2
            r9.left = r11
        L_0x012b:
            float r11 = r9.left
            com.app.office.ss.other.MergeCell r2 = r9.mergedCellSize
            float r2 = r2.getNovisibleWidth()
            float r11 = r11 - r2
            com.app.office.ss.other.DrawingCell r2 = r9.cellInfor
            float r2 = r2.getWidth()
            com.app.office.ss.other.DrawingCell r3 = r9.cellInfor
            float r3 = r3.getVisibleWidth()
            float r2 = r2 - r3
            float r11 = r11 - r2
            float r2 = r9.top
            float r3 = r1.ascent
            float r2 = r2 - r3
            com.app.office.ss.other.MergeCell r3 = r9.mergedCellSize
            float r3 = r3.getNoVisibleHeight()
            float r2 = r2 - r3
            com.app.office.ss.other.DrawingCell r3 = r9.cellInfor
            float r3 = r3.getHeight()
            com.app.office.ss.other.DrawingCell r4 = r9.cellInfor
            float r4 = r4.getVisibleHeight()
            float r3 = r3 - r4
            float r2 = r2 - r3
            com.app.office.ss.other.MergeCell r3 = r9.mergedCellSize
            boolean r3 = r3.isFrozenColumn()
            if (r3 == 0) goto L_0x0174
            float r11 = r9.left
            com.app.office.ss.other.DrawingCell r3 = r9.cellInfor
            float r3 = r3.getWidth()
            com.app.office.ss.other.DrawingCell r4 = r9.cellInfor
            float r4 = r4.getVisibleWidth()
            float r3 = r3 - r4
            float r11 = r11 - r3
        L_0x0174:
            com.app.office.ss.other.MergeCell r3 = r9.mergedCellSize
            boolean r3 = r3.isFrozenRow()
            if (r3 == 0) goto L_0x018f
            float r2 = r9.top
            float r1 = r1.ascent
            float r2 = r2 - r1
            com.app.office.ss.other.DrawingCell r1 = r9.cellInfor
            float r1 = r1.getHeight()
            com.app.office.ss.other.DrawingCell r3 = r9.cellInfor
            float r3 = r3.getVisibleHeight()
            float r1 = r1 - r3
            float r2 = r2 - r1
        L_0x018f:
            r10.drawText(r12, r11, r2, r13)
            r10.restore()
            r13.setColor(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.view.CellView.drawNumericCell(android.graphics.Canvas, com.app.office.ss.model.baseModel.Cell, java.lang.String, android.graphics.Paint):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000e, code lost:
        if (r5 != 3) goto L_0x0010;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initPageProp(com.app.office.simpletext.model.IAttributeSet r4, com.app.office.ss.model.style.CellStyle r5, int r6, int r7) {
        /*
            r3 = this;
            short r5 = r5.getVerticalAlign()
            r0 = 2
            r1 = 1
            r2 = 0
            if (r5 == 0) goto L_0x0010
            if (r5 == r1) goto L_0x0012
            if (r5 == r0) goto L_0x0013
            r0 = 3
            if (r5 == r0) goto L_0x0012
        L_0x0010:
            r0 = 0
            goto L_0x0013
        L_0x0012:
            r0 = 1
        L_0x0013:
            com.app.office.simpletext.model.AttrManage r5 = com.app.office.simpletext.model.AttrManage.instance()
            r5.setPageVerticalAlign(r4, r0)
            com.app.office.simpletext.model.AttrManage r5 = com.app.office.simpletext.model.AttrManage.instance()
            float r6 = (float) r6
            r0 = 1097859072(0x41700000, float:15.0)
            float r6 = r6 * r0
            int r6 = java.lang.Math.round(r6)
            r5.setPageWidth(r4, r6)
            com.app.office.simpletext.model.AttrManage r5 = com.app.office.simpletext.model.AttrManage.instance()
            float r6 = (float) r7
            float r6 = r6 * r0
            int r6 = java.lang.Math.round(r6)
            r5.setPageHeight(r4, r6)
            com.app.office.simpletext.model.AttrManage r5 = com.app.office.simpletext.model.AttrManage.instance()
            r6 = 1106247680(0x41f00000, float:30.0)
            int r7 = java.lang.Math.round(r6)
            r5.setPageMarginLeft(r4, r7)
            com.app.office.simpletext.model.AttrManage r5 = com.app.office.simpletext.model.AttrManage.instance()
            int r6 = java.lang.Math.round(r6)
            r5.setPageMarginRight(r4, r6)
            com.app.office.simpletext.model.AttrManage r5 = com.app.office.simpletext.model.AttrManage.instance()
            r5.setPageMarginTop(r4, r2)
            com.app.office.simpletext.model.AttrManage r5 = com.app.office.simpletext.model.AttrManage.instance()
            r5.setPageMarginBottom(r4, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.view.CellView.initPageProp(com.app.office.simpletext.model.IAttributeSet, com.app.office.ss.model.style.CellStyle, int, int):void");
    }

    private SectionElement convertToSectionElement(Cell cell, String str, int i, int i2) {
        SectionElement sectionElement = new SectionElement();
        sectionElement.setStartOffset(0);
        initPageProp(sectionElement.getAttribute(), cell.getCellStyle(), i, i2);
        sectionElement.setEndOffset((long) processParagraph(sectionElement, cell, str));
        return sectionElement;
    }

    private int processParagraph(SectionElement sectionElement, Cell cell, String str) {
        int i = 0;
        for (String processRun : str.split("\n")) {
            ParagraphElement paragraphElement = new ParagraphElement();
            paragraphElement.setStartOffset((long) i);
            AttributeSetImpl attributeSetImpl = new AttributeSetImpl();
            ParaAttr.instance().setParaAttribute(cell.getCellStyle(), paragraphElement.getAttribute(), attributeSetImpl);
            i = processRun(paragraphElement, cell, processRun, i, attributeSetImpl);
            paragraphElement.setEndOffset((long) i);
            sectionElement.appendParagraph(paragraphElement, 0);
        }
        return i;
    }

    private int processRun(ParagraphElement paragraphElement, Cell cell, String str, int i, IAttributeSet iAttributeSet) {
        CellLeafElement cellLeafElement;
        if (str.length() == 0) {
            CellLeafElement cellLeafElement2 = new CellLeafElement(cell, 0, 0);
            cellLeafElement2.appendNewlineFlag();
            RunAttr.instance().setRunAttribute(this.sheetView.getCurrentSheet(), cell, cellLeafElement2.getAttribute(), iAttributeSet);
            cellLeafElement2.setStartOffset((long) i);
            int i2 = i + 1;
            cellLeafElement2.setEndOffset((long) i2);
            paragraphElement.appendLeaf(cellLeafElement2);
            return i2;
        }
        int length = str.length();
        if (length > 0) {
            String sharedString = cell.getSheet().getWorkbook().getSharedString(cell.getStringCellValueIndex());
            if (sharedString == null) {
                cellLeafElement = new LeafElement(str);
            } else {
                int indexOf = sharedString.indexOf(str);
                cellLeafElement = new CellLeafElement(cell, indexOf, str.length() + indexOf);
            }
            RunAttr.instance().setRunAttribute(this.sheetView.getCurrentSheet(), cell, cellLeafElement.getAttribute(), iAttributeSet);
            cellLeafElement.setStartOffset((long) i);
            i += length;
            cellLeafElement.setEndOffset((long) i);
            paragraphElement.appendLeaf(cellLeafElement);
        } else {
            cellLeafElement = null;
        }
        if (cellLeafElement == null) {
            return i;
        }
        if (cellLeafElement instanceof CellLeafElement) {
            cellLeafElement.appendNewlineFlag();
            int i3 = i + 1;
            cellLeafElement.setEndOffset((long) i3);
            return i3;
        }
        cellLeafElement.setText(cellLeafElement.getText((IDocument) null) + "\n");
        return i;
    }

    private void drawWrapText(Canvas canvas, Cell cell, String str) {
        STRoot sTRoot = cell.getSTRoot();
        if (sTRoot == null) {
            Rect cellAnchor = ModelUtil.instance().getCellAnchor(this.sheetView.getCurrentSheet(), cell.getRowNumber(), cell.getColNumber());
            SectionElement convertToSectionElement = convertToSectionElement(cell, str, cellAnchor.width(), cellAnchor.height());
            if (convertToSectionElement.getEndOffset() - convertToSectionElement.getStartOffset() != 0) {
                STDocument sTDocument = new STDocument();
                sTDocument.appendSection(convertToSectionElement);
                STRoot sTRoot2 = new STRoot(this.sheetView.getSpreadsheet().getEditor(), sTDocument);
                sTRoot2.setWrapLine(true);
                sTRoot2.doLayout();
                cell.setSTRoot(sTRoot2);
                sTRoot = sTRoot2;
            } else {
                return;
            }
        }
        sTRoot.draw(canvas, Math.round((this.left - this.mergedCellSize.getNovisibleWidth()) - (this.cellInfor.getWidth() - this.cellInfor.getVisibleWidth())), Math.round((this.top - this.mergedCellSize.getNoVisibleHeight()) - (this.cellInfor.getHeight() - this.cellInfor.getVisibleHeight())), this.sheetView.getZoom());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x015a, code lost:
        if (r5 != 3) goto L_0x017f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawNonWrapText(android.graphics.Canvas r22, com.app.office.ss.model.baseModel.Cell r23, java.lang.String r24, android.graphics.Paint r25) {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            r2 = r25
            android.graphics.Paint$FontMetrics r3 = r25.getFontMetrics()
            r4 = r24
            float r5 = r2.measureText(r4)
            int r5 = (int) r5
            float r6 = r3.descent
            float r7 = r3.ascent
            float r6 = r6 - r7
            double r6 = (double) r6
            double r6 = java.lang.Math.ceil(r6)
            int r6 = (int) r6
            com.app.office.ss.model.style.CellStyle r7 = r23.getCellStyle()
            com.app.office.ss.view.SheetView r8 = r0.sheetView
            short r9 = r7.getIndent()
            int r8 = r8.getIndentWidthWithZoom(r9)
            int r9 = r8 + 4
            float r9 = (float) r9
            com.app.office.ss.other.MergeCell r10 = r0.mergedCellSize
            float r10 = r10.getWidth()
            int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r9 < 0) goto L_0x003b
            r22.restore()
            return
        L_0x003b:
            short r9 = r23.getCellNumericType()
            r10 = 10
            r11 = 0
            if (r9 != r10) goto L_0x0086
            int r9 = r5 + r8
            int r9 = r9 + 4
            float r9 = (float) r9
            com.app.office.ss.other.MergeCell r12 = r0.mergedCellSize
            float r12 = r12.getWidth()
            int r9 = (r9 > r12 ? 1 : (r9 == r12 ? 0 : -1))
            if (r9 <= 0) goto L_0x0086
            com.app.office.ss.other.MergeCell r4 = r0.mergedCellSize
            float r4 = r4.getWidth()
            r5 = 1082130432(0x40800000, float:4.0)
            float r4 = r4 - r5
            java.lang.String r5 = "#"
            float r5 = r2.measureText(r5)
            float r4 = r4 / r5
            int r4 = (int) r4
            java.lang.StringBuilder r5 = r0.strBuilder
            int r8 = r5.length()
            r5.delete(r11, r8)
            r5 = 0
        L_0x006e:
            if (r5 >= r4) goto L_0x007a
            java.lang.StringBuilder r8 = r0.strBuilder
            r9 = 35
            r8.append(r9)
            int r5 = r5 + 1
            goto L_0x006e
        L_0x007a:
            java.lang.StringBuilder r4 = r0.strBuilder
            java.lang.String r4 = r4.toString()
            float r5 = r2.measureText(r4)
            int r5 = (int) r5
            r8 = 0
        L_0x0086:
            r22.save()
            android.graphics.RectF r9 = r0.cellRect
            r1.clipRect(r9)
            short r9 = r7.getHorizontalAlign()
            r12 = 3
            if (r9 != 0) goto L_0x009c
            short r13 = r23.getCellNumericType()
            if (r13 != r10) goto L_0x009c
            r9 = 3
        L_0x009c:
            r10 = 2
            r13 = 1
            r14 = 1073741824(0x40000000, float:2.0)
            switch(r9) {
                case 0: goto L_0x010c;
                case 1: goto L_0x010c;
                case 2: goto L_0x00fd;
                case 3: goto L_0x00a5;
                case 4: goto L_0x010c;
                case 5: goto L_0x010c;
                case 6: goto L_0x010c;
                default: goto L_0x00a3;
            }
        L_0x00a3:
            goto L_0x0150
        L_0x00a5:
            com.app.office.ss.other.MergeCell r9 = r0.mergedCellSize
            float r15 = r9.getWidth()
            float r8 = (float) r8
            float r15 = r15 - r8
            r9.setWidth(r15)
            int r8 = r5 + 2
            float r8 = (float) r8
            com.app.office.ss.other.MergeCell r9 = r0.mergedCellSize
            float r9 = r9.getWidth()
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 <= 0) goto L_0x00ee
            int r5 = r4.length()
            float[] r5 = new float[r5]
            int r8 = r4.length()
            r2.getTextWidths(r4, r11, r8, r5)
            int r8 = r4.length()
            r9 = 0
        L_0x00cf:
            com.app.office.ss.other.MergeCell r11 = r0.mergedCellSize
            float r11 = r11.getWidth()
            float r11 = r11 - r14
            int r11 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r11 >= 0) goto L_0x00e0
            int r8 = r8 + -1
            r11 = r5[r8]
            float r9 = r9 + r11
            goto L_0x00cf
        L_0x00e0:
            int r8 = r8 + r13
            int r5 = r4.length()
            java.lang.String r4 = r4.substring(r8, r5)
            float r5 = r2.measureText(r4)
            int r5 = (int) r5
        L_0x00ee:
            float r8 = r0.left
            com.app.office.ss.other.MergeCell r9 = r0.mergedCellSize
            float r9 = r9.getWidth()
            float r5 = (float) r5
            float r9 = r9 - r5
            float r9 = r9 - r14
            float r8 = r8 + r9
            r0.left = r8
            goto L_0x0150
        L_0x00fd:
            float r8 = r0.left
            com.app.office.ss.other.MergeCell r9 = r0.mergedCellSize
            float r9 = r9.getWidth()
            float r5 = (float) r5
            float r9 = r9 - r5
            float r9 = r9 / r14
            float r8 = r8 + r9
            r0.left = r8
            goto L_0x0150
        L_0x010c:
            float r9 = r0.left
            int r15 = r8 + 2
            float r15 = (float) r15
            float r9 = r9 + r15
            r0.left = r9
            com.app.office.ss.other.MergeCell r9 = r0.mergedCellSize
            float r15 = r9.getWidth()
            float r8 = (float) r8
            float r15 = r15 - r8
            r9.setWidth(r15)
            int r5 = r5 + r10
            float r5 = (float) r5
            com.app.office.ss.other.MergeCell r8 = r0.mergedCellSize
            float r8 = r8.getWidth()
            int r5 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r5 <= 0) goto L_0x0150
            int r5 = r4.length()
            float[] r5 = new float[r5]
            int r8 = r4.length()
            r2.getTextWidths(r4, r11, r8, r5)
            r8 = r5[r11]
            r9 = 0
        L_0x013b:
            com.app.office.ss.other.MergeCell r15 = r0.mergedCellSize
            float r15 = r15.getWidth()
            float r15 = r15 - r14
            int r15 = (r8 > r15 ? 1 : (r8 == r15 ? 0 : -1))
            if (r15 >= 0) goto L_0x014c
            int r9 = r9 + 1
            r15 = r5[r9]
            float r8 = r8 + r15
            goto L_0x013b
        L_0x014c:
            java.lang.String r4 = r4.substring(r11, r9)
        L_0x0150:
            short r5 = r7.getVerticalAlign()
            if (r5 == 0) goto L_0x017a
            if (r5 == r13) goto L_0x016b
            if (r5 == r10) goto L_0x015d
            if (r5 == r12) goto L_0x016b
            goto L_0x017f
        L_0x015d:
            float r5 = r0.top
            com.app.office.ss.other.MergeCell r7 = r0.mergedCellSize
            float r7 = r7.getHeight()
            float r6 = (float) r6
            float r7 = r7 - r6
            float r5 = r5 + r7
            r0.top = r5
            goto L_0x017f
        L_0x016b:
            float r5 = r0.top
            com.app.office.ss.other.MergeCell r7 = r0.mergedCellSize
            float r7 = r7.getHeight()
            float r6 = (float) r6
            float r7 = r7 - r6
            float r7 = r7 / r14
            float r5 = r5 + r7
            r0.top = r5
            goto L_0x017f
        L_0x017a:
            float r5 = r0.top
            float r5 = r5 + r14
            r0.top = r5
        L_0x017f:
            float r5 = r0.left
            com.app.office.ss.other.MergeCell r6 = r0.mergedCellSize
            float r6 = r6.getNovisibleWidth()
            float r5 = r5 - r6
            com.app.office.ss.other.DrawingCell r6 = r0.cellInfor
            float r6 = r6.getWidth()
            com.app.office.ss.other.DrawingCell r7 = r0.cellInfor
            float r7 = r7.getVisibleWidth()
            float r6 = r6 - r7
            float r5 = r5 - r6
            float r6 = r0.top
            float r3 = r3.ascent
            float r6 = r6 - r3
            com.app.office.ss.other.MergeCell r3 = r0.mergedCellSize
            float r3 = r3.getNoVisibleHeight()
            float r6 = r6 - r3
            com.app.office.ss.other.DrawingCell r3 = r0.cellInfor
            float r3 = r3.getHeight()
            com.app.office.ss.other.DrawingCell r7 = r0.cellInfor
            float r7 = r7.getVisibleHeight()
            float r3 = r3 - r7
            float r3 = r6 - r3
            int r6 = r23.getExpandedRangeAddressIndex()
            if (r6 < 0) goto L_0x01ce
            com.app.office.ss.view.SheetView r15 = r0.sheetView
            android.graphics.RectF r2 = new android.graphics.RectF
            android.graphics.RectF r6 = r0.cellRect
            r2.<init>(r6)
            r16 = r23
            r17 = r2
            r18 = r5
            r19 = r3
            r20 = r4
            r15.addExtendCell(r16, r17, r18, r19, r20)
            goto L_0x01d1
        L_0x01ce:
            r1.drawText(r4, r5, r3, r2)
        L_0x01d1:
            r22.restore()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.view.CellView.drawNonWrapText(android.graphics.Canvas, com.app.office.ss.model.baseModel.Cell, java.lang.String, android.graphics.Paint):void");
    }

    public void dispose() {
        this.sheetView = null;
        CellBorderView cellBorderView2 = this.cellBorderView;
        if (cellBorderView2 != null) {
            cellBorderView2.dispose();
            this.cellBorderView = null;
        }
        this.cellRect = null;
        MergeCell mergeCell = this.mergedCellSize;
        if (mergeCell != null) {
            mergeCell.dispose();
            this.mergedCellSize = null;
        }
        MergeCellMgr mergeCellMgr = this.mergedCellMgr;
        if (mergeCellMgr != null) {
            mergeCellMgr.dispose();
            this.mergedCellMgr = null;
        }
        this.cellInfor = null;
        this.strBuilder = null;
        TableStyleKit tableStyleKit2 = this.tableStyleKit;
        if (tableStyleKit2 != null) {
            tableStyleKit2.dispose();
            this.tableStyleKit = null;
        }
    }
}
