package com.app.office.fc.hssf.model;

import com.app.office.common.shape.ShapeTypes;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.hssf.formula.FormulaShifter;
import com.app.office.fc.hssf.record.BOFRecord;
import com.app.office.fc.hssf.record.CalcCountRecord;
import com.app.office.fc.hssf.record.CalcModeRecord;
import com.app.office.fc.hssf.record.CellValueRecordInterface;
import com.app.office.fc.hssf.record.ColumnInfoRecord;
import com.app.office.fc.hssf.record.ContinueRecord;
import com.app.office.fc.hssf.record.DefaultColWidthRecord;
import com.app.office.fc.hssf.record.DefaultRowHeightRecord;
import com.app.office.fc.hssf.record.DeltaRecord;
import com.app.office.fc.hssf.record.DimensionsRecord;
import com.app.office.fc.hssf.record.DrawingRecord;
import com.app.office.fc.hssf.record.EOFRecord;
import com.app.office.fc.hssf.record.EscherAggregate;
import com.app.office.fc.hssf.record.GridsetRecord;
import com.app.office.fc.hssf.record.GutsRecord;
import com.app.office.fc.hssf.record.IterationRecord;
import com.app.office.fc.hssf.record.NoteRecord;
import com.app.office.fc.hssf.record.ObjRecord;
import com.app.office.fc.hssf.record.PaneRecord;
import com.app.office.fc.hssf.record.PrintGridlinesRecord;
import com.app.office.fc.hssf.record.PrintHeadersRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.RecordBase;
import com.app.office.fc.hssf.record.RefModeRecord;
import com.app.office.fc.hssf.record.RowRecord;
import com.app.office.fc.hssf.record.SCLRecord;
import com.app.office.fc.hssf.record.SaveRecalcRecord;
import com.app.office.fc.hssf.record.SelectionRecord;
import com.app.office.fc.hssf.record.TextObjectRecord;
import com.app.office.fc.hssf.record.UncalcedRecord;
import com.app.office.fc.hssf.record.WSBoolRecord;
import com.app.office.fc.hssf.record.WindowTwoRecord;
import com.app.office.fc.hssf.record.aggregates.ChartSubstreamRecordAggregate;
import com.app.office.fc.hssf.record.aggregates.ColumnInfoRecordsAggregate;
import com.app.office.fc.hssf.record.aggregates.ConditionalFormattingTable;
import com.app.office.fc.hssf.record.aggregates.CustomViewSettingsRecordAggregate;
import com.app.office.fc.hssf.record.aggregates.DataValidityTable;
import com.app.office.fc.hssf.record.aggregates.MergedCellsTable;
import com.app.office.fc.hssf.record.aggregates.PageSettingsBlock;
import com.app.office.fc.hssf.record.aggregates.RecordAggregate;
import com.app.office.fc.hssf.record.aggregates.RowRecordsAggregate;
import com.app.office.fc.hssf.record.aggregates.WorksheetProtectionBlock;
import com.app.office.fc.hssf.usermodel.HSSFAnchor;
import com.app.office.fc.hssf.usermodel.HSSFChart;
import com.app.office.fc.hssf.usermodel.HSSFShape;
import com.app.office.fc.hssf.util.ColumnInfo;
import com.app.office.fc.hssf.util.HSSFPaneInformation;
import com.app.office.fc.ss.util.HSSFCellRangeAddress;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import com.app.office.ss.model.XLSModel.AWorkbook;
import com.app.office.system.AbortReaderError;
import com.app.office.system.AbstractReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Internal
public final class InternalSheet {
    public static final short BottomMargin = 3;
    public static final short LeftMargin = 0;
    public static final byte PANE_LOWER_LEFT = 2;
    public static final byte PANE_LOWER_RIGHT = 0;
    public static final byte PANE_UPPER_LEFT = 3;
    public static final byte PANE_UPPER_RIGHT = 1;
    public static final short RightMargin = 1;
    public static final short TopMargin = 2;
    private static POILogger log = POILogFactory.getLogger(InternalSheet.class);
    ColumnInfoRecordsAggregate _columnInfos;
    private DataValidityTable _dataValidityTable;
    private DimensionsRecord _dimensions;
    private GutsRecord _gutsRecord;
    protected boolean _isUncalced;
    private final MergedCellsTable _mergedCellsTable;
    private final WorksheetProtectionBlock _protectionBlock;
    private PageSettingsBlock _psBlock;
    private List<RecordBase> _records;
    protected final RowRecordsAggregate _rowsAggregate;
    protected SelectionRecord _selection;
    private ConditionalFormattingTable condFormatting;
    protected DefaultColWidthRecord defaultcolwidth;
    protected DefaultRowHeightRecord defaultrowheight;
    protected GridsetRecord gridset;
    protected PrintGridlinesRecord printGridlines;
    private Iterator<RowRecord> rowRecIterator;
    private int sheetType;
    protected WindowTwoRecord windowTwo;

    public static InternalSheet createSheet(RecordStream recordStream) {
        return new InternalSheet(recordStream, (AbstractReader) null);
    }

    public static InternalSheet createSheet(RecordStream recordStream, AbstractReader abstractReader) {
        return new InternalSheet(recordStream, abstractReader);
    }

