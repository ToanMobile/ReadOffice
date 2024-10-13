package com.app.office.fc.hssf.usermodel;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.messaging.Constants;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.ptg.ExpPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.model.InternalSheet;
import com.app.office.fc.hssf.model.InternalWorkbook;
import com.app.office.fc.hssf.record.BlankRecord;
import com.app.office.fc.hssf.record.BoolErrRecord;
import com.app.office.fc.hssf.record.CellValueRecordInterface;
import com.app.office.fc.hssf.record.CommonObjectDataSubRecord;
import com.app.office.fc.hssf.record.DrawingRecord;
import com.app.office.fc.hssf.record.ExtendedFormatRecord;
import com.app.office.fc.hssf.record.FormulaRecord;
import com.app.office.fc.hssf.record.HyperlinkRecord;
import com.app.office.fc.hssf.record.LabelSSTRecord;
import com.app.office.fc.hssf.record.NoteRecord;
import com.app.office.fc.hssf.record.NumberRecord;
import com.app.office.fc.hssf.record.ObjRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.RecordBase;
import com.app.office.fc.hssf.record.SubRecord;
import com.app.office.fc.hssf.record.TextObjectRecord;
import com.app.office.fc.hssf.record.aggregates.FormulaRecordAggregate;
import com.app.office.fc.hssf.record.common.UnicodeString;
import com.app.office.fc.ss.SpreadsheetVersion;
import com.app.office.fc.ss.usermodel.Comment;
import com.app.office.fc.ss.usermodel.FormulaError;
import com.app.office.fc.ss.usermodel.ICell;
import com.app.office.fc.ss.usermodel.ICellStyle;
import com.app.office.fc.ss.usermodel.IHyperlink;
import com.app.office.fc.ss.usermodel.RichTextString;
import com.app.office.fc.ss.usermodel.Sheet;
import com.app.office.fc.ss.util.CellReference;
import com.app.office.fc.ss.util.HSSFCellRangeAddress;
import com.app.office.fc.ss.util.NumberToTextConverter;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class HSSFCell implements ICell {
    public static final short ENCODING_COMPRESSED_UNICODE = 0;
    public static final short ENCODING_UNCHANGED = -1;
    public static final short ENCODING_UTF_16 = 1;
    private static final String FILE_FORMAT_NAME = "BIFF8";
    private static final String LAST_COLUMN_NAME = SpreadsheetVersion.EXCEL97.getLastColumnName();
    public static final int LAST_COLUMN_NUMBER = SpreadsheetVersion.EXCEL97.getLastColumnIndex();
    private static POILogger log = POILogFactory.getLogger(HSSFCell.class);
    private final HSSFWorkbook _book;
    private int _cellType;
    private HSSFComment _comment;
    private CellValueRecordInterface _record;
    private final HSSFSheet _sheet;
    private HSSFRichTextString _stringValue;
    private int rangeAddressIndex = -1;

    public HSSFCell(HSSFWorkbook hSSFWorkbook, HSSFSheet hSSFSheet, int i, short s) {
        checkBounds(s);
        this._stringValue = null;
        this._book = hSSFWorkbook;
        this._sheet = hSSFSheet;
        setCellType(3, false, i, s, hSSFSheet.getSheet().getXFIndexForColAt(s));
    }

    public HSSFSheet getSheet() {
        return this._sheet;
    }

    public HSSFRow getRow() {
        return this._sheet.getRow(getRowIndex());
    }

    protected HSSFCell(HSSFWorkbook hSSFWorkbook, HSSFSheet hSSFSheet, int i, short s, int i2) {
        checkBounds(s);
        this._cellType = -1;
        this._stringValue = null;
        this._book = hSSFWorkbook;
        this._sheet = hSSFSheet;
        setCellType(i2, false, i, s, hSSFSheet.getSheet().getXFIndexForColAt(s));
    }

    protected HSSFCell(HSSFWorkbook hSSFWorkbook, HSSFSheet hSSFSheet, CellValueRecordInterface cellValueRecordInterface) {
        this._record = cellValueRecordInterface;
        int determineType = determineType(cellValueRecordInterface);
        this._cellType = determineType;
        this._stringValue = null;
        this._book = hSSFWorkbook;
        this._sheet = hSSFSheet;
        if (determineType == 1) {
            this._stringValue = new HSSFRichTextString(hSSFWorkbook.getWorkbook(), (LabelSSTRecord) cellValueRecordInterface);
        } else if (determineType == 2) {
            this._stringValue = new HSSFRichTextString(((FormulaRecordAggregate) cellValueRecordInterface).getStringValue());
        }
    }

    private static int determineType(CellValueRecordInterface cellValueRecordInterface) {
        if (cellValueRecordInterface instanceof FormulaRecordAggregate) {
            return 2;
        }
        Record record = (Record) cellValueRecordInterface;
        short sid = record.getSid();
        if (sid == 253) {
            return 1;
        }
        if (sid == 513) {
            return 3;
        }
        if (sid == 515) {
            return 0;
        }
        if (sid == 517) {
            return ((BoolErrRecord) record).isBoolean() ? 4 : 5;
        }
        throw new RuntimeException("Bad cell value rec (" + cellValueRecordInterface.getClass().getName() + ")");
    }

    /* access modifiers changed from: protected */
    public InternalWorkbook getBoundWorkbook() {
        return this._book.getWorkbook();
    }

    public int getRowIndex() {
        return this._record.getRow();
    }

    public void setCellNum(short s) {
        this._record.setColumn(s);
    }

    /* access modifiers changed from: protected */
    public void updateCellNum(short s) {
        this._record.setColumn(s);
    }

    public short getCellNum() {
        return (short) getColumnIndex();
    }

    public int getColumnIndex() {
        return this._record.getColumn() & 65535;
    }

    public void setCellType(int i) {
        notifyFormulaChanging();
        if (isPartOfArrayFormulaGroup()) {
            notifyArrayFormulaChanging();
        }
        setCellType(i, true, this._record.getRow(), this._record.getColumn(), this._record.getXFIndex());
    }

    public void setCellType(int i, boolean z) {
        notifyFormulaChanging();
        if (isPartOfArrayFormulaGroup()) {
            notifyArrayFormulaChanging();
        }
        setCellType(i, z, this._record.getRow(), this._record.getColumn(), this._record.getXFIndex());
    }

    private void setCellType(int i, boolean z, int i2, short s, short s2) {
        NumberRecord numberRecord;
        LabelSSTRecord labelSSTRecord;
        FormulaRecordAggregate formulaRecordAggregate;
        BlankRecord blankRecord;
        BoolErrRecord boolErrRecord;
        BoolErrRecord boolErrRecord2;
        if (i <= 5) {
            if (i == 0) {
                if (i != this._cellType) {
                    numberRecord = new NumberRecord();
                } else {
                    numberRecord = (NumberRecord) this._record;
                }
                numberRecord.setColumn(s);
                if (z) {
                    numberRecord.setValue(getNumericCellValue());
                }
                numberRecord.setXFIndex(s2);
                numberRecord.setRow(i2);
                this._record = numberRecord;
            } else if (i == 1) {
                if (i == this._cellType) {
                    labelSSTRecord = (LabelSSTRecord) this._record;
                } else {
                    LabelSSTRecord labelSSTRecord2 = new LabelSSTRecord();
                    labelSSTRecord2.setColumn(s);
                    labelSSTRecord2.setRow(i2);
                    labelSSTRecord2.setXFIndex(s2);
                    labelSSTRecord = labelSSTRecord2;
                }
                if (z) {
                    int addSSTString = this._book.getWorkbook().addSSTString(new UnicodeString(convertCellValueToString()));
                    labelSSTRecord.setSSTIndex(addSSTString);
                    UnicodeString sSTString = this._book.getWorkbook().getSSTString(addSSTString);
                    HSSFRichTextString hSSFRichTextString = new HSSFRichTextString();
                    this._stringValue = hSSFRichTextString;
                    hSSFRichTextString.setUnicodeString(sSTString);
                }
                this._record = labelSSTRecord;
            } else if (i == 2) {
                if (i != this._cellType) {
                    formulaRecordAggregate = this._sheet.getSheet().getRowsAggregate().createFormula(i2, s);
                } else {
                    FormulaRecordAggregate formulaRecordAggregate2 = (FormulaRecordAggregate) this._record;
                    formulaRecordAggregate2.setRow(i2);
                    formulaRecordAggregate2.setColumn(s);
                    formulaRecordAggregate = formulaRecordAggregate2;
                }
                if (z) {
                    formulaRecordAggregate.getFormulaRecord().setValue(getNumericCellValue());
                }
                formulaRecordAggregate.setXFIndex(s2);
                this._record = formulaRecordAggregate;
            } else if (i == 3) {
                if (i != this._cellType) {
                    blankRecord = new BlankRecord();
                } else {
                    blankRecord = (BlankRecord) this._record;
                }
                blankRecord.setColumn(s);
                blankRecord.setXFIndex(s2);
                blankRecord.setRow(i2);
                this._record = blankRecord;
            } else if (i == 4) {
                if (i != this._cellType) {
                    boolErrRecord = new BoolErrRecord();
                } else {
                    boolErrRecord = (BoolErrRecord) this._record;
                }
                boolErrRecord.setColumn(s);
                if (z) {
                    boolErrRecord.setValue(convertCellValueToBoolean());
                }
                boolErrRecord.setXFIndex(s2);
                boolErrRecord.setRow(i2);
                this._record = boolErrRecord;
            } else if (i == 5) {
                if (i != this._cellType) {
                    boolErrRecord2 = new BoolErrRecord();
                } else {
                    boolErrRecord2 = (BoolErrRecord) this._record;
                }
                boolErrRecord2.setColumn(s);
                if (z) {
                    boolErrRecord2.setValue((byte) 15);
                }
                boolErrRecord2.setXFIndex(s2);
                boolErrRecord2.setRow(i2);
                this._record = boolErrRecord2;
            }
            int i3 = this._cellType;
            if (!(i == i3 || i3 == -1)) {
                this._sheet.getSheet().replaceValueRecord(this._record);
            }
            this._cellType = i;
            return;
        }
        throw new RuntimeException("I have no idea what type that is!");
    }

    public int getCellType() {
        return this._cellType;
    }

    public void setCellValue(double d) {
        if (Double.isInfinite(d)) {
            setCellErrorValue(FormulaError.DIV0.getCode());
        } else if (Double.isNaN(d)) {
            setCellErrorValue(FormulaError.NUM.getCode());
        } else {
            int row = this._record.getRow();
            short column = this._record.getColumn();
            short xFIndex = this._record.getXFIndex();
            int i = this._cellType;
            if (i != 0) {
                if (i != 2) {
                    setCellType(0, false, row, column, xFIndex);
                } else {
                    ((FormulaRecordAggregate) this._record).setCachedDoubleResult(d);
                    return;
                }
            }
            ((NumberRecord) this._record).setValue(d);
        }
    }

    public void setCellValue(Date date) {
        setCellValue(HSSFDateUtil.getExcelDate(date, this._book.getWorkbook().isUsing1904DateWindowing()));
    }

    public void setCellValue(Calendar calendar) {
        setCellValue(HSSFDateUtil.getExcelDate(calendar, this._book.getWorkbook().isUsing1904DateWindowing()));
    }

    public void setCellValue(String str) {
        setCellValue((RichTextString) str == null ? null : new HSSFRichTextString(str));
    }

    public void setCellValue(RichTextString richTextString) {
        HSSFRichTextString hSSFRichTextString = (HSSFRichTextString) richTextString;
        int row = this._record.getRow();
        short column = this._record.getColumn();
        short xFIndex = this._record.getXFIndex();
        if (hSSFRichTextString == null) {
            notifyFormulaChanging();
            setCellType(3, false, row, column, xFIndex);
        } else if (hSSFRichTextString.length() <= SpreadsheetVersion.EXCEL97.getMaxTextLength()) {
            int i = this._cellType;
            if (i == 2) {
                ((FormulaRecordAggregate) this._record).setCachedStringResult(hSSFRichTextString.getString());
                this._stringValue = new HSSFRichTextString(richTextString.getString());
                return;
            }
            if (i != 1) {
                setCellType(1, false, row, column, xFIndex);
            }
            int addSSTString = this._book.getWorkbook().addSSTString(hSSFRichTextString.getUnicodeString());
            ((LabelSSTRecord) this._record).setSSTIndex(addSSTString);
            this._stringValue = hSSFRichTextString;
            hSSFRichTextString.setWorkbookReferences(this._book.getWorkbook(), (LabelSSTRecord) this._record);
            this._stringValue.setUnicodeString(this._book.getWorkbook().getSSTString(addSSTString));
        } else {
            throw new IllegalArgumentException("The maximum length of cell contents (text) is 32,767 characters");
        }
    }

    public void setCellFormula(String str) {
        if (isPartOfArrayFormulaGroup()) {
            notifyArrayFormulaChanging();
        }
        int row = this._record.getRow();
        short column = this._record.getColumn();
        short xFIndex = this._record.getXFIndex();
        if (str == null) {
            notifyFormulaChanging();
            setCellType(3, false, row, column, xFIndex);
            return;
        }
        this._book.getSheetIndex((Sheet) this._sheet);
    }

    public void setCellFormula(Ptg[] ptgArr) {
        if (isPartOfArrayFormulaGroup()) {
            notifyArrayFormulaChanging();
        }
        setCellType(2, false, this._record.getRow(), this._record.getColumn(), this._record.getXFIndex());
        FormulaRecordAggregate formulaRecordAggregate = (FormulaRecordAggregate) this._record;
        FormulaRecord formulaRecord = formulaRecordAggregate.getFormulaRecord();
        formulaRecord.setOptions(2);
        formulaRecord.setValue(0.0d);
        if (formulaRecordAggregate.getXFIndex() == 0) {
            formulaRecordAggregate.setXFIndex(15);
        }
        formulaRecordAggregate.setParsedExpression(ptgArr);
    }

    private void notifyFormulaChanging() {
        CellValueRecordInterface cellValueRecordInterface = this._record;
        if (cellValueRecordInterface instanceof FormulaRecordAggregate) {
            ((FormulaRecordAggregate) cellValueRecordInterface).notifyFormulaChanging();
        }
    }

    public String getCellFormula() {
        if (this._record instanceof FormulaRecordAggregate) {
            return null;
        }
        throw typeMismatch(2, this._cellType, true);
    }

    private static String getCellTypeName(int i) {
        if (i == 0) {
            return "numeric";
        }
        if (i == 1) {
            return "text";
        }
        if (i == 2) {
            return "formula";
        }
        if (i == 3) {
            return "blank";
        }
        if (i == 4) {
            return TypedValues.Custom.S_BOOLEAN;
        }
        if (i == 5) {
            return Constants.IPC_BUNDLE_KEY_SEND_ERROR;
        }
        return "#unknown cell type (" + i + ")#";
    }

    private static RuntimeException typeMismatch(int i, int i2, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("Cannot get a ");
        sb.append(getCellTypeName(i));
        sb.append(" value from a ");
        sb.append(getCellTypeName(i2));
        sb.append(" ");
        sb.append(z ? "formula " : "");
        sb.append("cell");
        return new IllegalStateException(sb.toString());
    }

    private static void checkFormulaCachedValueType(int i, FormulaRecord formulaRecord) {
        int cachedResultType = formulaRecord.getCachedResultType();
        if (cachedResultType != i) {
            throw typeMismatch(i, cachedResultType, true);
        }
    }

    public int getFormulaCachedValueType() {
        return ((FormulaRecordAggregate) this._record).getFormulaRecord().getCachedResultType();
    }

    public double getNumericCellValue() {
        int i = this._cellType;
        if (i == 0) {
            return ((NumberRecord) this._record).getValue();
        }
        if (i == 2) {
            FormulaRecord formulaRecord = ((FormulaRecordAggregate) this._record).getFormulaRecord();
            checkFormulaCachedValueType(0, formulaRecord);
            return formulaRecord.getValue();
        } else if (i == 3) {
            return 0.0d;
        } else {
            throw typeMismatch(0, i, false);
        }
    }

    public Date getDateCellValue() {
        if (this._cellType == 3) {
            return null;
        }
        double numericCellValue = getNumericCellValue();
        if (this._book.getWorkbook().isUsing1904DateWindowing()) {
            return HSSFDateUtil.getJavaDate(numericCellValue, true);
        }
        return HSSFDateUtil.getJavaDate(numericCellValue, false);
    }

    public int getSSTStringIndex() {
        return getRichStringCellValue().getSSTIndex();
    }

    public String getStringCellValue() {
        return getRichStringCellValue().getString();
    }

    public HSSFRichTextString getRichStringCellValue() {
        int i = this._cellType;
        if (i == 1) {
            return this._stringValue;
        }
        String str = "";
        if (i == 2) {
            FormulaRecordAggregate formulaRecordAggregate = (FormulaRecordAggregate) this._record;
            checkFormulaCachedValueType(1, formulaRecordAggregate.getFormulaRecord());
            String stringValue = formulaRecordAggregate.getStringValue();
            if (stringValue != null) {
                str = stringValue;
            }
            return new HSSFRichTextString(str);
        } else if (i == 3) {
            return new HSSFRichTextString(str);
        } else {
            throw typeMismatch(1, i, false);
        }
    }

    public void setCellValue(boolean z) {
        int row = this._record.getRow();
        short column = this._record.getColumn();
        short xFIndex = this._record.getXFIndex();
        int i = this._cellType;
        if (i != 2) {
            if (i != 4) {
                setCellType(4, false, row, column, xFIndex);
            }
            ((BoolErrRecord) this._record).setValue(z);
            return;
        }
        ((FormulaRecordAggregate) this._record).setCachedBooleanResult(z);
    }

    public void setCellErrorValue(byte b) {
        int row = this._record.getRow();
        short column = this._record.getColumn();
        short xFIndex = this._record.getXFIndex();
        int i = this._cellType;
        if (i != 2) {
            if (i != 5) {
                setCellType(5, false, row, column, xFIndex);
            }
            ((BoolErrRecord) this._record).setValue(b);
            return;
        }
        ((FormulaRecordAggregate) this._record).setCachedErrorResult(b);
    }

    private boolean convertCellValueToBoolean() {
        int i = this._cellType;
        if (i != 0) {
            if (i == 1) {
                return Boolean.valueOf(this._book.getWorkbook().getSSTString(((LabelSSTRecord) this._record).getSSTIndex()).getString()).booleanValue();
            } else if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        return ((BoolErrRecord) this._record).getBooleanValue();
                    }
                    if (i != 5) {
                        throw new RuntimeException("Unexpected cell type (" + this._cellType + ")");
                    }
                }
                return false;
            } else {
                FormulaRecord formulaRecord = ((FormulaRecordAggregate) this._record).getFormulaRecord();
                checkFormulaCachedValueType(4, formulaRecord);
                return formulaRecord.getCachedBooleanValue();
            }
        } else if (((NumberRecord) this._record).getValue() != 0.0d) {
            return true;
        } else {
            return false;
        }
    }

    private String convertCellValueToString() {
        int i = this._cellType;
        if (i == 0) {
            return NumberToTextConverter.toText(((NumberRecord) this._record).getValue());
        }
        if (i == 1) {
            return this._book.getWorkbook().getSSTString(((LabelSSTRecord) this._record).getSSTIndex()).getString();
        } else if (i == 2) {
            FormulaRecordAggregate formulaRecordAggregate = (FormulaRecordAggregate) this._record;
            FormulaRecord formulaRecord = formulaRecordAggregate.getFormulaRecord();
            int cachedResultType = formulaRecord.getCachedResultType();
            if (cachedResultType == 0) {
                return NumberToTextConverter.toText(formulaRecord.getValue());
            }
            if (cachedResultType == 1) {
                return formulaRecordAggregate.getStringValue();
            }
            if (cachedResultType == 4) {
                return formulaRecord.getCachedBooleanValue() ? "TRUE" : "FALSE";
            }
            if (cachedResultType == 5) {
                return HSSFErrorConstants.getText(formulaRecord.getCachedErrorValue());
            }
            throw new IllegalStateException("Unexpected formula result type (" + this._cellType + ")");
        } else if (i == 3) {
            return "";
        } else {
            if (i == 4) {
                return ((BoolErrRecord) this._record).getBooleanValue() ? "TRUE" : "FALSE";
            }
            if (i == 5) {
                return HSSFErrorConstants.getText(((BoolErrRecord) this._record).getErrorValue());
            }
            throw new IllegalStateException("Unexpected cell type (" + this._cellType + ")");
        }
    }

    public boolean getBooleanCellValue() {
        int i = this._cellType;
        if (i == 2) {
            FormulaRecord formulaRecord = ((FormulaRecordAggregate) this._record).getFormulaRecord();
            checkFormulaCachedValueType(4, formulaRecord);
            return formulaRecord.getCachedBooleanValue();
        } else if (i == 3) {
            return false;
        } else {
            if (i == 4) {
                return ((BoolErrRecord) this._record).getBooleanValue();
            }
            throw typeMismatch(4, i, false);
        }
    }

    public byte getErrorCellValue() {
        int i = this._cellType;
        if (i == 2) {
            FormulaRecord formulaRecord = ((FormulaRecordAggregate) this._record).getFormulaRecord();
            checkFormulaCachedValueType(5, formulaRecord);
            return (byte) formulaRecord.getCachedErrorValue();
        } else if (i == 5) {
            return ((BoolErrRecord) this._record).getErrorValue();
        } else {
            throw typeMismatch(5, i, false);
        }
    }

    public void setCellStyle(ICellStyle iCellStyle) {
        setCellStyle((HSSFCellStyle) iCellStyle);
    }

    public void setCellStyle(HSSFCellStyle hSSFCellStyle) {
        short s;
        hSSFCellStyle.verifyBelongsToWorkbook(this._book);
        if (hSSFCellStyle.getUserStyleName() != null) {
            s = applyUserCellStyle(hSSFCellStyle);
        } else {
            s = hSSFCellStyle.getIndex();
        }
        this._record.setXFIndex(s);
    }

    public HSSFCellStyle getCellStyle() {
        short xFIndex = this._record.getXFIndex();
        return new HSSFCellStyle(xFIndex, this._book.getWorkbook().getExFormatAt(xFIndex), this._book);
    }

    public int getCellStyleIndex() {
        return this._record.getXFIndex();
    }

    /* access modifiers changed from: protected */
    public CellValueRecordInterface getCellValueRecord() {
        return this._record;
    }

    private static void checkBounds(int i) {
        if (i < 0 || i > LAST_COLUMN_NUMBER) {
            throw new IllegalArgumentException("Invalid column index (" + i + ").  Allowable column range for " + FILE_FORMAT_NAME + " is (0.." + LAST_COLUMN_NUMBER + ") or ('A'..'" + LAST_COLUMN_NAME + "')");
        }
    }

    public void setAsActiveCell() {
        int row = this._record.getRow();
        short column = this._record.getColumn();
        this._sheet.getSheet().setActiveCellRow(row);
        this._sheet.getSheet().setActiveCellCol(column);
    }

    public String toString() {
        int cellType = getCellType();
        if (cellType == 0) {
            return String.valueOf(getNumericCellValue());
        }
        if (cellType == 1) {
            return getStringCellValue();
        }
        if (cellType == 2) {
            return getCellFormula();
        }
        if (cellType == 3) {
            return "";
        }
        if (cellType == 4) {
            return getBooleanCellValue() ? "TRUE" : "FALSE";
        }
        if (cellType == 5) {
            return ErrorEval.getText(((BoolErrRecord) this._record).getErrorValue());
        }
        return "Unknown Cell Type: " + getCellType();
    }

    public void setCellComment(Comment comment) {
        if (comment == null) {
            removeCellComment();
            return;
        }
        comment.setRow(this._record.getRow());
        comment.setColumn(this._record.getColumn());
        this._comment = (HSSFComment) comment;
    }

    public HSSFComment getCellComment() {
        if (this._comment == null) {
            this._comment = findCellComment(this._sheet.getSheet(), this._record.getRow(), this._record.getColumn());
        }
        return this._comment;
    }

    public void removeCellComment() {
        HSSFComment findCellComment = findCellComment(this._sheet.getSheet(), this._record.getRow(), this._record.getColumn());
        this._comment = null;
        if (findCellComment != null) {
            List<RecordBase> records = this._sheet.getSheet().getRecords();
            records.remove(findCellComment.getNoteRecord());
            if (findCellComment.getTextObjectRecord() != null) {
                TextObjectRecord textObjectRecord = findCellComment.getTextObjectRecord();
                int indexOf = records.indexOf(textObjectRecord);
                int i = indexOf - 3;
                if (records.get(i) instanceof DrawingRecord) {
                    int i2 = indexOf - 2;
                    if (records.get(i2) instanceof ObjRecord) {
                        int i3 = indexOf - 1;
                        if (records.get(i3) instanceof DrawingRecord) {
                            records.remove(i3);
                            records.remove(i2);
                            records.remove(i);
                            records.remove(textObjectRecord);
                            return;
                        }
                    }
                }
                throw new IllegalStateException("Found the wrong records before the TextObjectRecord, can't remove comment");
            }
        }
    }

    protected static HSSFComment findCellComment(InternalSheet internalSheet, int i, int i2) {
        HashMap hashMap = new HashMap();
        Iterator<RecordBase> it = internalSheet.getRecords().iterator();
        boolean z = false;
        int i3 = 0;
        while (it.hasNext()) {
            RecordBase next = it.next();
            if (next instanceof NoteRecord) {
                NoteRecord noteRecord = (NoteRecord) next;
                if (noteRecord.getRow() != i || noteRecord.getColumn() != i2) {
                    i3++;
                } else if (i3 < hashMap.size()) {
                    TextObjectRecord textObjectRecord = (TextObjectRecord) hashMap.get(Integer.valueOf(noteRecord.getShapeId()));
                    if (textObjectRecord != null) {
                        HSSFComment hSSFComment = new HSSFComment(noteRecord, textObjectRecord);
                        hSSFComment.setRow(noteRecord.getRow());
                        hSSFComment.setColumn(noteRecord.getColumn());
                        hSSFComment.setAuthor(noteRecord.getAuthor());
                        if (noteRecord.getFlags() == 2) {
                            z = true;
                        }
                        hSSFComment.setVisible(z);
                        hSSFComment.setString(textObjectRecord.getStr());
                        return hSSFComment;
                    }
                    POILogger pOILogger = log;
                    int i4 = POILogger.WARN;
                    pOILogger.log(i4, (Object) "Failed to match NoteRecord and TextObjectRecord, row: " + i + ", column: " + i2);
                    return null;
                } else {
                    POILogger pOILogger2 = log;
                    int i5 = POILogger.WARN;
                    pOILogger2.log(i5, (Object) "Failed to match NoteRecord and TextObjectRecord, row: " + i + ", column: " + i2);
                    return null;
                }
            } else if (next instanceof ObjRecord) {
                SubRecord subRecord = ((ObjRecord) next).getSubRecords().get(0);
                if (subRecord instanceof CommonObjectDataSubRecord) {
                    CommonObjectDataSubRecord commonObjectDataSubRecord = (CommonObjectDataSubRecord) subRecord;
                    if (commonObjectDataSubRecord.getObjectType() == 25) {
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            RecordBase next2 = it.next();
                            if (next2 instanceof TextObjectRecord) {
                                hashMap.put(Integer.valueOf(commonObjectDataSubRecord.getObjectId()), (TextObjectRecord) next2);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public HSSFHyperlink getHyperlink() {
        for (RecordBase next : this._sheet.getSheet().getRecords()) {
            if (next instanceof HyperlinkRecord) {
                HyperlinkRecord hyperlinkRecord = (HyperlinkRecord) next;
                if (hyperlinkRecord.getFirstColumn() == this._record.getColumn() && hyperlinkRecord.getFirstRow() == this._record.getRow()) {
                    return new HSSFHyperlink(hyperlinkRecord);
                }
            }
        }
        return null;
    }

    public void setHyperlink(IHyperlink iHyperlink) {
        HSSFHyperlink hSSFHyperlink = (HSSFHyperlink) iHyperlink;
        hSSFHyperlink.setFirstRow(this._record.getRow());
        hSSFHyperlink.setLastRow(this._record.getRow());
        hSSFHyperlink.setFirstColumn(this._record.getColumn());
        hSSFHyperlink.setLastColumn(this._record.getColumn());
        int type = hSSFHyperlink.getType();
        if (type != 1) {
            if (type == 2) {
                hSSFHyperlink.setLabel("place");
            } else if (type != 3) {
                if (type == 4) {
                    hSSFHyperlink.setLabel("file");
                }
            }
            List<RecordBase> records = this._sheet.getSheet().getRecords();
            records.add(records.size() - 1, hSSFHyperlink.record);
        }
        hSSFHyperlink.setLabel("url");
        List<RecordBase> records2 = this._sheet.getSheet().getRecords();
        records2.add(records2.size() - 1, hSSFHyperlink.record);
    }

    public int getCachedFormulaResultType() {
        if (this._cellType == 2) {
            return ((FormulaRecordAggregate) this._record).getFormulaRecord().getCachedResultType();
        }
        throw new IllegalStateException("Only formula cells have cached results");
    }

    /* access modifiers changed from: package-private */
    public void setCellArrayFormula(HSSFCellRangeAddress hSSFCellRangeAddress) {
        setCellType(2, false, this._record.getRow(), this._record.getColumn(), this._record.getXFIndex());
        ((FormulaRecordAggregate) this._record).setParsedExpression(new Ptg[]{new ExpPtg(hSSFCellRangeAddress.getFirstRow(), hSSFCellRangeAddress.getFirstColumn())});
    }

    public HSSFCellRangeAddress getArrayFormulaRange() {
        if (this._cellType == 2) {
            return ((FormulaRecordAggregate) this._record).getArrayFormulaRange();
        }
        String formatAsString = new CellReference((ICell) this).formatAsString();
        throw new IllegalStateException("Cell " + formatAsString + " is not part of an array formula.");
    }

    public boolean isPartOfArrayFormulaGroup() {
        if (this._cellType != 2) {
            return false;
        }
        return ((FormulaRecordAggregate) this._record).isPartOfArrayFormula();
    }

    /* access modifiers changed from: package-private */
    public void notifyArrayFormulaChanging(String str) {
        if (getArrayFormulaRange().getNumberOfCells() <= 1) {
            getRow().getSheet().removeArrayFormula(this);
            return;
        }
        throw new IllegalStateException(str);
    }

    /* access modifiers changed from: package-private */
    public void notifyArrayFormulaChanging() {
        CellReference cellReference = new CellReference((ICell) this);
        notifyArrayFormulaChanging("Cell " + cellReference.formatAsString() + " is part of a multi-cell array formula. You cannot change part of an array.");
    }

    private short applyUserCellStyle(HSSFCellStyle hSSFCellStyle) {
        if (hSSFCellStyle.getUserStyleName() != null) {
            InternalWorkbook workbook = this._book.getWorkbook();
            int numExFormats = workbook.getNumExFormats();
            short s = 0;
            while (true) {
                if (s >= numExFormats) {
                    s = -1;
                    break;
                }
                ExtendedFormatRecord exFormatAt = workbook.getExFormatAt(s);
                if (exFormatAt.getXFType() == 0 && exFormatAt.getParentIndex() == hSSFCellStyle.getIndex()) {
                    break;
                }
                s = (short) (s + 1);
            }
            if (s != -1) {
                return s;
            }
            ExtendedFormatRecord createCellXF = workbook.createCellXF();
            createCellXF.cloneStyleFrom(workbook.getExFormatAt(hSSFCellStyle.getIndex()));
            createCellXF.setIndentionOptions(0);
            createCellXF.setXFType(0);
            createCellXF.setParentIndex(hSSFCellStyle.getIndex());
            return (short) numExFormats;
        }
        throw new IllegalArgumentException("Expected user-defined style");
    }

    public int getRangeAddressIndex() {
        return this.rangeAddressIndex;
    }

    public void setRangeAddressIndex(int i) {
        this.rangeAddressIndex = i;
    }
}
