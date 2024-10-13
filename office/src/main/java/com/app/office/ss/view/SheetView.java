package com.app.office.ss.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import com.app.office.common.PaintKit;
import com.app.office.common.picture.PictureKit;
import com.app.office.constant.EventConstant;
import com.app.office.simpletext.font.FontKit;
import com.app.office.simpletext.view.STRoot;
import com.app.office.ss.control.Spreadsheet;
import com.app.office.ss.model.CellRangeAddress;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Row;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.sheetProperty.ColumnInfo;
import com.app.office.ss.model.table.SSTable;
import com.app.office.ss.model.table.SSTableCellStyle;
import com.app.office.ss.other.DrawingCell;
import com.app.office.ss.other.FindingMgr;
import com.app.office.ss.other.FocusCell;
import com.app.office.ss.other.SheetScroller;
import com.app.office.ss.util.ModelUtil;
import java.util.ArrayList;
import java.util.List;

public class SheetView {
    public static final int MAXCOLUMN_03 = 256;
    public static final int MAXCOLUMN_07 = 16384;
    public static final int MAXROW_03 = 65536;
    public static final int MAXROW_07 = 1048576;
    private DrawingCell cellInfor;
    private CellView cellView = null;
    private Rect clipRect;
    private ColumnHeader columnHeader;
    private PathEffect effects = new DashPathEffect(new float[]{5.0f, 5.0f, 5.0f, 5.0f}, 1.0f);
    private List<ExtendCell> extendCell = new ArrayList();
    private FindingMgr findingMgr;
    private boolean isDrawMovingHeaderLine;
    private RowHeader rowHeader;
    private float scrollX;
    private float scrollY;
    private CellRangeAddress selecetedCellsRange;
    private FocusCell selectedHeaderInfor;
    private ShapeView shapeView;
    private Sheet sheet;
    private SheetScroller sheetScroller = new SheetScroller();
    /* access modifiers changed from: private */
    public Spreadsheet spreadsheet;
    private TableFormatView tableFormatView;
    private float zoom = 1.0f;

    public SheetView(Spreadsheet spreadsheet2, Sheet sheet2) {
        this.spreadsheet = spreadsheet2;
        this.sheet = sheet2;
        this.rowHeader = new RowHeader(this);
        this.columnHeader = new ColumnHeader(this);
        this.shapeView = new ShapeView(this);
        this.tableFormatView = new TableFormatView(this);
        this.cellView = new CellView(this);
        this.selecetedCellsRange = new CellRangeAddress(0, 0, 0, 0);
        this.cellInfor = new DrawingCell();
        initForDrawing();
    }

    private void initForDrawing() {
        this.scrollX = (float) this.sheet.getScrollX();
        this.scrollY = (float) this.sheet.getScrollY();
        this.sheetScroller.update(this.sheet, Math.round(this.scrollX), Math.round(this.scrollY));
        setZoom(this.sheet.getZoom(), true);
        selectedCell(this.sheet.getActiveCellRow(), this.sheet.getActiveCellColumn());
        this.spreadsheet.getControl().actionEvent(EventConstant.APP_CONTENT_SELECTED, this.cellInfor);
    }

    private void resizeCalloutView() {
        if (this.spreadsheet.getCalloutView() != null) {
            this.spreadsheet.getCalloutView().setZoom(this.zoom);
            float f = this.scrollX;
            float f2 = this.zoom;
            int i = (int) (f * f2);
            int i2 = (int) (this.scrollY * f2);
            this.spreadsheet.getCalloutView().layout(getRowHeaderWidth() - i, getColumnHeaderHeight() - i2, this.spreadsheet.getCalloutView().getRight(), this.spreadsheet.getCalloutView().getBottom());
            this.spreadsheet.getCalloutView().setClip(i, i2);
        }
    }

    public void changeSheet(Sheet sheet2) {
        synchronized (this) {
            this.sheet.removeSTRoot();
            this.sheet = sheet2;
            initForDrawing();
            resizeCalloutView();
            this.spreadsheet.post(new Runnable() {
                public void run() {
                    SheetView.this.spreadsheet.getControl().actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
                }
            });
        }
    }

    public Bitmap getThumbnail(Sheet sheet2, int i, int i2, float f) {
        synchronized (this) {
            Bitmap createBitmap = Bitmap.createBitmap((int) (((float) i) * f), (int) (((float) i2) * f), Bitmap.Config.ARGB_8888);
            if (createBitmap == null) {
                return null;
            }
            boolean isDrawPictrue = PictureKit.instance().isDrawPictrue();
            PictureKit.instance().setDrawPictrue(true);
            Canvas canvas = new Canvas(createBitmap);
            canvas.drawColor(-1);
            int scrollX2 = sheet2.getScrollX();
            int scrollY2 = sheet2.getScrollY();
            float zoom2 = sheet2.getZoom();
            Sheet sheet3 = this.sheet;
            this.sheet = sheet2;
            this.scrollX = 0.0f;
            this.scrollY = 0.0f;
            sheet2.setScroll(0, 0);
            setZoom(f, true);
            this.sheetScroller.update(sheet2, Math.round(this.scrollX), Math.round(this.scrollY));
            drawThumbnail(canvas);
            sheet2.setScroll(scrollX2, scrollY2);
            sheet2.setZoom(zoom2);
            this.sheet = sheet3;
            this.scrollX = (float) sheet3.getScrollX();
            this.scrollY = (float) sheet3.getScrollY();
            setZoom(sheet3.getZoom(), true);
            this.sheetScroller.update(sheet2, Math.round(this.scrollX), Math.round(this.scrollY));
            PictureKit.instance().setDrawPictrue(isDrawPictrue);
            return createBitmap;
        }
    }

    private void drawThumbnail(Canvas canvas) {
        this.spreadsheet.startDrawing();
        this.clipRect = canvas.getClipBounds();
        int columnRightBound = this.columnHeader.getColumnRightBound(canvas, this.zoom);
        int rowBottomBound = this.rowHeader.getRowBottomBound(canvas, this.zoom);
        int i = this.clipRect.right + 10;
        if (columnRightBound >= this.clipRect.right) {
            columnRightBound = i;
        }
        int i2 = this.clipRect.bottom + 50;
        if (rowBottomBound >= this.clipRect.bottom) {
            rowBottomBound = i2;
        }
        this.rowHeader.draw(canvas, columnRightBound, this.zoom);
        this.columnHeader.draw(canvas, rowBottomBound, this.zoom);
        canvas.save();
        canvas.clipRect((float) this.rowHeader.getRowHeaderWidth(), (float) this.columnHeader.getColumnHeaderHeight(), (float) columnRightBound, (float) rowBottomBound);
        drawRows(canvas);
        this.tableFormatView.draw(canvas);
        this.shapeView.draw(canvas);
        canvas.restore();
    }

    public int getMaxScrollY() {
        return Math.round(this.sheet.getMaxScrollY() * this.zoom);
    }

    public int getMaxScrollX() {
        return Math.round(this.sheet.getMaxScrollX() * this.zoom);
    }

    public void drawSheet(Canvas canvas) {
        synchronized (this) {
            this.spreadsheet.startDrawing();
            this.clipRect = canvas.getClipBounds();
            int columnRightBound = this.columnHeader.getColumnRightBound(canvas, this.zoom);
            int rowBottomBound = this.rowHeader.getRowBottomBound(canvas, this.zoom);
            int i = this.clipRect.right + 10;
            if (columnRightBound >= this.clipRect.right) {
                columnRightBound = i;
            }
            int i2 = this.clipRect.bottom + 50;
            if (rowBottomBound >= this.clipRect.bottom) {
                rowBottomBound = i2;
            }
            this.rowHeader.draw(canvas, columnRightBound, this.zoom);
            this.columnHeader.draw(canvas, rowBottomBound, this.zoom);
            canvas.save();
            canvas.clipRect((float) this.rowHeader.getRowHeaderWidth(), (float) this.columnHeader.getColumnHeaderHeight(), (float) columnRightBound, (float) rowBottomBound);
            drawRows(canvas);
            this.tableFormatView.draw(canvas);
            drawActiveCellBorder(canvas);
            this.shapeView.draw(canvas);
            drawMovingHeaderLine(canvas);
            canvas.restore();
        }
    }