    private InternalSheet(RecordStream recordStream, AbstractReader abstractReader) {
        RowRecordsAggregate rowRecordsAggregate = null;
        this.printGridlines = null;
        this.gridset = null;
        this.defaultcolwidth = new DefaultColWidthRecord();
        this.defaultrowheight = new DefaultRowHeightRecord();
        this._protectionBlock = new WorksheetProtectionBlock();
        this.windowTwo = null;
        this._selection = null;
        this._dataValidityTable = null;
        this.rowRecIterator = null;
        this._isUncalced = false;
        this.sheetType = 16;
        this._mergedCellsTable = new MergedCellsTable();
        ArrayList arrayList = new ArrayList(128);
        this._records = arrayList;
        if (recordStream.peekNextSid() == 2057) {
            BOFRecord bOFRecord = (BOFRecord) recordStream.getNext();
            if (bOFRecord.getType() != 16) {
                this.sheetType = bOFRecord.getType();
            }
            arrayList.add(bOFRecord);
            int i = -1;
            while (true) {
                if (!recordStream.hasNext()) {
                    break;
                } else if (abstractReader == null || !abstractReader.isAborted()) {
                    int peekNextSid = recordStream.peekNextSid();
                    if (peekNextSid == 432) {
                        ConditionalFormattingTable conditionalFormattingTable = new ConditionalFormattingTable(recordStream);
                        this.condFormatting = conditionalFormattingTable;
                        arrayList.add(conditionalFormattingTable);
                    } else if (peekNextSid == 125) {
                        ColumnInfoRecordsAggregate columnInfoRecordsAggregate = new ColumnInfoRecordsAggregate(recordStream);
                        this._columnInfos = columnInfoRecordsAggregate;
                        arrayList.add(columnInfoRecordsAggregate);
                    } else if (peekNextSid == 434) {
                        DataValidityTable dataValidityTable = new DataValidityTable(recordStream);
                        this._dataValidityTable = dataValidityTable;
                        arrayList.add(dataValidityTable);
                    } else if (RecordOrderer.isRowBlockRecord(peekNextSid)) {
                        if (rowRecordsAggregate == null) {
                            RowBlocksReader rowBlocksReader = new RowBlocksReader(recordStream);
                            this._mergedCellsTable.addRecords(rowBlocksReader.getLooseMergedCells());
                            RowRecordsAggregate rowRecordsAggregate2 = new RowRecordsAggregate(rowBlocksReader.getPlainRecordStream(), rowBlocksReader.getSharedFormulaManager());
                            arrayList.add(rowRecordsAggregate2);
                            rowRecordsAggregate = rowRecordsAggregate2;
                        } else {
                            throw new RuntimeException("row/cell records found in the wrong place");
                        }
                    } else if (CustomViewSettingsRecordAggregate.isBeginRecord(peekNextSid)) {
                        arrayList.add(new CustomViewSettingsRecordAggregate(recordStream));
                    } else if (PageSettingsBlock.isComponentRecord(peekNextSid)) {
                        PageSettingsBlock pageSettingsBlock = this._psBlock;
                        if (pageSettingsBlock == null) {
                            PageSettingsBlock pageSettingsBlock2 = new PageSettingsBlock(recordStream);
                            this._psBlock = pageSettingsBlock2;
                            arrayList.add(pageSettingsBlock2);
                        } else {
                            pageSettingsBlock.addLateRecords(recordStream);
                        }
                        this._psBlock.positionRecords(arrayList);
                    } else if (WorksheetProtectionBlock.isComponentRecord(peekNextSid)) {
                        this._protectionBlock.addRecords(recordStream);
                    } else if (peekNextSid == 229) {
                        this._mergedCellsTable.read(recordStream);
                    } else if (peekNextSid == 2057) {
                        spillAggregate(new ChartSubstreamRecordAggregate(recordStream), arrayList);
                    } else {
                        Record next = recordStream.getNext();
                        if (peekNextSid == 523) {
                            continue;
                        } else if (peekNextSid == 94) {
                            this._isUncalced = true;
                        } else if (peekNextSid == 2152 || peekNextSid == 2151) {
                            arrayList.add(next);
                        } else if (peekNextSid == 10) {
                            arrayList.add(next);
                            break;
                        } else {
                            if (peekNextSid == 512) {
                                if (this._columnInfos == null) {
                                    ColumnInfoRecordsAggregate columnInfoRecordsAggregate2 = new ColumnInfoRecordsAggregate();
                                    this._columnInfos = columnInfoRecordsAggregate2;
                                    arrayList.add(columnInfoRecordsAggregate2);
                                }
                                this._dimensions = (DimensionsRecord) next;
                                i = arrayList.size();
                            } else if (peekNextSid == 85) {
                                this.defaultcolwidth = (DefaultColWidthRecord) next;
                            } else if (peekNextSid == 549) {
                                this.defaultrowheight = (DefaultRowHeightRecord) next;
                            } else if (peekNextSid == 43) {
                                this.printGridlines = (PrintGridlinesRecord) next;
                            } else if (peekNextSid == 130) {
                                this.gridset = (GridsetRecord) next;
                            } else if (peekNextSid == 29) {
                                this._selection = (SelectionRecord) next;
                            } else if (peekNextSid == 574) {
                                this.windowTwo = (WindowTwoRecord) next;
                            } else if (peekNextSid == 128) {
                                this._gutsRecord = (GutsRecord) next;
                            }
                            arrayList.add(next);
                        }
                    }
                } else {
                    throw new AbortReaderError("abort Reader");
                }
            }
            if (this.windowTwo != null) {
                if (this._dimensions == null) {
                    if (rowRecordsAggregate == null) {
                        rowRecordsAggregate = new RowRecordsAggregate();
                    } else {
                        log.log(POILogger.WARN, (Object) "DIMENSION record not found even though row/cells present");
                    }
                    i = findFirstRecordLocBySid(574);
                    DimensionsRecord createDimensions = rowRecordsAggregate.createDimensions();
                    this._dimensions = createDimensions;
                    arrayList.add(i, createDimensions);
                }
                if (rowRecordsAggregate == null) {
                    rowRecordsAggregate = new RowRecordsAggregate();
                    arrayList.add(i + 1, rowRecordsAggregate);
                }
                this._rowsAggregate = rowRecordsAggregate;
                RecordOrderer.addNewSheetRecord(arrayList, this._mergedCellsTable);
                RecordOrderer.addNewSheetRecord(arrayList, this._protectionBlock);
                if (log.check(POILogger.DEBUG)) {
                    log.log(POILogger.DEBUG, (Object) "sheet createSheet (existing file) exited");
                    return;
                }
                return;
            }
            throw new RuntimeException("WINDOW2 was not found");
        }
        throw new RuntimeException("BOF record expected");
    }

