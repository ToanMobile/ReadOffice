package com.app.office.fc.hssf.usermodel;

import androidx.core.view.MotionEventCompat;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.hssf.formula.FormulaShifter;
import com.app.office.fc.hssf.formula.ptg.Area3DPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.model.InternalSheet;
import com.app.office.fc.hssf.model.InternalWorkbook;
import com.app.office.fc.hssf.record.AutoFilterInfoRecord;
import com.app.office.fc.hssf.record.CellValueRecordInterface;
import com.app.office.fc.hssf.record.EscherAggregate;
import com.app.office.fc.hssf.record.NameRecord;
import com.app.office.fc.hssf.record.NoteRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.RowRecord;
import com.app.office.fc.hssf.record.SCLRecord;
import com.app.office.fc.hssf.record.WSBoolRecord;
import com.app.office.fc.hssf.record.aggregates.FormulaRecordAggregate;
import com.app.office.fc.hssf.record.aggregates.WorksheetProtectionBlock;
import com.app.office.fc.hssf.util.ColumnInfo;
import com.app.office.fc.hssf.util.HSSFPaneInformation;
import com.app.office.fc.ss.SpreadsheetVersion;
import com.app.office.fc.ss.usermodel.CellRange;
import com.app.office.fc.ss.usermodel.DataValidation;
import com.app.office.fc.ss.usermodel.DataValidationHelper;
import com.app.office.fc.ss.usermodel.ICell;
import com.app.office.fc.ss.usermodel.ICellStyle;
import com.app.office.fc.ss.usermodel.IRow;
import com.app.office.fc.ss.usermodel.Sheet;
import com.app.office.fc.ss.util.CellReference;
import com.app.office.fc.ss.util.HSSFCellRangeAddress;
import com.app.office.fc.ss.util.Region;
import com.app.office.fc.ss.util.SSCellRange;
import com.app.office.fc.ss.util.SheetUtil;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import com.app.office.ss.model.XLSModel.AWorkbook;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public final class HSSFSheet implements Sheet {
    private static final int DEBUG = POILogger.DEBUG;
    public static final int INITIAL_CAPACITY = 20;
    private static final POILogger log = POILogFactory.getLogger(HSSFSheet.class);
    protected final InternalWorkbook _book;
    private int _firstrow;
    private int _lastrow;
    private HSSFPatriarch _patriarch;
    private final TreeMap<Integer, HSSFRow> _rows;
    private final InternalSheet _sheet;
    protected final HSSFWorkbook _workbook;
    private int column_activecell;
    private boolean isInitForDraw;
    HSSFPaneInformation paneInformation;
    private int row_activecell;
    private int scrollX;
    private int scrollY;
    private float zoom;

    public CellRange<HSSFCell> setArrayFormula(String str, HSSFCellRangeAddress hSSFCellRangeAddress) {
        return null;
    }

    protected HSSFSheet(HSSFWorkbook hSSFWorkbook) {
        this.zoom = 1.0f;
        this.row_activecell = -1;
        this.column_activecell = -1;
        this._sheet = InternalSheet.createSheet();
        this._rows = new TreeMap<>();
        this._workbook = hSSFWorkbook;
        this._book = hSSFWorkbook.getWorkbook();
    }

    protected HSSFSheet(HSSFWorkbook hSSFWorkbook, InternalSheet internalSheet) {
        this.zoom = 1.0f;
        this.row_activecell = -1;
        this.column_activecell = -1;
        this._sheet = internalSheet;
        this._rows = new TreeMap<>();
        this._workbook = hSSFWorkbook;
        this._book = hSSFWorkbook.getWorkbook();
        setPropertiesFromSheet(internalSheet);
    }

    /* access modifiers changed from: package-private */
    public HSSFSheet cloneSheet(HSSFWorkbook hSSFWorkbook) {
        return new HSSFSheet(hSSFWorkbook, this._sheet.cloneSheet());
    }

    public HSSFWorkbook getWorkbook() {
        return this._workbook;
    }

    private void setPropertiesFromSheet(InternalSheet internalSheet) {
        HSSFRow hSSFRow;
        RowRecord nextRow = internalSheet.getNextRow();
        boolean z = nextRow != null;
        while (nextRow != null) {
            createRowFromRecord(nextRow);
            nextRow = internalSheet.getNextRow();
        }
        Iterator<CellValueRecordInterface> cellValueIterator = internalSheet.getCellValueIterator();
        long currentTimeMillis = System.currentTimeMillis();
        POILogger pOILogger = log;
        if (pOILogger.check(POILogger.DEBUG)) {
            pOILogger.log(DEBUG, (Object) "Time at start of cell creating in HSSF sheet = ", (Object) Long.valueOf(currentTimeMillis));
        }
        HSSFRow hSSFRow2 = null;
        while (cellValueIterator.hasNext()) {
            CellValueRecordInterface next = cellValueIterator.next();
            long currentTimeMillis2 = System.currentTimeMillis();
            if ((hSSFRow2 != null && hSSFRow2.getRowNum() == next.getRow()) || (hSSFRow2 = getRow(next.getRow())) != null) {
                hSSFRow = hSSFRow2;
            } else if (!z) {
                RowRecord rowRecord = new RowRecord(next.getRow());
                internalSheet.addRow(rowRecord);
                HSSFRow createRowFromRecord = createRowFromRecord(rowRecord);
                hSSFRow = hSSFRow2;
                hSSFRow2 = createRowFromRecord;
            } else {
                throw new RuntimeException("Unexpected missing row when some rows already present");
            }
            POILogger pOILogger2 = log;
            if (pOILogger2.check(POILogger.DEBUG)) {
                int i = DEBUG;
                pOILogger2.log(i, (Object) "record id = " + Integer.toHexString(((Record) next).getSid()));
            }
            hSSFRow2.createCellFromRecord(next);
            if (pOILogger2.check(POILogger.DEBUG)) {
                pOILogger2.log(DEBUG, (Object) "record took ", (Object) Long.valueOf(System.currentTimeMillis() - currentTimeMillis2));
            }
            hSSFRow2 = hSSFRow;
        }
        POILogger pOILogger3 = log;
        if (pOILogger3.check(POILogger.DEBUG)) {
            pOILogger3.log(DEBUG, (Object) "total sheet cell creation took ", (Object) Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
        }
    }

    public HSSFRow createRow(int i) {
        HSSFRow hSSFRow = new HSSFRow(this._workbook, this, i);
        addRow(hSSFRow, true);
        return hSSFRow;
    }

    private HSSFRow createRowFromRecord(RowRecord rowRecord) {
        HSSFRow hSSFRow = new HSSFRow(this._workbook, this, rowRecord);
        addRow(hSSFRow, false);
        return hSSFRow;
    }

    public void removeRow(IRow iRow) {
        HSSFRow hSSFRow = (HSSFRow) iRow;
        if (iRow.getSheet() == this) {
            Iterator it = iRow.iterator();
            while (it.hasNext()) {
                HSSFCell hSSFCell = (HSSFCell) ((ICell) it.next());
                if (hSSFCell.isPartOfArrayFormulaGroup()) {
                    hSSFCell.notifyArrayFormulaChanging("Row[rownum=" + iRow.getRowNum() + "] contains cell(s) included in a multi-cell array formula. You cannot change part of an array.");
                }
            }
            if (this._rows.size() > 0) {
                if (this._rows.remove(Integer.valueOf(iRow.getRowNum())) == iRow) {
                    if (hSSFRow.getRowNum() == getLastRowNum()) {
                        this._lastrow = findLastRow(this._lastrow);
                    }
                    if (hSSFRow.getRowNum() == getFirstRowNum()) {
                        this._firstrow = findFirstRow(this._firstrow);
                    }
                    this._sheet.removeRow(hSSFRow.getRowRecord());
                    return;
                }
                throw new IllegalArgumentException("Specified row does not belong to this sheet");
            }
            return;
        }
        throw new IllegalArgumentException("Specified row does not belong to this sheet");
    }

    private int findLastRow(int i) {
        if (i < 1) {
            return 0;
        }
        int i2 = i - 1;
        HSSFRow row = getRow(i2);
        while (row == null && i2 > 0) {
            i2--;
            row = getRow(i2);
        }
        if (row == null) {
            return 0;
        }
        return i2;
    }

    private int findFirstRow(int i) {
        int i2 = i + 1;
        HSSFRow row = getRow(i2);
        while (row == null && i2 <= getLastRowNum()) {
            i2++;
            row = getRow(i2);
        }
        if (i2 > getLastRowNum()) {
            return 0;
        }
        return i2;
    }

    private void addRow(HSSFRow hSSFRow, boolean z) {
        this._rows.put(Integer.valueOf(hSSFRow.getRowNum()), hSSFRow);
        if (z) {
            this._sheet.addRow(hSSFRow.getRowRecord());
        }
        boolean z2 = true;
        if (this._rows.size() != 1) {
            z2 = false;
        }
        if (hSSFRow.getRowNum() > getLastRowNum() || z2) {
            this._lastrow = hSSFRow.getRowNum();
        }
        if (hSSFRow.getRowNum() < getFirstRowNum() || z2) {
            this._firstrow = hSSFRow.getRowNum();
        }
    }

    public HSSFRow getRow(int i) {
        return this._rows.get(Integer.valueOf(i));
    }

    public int getPhysicalNumberOfRows() {
        return this._rows.size();
    }

    public int getFirstRowNum() {
        return this._firstrow;
    }

    public int getLastRowNum() {
        return this._lastrow;
    }

    public void addValidationData(DataValidation dataValidation) {
        if (dataValidation != null) {
            this._sheet.getOrCreateDataValidityTable().addDataValidation(((HSSFDataValidation) dataValidation).createDVRecord(this));
            return;
        }
        throw new IllegalArgumentException("objValidation must not be null");
    }

    public void setColumnHidden(short s, boolean z) {
        setColumnHidden((int) s & 65535, z);
    }

    public boolean isColumnHidden(short s) {
        return isColumnHidden((int) s & 65535);
    }

    public void setColumnWidth(short s, short s2) {
        setColumnWidth((int) s & 65535, (int) s2 & 65535);
    }

    public short getColumnWidth(short s) {
        return (short) getColumnWidth((int) s & 65535);
    }

    public void setDefaultColumnWidth(short s) {
        setDefaultColumnWidth((int) s & 65535);
    }

    public void setColumnHidden(int i, boolean z) {
        this._sheet.setColumnHidden(i, z);
    }

    public boolean isColumnHidden(int i) {
        return this._sheet.isColumnHidden(i);
    }

    public List<ColumnInfo> getColumnInfo() {
        return this._sheet.getColumnInfo();
    }

    public void setColumnWidth(int i, int i2) {
        this._sheet.setColumnWidth(i, i2);
    }

    public int getColumnWidth(int i) {
        return this._sheet.getColumnWidth(i);
    }

    public int getColumnPixelWidth(int i) {
        return this._sheet.getColumnPixelWidth(i);
    }

    public void setColumnPixelWidth(int i, int i2) {
        this._sheet.setColumnPixelWidth(i, i2);
    }

    public int getDefaultColumnWidth() {
        return this._sheet.getDefaultColumnWidth();
    }

    public void setDefaultColumnWidth(int i) {
        this._sheet.setDefaultColumnWidth(i);
    }

    public short getDefaultRowHeight() {
        return this._sheet.getDefaultRowHeight();
    }

    public float getDefaultRowHeightInPoints() {
        return ((float) this._sheet.getDefaultRowHeight()) / 20.0f;
    }

    public void setDefaultRowHeight(short s) {
        this._sheet.setDefaultRowHeight(s);
    }

    public void setDefaultRowHeightInPoints(float f) {
        this._sheet.setDefaultRowHeight((short) ((int) (f * 20.0f)));
    }

    public HSSFCellStyle getColumnStyle(int i) {
        short xFIndexForColAt = this._sheet.getXFIndexForColAt((short) i);
        if (xFIndexForColAt == 15) {
            return null;
        }
        return new HSSFCellStyle(xFIndexForColAt, this._book.getExFormatAt(xFIndexForColAt), this._book);
    }

    public boolean isGridsPrinted() {
        return this._sheet.isGridsPrinted();
    }

    public void setGridsPrinted(boolean z) {
        this._sheet.setGridsPrinted(z);
    }

    public int addMergedRegion(Region region) {
        return this._sheet.addMergedRegion(region.getRowFrom(), region.getColumnFrom(), region.getRowTo(), region.getColumnTo());
    }

    public int addMergedRegion(HSSFCellRangeAddress hSSFCellRangeAddress) {
        hSSFCellRangeAddress.validate(SpreadsheetVersion.EXCEL97);
        validateArrayFormulas(hSSFCellRangeAddress);
        return this._sheet.addMergedRegion(hSSFCellRangeAddress.getFirstRow(), hSSFCellRangeAddress.getFirstColumn(), hSSFCellRangeAddress.getLastRow(), hSSFCellRangeAddress.getLastColumn());
    }

    private void validateArrayFormulas(HSSFCellRangeAddress hSSFCellRangeAddress) {
        HSSFCell cell;
        int firstColumn = hSSFCellRangeAddress.getFirstColumn();
        int lastRow = hSSFCellRangeAddress.getLastRow();
        int lastColumn = hSSFCellRangeAddress.getLastColumn();
        for (int firstRow = hSSFCellRangeAddress.getFirstRow(); firstRow <= lastRow; firstRow++) {
            for (int i = firstColumn; i <= lastColumn; i++) {
                HSSFRow row = getRow(firstRow);
                if (!(row == null || (cell = row.getCell(i)) == null || !cell.isPartOfArrayFormulaGroup())) {
                    HSSFCellRangeAddress arrayFormulaRange = cell.getArrayFormulaRange();
                    if (arrayFormulaRange.getNumberOfCells() > 1 && (arrayFormulaRange.isInRange(hSSFCellRangeAddress.getFirstRow(), hSSFCellRangeAddress.getFirstColumn()) || arrayFormulaRange.isInRange(hSSFCellRangeAddress.getFirstRow(), hSSFCellRangeAddress.getFirstColumn()))) {
                        throw new IllegalStateException("The range " + hSSFCellRangeAddress.formatAsString() + " intersects with a multi-cell array formula. You cannot merge cells of an array.");
                    }
                }
            }
        }
    }

    public void setForceFormulaRecalculation(boolean z) {
        this._sheet.setUncalced(z);
    }

    public boolean getForceFormulaRecalculation() {
        return this._sheet.getUncalced();
    }

    public void setVerticallyCenter(boolean z) {
        this._sheet.getPageSettings().getVCenter().setVCenter(z);
    }

    public boolean getVerticallyCenter(boolean z) {
        return getVerticallyCenter();
    }

    public boolean getVerticallyCenter() {
        return this._sheet.getPageSettings().getVCenter().getVCenter();
    }

    public void setHorizontallyCenter(boolean z) {
        this._sheet.getPageSettings().getHCenter().setHCenter(z);
    }

    public boolean getHorizontallyCenter() {
        return this._sheet.getPageSettings().getHCenter().getHCenter();
    }

    public void setRightToLeft(boolean z) {
        this._sheet.getWindowTwo().setArabic(z);
    }

    public boolean isRightToLeft() {
        return this._sheet.getWindowTwo().getArabic();
    }

    public void removeMergedRegion(int i) {
        this._sheet.removeMergedRegion(i);
    }

    public int getNumMergedRegions() {
        return this._sheet.getNumMergedRegions();
    }

    public com.app.office.fc.hssf.util.Region getMergedRegionAt(int i) {
        HSSFCellRangeAddress mergedRegion = getMergedRegion(i);
        return new com.app.office.fc.hssf.util.Region(mergedRegion.getFirstRow(), (short) mergedRegion.getFirstColumn(), mergedRegion.getLastRow(), (short) mergedRegion.getLastColumn());
    }

    public HSSFCellRangeAddress getMergedRegion(int i) {
        return this._sheet.getMergedRegionAt(i);
    }

    public Iterator<IRow> rowIterator() {
        return this._rows.values().iterator();
    }

    public Iterator<IRow> iterator() {
        return rowIterator();
    }

    /* access modifiers changed from: package-private */
    public InternalSheet getSheet() {
        return this._sheet;
    }

    public void setAlternativeExpression(boolean z) {
        ((WSBoolRecord) this._sheet.findFirstRecordBySid(129)).setAlternateExpression(z);
    }

    public void setAlternativeFormula(boolean z) {
        ((WSBoolRecord) this._sheet.findFirstRecordBySid(129)).setAlternateFormula(z);
    }

    public void setAutobreaks(boolean z) {
        ((WSBoolRecord) this._sheet.findFirstRecordBySid(129)).setAutobreaks(z);
    }

    public void setDialog(boolean z) {
        ((WSBoolRecord) this._sheet.findFirstRecordBySid(129)).setDialog(z);
    }

    public void setDisplayGuts(boolean z) {
        ((WSBoolRecord) this._sheet.findFirstRecordBySid(129)).setDisplayGuts(z);
    }

    public void setFitToPage(boolean z) {
        ((WSBoolRecord) this._sheet.findFirstRecordBySid(129)).setFitToPage(z);
    }

    public void setRowSumsBelow(boolean z) {
        WSBoolRecord wSBoolRecord = (WSBoolRecord) this._sheet.findFirstRecordBySid(129);
        wSBoolRecord.setRowSumsBelow(z);
        wSBoolRecord.setAlternateExpression(z);
    }

    public void setRowSumsRight(boolean z) {
        ((WSBoolRecord) this._sheet.findFirstRecordBySid(129)).setRowSumsRight(z);
    }

    public boolean getAlternateExpression() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid(129)).getAlternateExpression();
    }

    public boolean getAlternateFormula() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid(129)).getAlternateFormula();
    }

    public boolean getAutobreaks() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid(129)).getAutobreaks();
    }

    public boolean getDialog() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid(129)).getDialog();
    }

    public boolean getDisplayGuts() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid(129)).getDisplayGuts();
    }

    public boolean isDisplayZeros() {
        return this._sheet.getWindowTwo().getDisplayZeros();
    }

    public void setDisplayZeros(boolean z) {
        this._sheet.getWindowTwo().setDisplayZeros(z);
    }

    public boolean getFitToPage() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid(129)).getFitToPage();
    }

    public boolean getRowSumsBelow() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid(129)).getRowSumsBelow();
    }

    public boolean getRowSumsRight() {
        return ((WSBoolRecord) this._sheet.findFirstRecordBySid(129)).getRowSumsRight();
    }

    public boolean isPrintGridlines() {
        return getSheet().getPrintGridlines().getPrintGridlines();
    }

    public void setPrintGridlines(boolean z) {
        getSheet().getPrintGridlines().setPrintGridlines(z);
    }

    public HSSFPrintSetup getPrintSetup() {
        return new HSSFPrintSetup(this._sheet.getPageSettings().getPrintSetup());
    }

    public HSSFHeader getHeader() {
        return new HSSFHeader(this._sheet.getPageSettings());
    }

    public HSSFFooter getFooter() {
        return new HSSFFooter(this._sheet.getPageSettings());
    }

    public boolean isSelected() {
        return getSheet().getWindowTwo().getSelected();
    }

    public void setSelected(boolean z) {
        getSheet().getWindowTwo().setSelected(z);
    }

    public boolean isActive() {
        return getSheet().getWindowTwo().isActive();
    }

    public void setActive(boolean z) {
        getSheet().getWindowTwo().setActive(z);
    }

    public double getMargin(short s) {
        return this._sheet.getPageSettings().getMargin(s);
    }

    public void setMargin(short s, double d) {
        this._sheet.getPageSettings().setMargin(s, d);
    }

    private WorksheetProtectionBlock getProtectionBlock() {
        return this._sheet.getProtectionBlock();
    }

    public boolean getProtect() {
        return getProtectionBlock().isSheetProtected();
    }

    public short getPassword() {
        return (short) getProtectionBlock().getPasswordHash();
    }

    public boolean getObjectProtect() {
        return getProtectionBlock().isObjectProtected();
    }

    public boolean getScenarioProtect() {
        return getProtectionBlock().isScenarioProtected();
    }

    public void protectSheet(String str) {
        getProtectionBlock().protectSheet(str, true, true);
    }

    public void setZoom(int i, int i2) {
        if (i < 1 || i > 65535) {
            throw new IllegalArgumentException("Numerator must be greater than 1 and less than 65536");
        } else if (i2 < 1 || i2 > 65535) {
            throw new IllegalArgumentException("Denominator must be greater than 1 and less than 65536");
        } else {
            SCLRecord sCLRecord = new SCLRecord();
            sCLRecord.setNumerator((short) i);
            sCLRecord.setDenominator((short) i2);
            getSheet().setSCLRecord(sCLRecord);
        }
    }

    public short getTopRow() {
        return this._sheet.getTopRow();
    }

    public short getLeftCol() {
        return this._sheet.getLeftCol();
    }

    public void showInPane(short s, short s2) {
        this._sheet.setTopRow(s);
        this._sheet.setLeftCol(s2);
    }

    /* access modifiers changed from: protected */
    public void shiftMerged(int i, int i2, int i3, boolean z) {
        ArrayList<HSSFCellRangeAddress> arrayList = new ArrayList<>();
        int i4 = 0;
        while (i4 < getNumMergedRegions()) {
            HSSFCellRangeAddress mergedRegion = getMergedRegion(i4);
            boolean z2 = mergedRegion.getFirstRow() >= i || mergedRegion.getLastRow() >= i;
            boolean z3 = mergedRegion.getFirstRow() <= i2 || mergedRegion.getLastRow() <= i2;
            if (z2 && z3 && !SheetUtil.containsCell(mergedRegion, i - 1, 0) && !SheetUtil.containsCell(mergedRegion, i2 + 1, 0)) {
                mergedRegion.setFirstRow(mergedRegion.getFirstRow() + i3);
                mergedRegion.setLastRow(mergedRegion.getLastRow() + i3);
                arrayList.add(mergedRegion);
                removeMergedRegion(i4);
                i4--;
            }
            i4++;
        }
        for (HSSFCellRangeAddress addMergedRegion : arrayList) {
            addMergedRegion(addMergedRegion);
        }
    }

    public void shiftRows(int i, int i2, int i3) {
        shiftRows(i, i2, i3, false, false);
    }

    public void shiftRows(int i, int i2, int i3, boolean z, boolean z2) {
        shiftRows(i, i2, i3, z, z2, true);
    }

    public void shiftRows(int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
        int i4;
        int i5;
        NoteRecord[] noteRecordArr;
        HSSFComment cellComment;
        int i6 = i;
        int i7 = i2;
        int i8 = i3;
        if (i8 < 0) {
            i4 = i6;
            i5 = 1;
        } else if (i8 > 0) {
            i5 = -1;
            i4 = i7;
        } else {
            return;
        }
        if (z3) {
            noteRecordArr = this._sheet.getNoteRecords();
        } else {
            noteRecordArr = NoteRecord.EMPTY_ARRAY;
        }
        shiftMerged(i6, i7, i8, true);
        this._sheet.getPageSettings().shiftRowBreaks(i6, i7, i8);
        while (i4 >= i6 && i4 <= i7 && i4 >= 0 && i4 < 65536) {
            HSSFRow row = getRow(i4);
            if (row != null) {
                notifyRowShifting(row);
            }
            int i9 = i4 + i8;
            HSSFRow row2 = getRow(i9);
            if (row2 == null) {
                row2 = createRow(i9);
            }
            row2.removeAllCells();
            if (row != null) {
                if (z) {
                    row2.setHeight(row.getHeight());
                }
                if (z2) {
                    row.setHeight(255);
                }
                Iterator<ICell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    HSSFCell hSSFCell = (HSSFCell) cellIterator.next();
                    row.removeCell(hSSFCell);
                    CellValueRecordInterface cellValueRecord = hSSFCell.getCellValueRecord();
                    cellValueRecord.setRow(i9);
                    row2.createCellFromRecord(cellValueRecord);
                    this._sheet.addValueRecord(i9, cellValueRecord);
                    HSSFHyperlink hyperlink = hSSFCell.getHyperlink();
                    if (hyperlink != null) {
                        hyperlink.setFirstRow(hyperlink.getFirstRow() + i8);
                        hyperlink.setLastRow(hyperlink.getLastRow() + i8);
                    }
                }
                row.removeAllCells();
                if (z3) {
                    for (int length = noteRecordArr.length - 1; length >= 0; length--) {
                        NoteRecord noteRecord = noteRecordArr[length];
                        if (noteRecord.getRow() == i4 && (cellComment = getCellComment(i4, noteRecord.getColumn())) != null) {
                            cellComment.setRow(i9);
                        }
                    }
                }
            }
            i4 += i5;
        }
        if (i8 > 0) {
            if (i6 == this._firstrow) {
                int i10 = i6 + i8;
                this._firstrow = Math.max(i10, 0);
                int i11 = i6 + 1;
                while (true) {
                    if (i11 >= i10) {
                        break;
                    } else if (getRow(i11) != null) {
                        this._firstrow = i11;
                        break;
                    } else {
                        i11++;
                    }
                }
            }
            int i12 = i7 + i8;
            if (i12 > this._lastrow) {
                this._lastrow = Math.min(i12, SpreadsheetVersion.EXCEL97.getLastRowIndex());
            }
        } else {
            int i13 = i6 + i8;
            if (i13 < this._firstrow) {
                this._firstrow = Math.max(i13, 0);
            }
            if (i7 == this._lastrow) {
                int i14 = i7 + i8;
                this._lastrow = Math.min(i14, SpreadsheetVersion.EXCEL97.getLastRowIndex());
                int i15 = i7 - 1;
                while (true) {
                    if (i15 <= i14) {
                        break;
                    } else if (getRow(i15) != null) {
                        this._lastrow = i15;
                        break;
                    } else {
                        i15++;
                    }
                }
            }
        }
        short checkExternSheet = this._book.checkExternSheet(this._workbook.getSheetIndex((Sheet) this));
        FormulaShifter createForRowShift = FormulaShifter.createForRowShift(checkExternSheet, i6, i7, i8);
        this._sheet.updateFormulasAfterCellShift(createForRowShift, checkExternSheet);
        int numberOfSheets = this._workbook.getNumberOfSheets();
        for (int i16 = 0; i16 < numberOfSheets; i16++) {
            InternalSheet sheet = this._workbook.getSheetAt(i16).getSheet();
            if (sheet != this._sheet) {
                sheet.updateFormulasAfterCellShift(createForRowShift, this._book.checkExternSheet(i16));
            }
        }
        this._workbook.getWorkbook().updateNamesAfterCellShift(createForRowShift);
    }

    /* access modifiers changed from: protected */
    public void insertChartRecords(List<Record> list) {
        this._sheet.getRecords().addAll(this._sheet.findFirstRecordLocBySid(574), list);
    }

    private void notifyRowShifting(HSSFRow hSSFRow) {
        String str = "Row[rownum=" + hSSFRow.getRowNum() + "] contains cell(s) included in a multi-cell array formula. You cannot change part of an array.";
        Iterator it = hSSFRow.iterator();
        while (it.hasNext()) {
            HSSFCell hSSFCell = (HSSFCell) ((ICell) it.next());
            if (hSSFCell.isPartOfArrayFormulaGroup()) {
                hSSFCell.notifyArrayFormulaChanging(str);
            }
        }
    }

    public void createFreezePane(int i, int i2, int i3, int i4) {
        validateColumn(i);
        validateRow(i2);
        if (i3 < i) {
            throw new IllegalArgumentException("leftmostColumn parameter must not be less than colSplit parameter");
        } else if (i4 >= i2) {
            getSheet().createFreezePane(i, i2, i4, i3);
        } else {
            throw new IllegalArgumentException("topRow parameter must not be less than leftmostColumn parameter");
        }
    }

    public void createFreezePane(int i, int i2) {
        createFreezePane(i, i2, i, i2);
    }

    public void createSplitPane(int i, int i2, int i3, int i4, int i5) {
        getSheet().createSplitPane(i, i2, i4, i3, i5);
    }

    public HSSFPaneInformation getPaneInformation() {
        if (this.paneInformation == null) {
            this.paneInformation = getSheet().getPaneInformation();
        }
        return this.paneInformation;
    }

    public void setDisplayGridlines(boolean z) {
        this._sheet.setDisplayGridlines(z);
    }

    public boolean isDisplayGridlines() {
        return this._sheet.isDisplayGridlines();
    }

    public void setDisplayFormulas(boolean z) {
        this._sheet.setDisplayFormulas(z);
    }

    public boolean isDisplayFormulas() {
        return this._sheet.isDisplayFormulas();
    }

    public void setDisplayRowColHeadings(boolean z) {
        this._sheet.setDisplayRowColHeadings(z);
    }

    public boolean isDisplayRowColHeadings() {
        return this._sheet.isDisplayRowColHeadings();
    }

    public void setRowBreak(int i) {
        validateRow(i);
        this._sheet.getPageSettings().setRowBreak(i, 0, 255);
    }

    public boolean isRowBroken(int i) {
        return this._sheet.getPageSettings().isRowBroken(i);
    }

    public void removeRowBreak(int i) {
        this._sheet.getPageSettings().removeRowBreak(i);
    }

    public int[] getRowBreaks() {
        return this._sheet.getPageSettings().getRowBreaks();
    }

    public int[] getColumnBreaks() {
        return this._sheet.getPageSettings().getColumnBreaks();
    }

    public void setColumnBreak(int i) {
        short s = (short) i;
        validateColumn(s);
        this._sheet.getPageSettings().setColumnBreak(s, 0, (short) SpreadsheetVersion.EXCEL97.getLastRowIndex());
    }

    public boolean isColumnBroken(int i) {
        return this._sheet.getPageSettings().isColumnBroken(i);
    }

    public void removeColumnBreak(int i) {
        this._sheet.getPageSettings().removeColumnBreak(i);
    }

    /* access modifiers changed from: protected */
    public void validateRow(int i) {
        int lastRowIndex = SpreadsheetVersion.EXCEL97.getLastRowIndex();
        if (i > lastRowIndex) {
            throw new IllegalArgumentException("Maximum row number is " + lastRowIndex);
        } else if (i < 0) {
            throw new IllegalArgumentException("Minumum row number is 0");
        }
    }

    /* access modifiers changed from: protected */
    public void validateColumn(int i) {
        int lastColumnIndex = SpreadsheetVersion.EXCEL97.getLastColumnIndex();
        if (i > lastColumnIndex) {
            throw new IllegalArgumentException("Maximum column number is " + lastColumnIndex);
        } else if (i < 0) {
            throw new IllegalArgumentException("Minimum column number is 0");
        }
    }

    public void dumpDrawingRecords(boolean z) {
        this._sheet.aggregateDrawingRecords(this._book.getDrawingManager(), false);
        List<EscherRecord> escherRecords = ((EscherAggregate) getSheet().findFirstRecordBySid(EscherAggregate.sid)).getEscherRecords();
        PrintWriter printWriter = new PrintWriter(System.out);
        for (EscherRecord next : escherRecords) {
            if (z) {
                System.out.println(next.toString());
            } else {
                next.display(printWriter, 0);
            }
        }
        printWriter.flush();
    }

    public HSSFPatriarch createDrawingPatriarch() {
        if (this._patriarch == null) {
            this._workbook.initDrawings();
            if (this._patriarch == null) {
                this._sheet.aggregateDrawingRecords(this._book.getDrawingManager(), true);
                ((EscherAggregate) this._sheet.findFirstRecordBySid(EscherAggregate.sid)).setPatriarch(this._patriarch);
            }
        }
        return this._patriarch;
    }

    public EscherAggregate getDrawingEscherAggregate() {
        this._book.findDrawingGroup();
        if (this._book.getDrawingManager() == null || this._sheet.aggregateDrawingRecords(this._book.getDrawingManager(), false) == -1) {
            return null;
        }
        return (EscherAggregate) this._sheet.findFirstRecordBySid(EscherAggregate.sid);
    }

    public HSSFPatriarch getDrawingPatriarch() {
        HSSFPatriarch hSSFPatriarch = this._patriarch;
        if (hSSFPatriarch != null) {
            return hSSFPatriarch;
        }
        EscherAggregate drawingEscherAggregate = getDrawingEscherAggregate();
        if (drawingEscherAggregate == null) {
            return null;
        }
        drawingEscherAggregate.setPatriarch(this._patriarch);
        drawingEscherAggregate.convertRecordsToUserModel((AWorkbook) null);
        return this._patriarch;
    }

    public void setColumnGroupCollapsed(short s, boolean z) {
        setColumnGroupCollapsed((int) s & 65535, z);
    }

    public void groupColumn(short s, short s2) {
        groupColumn((int) s & 65535, (int) s2 & 65535);
    }

    public void ungroupColumn(short s, short s2) {
        ungroupColumn((int) s & 65535, (int) s2 & 65535);
    }

    public void setColumnGroupCollapsed(int i, boolean z) {
        this._sheet.setColumnGroupCollapsed(i, z);
    }

    public void groupColumn(int i, int i2) {
        this._sheet.groupColumnRange(i, i2, true);
    }

    public void ungroupColumn(int i, int i2) {
        this._sheet.groupColumnRange(i, i2, false);
    }

    public void groupRow(int i, int i2) {
        this._sheet.groupRowRange(i, i2, true);
    }

    public void ungroupRow(int i, int i2) {
        this._sheet.groupRowRange(i, i2, false);
    }

    public void setRowGroupCollapsed(int i, boolean z) {
        if (z) {
            this._sheet.getRowsAggregate().collapseRow(i);
        } else {
            this._sheet.getRowsAggregate().expandRow(i);
        }
    }

    public void setDefaultColumnStyle(int i, ICellStyle iCellStyle) {
        this._sheet.setDefaultColumnStyle(i, ((HSSFCellStyle) iCellStyle).getIndex());
    }

    public void autoSizeColumn(int i) {
        autoSizeColumn(i, false);
    }

    public void autoSizeColumn(int i, boolean z) {
        double columnWidth = SheetUtil.getColumnWidth(this, i, z);
        if (columnWidth != -1.0d) {
            double d = columnWidth * 256.0d;
            double d2 = (double) MotionEventCompat.ACTION_POINTER_INDEX_MASK;
            if (d > d2) {
                d = d2;
            }
            setColumnWidth(i, (int) d);
        }
    }

    public HSSFComment getCellComment(int i, int i2) {
        HSSFRow row = getRow(i);
        if (row == null) {
            return null;
        }
        HSSFCell cell = row.getCell(i2);
        if (cell != null) {
            return cell.getCellComment();
        }
        return HSSFCell.findCellComment(this._sheet, i, i2);
    }

    public HSSFSheetConditionalFormatting getSheetConditionalFormatting() {
        return new HSSFSheetConditionalFormatting(this);
    }

    public String getSheetName() {
        HSSFWorkbook workbook = getWorkbook();
        return workbook.getSheetName(workbook.getSheetIndex((Sheet) this));
    }

    private CellRange<HSSFCell> getCellRange(HSSFCellRangeAddress hSSFCellRangeAddress) {
        int firstRow = hSSFCellRangeAddress.getFirstRow();
        int firstColumn = hSSFCellRangeAddress.getFirstColumn();
        int lastRow = hSSFCellRangeAddress.getLastRow();
        int lastColumn = hSSFCellRangeAddress.getLastColumn();
        int i = (lastRow - firstRow) + 1;
        int i2 = (lastColumn - firstColumn) + 1;
        ArrayList arrayList = new ArrayList(i * i2);
        for (int i3 = firstRow; i3 <= lastRow; i3++) {
            for (int i4 = firstColumn; i4 <= lastColumn; i4++) {
                HSSFRow row = getRow(i3);
                if (row == null) {
                    row = createRow(i3);
                }
                HSSFCell cell = row.getCell(i4);
                if (cell == null) {
                    cell = row.createCell(i4);
                }
                arrayList.add(cell);
            }
        }
        return SSCellRange.create(firstRow, firstColumn, i, i2, arrayList, HSSFCell.class);
    }

    public CellRange<HSSFCell> removeArrayFormula(ICell iCell) {
        if (iCell.getSheet() == this) {
            CellValueRecordInterface cellValueRecord = ((HSSFCell) iCell).getCellValueRecord();
            if (cellValueRecord instanceof FormulaRecordAggregate) {
                CellRange<HSSFCell> cellRange = getCellRange(((FormulaRecordAggregate) cellValueRecord).removeArrayFormula(iCell.getRowIndex(), iCell.getColumnIndex()));
                for (HSSFCell cellType : cellRange) {
                    cellType.setCellType(3);
                }
                return cellRange;
            }
            String formatAsString = new CellReference(iCell).formatAsString();
            throw new IllegalArgumentException("Cell " + formatAsString + " is not part of an array formula.");
        }
        throw new IllegalArgumentException("Specified cell does not belong to this sheet.");
    }

    public DataValidationHelper getDataValidationHelper() {
        return new HSSFDataValidationHelper(this);
    }

    public HSSFAutoFilter setAutoFilter(HSSFCellRangeAddress hSSFCellRangeAddress) {
        InternalWorkbook workbook = this._workbook.getWorkbook();
        int sheetIndex = this._workbook.getSheetIndex((Sheet) this);
        int i = sheetIndex + 1;
        NameRecord specificBuiltinRecord = workbook.getSpecificBuiltinRecord((byte) 13, i);
        if (specificBuiltinRecord == null) {
            specificBuiltinRecord = workbook.createBuiltInName((byte) 13, i);
        }
        specificBuiltinRecord.setNameDefinition(new Ptg[]{new Area3DPtg(hSSFCellRangeAddress.getFirstRow(), hSSFCellRangeAddress.getLastRow(), hSSFCellRangeAddress.getFirstColumn(), hSSFCellRangeAddress.getLastColumn(), false, false, false, false, sheetIndex)});
        AutoFilterInfoRecord autoFilterInfoRecord = new AutoFilterInfoRecord();
        autoFilterInfoRecord.setNumEntries((short) ((hSSFCellRangeAddress.getLastColumn() + 1) - hSSFCellRangeAddress.getFirstColumn()));
        this._sheet.getRecords().add(this._sheet.findFirstRecordLocBySid(512), autoFilterInfoRecord);
        HSSFPatriarch createDrawingPatriarch = createDrawingPatriarch();
        int firstColumn = hSSFCellRangeAddress.getFirstColumn();
        while (firstColumn <= hSSFCellRangeAddress.getLastColumn()) {
            firstColumn++;
            createDrawingPatriarch.createComboBox(new HSSFClientAnchor(0, 0, 0, 0, (short) firstColumn, hSSFCellRangeAddress.getFirstRow(), (short) firstColumn, hSSFCellRangeAddress.getFirstRow() + 1));
        }
        return new HSSFAutoFilter(this);
    }

    public boolean isInitForDraw() {
        return this.isInitForDraw;
    }

    public void setInitForDraw(boolean z) {
        this.isInitForDraw = z;
    }

    public void setZoom(float f) {
        this.zoom = f;
    }

    public float getZoom() {
        return this.zoom;
    }

    public int getScrollX() {
        return this.scrollX;
    }

    public int getScrollY() {
        return this.scrollY;
    }

    public void setScroll(int i, int i2) {
        this.scrollX = i;
        this.scrollY = i2;
    }

    public int getActiveCellRow() {
        return this.row_activecell;
    }

    public void setActiveCell(int i, int i2) {
        this.row_activecell = i;
        this.column_activecell = i2;
    }

    public int getActiveCellColumn() {
        return this.column_activecell;
    }

    public HSSFCell getActiveCell() {
        if (getRow(this.row_activecell) != null) {
            return getRow(this.row_activecell).getCell(this.column_activecell);
        }
        return null;
    }
}