    private void drawActiveCellBorder(Canvas canvas) {
        this.cellView.drawActiveCellBorder(canvas, ModelUtil.instance().getCellAnchor(this, this.sheet.getActiveCellRow(), this.sheet.getActiveCellColumn()), this.sheet.getActiveCellType());
    }

    private void drawMovingHeaderLine(Canvas canvas) {
        if (this.isDrawMovingHeaderLine && this.selectedHeaderInfor != null) {
            Paint paint = PaintKit.instance().getPaint();
            int color = paint.getColor();
            PathEffect pathEffect = paint.getPathEffect();
            Rect clipBounds = canvas.getClipBounds();
            paint.setColor(-16777216);
            paint.setStyle(Paint.Style.STROKE);
            Path path = new Path();
            if (this.selectedHeaderInfor.getType() == 1) {
                path.moveTo(0.0f, (float) this.selectedHeaderInfor.getRect().bottom);
                path.lineTo((float) clipBounds.right, (float) this.selectedHeaderInfor.getRect().bottom);
            } else if (this.selectedHeaderInfor.getType() == 2) {
                path.moveTo((float) this.selectedHeaderInfor.getRect().right, 0.0f);
                path.lineTo((float) this.selectedHeaderInfor.getRect().right, (float) clipBounds.bottom);
            }
            paint.setPathEffect(this.effects);
            canvas.drawPath(path, paint);
            paint.setPathEffect(pathEffect);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(color);
        }
    }

    private int getExtendTextRightBound(Row row, int i, float f) {
        String formatContents;
        while (true) {
            i++;
            if (f <= 0.0f) {
                return i - 1;
            }
            Cell cell = row.getCell(i, false);
            if (cell == null || (cell.getRangeAddressIndex() < 0 && ((formatContents = ModelUtil.instance().getFormatContents(this.sheet.getWorkbook(), cell)) == null || formatContents.length() == 0))) {
                f -= this.sheet.getColumnPixelWidth(i) * this.zoom;
            }
        }
        return i - 1;
    }

    private int getExtendTextLeftBound(Row row, int i, float f) {
        String formatContents;
        int i2 = i - 1;
        while (i2 >= 0 && f > 0.0f) {
            Cell cell = row.getCell(i2, false);
            if (cell != null && (cell.getRangeAddressIndex() >= 0 || ((formatContents = ModelUtil.instance().getFormatContents(this.sheet.getWorkbook(), cell)) != null && formatContents.length() != 0))) {
                return i2 + 1;
            }
            f -= this.sheet.getColumnPixelWidth(i2) * this.zoom;
            i2--;
        }
        return i2 + 1;
    }

    public int getIndentWidth(int i) {
        return (int) Math.round(this.sheet.getWorkbook().getFont(0).getFontSize() * 2.0d * ((double) i) * 1.3333333730697632d);
    }