    public boolean isChartSheet() {
        return this.sheetType == 32;
    }

    private static void spillAggregate(RecordAggregate recordAggregate, final List<RecordBase> list) {
        recordAggregate.visitContainedRecords(new RecordAggregate.RecordVisitor() {
            public void visitRecord(Record record) {
                list.add(record);
            }
        });
    }

    private static final class RecordCloner implements RecordAggregate.RecordVisitor {
        private final List<Record> _destList;

        public RecordCloner(List<Record> list) {
            this._destList = list;
        }

        public void visitRecord(Record record) {
            this._destList.add((Record) record.clone());
        }
    }

    public InternalSheet cloneSheet() {
        ArrayList arrayList = new ArrayList(this._records.size());
        for (int i = 0; i < this._records.size(); i++) {
            RecordBase recordBase = this._records.get(i);
            if (recordBase instanceof RecordAggregate) {
                ((RecordAggregate) recordBase).visitContainedRecords(new RecordCloner(arrayList));
            } else {
                arrayList.add((Record) ((Record) recordBase).clone());
            }
        }
        return createSheet(new RecordStream(arrayList, 0));
    }

    public static InternalSheet createSheet() {
        return new InternalSheet();
    }

    private InternalSheet() {
        this.printGridlines = null;
        this.gridset = null;
        this.defaultcolwidth = new DefaultColWidthRecord();
        this.defaultrowheight = new DefaultRowHeightRecord();
        WorksheetProtectionBlock worksheetProtectionBlock = new WorksheetProtectionBlock();
        this._protectionBlock = worksheetProtectionBlock;
        this.windowTwo = null;
        this._selection = null;
        this._dataValidityTable = null;
        this.rowRecIterator = null;
        this._isUncalced = false;
        this.sheetType = 16;
        MergedCellsTable mergedCellsTable = new MergedCellsTable();
        this._mergedCellsTable = mergedCellsTable;
        ArrayList arrayList = new ArrayList(32);
        if (log.check(POILogger.DEBUG)) {
            log.log(POILogger.DEBUG, (Object) "Sheet createsheet from scratch called");
        }
        arrayList.add(createBOF());
        arrayList.add(createCalcMode());
        arrayList.add(createCalcCount());
        arrayList.add(createRefMode());
        arrayList.add(createIteration());
        arrayList.add(createDelta());
        arrayList.add(createSaveRecalc());
        arrayList.add(createPrintHeaders());
        PrintGridlinesRecord createPrintGridlines = createPrintGridlines();
        this.printGridlines = createPrintGridlines;
        arrayList.add(createPrintGridlines);
        GridsetRecord createGridset = createGridset();
        this.gridset = createGridset;
        arrayList.add(createGridset);
        GutsRecord createGuts = createGuts();
        this._gutsRecord = createGuts;
        arrayList.add(createGuts);
        DefaultRowHeightRecord createDefaultRowHeight = createDefaultRowHeight();
        this.defaultrowheight = createDefaultRowHeight;
        arrayList.add(createDefaultRowHeight);
        arrayList.add(createWSBool());
        PageSettingsBlock pageSettingsBlock = new PageSettingsBlock();
        this._psBlock = pageSettingsBlock;
        arrayList.add(pageSettingsBlock);
        arrayList.add(worksheetProtectionBlock);
        DefaultColWidthRecord createDefaultColWidth = createDefaultColWidth();
        this.defaultcolwidth = createDefaultColWidth;
        arrayList.add(createDefaultColWidth);
        ColumnInfoRecordsAggregate columnInfoRecordsAggregate = new ColumnInfoRecordsAggregate();
        arrayList.add(columnInfoRecordsAggregate);
        this._columnInfos = columnInfoRecordsAggregate;
        DimensionsRecord createDimensions = createDimensions();
        this._dimensions = createDimensions;
        arrayList.add(createDimensions);
        RowRecordsAggregate rowRecordsAggregate = new RowRecordsAggregate();
        this._rowsAggregate = rowRecordsAggregate;
        arrayList.add(rowRecordsAggregate);
        WindowTwoRecord createWindowTwo = createWindowTwo();
        this.windowTwo = createWindowTwo;
        arrayList.add(createWindowTwo);
        SelectionRecord createSelection = createSelection();
        this._selection = createSelection;
        arrayList.add(createSelection);
        arrayList.add(mergedCellsTable);
        arrayList.add(EOFRecord.instance);
        this._records = arrayList;
        if (log.check(POILogger.DEBUG)) {
            log.log(POILogger.DEBUG, (Object) "Sheet createsheet from scratch exit");
        }
    }

    public RowRecordsAggregate getRowsAggregate() {
        return this._rowsAggregate;
    }

    private MergedCellsTable getMergedRecords() {
        return this._mergedCellsTable;
    }

    public void updateFormulasAfterCellShift(FormulaShifter formulaShifter, int i) {
        getRowsAggregate().updateFormulasAfterRowShift(formulaShifter, i);
        if (this.condFormatting != null) {
            getConditionalFormattingTable().updateFormulasAfterCellShift(formulaShifter, i);
        }
    }

    public int addMergedRegion(int i, int i2, int i3, int i4) {
        if (i3 < i) {
            throw new IllegalArgumentException("The 'to' row (" + i3 + ") must not be less than the 'from' row (" + i + ")");
        } else if (i4 >= i2) {
            MergedCellsTable mergedRecords = getMergedRecords();
            mergedRecords.addArea(i, i2, i3, i4);
            return mergedRecords.getNumberOfMergedRegions() - 1;
        } else {
            throw new IllegalArgumentException("The 'to' col (" + i4 + ") must not be less than the 'from' col (" + i2 + ")");
        }
    }

    public void removeMergedRegion(int i) {
        MergedCellsTable mergedRecords = getMergedRecords();
        if (i < mergedRecords.getNumberOfMergedRegions()) {
            mergedRecords.remove(i);
        }
    }

    public HSSFCellRangeAddress getMergedRegionAt(int i) {
        MergedCellsTable mergedRecords = getMergedRecords();
        if (i >= mergedRecords.getNumberOfMergedRegions()) {
            return null;
        }
        return mergedRecords.get(i);
    }

    public int getNumMergedRegions() {
        return getMergedRecords().getNumberOfMergedRegions();
    }

    public ConditionalFormattingTable getConditionalFormattingTable() {
        if (this.condFormatting == null) {
            ConditionalFormattingTable conditionalFormattingTable = new ConditionalFormattingTable();
            this.condFormatting = conditionalFormattingTable;
            RecordOrderer.addNewSheetRecord(this._records, conditionalFormattingTable);
        }
        return this.condFormatting;
    }

    public void setDimensions(int i, short s, int i2, short s2) {
        if (log.check(POILogger.DEBUG)) {
            log.log(POILogger.DEBUG, (Object) "Sheet.setDimensions");
            POILogger pOILogger = log;
            int i3 = POILogger.DEBUG;
            StringBuffer stringBuffer = new StringBuffer("firstrow");
            stringBuffer.append(i);
            stringBuffer.append("firstcol");
            stringBuffer.append(s);
            stringBuffer.append("lastrow");
            stringBuffer.append(i2);
            stringBuffer.append("lastcol");
            stringBuffer.append(s2);
            pOILogger.log(i3, (Object) stringBuffer.toString());
        }
        this._dimensions.setFirstCol(s);
        this._dimensions.setFirstRow(i);
        this._dimensions.setLastCol(s2);
        this._dimensions.setLastRow(i2);
        if (log.check(POILogger.DEBUG)) {
            log.log(POILogger.DEBUG, (Object) "Sheet.setDimensions exiting");
        }
    }

    public void visitContainedRecords(RecordAggregate.RecordVisitor recordVisitor, int i) {
        RecordAggregate.PositionTrackingVisitor positionTrackingVisitor = new RecordAggregate.PositionTrackingVisitor(recordVisitor, i);
        boolean z = false;
        for (int i2 = 0; i2 < this._records.size(); i2++) {
            RecordBase recordBase = this._records.get(i2);
            if (recordBase instanceof RecordAggregate) {
                ((RecordAggregate) recordBase).visitContainedRecords(positionTrackingVisitor);
            } else {
                positionTrackingVisitor.visitRecord((Record) recordBase);
            }
            if ((recordBase instanceof BOFRecord) && !z) {
                if (this._isUncalced) {
                    positionTrackingVisitor.visitRecord(new UncalcedRecord());
                }
                if (this._rowsAggregate != null) {
                    int sizeOfInitialSheetRecords = getSizeOfInitialSheetRecords(i2);
                    positionTrackingVisitor.visitRecord(this._rowsAggregate.createIndexRecord(positionTrackingVisitor.getPosition(), sizeOfInitialSheetRecords));
                }
                z = true;
            }
        }
    }

    private int getSizeOfInitialSheetRecords(int i) {
        int i2 = 0;
        for (int i3 = i + 1; i3 < this._records.size(); i3++) {
            RecordBase recordBase = this._records.get(i3);
            if (recordBase instanceof RowRecordsAggregate) {
                break;
            }
            i2 += recordBase.getRecordSize();
        }
        return this._isUncalced ? i2 + UncalcedRecord.getStaticRecordSize() : i2;
    }

    public void addValueRecord(int i, CellValueRecordInterface cellValueRecordInterface) {
        if (log.check(POILogger.DEBUG)) {
            POILogger pOILogger = log;
            int i2 = POILogger.DEBUG;
            pOILogger.log(i2, (Object) "add value record  row" + i);
        }
        DimensionsRecord dimensionsRecord = this._dimensions;
        if (cellValueRecordInterface.getColumn() > dimensionsRecord.getLastCol()) {
            dimensionsRecord.setLastCol((short) (cellValueRecordInterface.getColumn() + 1));
        }
        if (cellValueRecordInterface.getColumn() < dimensionsRecord.getFirstCol()) {
            dimensionsRecord.setFirstCol(cellValueRecordInterface.getColumn());
        }
        this._rowsAggregate.insertCell(cellValueRecordInterface);
    }

    public void removeValueRecord(int i, CellValueRecordInterface cellValueRecordInterface) {
        log.logFormatted(POILogger.DEBUG, "remove value record row %", new int[]{i});
        this._rowsAggregate.removeCell(cellValueRecordInterface);
    }

    public void replaceValueRecord(CellValueRecordInterface cellValueRecordInterface) {
        if (log.check(POILogger.DEBUG)) {
            log.log(POILogger.DEBUG, (Object) "replaceValueRecord ");
        }
        this._rowsAggregate.removeCell(cellValueRecordInterface);
        this._rowsAggregate.insertCell(cellValueRecordInterface);
    }