    public int getIndentWidthWithZoom(int i) {
        return (int) Math.round(this.sheet.getWorkbook().getFont(0).getFontSize() * 2.0d * ((double) i) * 1.3333333730697632d * ((double) this.zoom));
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x01db  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x01e6  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x01fa  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0207  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x000c A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initRowExtendedRangeAddress(com.app.office.ss.model.baseModel.Row r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            java.util.Collection r2 = r18.cellCollection()
            java.util.Iterator r2 = r2.iterator()
        L_0x000c:
            boolean r3 = r2.hasNext()
            r4 = 0
            if (r3 == 0) goto L_0x021e
            java.lang.Object r3 = r2.next()
            r6 = r3
            com.app.office.ss.model.baseModel.Cell r6 = (com.app.office.ss.model.baseModel.Cell) r6
            com.app.office.ss.model.baseModel.Sheet r3 = r0.sheet
            int r5 = r6.getColNumber()
            float r3 = r3.getColumnPixelWidth(r5)
            float r5 = r0.zoom
            float r3 = r3 * r5
            com.app.office.ss.model.style.CellStyle r5 = r6.getCellStyle()
            if (r5 == 0) goto L_0x0038
            com.app.office.ss.model.style.CellStyle r5 = r6.getCellStyle()
            boolean r5 = r5.isWrapText()
            if (r5 != 0) goto L_0x000c
        L_0x0038:
            short r5 = r6.getCellType()
            r7 = 4
            if (r5 == r7) goto L_0x000c
            short r5 = r6.getCellNumericType()
            r7 = 10
            if (r5 == r7) goto L_0x000c
            short r5 = r6.getCellType()
            if (r5 != 0) goto L_0x0056
            short r5 = r6.getCellNumericType()
            r7 = 11
            if (r5 == r7) goto L_0x0056
            goto L_0x000c
        L_0x0056:
            com.app.office.ss.model.style.CellStyle r5 = r6.getCellStyle()
            if (r5 == 0) goto L_0x0066
            short r8 = r5.getIndent()
            int r8 = r0.getIndentWidth(r8)
            float r8 = (float) r8
            goto L_0x0067
        L_0x0066:
            r8 = 0
        L_0x0067:
            boolean r9 = com.app.office.ss.view.CellView.isComplexText(r6)
            r10 = 1073741824(0x40000000, float:2.0)
            if (r9 == 0) goto L_0x0179
            com.app.office.ss.util.ModelUtil r9 = com.app.office.ss.util.ModelUtil.instance()
            com.app.office.ss.model.baseModel.Sheet r11 = r0.sheet
            int r12 = r6.getRowNumber()
            int r13 = r6.getColNumber()
            android.graphics.Rect r9 = r9.getCellAnchor((com.app.office.ss.model.baseModel.Sheet) r11, (int) r12, (int) r13)
            com.app.office.ss.model.baseModel.Sheet r11 = r6.getSheet()
            com.app.office.ss.model.baseModel.Workbook r11 = r11.getWorkbook()
            int r12 = r6.getStringCellValueIndex()
            java.lang.Object r11 = r11.getSharedItem(r12)
            com.app.office.simpletext.model.SectionElement r11 = (com.app.office.simpletext.model.SectionElement) r11
            if (r11 == 0) goto L_0x000c
            long r12 = r11.getEndOffset()
            long r14 = r11.getStartOffset()
            long r12 = r12 - r14
            r14 = 0
            int r16 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r16 != 0) goto L_0x00a6
            goto L_0x000c
        L_0x00a6:
            com.app.office.simpletext.model.IElementCollection r12 = r11.getParaCollection()
            java.util.ArrayList r13 = new java.util.ArrayList
            int r14 = r12.size()
            r13.<init>(r14)
            r14 = 0
        L_0x00b4:
            int r15 = r12.size()
            if (r14 >= r15) goto L_0x00e3
            com.app.office.simpletext.model.AttrManage r15 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IElement r16 = r12.getElementForIndex(r14)
            com.app.office.simpletext.model.IAttributeSet r7 = r16.getAttribute()
            int r7 = r15.getParaHorizontalAlign(r7)
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r13.add(r7)
            com.app.office.simpletext.model.AttrManage r7 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IElement r15 = r12.getElementForIndex(r14)
            com.app.office.simpletext.model.IAttributeSet r15 = r15.getAttribute()
            r7.setParaHorizontalAlign(r15, r4)
            int r14 = r14 + 1
            goto L_0x00b4
        L_0x00e3:
            com.app.office.simpletext.model.IAttributeSet r7 = r11.getAttribute()
            com.app.office.simpletext.model.AttrManage r14 = com.app.office.simpletext.model.AttrManage.instance()
            r15 = 1357905920(0x50f00000, float:3.22122547E10)
            int r15 = java.lang.Math.round(r15)
            r14.setPageWidth(r7, r15)
            com.app.office.simpletext.model.AttrManage r14 = com.app.office.simpletext.model.AttrManage.instance()
            int r9 = r9.height()
            float r9 = (float) r9
            r15 = 1097859072(0x41700000, float:15.0)
            float r9 = r9 * r15
            int r9 = java.lang.Math.round(r9)
            r14.setPageHeight(r7, r9)
            com.app.office.simpletext.model.STDocument r9 = new com.app.office.simpletext.model.STDocument
            r9.<init>()
            r9.appendSection(r11)
            com.app.office.simpletext.view.STRoot r11 = new com.app.office.simpletext.view.STRoot
            com.app.office.ss.control.Spreadsheet r14 = r0.spreadsheet
            com.app.office.simpletext.control.IWord r14 = r14.getEditor()
            r11.<init>(r14, r9)
            r11.setWrapLine(r4)
            r11.doLayout()
            com.app.office.simpletext.view.IView r14 = r11.getChildView()
            int r14 = r14.getLayoutSpan(r4)
            r11.dispose()
            com.app.office.simpletext.model.AttrManage r11 = com.app.office.simpletext.model.AttrManage.instance()
            float r14 = (float) r14
            float r14 = r14 + r8
            r8 = 1082130432(0x40800000, float:4.0)
            float r14 = r14 + r8
            float r15 = r15 * r14
            int r8 = java.lang.Math.round(r15)
            r11.setPageWidth(r7, r8)
            com.app.office.simpletext.view.STRoot r7 = new com.app.office.simpletext.view.STRoot
            com.app.office.ss.control.Spreadsheet r8 = r0.spreadsheet
            com.app.office.simpletext.control.IWord r8 = r8.getEditor()
            r7.<init>(r8, r9)
            r7.doLayout()
        L_0x014c:
            int r8 = r12.size()
            if (r4 >= r8) goto L_0x016e
            com.app.office.simpletext.model.AttrManage r8 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IElement r9 = r12.getElementForIndex(r4)
            com.app.office.simpletext.model.IAttributeSet r9 = r9.getAttribute()
            java.lang.Object r11 = r13.get(r4)
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            r8.setParaHorizontalAlign(r9, r11)
            int r4 = r4 + 1
            goto L_0x014c
        L_0x016e:
            float r4 = r0.zoom
            float r14 = r14 * r4
            int r4 = (int) r14
            float r4 = (float) r4
            float r4 = r4 - r3
            r6.setSTRoot(r7)
            goto L_0x01bb
        L_0x0179:
            int r4 = r6.getRangeAddressIndex()
            if (r4 >= 0) goto L_0x01bd
            com.app.office.ss.util.ModelUtil r4 = com.app.office.ss.util.ModelUtil.instance()
            com.app.office.ss.model.baseModel.Sheet r7 = r0.sheet
            com.app.office.ss.model.baseModel.Workbook r7 = r7.getWorkbook()
            java.lang.String r4 = r4.getFormatContents(r7, r6)
            if (r4 == 0) goto L_0x000c
            int r7 = r4.length()
            if (r7 != 0) goto L_0x0197
            goto L_0x000c
        L_0x0197:
            com.app.office.simpletext.font.FontKit r7 = com.app.office.simpletext.font.FontKit.instance()
            com.app.office.ss.model.baseModel.Sheet r9 = r0.sheet
            com.app.office.ss.model.baseModel.Workbook r9 = r9.getWorkbook()
            r11 = 0
            android.graphics.Paint r7 = r7.getCellPaint(r6, r9, r11)
            float r9 = r7.getTextSize()
            float r11 = r0.zoom
            float r11 = r11 * r9
            r7.setTextSize(r11)
            float r4 = r7.measureText(r4)
            float r4 = r4 + r8
            float r4 = r4 + r10
            float r4 = r4 - r3
            r7.setTextSize(r9)
        L_0x01bb:
            r3 = 0
            goto L_0x01bf
        L_0x01bd:
            r3 = 0
            r4 = 0
        L_0x01bf:
            int r3 = (r4 > r3 ? 1 : (r4 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x000c
            int r3 = r6.getRangeAddressIndex()
            if (r3 >= 0) goto L_0x000c
            int r3 = r6.getColNumber()
            int r7 = r6.getColNumber()
            short r5 = r5.getHorizontalAlign()
            switch(r5) {
                case 0: goto L_0x01fa;
                case 1: goto L_0x01fa;
                case 2: goto L_0x01e6;
                case 3: goto L_0x01db;
                case 4: goto L_0x01fa;
                case 5: goto L_0x01fa;
                case 6: goto L_0x01fa;
                default: goto L_0x01d8;
            }
        L_0x01d8:
            r10 = r3
            r8 = r7
            goto L_0x0203
        L_0x01db:
            int r5 = r6.getColNumber()
            int r4 = r0.getExtendTextLeftBound(r1, r5, r4)
            r10 = r3
            r8 = r4
            goto L_0x0203
        L_0x01e6:
            int r3 = r6.getColNumber()
            float r4 = r4 / r10
            int r3 = r0.getExtendTextLeftBound(r1, r3, r4)
            int r5 = r6.getColNumber()
            int r4 = r0.getExtendTextRightBound(r1, r5, r4)
            r8 = r3
            r10 = r4
            goto L_0x0203
        L_0x01fa:
            int r3 = r6.getColNumber()
            int r3 = r0.getExtendTextRightBound(r1, r3, r4)
            goto L_0x01d8
        L_0x0203:
            if (r8 != r10) goto L_0x0207
            goto L_0x000c
        L_0x0207:
            com.app.office.ss.other.ExpandedCellRangeAddress r3 = new com.app.office.ss.other.ExpandedCellRangeAddress
            int r7 = r18.getRowNumber()
            int r9 = r18.getRowNumber()
            r5 = r3
            r5.<init>(r6, r7, r8, r9, r10)
            int r4 = r18.getExpandedCellCount()
            r1.addExpandedRangeAddress(r4, r3)
            goto L_0x000c
        L_0x021e:
            int r2 = r18.getExpandedCellCount()
        L_0x0222:
            if (r4 >= r2) goto L_0x0268
            com.app.office.ss.other.ExpandedCellRangeAddress r3 = r1.getExpandedRangeAddress(r4)
            com.app.office.ss.model.CellRangeAddress r5 = r3.getRangedAddress()
            int r5 = r5.getFirstColumn()
        L_0x0230:
            com.app.office.ss.model.CellRangeAddress r6 = r3.getRangedAddress()
            int r6 = r6.getLastColumn()
            if (r5 > r6) goto L_0x0265
            com.app.office.ss.model.baseModel.Cell r6 = r1.getCell(r5)
            if (r6 != 0) goto L_0x025f
            com.app.office.ss.model.baseModel.Cell r6 = new com.app.office.ss.model.baseModel.Cell
            r7 = 3
            r6.<init>(r7)
            r6.setColNumber(r5)
            int r7 = r18.getRowNumber()
            r6.setRowNumber(r7)
            com.app.office.ss.model.baseModel.Sheet r7 = r0.sheet
            r6.setSheet(r7)
            int r7 = r18.getRowStyle()
            r6.setCellStyle(r7)
            r1.addCell(r6)
        L_0x025f:
            r6.setExpandedRangeAddressIndex(r4)
            int r5 = r5 + 1
            goto L_0x0230
        L_0x0265:
            int r4 = r4 + 1
            goto L_0x0222
        L_0x0268:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.view.SheetView.initRowExtendedRangeAddress(com.app.office.ss.model.baseModel.Row):void");
    }

    private void drawCells(Canvas canvas, Row row) {
        this.cellInfor.setHeight((row == null ? (float) this.sheet.getDefaultRowHeight() : row.getRowPixelHeight()) * this.zoom);
        if (this.cellInfor.getRowIndex() != this.sheetScroller.getMinRowIndex() || this.sheetScroller.isRowAllVisible()) {
            DrawingCell drawingCell = this.cellInfor;
            drawingCell.setVisibleHeight(drawingCell.getHeight());
        } else {
            this.cellInfor.setVisibleHeight(((float) this.sheetScroller.getVisibleRowHeight()) * this.zoom);
        }
        if (row == null && this.sheet.isAccomplished()) {
            row = this.sheet.getRowByColumnsStyle(this.cellInfor.getRowIndex());
        }
        if (row == null) {
            return;
        }
        if (this.sheet.isAccomplished() || row.isCompleted()) {
            this.cellInfor.setLeft((float) this.rowHeader.getRowHeaderWidth());
            this.cellInfor.setColumnIndex(this.sheetScroller.getMinColumnIndex());
            for (ExtendCell dispose : this.extendCell) {
                dispose.dispose();
            }
            this.extendCell.clear();
            if (this.sheet.isAccomplished() && !row.isInitExpandedRangeAddress()) {
                initRowExtendedRangeAddress(row);
                row.setInitExpandedRangeAddress(true);
            }
            Rect clipBounds = canvas.getClipBounds();
            int maxColumn = this.sheet.getWorkbook().getMaxColumn();
            while (this.cellInfor.getLeft() <= ((float) clipBounds.right) && this.cellInfor.getColumnIndex() < maxColumn) {
                ColumnInfo columnInfo = this.sheet.getColumnInfo(this.cellInfor.getColumnIndex());
                if (columnInfo == null || !columnInfo.isHidden()) {
                    this.cellInfor.setWidth((columnInfo != null ? columnInfo.getColWidth() : (float) this.sheet.getDefaultColWidth()) * this.zoom);
                    if (this.cellInfor.getColumnIndex() != this.sheetScroller.getMinColumnIndex() || this.sheetScroller.isColumnAllVisible()) {
                        DrawingCell drawingCell2 = this.cellInfor;
                        drawingCell2.setVisibleWidth(drawingCell2.getWidth());
                    } else {
                        this.cellInfor.setVisibleWidth(((float) this.sheetScroller.getVisibleColumnWidth()) * this.zoom);
                    }
                    this.cellView.draw(canvas, row.getCell(this.cellInfor.getColumnIndex()), this.cellInfor);
                    this.cellInfor.increaseLeftWithVisibleWidth();
                    this.cellInfor.increaseColumn();
                } else {
                    this.cellInfor.increaseColumn();
                }
            }
            for (ExtendCell next : this.extendCell) {
                Cell cell = next.getCell();
                SSTable tableInfo = cell.getTableInfo();
                SSTableCellStyle sSTableCellStyle = null;
                if (tableInfo != null) {
                    sSTableCellStyle = this.cellView.getTableCellStyle(tableInfo, this.sheet.getWorkbook(), cell.getRowNumber(), cell.getColNumber());
                }
                Paint cellPaint = FontKit.instance().getCellPaint(cell, getSpreadsheet().getWorkbook(), sSTableCellStyle);
                canvas.save();
                canvas.clipRect(next.getRect());
                Object content = next.getContent();
                if (content instanceof String) {
                    float textSize = cellPaint.getTextSize();
                    cellPaint.setTextSize(this.zoom * textSize);
                    canvas.drawText((String) content, next.getX(), next.getY(), cellPaint);
                    cellPaint.setTextSize(textSize);
                } else {
                    ((STRoot) content).draw(canvas, (int) next.getX(), (int) next.getY(), this.zoom);
                }
                canvas.restore();
            }
        }
    }

    private void drawRows(Canvas canvas) {
        Rect clipBounds = canvas.getClipBounds();
        this.cellInfor.setTop((float) this.columnHeader.getColumnHeaderHeight());
        this.cellInfor.setRowIndex(this.sheetScroller.getMinRowIndex());
        int maxRow = this.sheet.getWorkbook().getMaxRow();
        while (!this.spreadsheet.isAbortDrawing() && this.cellInfor.getTop() <= ((float) clipBounds.bottom) && this.cellInfor.getRowIndex() < maxRow) {
            Row row = this.sheet.getRow(this.cellInfor.getRowIndex());
            if (row == null || !row.isZeroHeight()) {
                drawCells(canvas, row);
                this.cellInfor.increaseTopWithVisibleHeight();
                this.cellInfor.increaseRow();
            } else {
                this.cellInfor.increaseRow();
            }
        }
    }

    public Sheet getCurrentSheet() {
        return this.sheet;
    }

    public int getColumnHeaderHeight() {
        return this.columnHeader.getColumnHeaderHeight();
    }

    public int getRowHeaderWidth() {
        return getRowHeader().getRowHeaderWidth();
    }

    public void scrollBy(float f, float f2) {
        synchronized (this) {
            this.scrollX += f / this.zoom;
            this.scrollX = Math.min(this.sheet.getMaxScrollX(), Math.max(0.0f, this.scrollX));
            this.scrollY += f2 / this.zoom;
            this.scrollY = Math.min(this.sheet.getMaxScrollY(), Math.max(0.0f, this.scrollY));
            this.sheet.setScroll(Math.round(this.scrollX), Math.round(this.scrollY));
            this.sheetScroller.update(this.sheet, Math.round(this.scrollX), Math.round(this.scrollY));
            resizeCalloutView();
        }
    }

    public void scrollTo(float f, float f2) {
        synchronized (this) {
            this.scrollX = f;
            this.scrollX = Math.min(this.sheet.getMaxScrollX(), Math.max(0.0f, this.scrollX));
            this.scrollY = f2;
            this.scrollY = Math.min(this.sheet.getMaxScrollY(), Math.max(0.0f, this.scrollY));
            this.sheet.setScroll(Math.round(this.scrollX), Math.round(this.scrollY));
            this.sheetScroller.update(this.sheet, Math.round(this.scrollX), Math.round(this.scrollY));
        }
    }

    public void updateMinRowAndColumnInfo() {
        this.sheetScroller.update(this.sheet, Math.round(this.scrollX), Math.round(this.scrollY));
    }

    public SheetScroller getMinRowAndColumnInformation() {
        return this.sheetScroller;
    }

    public float getScrollX() {
        return this.scrollX;
    }

    public float getScrollY() {
        return this.scrollY;
    }

    public float getZoom() {
        return this.zoom;
    }

    public void setZoom(float f) {
        synchronized (this) {
            setZoom(f, false);
            resizeCalloutView();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0393, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0060, code lost:
        if (com.app.office.ss.util.ModelUtil.instance().getValueY(r9, r9.sheet.getActiveCellRow() + 1, (float) r9.sheetScroller.getVisibleRowHeight()) < ((float) r11)) goto L_0x0062;
     */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00d3  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x01be  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void setZoom(float r10, boolean r11) {
        /*
            r9 = this;
            monitor-enter(r9)
            float r0 = r9.zoom     // Catch:{ all -> 0x0394 }
            r1 = 2
            r2 = 0
            r3 = 1065353216(0x3f800000, float:1.0)
            r4 = 1
            int r0 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1))
            if (r0 >= 0) goto L_0x009f
            if (r11 != 0) goto L_0x009f
            android.graphics.Rect r11 = r9.clipRect     // Catch:{ all -> 0x0394 }
            int r11 = r11.bottom     // Catch:{ all -> 0x0394 }
            com.app.office.ss.control.Spreadsheet r0 = r9.spreadsheet     // Catch:{ all -> 0x0394 }
            int r0 = r0.getBottomBarHeight()     // Catch:{ all -> 0x0394 }
            int r11 = r11 - r0
            com.app.office.ss.model.baseModel.Sheet r0 = r9.sheet     // Catch:{ all -> 0x0394 }
            short r0 = r0.getActiveCellType()     // Catch:{ all -> 0x0394 }
            if (r0 == 0) goto L_0x0064
            if (r0 == r4) goto L_0x0047
            if (r0 == r1) goto L_0x0027
            goto L_0x00a0
        L_0x0027:
            com.app.office.ss.util.ModelUtil r0 = com.app.office.ss.util.ModelUtil.instance()     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Sheet r5 = r9.sheet     // Catch:{ all -> 0x0394 }
            int r5 = r5.getActiveCellColumn()     // Catch:{ all -> 0x0394 }
            int r5 = r5 + r4
            com.app.office.ss.other.SheetScroller r6 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            double r6 = r6.getVisibleColumnWidth()     // Catch:{ all -> 0x0394 }
            float r6 = (float) r6     // Catch:{ all -> 0x0394 }
            float r0 = r0.getValueX((com.app.office.ss.view.SheetView) r9, (int) r5, (float) r6)     // Catch:{ all -> 0x0394 }
            android.graphics.Rect r5 = r9.clipRect     // Catch:{ all -> 0x0394 }
            int r5 = r5.right     // Catch:{ all -> 0x0394 }
            float r5 = (float) r5     // Catch:{ all -> 0x0394 }
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 >= 0) goto L_0x00a0
            goto L_0x0062
        L_0x0047:
            com.app.office.ss.util.ModelUtil r0 = com.app.office.ss.util.ModelUtil.instance()     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Sheet r5 = r9.sheet     // Catch:{ all -> 0x0394 }
            int r5 = r5.getActiveCellRow()     // Catch:{ all -> 0x0394 }
            int r5 = r5 + r4
            com.app.office.ss.other.SheetScroller r6 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            double r6 = r6.getVisibleRowHeight()     // Catch:{ all -> 0x0394 }
            float r6 = (float) r6     // Catch:{ all -> 0x0394 }
            float r0 = r0.getValueY((com.app.office.ss.view.SheetView) r9, (int) r5, (float) r6)     // Catch:{ all -> 0x0394 }
            float r5 = (float) r11     // Catch:{ all -> 0x0394 }
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 >= 0) goto L_0x00a0
        L_0x0062:
            r0 = 1
            goto L_0x00a1
        L_0x0064:
            com.app.office.ss.util.ModelUtil r0 = com.app.office.ss.util.ModelUtil.instance()     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Sheet r5 = r9.sheet     // Catch:{ all -> 0x0394 }
            int r5 = r5.getActiveCellRow()     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Sheet r6 = r9.sheet     // Catch:{ all -> 0x0394 }
            int r6 = r6.getActiveCellColumn()     // Catch:{ all -> 0x0394 }
            android.graphics.RectF r0 = r0.getCellAnchor((com.app.office.ss.view.SheetView) r9, (int) r5, (int) r6)     // Catch:{ all -> 0x0394 }
            float r5 = r0.width()     // Catch:{ all -> 0x0394 }
            int r5 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r5 <= 0) goto L_0x00a0
            float r5 = r0.height()     // Catch:{ all -> 0x0394 }
            int r5 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r5 <= 0) goto L_0x00a0
            android.graphics.Rect r5 = r9.clipRect     // Catch:{ all -> 0x0394 }
            int r5 = r5.left     // Catch:{ all -> 0x0394 }
            float r5 = (float) r5     // Catch:{ all -> 0x0394 }
            android.graphics.Rect r6 = r9.clipRect     // Catch:{ all -> 0x0394 }
            int r6 = r6.top     // Catch:{ all -> 0x0394 }
            float r6 = (float) r6     // Catch:{ all -> 0x0394 }
            android.graphics.Rect r7 = r9.clipRect     // Catch:{ all -> 0x0394 }
            int r7 = r7.right     // Catch:{ all -> 0x0394 }
            float r7 = (float) r7     // Catch:{ all -> 0x0394 }
            float r8 = (float) r11     // Catch:{ all -> 0x0394 }
            boolean r0 = r0.intersect(r5, r6, r7, r8)     // Catch:{ all -> 0x0394 }
            if (r0 == 0) goto L_0x00a0
            goto L_0x0062
        L_0x009f:
            r11 = 0
        L_0x00a0:
            r0 = 0
        L_0x00a1:
            r9.zoom = r10     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Sheet r5 = r9.sheet     // Catch:{ all -> 0x0394 }
            r5.setZoom(r10)     // Catch:{ all -> 0x0394 }
            com.app.office.ss.view.RowHeader r5 = r9.rowHeader     // Catch:{ all -> 0x0394 }
            r5.calculateRowHeaderWidth(r10)     // Catch:{ all -> 0x0394 }
            com.app.office.ss.view.ColumnHeader r5 = r9.columnHeader     // Catch:{ all -> 0x0394 }
            r5.calculateColumnHeaderHeight(r10)     // Catch:{ all -> 0x0394 }
            if (r0 == 0) goto L_0x0392
            android.graphics.Rect r0 = r9.clipRect     // Catch:{ all -> 0x0394 }
            if (r0 == 0) goto L_0x0392
            int r0 = r0.right     // Catch:{ all -> 0x0394 }
            com.app.office.ss.view.RowHeader r5 = r9.rowHeader     // Catch:{ all -> 0x0394 }
            int r5 = r5.getRowHeaderWidth()     // Catch:{ all -> 0x0394 }
            int r0 = r0 - r5
            float r0 = (float) r0     // Catch:{ all -> 0x0394 }
            com.app.office.ss.view.ColumnHeader r5 = r9.columnHeader     // Catch:{ all -> 0x0394 }
            int r5 = r5.getColumnHeaderHeight()     // Catch:{ all -> 0x0394 }
            int r5 = r11 - r5
            float r5 = (float) r5     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Sheet r6 = r9.sheet     // Catch:{ all -> 0x0394 }
            short r6 = r6.getActiveCellType()     // Catch:{ all -> 0x0394 }
            if (r6 == 0) goto L_0x01be
            if (r6 == r4) goto L_0x0148
            if (r6 == r1) goto L_0x00d9
            goto L_0x02da
        L_0x00d9:
            com.app.office.ss.util.ModelUtil r11 = com.app.office.ss.util.ModelUtil.instance()     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Sheet r1 = r9.sheet     // Catch:{ all -> 0x0394 }
            int r1 = r1.getActiveCellColumn()     // Catch:{ all -> 0x0394 }
            int r1 = r1 + r4
            com.app.office.ss.other.SheetScroller r2 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            double r5 = r2.getVisibleColumnWidth()     // Catch:{ all -> 0x0394 }
            long r5 = java.lang.Math.round(r5)     // Catch:{ all -> 0x0394 }
            float r2 = (float) r5     // Catch:{ all -> 0x0394 }
            float r11 = r11.getValueX((com.app.office.ss.view.SheetView) r9, (int) r1, (float) r2)     // Catch:{ all -> 0x0394 }
        L_0x00f3:
            android.graphics.Rect r1 = r9.clipRect     // Catch:{ all -> 0x0394 }
            int r1 = r1.right     // Catch:{ all -> 0x0394 }
            float r1 = (float) r1     // Catch:{ all -> 0x0394 }
            int r1 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x02da
            android.graphics.Rect r1 = r9.clipRect     // Catch:{ all -> 0x0394 }
            int r1 = r1.right     // Catch:{ all -> 0x0394 }
            float r1 = (float) r1     // Catch:{ all -> 0x0394 }
            float r11 = r11 - r1
            float r11 = java.lang.Math.abs(r11)     // Catch:{ all -> 0x0394 }
            int r11 = (r11 > r3 ? 1 : (r11 == r3 ? 0 : -1))
            if (r11 <= 0) goto L_0x02da
            com.app.office.ss.model.baseModel.Sheet r11 = r9.sheet     // Catch:{ all -> 0x0394 }
            com.app.office.ss.other.SheetScroller r1 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            int r1 = r1.getMinColumnIndex()     // Catch:{ all -> 0x0394 }
            float r11 = r11.getColumnPixelWidth(r1)     // Catch:{ all -> 0x0394 }
            float r1 = r11 * r10
            int r1 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r1 <= 0) goto L_0x011e
            goto L_0x02da
        L_0x011e:
            float r1 = r9.scrollX     // Catch:{ all -> 0x0394 }
            float r1 = r1 + r11
            r9.scrollX = r1     // Catch:{ all -> 0x0394 }
            com.app.office.ss.other.SheetScroller r11 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            int r1 = r11.getMinColumnIndex()     // Catch:{ all -> 0x0394 }
            int r1 = r1 + r4
            r11.setMinColumnIndex(r1)     // Catch:{ all -> 0x0394 }
            com.app.office.ss.util.ModelUtil r11 = com.app.office.ss.util.ModelUtil.instance()     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Sheet r1 = r9.sheet     // Catch:{ all -> 0x0394 }
            int r1 = r1.getActiveCellColumn()     // Catch:{ all -> 0x0394 }
            int r1 = r1 + r4
            com.app.office.ss.other.SheetScroller r2 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            double r5 = r2.getVisibleColumnWidth()     // Catch:{ all -> 0x0394 }
            long r5 = java.lang.Math.round(r5)     // Catch:{ all -> 0x0394 }
            float r2 = (float) r5     // Catch:{ all -> 0x0394 }
            float r11 = r11.getValueX((com.app.office.ss.view.SheetView) r9, (int) r1, (float) r2)     // Catch:{ all -> 0x0394 }
            goto L_0x00f3
        L_0x0148:
            com.app.office.ss.util.ModelUtil r0 = com.app.office.ss.util.ModelUtil.instance()     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Sheet r1 = r9.sheet     // Catch:{ all -> 0x0394 }
            int r1 = r1.getActiveCellRow()     // Catch:{ all -> 0x0394 }
            int r1 = r1 + r4
            com.app.office.ss.other.SheetScroller r2 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            double r6 = r2.getVisibleRowHeight()     // Catch:{ all -> 0x0394 }
            long r6 = java.lang.Math.round(r6)     // Catch:{ all -> 0x0394 }
            float r2 = (float) r6     // Catch:{ all -> 0x0394 }
            float r0 = r0.getValueY((com.app.office.ss.view.SheetView) r9, (int) r1, (float) r2)     // Catch:{ all -> 0x0394 }
        L_0x0162:
            float r1 = (float) r11     // Catch:{ all -> 0x0394 }
            int r2 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r2 <= 0) goto L_0x02da
            float r0 = r0 - r1
            float r0 = java.lang.Math.abs(r0)     // Catch:{ all -> 0x0394 }
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 <= 0) goto L_0x02da
            com.app.office.ss.model.baseModel.Sheet r0 = r9.sheet     // Catch:{ all -> 0x0394 }
            com.app.office.ss.other.SheetScroller r1 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            int r1 = r1.getMinRowIndex()     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Row r0 = r0.getRow(r1)     // Catch:{ all -> 0x0394 }
            if (r0 != 0) goto L_0x0188
            com.app.office.ss.model.baseModel.Sheet r0 = r9.getCurrentSheet()     // Catch:{ all -> 0x0394 }
            int r0 = r0.getDefaultRowHeight()     // Catch:{ all -> 0x0394 }
            float r0 = (float) r0     // Catch:{ all -> 0x0394 }
            goto L_0x018c
        L_0x0188:
            float r0 = r0.getRowPixelHeight()     // Catch:{ all -> 0x0394 }
        L_0x018c:
            float r1 = r0 * r10
            int r1 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r1 <= 0) goto L_0x0194
            goto L_0x02da
        L_0x0194:
            float r1 = r9.scrollY     // Catch:{ all -> 0x0394 }
            float r1 = r1 + r0
            r9.scrollY = r1     // Catch:{ all -> 0x0394 }
            com.app.office.ss.other.SheetScroller r0 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            int r1 = r0.getMinRowIndex()     // Catch:{ all -> 0x0394 }
            int r1 = r1 + r4
            r0.setMinRowIndex(r1)     // Catch:{ all -> 0x0394 }
            com.app.office.ss.util.ModelUtil r0 = com.app.office.ss.util.ModelUtil.instance()     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Sheet r1 = r9.sheet     // Catch:{ all -> 0x0394 }
            int r1 = r1.getActiveCellRow()     // Catch:{ all -> 0x0394 }
            int r1 = r1 + r4
            com.app.office.ss.other.SheetScroller r2 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            double r6 = r2.getVisibleRowHeight()     // Catch:{ all -> 0x0394 }
            long r6 = java.lang.Math.round(r6)     // Catch:{ all -> 0x0394 }
            float r2 = (float) r6     // Catch:{ all -> 0x0394 }
            float r0 = r0.getValueY((com.app.office.ss.view.SheetView) r9, (int) r1, (float) r2)     // Catch:{ all -> 0x0394 }
            goto L_0x0162
        L_0x01be:
            com.app.office.ss.util.ModelUtil r1 = com.app.office.ss.util.ModelUtil.instance()     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Sheet r6 = r9.sheet     // Catch:{ all -> 0x0394 }
            int r6 = r6.getActiveCellRow()     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Sheet r7 = r9.sheet     // Catch:{ all -> 0x0394 }
            int r7 = r7.getActiveCellColumn()     // Catch:{ all -> 0x0394 }
            android.graphics.RectF r1 = r1.getCellAnchor((com.app.office.ss.view.SheetView) r9, (int) r6, (int) r7)     // Catch:{ all -> 0x0394 }
        L_0x01d2:
            float r6 = r1.right     // Catch:{ all -> 0x0394 }
            com.app.office.ss.view.RowHeader r7 = r9.rowHeader     // Catch:{ all -> 0x0394 }
            int r7 = r7.getRowHeaderWidth()     // Catch:{ all -> 0x0394 }
            float r7 = (float) r7     // Catch:{ all -> 0x0394 }
            float r6 = r6 - r7
            float r6 = java.lang.Math.abs(r6)     // Catch:{ all -> 0x0394 }
            int r6 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r6 < 0) goto L_0x0208
            float r6 = r1.right     // Catch:{ all -> 0x0394 }
            android.graphics.Rect r7 = r9.clipRect     // Catch:{ all -> 0x0394 }
            int r7 = r7.right     // Catch:{ all -> 0x0394 }
            float r7 = (float) r7     // Catch:{ all -> 0x0394 }
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 > 0) goto L_0x0208
            float r6 = r1.bottom     // Catch:{ all -> 0x0394 }
            com.app.office.ss.view.ColumnHeader r7 = r9.columnHeader     // Catch:{ all -> 0x0394 }
            int r7 = r7.getColumnHeaderHeight()     // Catch:{ all -> 0x0394 }
            float r7 = (float) r7     // Catch:{ all -> 0x0394 }
            float r6 = r6 - r7
            float r6 = java.lang.Math.abs(r6)     // Catch:{ all -> 0x0394 }
            int r6 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r6 < 0) goto L_0x0208
            float r6 = r1.bottom     // Catch:{ all -> 0x0394 }
            float r7 = (float) r11     // Catch:{ all -> 0x0394 }
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 <= 0) goto L_0x02da
        L_0x0208:
            float r6 = r1.right     // Catch:{ all -> 0x0394 }
            com.app.office.ss.view.RowHeader r7 = r9.rowHeader     // Catch:{ all -> 0x0394 }
            int r7 = r7.getRowHeaderWidth()     // Catch:{ all -> 0x0394 }
            float r7 = (float) r7     // Catch:{ all -> 0x0394 }
            float r6 = r6 - r7
            float r6 = java.lang.Math.abs(r6)     // Catch:{ all -> 0x0394 }
            int r6 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r6 >= 0) goto L_0x023e
            com.app.office.ss.model.baseModel.Sheet r6 = r9.sheet     // Catch:{ all -> 0x0394 }
            com.app.office.ss.other.SheetScroller r7 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            int r7 = r7.getMinColumnIndex()     // Catch:{ all -> 0x0394 }
            float r6 = r6.getColumnPixelWidth(r7)     // Catch:{ all -> 0x0394 }
            float r7 = r6 * r10
            int r7 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r7 <= 0) goto L_0x022e
            goto L_0x02da
        L_0x022e:
            float r7 = r9.scrollX     // Catch:{ all -> 0x0394 }
            float r7 = r7 - r6
            r9.scrollX = r7     // Catch:{ all -> 0x0394 }
            com.app.office.ss.other.SheetScroller r6 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            int r7 = r6.getMinColumnIndex()     // Catch:{ all -> 0x0394 }
            int r7 = r7 - r4
            r6.setMinColumnIndex(r7)     // Catch:{ all -> 0x0394 }
            goto L_0x026c
        L_0x023e:
            float r6 = r1.right     // Catch:{ all -> 0x0394 }
            android.graphics.Rect r7 = r9.clipRect     // Catch:{ all -> 0x0394 }
            int r7 = r7.right     // Catch:{ all -> 0x0394 }
            float r7 = (float) r7     // Catch:{ all -> 0x0394 }
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 <= 0) goto L_0x026c
            com.app.office.ss.model.baseModel.Sheet r6 = r9.sheet     // Catch:{ all -> 0x0394 }
            com.app.office.ss.other.SheetScroller r7 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            int r7 = r7.getMinColumnIndex()     // Catch:{ all -> 0x0394 }
            float r6 = r6.getColumnPixelWidth(r7)     // Catch:{ all -> 0x0394 }
            float r7 = r6 * r10
            int r7 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r7 <= 0) goto L_0x025d
            goto L_0x02da
        L_0x025d:
            float r7 = r9.scrollX     // Catch:{ all -> 0x0394 }
            float r7 = r7 + r6
            r9.scrollX = r7     // Catch:{ all -> 0x0394 }
            com.app.office.ss.other.SheetScroller r6 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            int r7 = r6.getMinColumnIndex()     // Catch:{ all -> 0x0394 }
            int r7 = r7 + r4
            r6.setMinColumnIndex(r7)     // Catch:{ all -> 0x0394 }
        L_0x026c:
            float r6 = r1.bottom     // Catch:{ all -> 0x0394 }
            com.app.office.ss.view.ColumnHeader r7 = r9.columnHeader     // Catch:{ all -> 0x0394 }
            int r7 = r7.getColumnHeaderHeight()     // Catch:{ all -> 0x0394 }
            float r7 = (float) r7     // Catch:{ all -> 0x0394 }
            float r6 = r6 - r7
            float r6 = java.lang.Math.abs(r6)     // Catch:{ all -> 0x0394 }
            int r6 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r6 >= 0) goto L_0x02b1
            com.app.office.ss.model.baseModel.Sheet r1 = r9.sheet     // Catch:{ all -> 0x0394 }
            com.app.office.ss.other.SheetScroller r6 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            int r6 = r6.getMinRowIndex()     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Row r1 = r1.getRow(r6)     // Catch:{ all -> 0x0394 }
            if (r1 != 0) goto L_0x0296
            com.app.office.ss.model.baseModel.Sheet r1 = r9.getCurrentSheet()     // Catch:{ all -> 0x0394 }
            int r1 = r1.getDefaultRowHeight()     // Catch:{ all -> 0x0394 }
            float r1 = (float) r1     // Catch:{ all -> 0x0394 }
            goto L_0x029a
        L_0x0296:
            float r1 = r1.getRowPixelHeight()     // Catch:{ all -> 0x0394 }
        L_0x029a:
            float r6 = r1 * r10
            int r6 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
            if (r6 <= 0) goto L_0x02a1
            goto L_0x02da
        L_0x02a1:
            float r6 = r9.scrollY     // Catch:{ all -> 0x0394 }
            float r6 = r6 - r1
            r9.scrollY = r6     // Catch:{ all -> 0x0394 }
            com.app.office.ss.other.SheetScroller r1 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            int r6 = r1.getMinRowIndex()     // Catch:{ all -> 0x0394 }
            int r6 = r6 - r4
            r1.setMinRowIndex(r6)     // Catch:{ all -> 0x0394 }
            goto L_0x02f6
        L_0x02b1:
            float r1 = r1.bottom     // Catch:{ all -> 0x0394 }
            float r6 = (float) r11     // Catch:{ all -> 0x0394 }
            int r1 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r1 <= 0) goto L_0x02f6
            com.app.office.ss.model.baseModel.Sheet r1 = r9.sheet     // Catch:{ all -> 0x0394 }
            com.app.office.ss.other.SheetScroller r6 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            int r6 = r6.getMinRowIndex()     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Row r1 = r1.getRow(r6)     // Catch:{ all -> 0x0394 }
            if (r1 != 0) goto L_0x02d0
            com.app.office.ss.model.baseModel.Sheet r1 = r9.getCurrentSheet()     // Catch:{ all -> 0x0394 }
            int r1 = r1.getDefaultRowHeight()     // Catch:{ all -> 0x0394 }
            float r1 = (float) r1     // Catch:{ all -> 0x0394 }
            goto L_0x02d4
        L_0x02d0:
            float r1 = r1.getRowPixelHeight()     // Catch:{ all -> 0x0394 }
        L_0x02d4:
            float r6 = r1 * r10
            int r6 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
            if (r6 <= 0) goto L_0x02e7
        L_0x02da:
            com.app.office.ss.model.baseModel.Sheet r10 = r9.sheet     // Catch:{ all -> 0x0394 }
            float r11 = r9.scrollX     // Catch:{ all -> 0x0394 }
            int r11 = (int) r11     // Catch:{ all -> 0x0394 }
            float r0 = r9.scrollY     // Catch:{ all -> 0x0394 }
            int r0 = (int) r0     // Catch:{ all -> 0x0394 }
            r10.setScroll(r11, r0)     // Catch:{ all -> 0x0394 }
            goto L_0x0392
        L_0x02e7:
            float r6 = r9.scrollY     // Catch:{ all -> 0x0394 }
            float r6 = r6 + r1
            r9.scrollY = r6     // Catch:{ all -> 0x0394 }
            com.app.office.ss.other.SheetScroller r1 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            int r6 = r1.getMinRowIndex()     // Catch:{ all -> 0x0394 }
            int r6 = r6 + r4
            r1.setMinRowIndex(r6)     // Catch:{ all -> 0x0394 }
        L_0x02f6:
            com.app.office.ss.util.ModelUtil r1 = com.app.office.ss.util.ModelUtil.instance()     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Sheet r6 = r9.sheet     // Catch:{ all -> 0x0394 }
            int r6 = r6.getActiveCellRow()     // Catch:{ all -> 0x0394 }
            com.app.office.ss.model.baseModel.Sheet r7 = r9.sheet     // Catch:{ all -> 0x0394 }
            int r7 = r7.getActiveCellColumn()     // Catch:{ all -> 0x0394 }
            android.graphics.RectF r1 = r1.getCellAnchor((com.app.office.ss.view.SheetView) r9, (int) r6, (int) r7)     // Catch:{ all -> 0x0394 }
            float r6 = r1.left     // Catch:{ all -> 0x0394 }
            com.app.office.ss.view.RowHeader r7 = r9.rowHeader     // Catch:{ all -> 0x0394 }
            int r7 = r7.getRowHeaderWidth()     // Catch:{ all -> 0x0394 }
            float r7 = (float) r7     // Catch:{ all -> 0x0394 }
            float r6 = r6 - r7
            float r6 = java.lang.Math.abs(r6)     // Catch:{ all -> 0x0394 }
            int r6 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r6 >= 0) goto L_0x0329
            float r6 = r1.right     // Catch:{ all -> 0x0394 }
            android.graphics.Rect r7 = r9.clipRect     // Catch:{ all -> 0x0394 }
            int r7 = r7.right     // Catch:{ all -> 0x0394 }
            float r7 = (float) r7     // Catch:{ all -> 0x0394 }
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 < 0) goto L_0x0329
        L_0x0327:
            r6 = 1
            goto L_0x034f
        L_0x0329:
            float r6 = r1.right     // Catch:{ all -> 0x0394 }
            com.app.office.ss.view.RowHeader r7 = r9.rowHeader     // Catch:{ all -> 0x0394 }
            int r7 = r7.getRowHeaderWidth()     // Catch:{ all -> 0x0394 }
            float r7 = (float) r7     // Catch:{ all -> 0x0394 }
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 == 0) goto L_0x034e
            float r6 = r1.right     // Catch:{ all -> 0x0394 }
            float r7 = r1.left     // Catch:{ all -> 0x0394 }
            float r6 = r6 - r7
            float r6 = java.lang.Math.abs(r6)     // Catch:{ all -> 0x0394 }
            int r6 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r6 >= 0) goto L_0x034e
            com.app.office.ss.other.SheetScroller r6 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            int r7 = r6.getMinColumnIndex()     // Catch:{ all -> 0x0394 }
            int r7 = r7 - r4
            r6.setMinColumnIndex(r7)     // Catch:{ all -> 0x0394 }
            goto L_0x0327
        L_0x034e:
            r6 = 0
        L_0x034f:
            float r7 = r1.top     // Catch:{ all -> 0x0394 }
            com.app.office.ss.view.ColumnHeader r8 = r9.columnHeader     // Catch:{ all -> 0x0394 }
            int r8 = r8.getColumnHeaderHeight()     // Catch:{ all -> 0x0394 }
            float r8 = (float) r8     // Catch:{ all -> 0x0394 }
            float r7 = r7 - r8
            float r7 = java.lang.Math.abs(r7)     // Catch:{ all -> 0x0394 }
            int r7 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r7 >= 0) goto L_0x036e
            float r7 = r1.bottom     // Catch:{ all -> 0x0394 }
            android.graphics.Rect r8 = r9.clipRect     // Catch:{ all -> 0x0394 }
            int r8 = r8.bottom     // Catch:{ all -> 0x0394 }
            float r8 = (float) r8     // Catch:{ all -> 0x0394 }
            int r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r7 < 0) goto L_0x036e
        L_0x036c:
            r6 = 1
            goto L_0x038e
        L_0x036e:
            float r7 = r1.bottom     // Catch:{ all -> 0x0394 }
            com.app.office.ss.view.ColumnHeader r8 = r9.columnHeader     // Catch:{ all -> 0x0394 }
            int r8 = r8.getColumnHeaderHeight()     // Catch:{ all -> 0x0394 }
            float r8 = (float) r8     // Catch:{ all -> 0x0394 }
            int r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r7 == 0) goto L_0x038e
            float r7 = r1.bottom     // Catch:{ all -> 0x0394 }
            float r8 = r1.top     // Catch:{ all -> 0x0394 }
            int r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r7 >= 0) goto L_0x038e
            com.app.office.ss.other.SheetScroller r6 = r9.sheetScroller     // Catch:{ all -> 0x0394 }
            int r7 = r6.getMinRowIndex()     // Catch:{ all -> 0x0394 }
            int r7 = r7 - r4
            r6.setMinRowIndex(r7)     // Catch:{ all -> 0x0394 }
            goto L_0x036c
        L_0x038e:
            if (r6 == 0) goto L_0x01d2
            monitor-exit(r9)
            return
        L_0x0392:
            monitor-exit(r9)
            return
        L_0x0394:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.view.SheetView.setZoom(float, boolean):void");
    }

    public void setZoom(float f, float f2, float f3) {
        int width = this.spreadsheet.getWidth();
        int height = this.spreadsheet.getHeight();
        float rowHeaderWidth = (f2 - ((float) this.rowHeader.getRowHeaderWidth())) / this.zoom;
        float columnHeaderHeight = (f3 - ((float) this.columnHeader.getColumnHeaderHeight())) / this.zoom;
        float min = Math.min(this.sheet.getMaxScrollX(), rowHeaderWidth + ((float) this.sheet.getScrollX()));
        float min2 = Math.min(this.sheet.getMaxScrollY(), columnHeaderHeight + ((float) this.sheet.getScrollY()));
        this.zoom = f;
        this.sheet.setZoom(f);
        this.rowHeader.calculateRowHeaderWidth(f);
        this.columnHeader.calculateColumnHeaderHeight(f);
        scrollTo((float) ((int) (((min * f) - ((float) (width / 2))) / f)), (float) ((int) (((min2 * f) - ((float) (height / 2))) / f)));
    }

    public Spreadsheet getSpreadsheet() {
        return this.spreadsheet;
    }

    public void setSpreadsheet(Spreadsheet spreadsheet2) {
        this.spreadsheet = spreadsheet2;
    }

    public RowHeader getRowHeader() {
        return this.rowHeader;
    }

    public int getCurrentMinRow() {
        return this.sheetScroller.getMinRowIndex();
    }

    public int getCurrentMinColumn() {
        return this.sheetScroller.getMinColumnIndex();
    }

    public void selectedCell(int i, int i2) {
        Row row = this.sheet.getRow(i);
        if (row == null || row.getCell(i2) == null || row.getCell(i2).getRangeAddressIndex() < 0) {
            this.selecetedCellsRange.setFirstRow(i);
            this.selecetedCellsRange.setLastRow(i);
            this.selecetedCellsRange.setFirstColumn(i2);
            this.selecetedCellsRange.setLastColumn(i2);
        } else {
            CellRangeAddress mergeRange = this.sheet.getMergeRange(row.getCell(i2).getRangeAddressIndex());
            this.selecetedCellsRange.setFirstRow(mergeRange.getFirstRow());
            this.selecetedCellsRange.setLastRow(mergeRange.getLastRow());
            this.selecetedCellsRange.setFirstColumn(mergeRange.getFirstColumn());
            this.selecetedCellsRange.setLastColumn(mergeRange.getLastColumn());
        }
        getCurrentSheet().setActiveCellRowCol(this.selecetedCellsRange.getFirstRow(), this.selecetedCellsRange.getFirstColumn());
    }

    public void setDrawMovingHeaderLine(boolean z) {
        this.isDrawMovingHeaderLine = z;
    }

    public void changeHeaderArea(FocusCell focusCell) {
        this.selectedHeaderInfor = focusCell;
    }

    public void goToFindedCell(Cell cell) {
        if (cell != null) {
            int colNumber = cell.getColNumber();
            int rowNumber = cell.getRowNumber();
            if (cell.getColNumber() > 0) {
                colNumber = cell.getColNumber() - 1;
            }
            if (cell.getRowNumber() > 0) {
                rowNumber = cell.getRowNumber() - 1;
            }
            this.sheet.setActiveCellRowCol(cell.getRowNumber(), cell.getColNumber());
            selectedCell(cell.getRowNumber(), cell.getColNumber());
            goToCell(rowNumber, colNumber);
            this.spreadsheet.postInvalidate();
            this.spreadsheet.getControl().actionEvent(20, (Object) null);
            this.spreadsheet.getControl().actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
        }
    }

    public void goToCell(int i, int i2) {
        Rect cellAnchor = ModelUtil.instance().getCellAnchor(this.sheet, i, i2, true);
        scrollTo((float) cellAnchor.left, (float) cellAnchor.top);
    }

    public boolean find(String str) {
        if (this.findingMgr == null) {
            this.findingMgr = new FindingMgr();
        }
        Cell findCell = this.findingMgr.findCell(this.sheet, str);
        if (findCell == null) {
            return false;
        }
        goToFindedCell(findCell);
        return true;
    }

    public boolean findBackward() {
        Cell findBackward;
        FindingMgr findingMgr2 = this.findingMgr;
        if (findingMgr2 == null || (findBackward = findingMgr2.findBackward()) == null) {
            return false;
        }
        goToFindedCell(findBackward);
        return true;
    }

    public boolean findForward() {
        Cell findForward;
        FindingMgr findingMgr2 = this.findingMgr;
        if (findingMgr2 == null || (findForward = findingMgr2.findForward()) == null) {
            return false;
        }
        goToFindedCell(findForward);
        return true;
    }

    public void addExtendCell(Cell cell, RectF rectF, float f, float f2, Object obj) {
        this.extendCell.add(new ExtendCell(cell, rectF, f, f2, obj));
    }

    public int getSheetIndex() {
        return this.sheet.getWorkbook().getSheetIndex(this.sheet) + 1;
    }

    public void dispose() {
        this.spreadsheet = null;
        this.sheet = null;
        RowHeader rowHeader2 = this.rowHeader;
        if (rowHeader2 != null) {
            rowHeader2.dispose();
            this.rowHeader = null;
        }
        ColumnHeader columnHeader2 = this.columnHeader;
        if (columnHeader2 != null) {
            columnHeader2.dispose();
            this.columnHeader = null;
        }
        CellView cellView2 = this.cellView;
        if (cellView2 != null) {
            cellView2.dispose();
            this.cellView = null;
        }
        ShapeView shapeView2 = this.shapeView;
        if (shapeView2 != null) {
            shapeView2.dispose();
            this.shapeView = null;
        }
        SheetScroller sheetScroller2 = this.sheetScroller;
        if (sheetScroller2 != null) {
            sheetScroller2.dispose();
            this.sheetScroller = null;
        }
        DrawingCell drawingCell = this.cellInfor;
        if (drawingCell != null) {
            drawingCell.dispose();
            this.cellInfor = null;
        }
        FindingMgr findingMgr2 = this.findingMgr;
        if (findingMgr2 != null) {
            findingMgr2.dispose();
            this.findingMgr = null;
        }
        List<ExtendCell> list = this.extendCell;
        if (list != null) {
            list.clear();
            this.extendCell = null;
        }
        this.selectedHeaderInfor = null;
        this.clipRect = null;
        this.effects = null;
    }

    public class ExtendCell {
        private Cell cell;
        private Object content;
        private RectF rect;
        private float x;
        private float y;

        public ExtendCell(Cell cell2, RectF rectF, float f, float f2, Object obj) {
            this.cell = cell2;
            setRect(rectF);
            this.x = f;
            this.y = f2;
            if (obj instanceof String) {
                this.content = ((String) obj).intern();
            } else {
                this.content = obj;
            }
        }

        public Cell getCell() {
            return this.cell;
        }

        public float getX() {
            return this.x;
        }

        public float getY() {
            return this.y;
        }

        public Object getContent() {
            return this.content;
        }

        /* access modifiers changed from: private */
        public RectF getRect() {
            return this.rect;
        }

        private void setRect(RectF rectF) {
            this.rect = rectF;
        }

        public void dispose() {
            this.cell = null;
            this.rect = null;
            this.content = null;
        }
    }
}