    public void addRow(RowRecord rowRecord) {
        if (log.check(POILogger.DEBUG)) {
            log.log(POILogger.DEBUG, (Object) "addRow ");
        }
        DimensionsRecord dimensionsRecord = this._dimensions;
        if (rowRecord.getRowNumber() >= dimensionsRecord.getLastRow()) {
            dimensionsRecord.setLastRow(rowRecord.getRowNumber() + 1);
        }
        if (rowRecord.getRowNumber() < dimensionsRecord.getFirstRow()) {
            dimensionsRecord.setFirstRow(rowRecord.getRowNumber());
        }
        RowRecord row = this._rowsAggregate.getRow(rowRecord.getRowNumber());
        if (row != null) {
            this._rowsAggregate.removeRow(row);
        }
        this._rowsAggregate.insertRow(rowRecord);
        if (log.check(POILogger.DEBUG)) {
            log.log(POILogger.DEBUG, (Object) "exit addRow");
        }
    }

    public void removeRow(RowRecord rowRecord) {
        this._rowsAggregate.removeRow(rowRecord);
    }

    public Iterator<CellValueRecordInterface> getCellValueIterator() {
        return this._rowsAggregate.getCellValueIterator();
    }

    @Deprecated
    public CellValueRecordInterface[] getValueRecords() {
        return this._rowsAggregate.getValueRecords();
    }

    public RowRecord getNextRow() {
        if (this.rowRecIterator == null) {
            this.rowRecIterator = this._rowsAggregate.getIterator();
        }
        if (!this.rowRecIterator.hasNext()) {
            return null;
        }
        RowRecord next = this.rowRecIterator.next();
        this.rowRecIterator.remove();
        return next;
    }

    public RowRecord getRow(int i) {
        return this._rowsAggregate.getRow(i);
    }

    static BOFRecord createBOF() {
        BOFRecord bOFRecord = new BOFRecord();
        bOFRecord.setVersion(BOFRecord.VERSION);
        bOFRecord.setType(16);
        bOFRecord.setBuild(3515);
        bOFRecord.setBuildYear(BOFRecord.BUILD_YEAR);
        bOFRecord.setHistoryBitMask(ShapeTypes.ActionButtonForwardNext);
        bOFRecord.setRequiredVersion(6);
        return bOFRecord;
    }

    private static CalcModeRecord createCalcMode() {
        CalcModeRecord calcModeRecord = new CalcModeRecord();
        calcModeRecord.setCalcMode(1);
        return calcModeRecord;
    }

    private static CalcCountRecord createCalcCount() {
        CalcCountRecord calcCountRecord = new CalcCountRecord();
        calcCountRecord.setIterations(100);
        return calcCountRecord;
    }

    private static RefModeRecord createRefMode() {
        RefModeRecord refModeRecord = new RefModeRecord();
        refModeRecord.setMode(1);
        return refModeRecord;
    }

    private static IterationRecord createIteration() {
        return new IterationRecord(false);
    }

    private static DeltaRecord createDelta() {
        return new DeltaRecord(0.001d);
    }

    private static SaveRecalcRecord createSaveRecalc() {
        SaveRecalcRecord saveRecalcRecord = new SaveRecalcRecord();
        saveRecalcRecord.setRecalc(true);
        return saveRecalcRecord;
    }

    private static PrintHeadersRecord createPrintHeaders() {
        PrintHeadersRecord printHeadersRecord = new PrintHeadersRecord();
        printHeadersRecord.setPrintHeaders(false);
        return printHeadersRecord;
    }

    private static PrintGridlinesRecord createPrintGridlines() {
        PrintGridlinesRecord printGridlinesRecord = new PrintGridlinesRecord();
        printGridlinesRecord.setPrintGridlines(false);
        return printGridlinesRecord;
    }

    private static GridsetRecord createGridset() {
        GridsetRecord gridsetRecord = new GridsetRecord();
        gridsetRecord.setGridset(true);
        return gridsetRecord;
    }

    private static GutsRecord createGuts() {
        GutsRecord gutsRecord = new GutsRecord();
        gutsRecord.setLeftRowGutter(0);
        gutsRecord.setTopColGutter(0);
        gutsRecord.setRowLevelMax(0);
        gutsRecord.setColLevelMax(0);
        return gutsRecord;
    }

    private GutsRecord getGutsRecord() {
        if (this._gutsRecord == null) {
            GutsRecord createGuts = createGuts();
            RecordOrderer.addNewSheetRecord(this._records, createGuts);
            this._gutsRecord = createGuts;
        }
        return this._gutsRecord;
    }

    private static DefaultRowHeightRecord createDefaultRowHeight() {
        DefaultRowHeightRecord defaultRowHeightRecord = new DefaultRowHeightRecord();
        defaultRowHeightRecord.setOptionFlags(0);
        defaultRowHeightRecord.setRowHeight(255);
        return defaultRowHeightRecord;
    }

    private static WSBoolRecord createWSBool() {
        WSBoolRecord wSBoolRecord = new WSBoolRecord();
        wSBoolRecord.setWSBool1((byte) 4);
        wSBoolRecord.setWSBool2((byte) -63);
        return wSBoolRecord;
    }

    private static DefaultColWidthRecord createDefaultColWidth() {
        DefaultColWidthRecord defaultColWidthRecord = new DefaultColWidthRecord();
        defaultColWidthRecord.setColWidth(8);
        return defaultColWidthRecord;
    }

    public int getDefaultColumnWidth() {
        return this.defaultcolwidth.getColWidth();
    }

    public boolean isGridsPrinted() {
        if (this.gridset == null) {
            this.gridset = createGridset();
            this._records.add(findFirstRecordLocBySid(10), this.gridset);
        }
        return !this.gridset.getGridset();
    }

    public void setGridsPrinted(boolean z) {
        this.gridset.setGridset(!z);
    }

    public void setDefaultColumnWidth(int i) {
        this.defaultcolwidth.setColWidth(i);
    }

    public void setDefaultRowHeight(short s) {
        this.defaultrowheight.setRowHeight(s);
    }

    public short getDefaultRowHeight() {
        return this.defaultrowheight.getRowHeight();
    }

    public int getColumnWidth(int i) {
        ColumnInfoRecord findColumnInfo = this._columnInfos.findColumnInfo(i);
        if (findColumnInfo != null) {
            return findColumnInfo.getColumnWidth();
        }
        return this.defaultcolwidth.getColWidth() * 256;
    }

    public int getColumnPixelWidth(int i) {
        ColumnInfoRecord findColumnInfo = this._columnInfos.findColumnInfo(i);
        if (findColumnInfo != null) {
            return findColumnInfo.getColPixelWidth();
        }
        return 80;
    }

    public void setColumnPixelWidth(int i, int i2) {
        ColumnInfoRecord findColumnInfo = this._columnInfos.findColumnInfo(i);
        if (findColumnInfo != null) {
            findColumnInfo.setColPixelWidth(i2);
        }
    }

    public short getXFIndexForColAt(short s) {
        ColumnInfoRecord findColumnInfo = this._columnInfos.findColumnInfo(s);
        if (findColumnInfo != null) {
            return (short) findColumnInfo.getXFIndex();
        }
        return 15;
    }

    public void setColumnWidth(int i, int i2) {
        if (i2 <= 65280) {
            setColumn(i, (Short) null, Integer.valueOf(i2), (Integer) null, (Boolean) null, (Boolean) null);
            return;
        }
        throw new IllegalArgumentException("The maximum column width for an individual cell is 255 characters.");
    }

    public boolean isColumnHidden(int i) {
        ColumnInfoRecord findColumnInfo = this._columnInfos.findColumnInfo(i);
        if (findColumnInfo == null) {
            return false;
        }
        return findColumnInfo.getHidden();
    }

    public List<ColumnInfo> getColumnInfo() {
        ColumnInfoRecordsAggregate columnInfoRecordsAggregate = this._columnInfos;
        if (columnInfoRecordsAggregate == null) {
            return null;
        }
        int numColumns = columnInfoRecordsAggregate.getNumColumns();
        ArrayList arrayList = new ArrayList(5);
        for (int i = 0; i < numColumns; i++) {
            ColumnInfoRecord colInfo = this._columnInfos.getColInfo(i);
            arrayList.add(new ColumnInfo(colInfo.getFirstColumn(), colInfo.getLastColumn(), colInfo.getColumnWidth(), colInfo.getXFIndex(), colInfo.getHidden()));
        }
        return arrayList;
    }

    public void setColumnHidden(int i, boolean z) {
        setColumn(i, (Short) null, (Integer) null, (Integer) null, Boolean.valueOf(z), (Boolean) null);
    }

    public void setDefaultColumnStyle(int i, int i2) {
        setColumn(i, Short.valueOf((short) i2), (Integer) null, (Integer) null, (Boolean) null, (Boolean) null);
    }

    private void setColumn(int i, Short sh, Integer num, Integer num2, Boolean bool, Boolean bool2) {
        this._columnInfos.setColumn(i, sh, num, num2, bool, bool2);
    }

    public void groupColumnRange(int i, int i2, boolean z) {
        this._columnInfos.groupColumnRange(i, i2, z);
        int maxOutlineLevel = this._columnInfos.getMaxOutlineLevel();
        GutsRecord gutsRecord = getGutsRecord();
        gutsRecord.setColLevelMax((short) (maxOutlineLevel + 1));
        if (maxOutlineLevel == 0) {
            gutsRecord.setTopColGutter(0);
        } else {
            gutsRecord.setTopColGutter((short) (((maxOutlineLevel - 1) * 12) + 29));
        }
    }

    private static DimensionsRecord createDimensions() {
        DimensionsRecord dimensionsRecord = new DimensionsRecord();
        dimensionsRecord.setFirstCol(0);
        dimensionsRecord.setLastRow(1);
        dimensionsRecord.setFirstRow(0);
        dimensionsRecord.setLastCol(1);
        return dimensionsRecord;
    }

    private static WindowTwoRecord createWindowTwo() {
        WindowTwoRecord windowTwoRecord = new WindowTwoRecord();
        windowTwoRecord.setOptions(1718);
        windowTwoRecord.setTopRow(0);
        windowTwoRecord.setLeftCol(0);
        windowTwoRecord.setHeaderColor(64);
        windowTwoRecord.setPageBreakZoom(0);
        windowTwoRecord.setNormalZoom(0);
        return windowTwoRecord;
    }

    private static SelectionRecord createSelection() {
        return new SelectionRecord(0, 0);
    }

    public short getTopRow() {
        WindowTwoRecord windowTwoRecord = this.windowTwo;
        if (windowTwoRecord == null) {
            return 0;
        }
        return windowTwoRecord.getTopRow();
    }

    public void setTopRow(short s) {
        WindowTwoRecord windowTwoRecord = this.windowTwo;
        if (windowTwoRecord != null) {
            windowTwoRecord.setTopRow(s);
        }
    }

    public void setLeftCol(short s) {
        WindowTwoRecord windowTwoRecord = this.windowTwo;
        if (windowTwoRecord != null) {
            windowTwoRecord.setLeftCol(s);
        }
    }

    public short getLeftCol() {
        WindowTwoRecord windowTwoRecord = this.windowTwo;
        if (windowTwoRecord == null) {
            return 0;
        }
        return windowTwoRecord.getLeftCol();
    }

    public int getActiveCellRow() {
        SelectionRecord selectionRecord = this._selection;
        if (selectionRecord == null) {
            return 0;
        }
        return selectionRecord.getActiveCellRow();
    }

    public void setActiveCellRow(int i) {
        SelectionRecord selectionRecord = this._selection;
        if (selectionRecord != null) {
            selectionRecord.setActiveCellRow(i);
        }
    }

    public short getActiveCellCol() {
        SelectionRecord selectionRecord = this._selection;
        if (selectionRecord == null) {
            return 0;
        }
        return (short) selectionRecord.getActiveCellCol();
    }

    public void setActiveCellCol(short s) {
        SelectionRecord selectionRecord = this._selection;
        if (selectionRecord != null) {
            selectionRecord.setActiveCellCol(s);
        }
    }

    public List<RecordBase> getRecords() {
        return this._records;
    }

    public GridsetRecord getGridsetRecord() {
        return this.gridset;
    }

    public Record findFirstRecordBySid(short s) {
        int findFirstRecordLocBySid = findFirstRecordLocBySid(s);
        if (findFirstRecordLocBySid < 0) {
            return null;
        }
        return (Record) this._records.get(findFirstRecordLocBySid);
    }

    public void setSCLRecord(SCLRecord sCLRecord) {
        int findFirstRecordLocBySid = findFirstRecordLocBySid(SCLRecord.sid);
        if (findFirstRecordLocBySid == -1) {
            this._records.add(findFirstRecordLocBySid(574) + 1, sCLRecord);
            return;
        }
        this._records.set(findFirstRecordLocBySid, sCLRecord);
    }

    public int findFirstRecordLocBySid(short s) {
        int size = this._records.size();
        for (int i = 0; i < size; i++) {
            RecordBase recordBase = this._records.get(i);
            if ((recordBase instanceof Record) && ((Record) recordBase).getSid() == s) {
                return i;
            }
        }
        return -1;
    }

    public WindowTwoRecord getWindowTwo() {
        return this.windowTwo;
    }

    public PrintGridlinesRecord getPrintGridlines() {
        return this.printGridlines;
    }

    public void setPrintGridlines(PrintGridlinesRecord printGridlinesRecord) {
        this.printGridlines = printGridlinesRecord;
    }

    public void setSelected(boolean z) {
        this.windowTwo.setSelected(z);
    }

    public void createFreezePane(int i, int i2, int i3, int i4) {
        int findFirstRecordLocBySid = findFirstRecordLocBySid(65);
        if (findFirstRecordLocBySid != -1) {
            this._records.remove(findFirstRecordLocBySid);
        }
        if (i == 0 && i2 == 0) {
            this.windowTwo.setFreezePanes(false);
            this.windowTwo.setFreezePanesNoSplit(false);
            ((SelectionRecord) findFirstRecordBySid(29)).setPane((byte) 3);
            return;
        }
        int findFirstRecordLocBySid2 = findFirstRecordLocBySid(574);
        PaneRecord paneRecord = new PaneRecord();
        paneRecord.setX((short) i);
        paneRecord.setY((short) i2);
        paneRecord.setTopRow((short) i3);
        paneRecord.setLeftColumn((short) i4);
        if (i2 == 0) {
            paneRecord.setTopRow(0);
            paneRecord.setActivePane(1);
        } else if (i == 0) {
            paneRecord.setLeftColumn(0);
            paneRecord.setActivePane(2);
        } else {
            paneRecord.setActivePane(0);
        }
        this._records.add(findFirstRecordLocBySid2 + 1, paneRecord);
        this.windowTwo.setFreezePanes(true);
        this.windowTwo.setFreezePanesNoSplit(true);
        ((SelectionRecord) findFirstRecordBySid(29)).setPane((byte) paneRecord.getActivePane());
    }

    public void createSplitPane(int i, int i2, int i3, int i4, int i5) {
        int findFirstRecordLocBySid = findFirstRecordLocBySid(65);
        if (findFirstRecordLocBySid != -1) {
            this._records.remove(findFirstRecordLocBySid);
        }
        int findFirstRecordLocBySid2 = findFirstRecordLocBySid(574);
        PaneRecord paneRecord = new PaneRecord();
        paneRecord.setX((short) i);
        paneRecord.setY((short) i2);
        paneRecord.setTopRow((short) i3);
        paneRecord.setLeftColumn((short) i4);
        paneRecord.setActivePane((short) i5);
        this._records.add(findFirstRecordLocBySid2 + 1, paneRecord);
        this.windowTwo.setFreezePanes(false);
        this.windowTwo.setFreezePanesNoSplit(false);
        ((SelectionRecord) findFirstRecordBySid(29)).setPane((byte) 0);
    }

    public HSSFPaneInformation getPaneInformation() {
        PaneRecord paneRecord = (PaneRecord) findFirstRecordBySid(65);
        if (paneRecord == null) {
            return null;
        }
        return new HSSFPaneInformation(paneRecord.getX(), paneRecord.getY(), paneRecord.getTopRow(), paneRecord.getLeftColumn(), (byte) paneRecord.getActivePane(), this.windowTwo.getFreezePanes());
    }

    public SelectionRecord getSelection() {
        return this._selection;
    }

    public void setSelection(SelectionRecord selectionRecord) {
        this._selection = selectionRecord;
    }

    public WorksheetProtectionBlock getProtectionBlock() {
        return this._protectionBlock;
    }

    public void setDisplayGridlines(boolean z) {
        this.windowTwo.setDisplayGridlines(z);
    }

    public boolean isDisplayGridlines() {
        return this.windowTwo.getDisplayGridlines();
    }

    public void setDisplayFormulas(boolean z) {
        this.windowTwo.setDisplayFormulas(z);
    }

    public boolean isDisplayFormulas() {
        return this.windowTwo.getDisplayFormulas();
    }

    public void setDisplayRowColHeadings(boolean z) {
        this.windowTwo.setDisplayRowColHeadings(z);
    }

    public boolean isDisplayRowColHeadings() {
        return this.windowTwo.getDisplayRowColHeadings();
    }

    public boolean getUncalced() {
        return this._isUncalced;
    }

    public void setUncalced(boolean z) {
        this._isUncalced = z;
    }

    public HSSFChart getChart() {
        int findFirstRecordLocBySid;
        if (this.sheetType != 32 || (findFirstRecordLocBySid = findFirstRecordLocBySid(4098)) < 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Object obj = this._records.get(findFirstRecordLocBySid);
        while (true) {
            RecordBase recordBase = (RecordBase) obj;
            if (!(recordBase instanceof WorksheetProtectionBlock)) {
                arrayList.add((Record) recordBase);
                findFirstRecordLocBySid++;
                obj = this._records.get(findFirstRecordLocBySid);
            } else {
                HSSFChart hSSFChart = new HSSFChart((AWorkbook) null, (EscherContainerRecord) null, (HSSFShape) null, (HSSFAnchor) null);
                HSSFChart.convertRecordsToChart(arrayList, hSSFChart);
                return hSSFChart;
            }
        }
    }

    public int aggregateDrawingRecords(DrawingManager2 drawingManager2, boolean z) {
        int findFirstRecordLocBySid = findFirstRecordLocBySid(236);
        if (!(findFirstRecordLocBySid == -1)) {
            List<RecordBase> records = getRecords();
            EscherAggregate createAggregate = EscherAggregate.createAggregate(records, findFirstRecordLocBySid, drawingManager2);
            int i = findFirstRecordLocBySid;
            while (true) {
                int i2 = i + 1;
                if (i2 >= records.size() || ((!(records.get(i) instanceof DrawingRecord) && !(records.get(i) instanceof ContinueRecord)) || (!(records.get(i2) instanceof ObjRecord) && !(records.get(i2) instanceof TextObjectRecord)))) {
                    int i3 = i - 1;
                } else {
                    i += EscherAggregate.shapeContainRecords(records, i);
                }
            }
            int i32 = i - 1;
            for (int i4 = 0; i4 < (i32 - findFirstRecordLocBySid) + 1; i4++) {
                records.remove(findFirstRecordLocBySid);
            }
            records.add(findFirstRecordLocBySid, createAggregate);
            return findFirstRecordLocBySid;
        } else if (!z) {
            return -1;
        } else {
            EscherAggregate escherAggregate = new EscherAggregate(drawingManager2);
            int findFirstRecordLocBySid2 = findFirstRecordLocBySid(EscherAggregate.sid);
            if (findFirstRecordLocBySid2 == -1) {
                findFirstRecordLocBySid2 = findFirstRecordLocBySid(574);
            } else {
                getRecords().remove(findFirstRecordLocBySid2);
            }
            getRecords().add(findFirstRecordLocBySid2, escherAggregate);
            return findFirstRecordLocBySid2;
        }
    }

    public void preSerialize() {
        for (RecordBase next : getRecords()) {
            if (next instanceof EscherAggregate) {
                next.getRecordSize();
            }
        }
    }

    public PageSettingsBlock getPageSettings() {
        if (this._psBlock == null) {
            PageSettingsBlock pageSettingsBlock = new PageSettingsBlock();
            this._psBlock = pageSettingsBlock;
            RecordOrderer.addNewSheetRecord(this._records, pageSettingsBlock);
        }
        return this._psBlock;
    }

    public void setColumnGroupCollapsed(int i, boolean z) {
        if (z) {
            this._columnInfos.collapseColumn(i);
        } else {
            this._columnInfos.expandColumn(i);
        }
    }

    public void groupRowRange(int i, int i2, boolean z) {
        while (i <= i2) {
            RowRecord row = getRow(i);
            if (row == null) {
                row = RowRecordsAggregate.createRow(i);
                addRow(row);
            }
            short outlineLevel = row.getOutlineLevel();
            row.setOutlineLevel((short) Math.min(7, Math.max(0, z ? outlineLevel + 1 : outlineLevel - 1)));
            i++;
        }
        recalcRowGutter();
    }

    private void recalcRowGutter() {
        Iterator<RowRecord> iterator = this._rowsAggregate.getIterator();
        int i = 0;
        while (iterator.hasNext()) {
            i = Math.max(iterator.next().getOutlineLevel(), i);
        }
        GutsRecord gutsRecord = getGutsRecord();
        gutsRecord.setRowLevelMax((short) (i + 1));
        gutsRecord.setLeftRowGutter((short) ((i * 12) + 29));
    }

    public DataValidityTable getOrCreateDataValidityTable() {
        if (this._dataValidityTable == null) {
            DataValidityTable dataValidityTable = new DataValidityTable();
            RecordOrderer.addNewSheetRecord(this._records, dataValidityTable);
            this._dataValidityTable = dataValidityTable;
        }
        return this._dataValidityTable;
    }

    public NoteRecord[] getNoteRecords() {
        ArrayList arrayList = new ArrayList();
        for (int size = this._records.size() - 1; size >= 0; size--) {
            RecordBase recordBase = this._records.get(size);
            if (recordBase instanceof NoteRecord) {
                arrayList.add((NoteRecord) recordBase);
            }
        }
        if (arrayList.size() < 1) {
            return NoteRecord.EMPTY_ARRAY;
        }
        NoteRecord[] noteRecordArr = new NoteRecord[arrayList.size()];
        arrayList.toArray(noteRecordArr);
        return noteRecordArr;
    }

    public void dispose() {
        this._records.clear();
        this.printGridlines = null;
        this.gridset = null;
        this._gutsRecord = null;
        this.defaultcolwidth = null;
        this.defaultrowheight = null;
        this._psBlock = null;
        this.windowTwo = null;
        this._selection = null;
        this._dimensions = null;
        this._dataValidityTable = null;
        this.condFormatting = null;
        this.rowRecIterator = null;
        this._rowsAggregate.dispose();
    }
}
