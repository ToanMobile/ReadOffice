package com.app.office.ss.model.XLSModel;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.messaging.Constants;
import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.record.BlankRecord;
import com.app.office.fc.hssf.record.BoolErrRecord;
import com.app.office.fc.hssf.record.CellValueRecordInterface;
import com.app.office.fc.hssf.record.FormulaRecord;
import com.app.office.fc.hssf.record.LabelRecord;
import com.app.office.fc.hssf.record.LabelSSTRecord;
import com.app.office.fc.hssf.record.NumberRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.StringRecord;
import com.app.office.fc.hssf.record.aggregates.FormulaRecordAggregate;
import com.app.office.fc.hssf.record.common.UnicodeString;
import com.app.office.fc.hssf.usermodel.HSSFRichTextString;
import com.app.office.fc.ss.SpreadsheetVersion;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.util.SectionElementFactory;

public class ACell extends Cell {
    private CellValueRecordInterface record;

    public ACell(Sheet sheet, CellValueRecordInterface cellValueRecordInterface) {
        super(3);
        this.record = cellValueRecordInterface;
        this.cellType = (short) determineType(cellValueRecordInterface);
        this.sheet = sheet;
        this.rowNumber = cellValueRecordInterface.getRow();
        this.colNumber = cellValueRecordInterface.getColumn();
        this.styleIndex = cellValueRecordInterface.getXFIndex();
        short s = this.cellType;
        if (s == 0) {
            this.value = Double.valueOf(getNumericCellValue());
        } else if (s != 1) {
            if (s == 2) {
                procellFormulaCellValue((FormulaRecordAggregate) cellValueRecordInterface);
            } else if (s == 4) {
                this.value = Boolean.valueOf(getBooleanCellValue());
            } else if (s == 5) {
                this.value = Byte.valueOf(getErrorCellValue());
            }
        } else if (cellValueRecordInterface instanceof LabelSSTRecord) {
            this.value = Integer.valueOf(((LabelSSTRecord) cellValueRecordInterface).getSSTIndex());
            processSST();
        } else if (cellValueRecordInterface instanceof LabelRecord) {
            this.value = Integer.valueOf(sheet.getWorkbook().addSharedString(((LabelRecord) cellValueRecordInterface).getValue()));
        }
    }

    private void processSST() {
        Workbook workbook = this.sheet.getWorkbook();
        Object sharedItem = workbook.getSharedItem(((Integer) this.value).intValue());
        if (sharedItem instanceof UnicodeString) {
            this.value = Integer.valueOf(workbook.addSharedString(SectionElementFactory.getSectionElement(workbook, (UnicodeString) sharedItem, (Cell) this)));
        }
    }

    private void procellFormulaCellValue(FormulaRecordAggregate formulaRecordAggregate) {
        StringRecord stringRecord = formulaRecordAggregate.getStringRecord();
        if (stringRecord != null) {
            this.cellType = 1;
            this.value = Integer.valueOf(this.sheet.getWorkbook().addSharedString(stringRecord.getString()));
            return;
        }
        FormulaRecord formulaRecord = formulaRecordAggregate.getFormulaRecord();
        this.cellType = (short) formulaRecord.getCachedResultType();
        short s = this.cellType;
        if (s == 0) {
            this.value = Double.valueOf(formulaRecord.getValue());
        } else if (s == 4) {
            this.value = Boolean.valueOf(formulaRecord.getCachedBooleanValue());
        } else if (s == 5) {
            this.value = Byte.valueOf((byte) formulaRecord.getCachedErrorValue());
        }
    }

    public ACell(AWorkbook aWorkbook, ASheet aSheet, int i, short s) {
        super(5);
        this.sheet = aSheet;
        setCellType(3, false, i, s, aSheet.getInternalSheet().getXFIndexForColAt(s));
    }

    public void setCellFormula(Ptg[] ptgArr) {
        setCellType(2, false, this.record.getRow(), this.record.getColumn(), this.record.getXFIndex());
        FormulaRecordAggregate formulaRecordAggregate = (FormulaRecordAggregate) this.record;
        FormulaRecord formulaRecord = formulaRecordAggregate.getFormulaRecord();
        formulaRecord.setOptions(2);
        formulaRecord.setValue(0.0d);
        if (formulaRecordAggregate.getXFIndex() == 0) {
            formulaRecordAggregate.setXFIndex(15);
        }
        formulaRecordAggregate.setParsedExpression(ptgArr);
    }

    public String getStringCellValue() {
        short s = this.cellType;
        if (s != 1) {
            if (s != 2) {
                if (s == 3) {
                    return "";
                }
                throw typeMismatch(1, this.cellType, false);
            }
        } else if (this.record instanceof LabelSSTRecord) {
            return this.sheet.getWorkbook().getSharedString(((LabelSSTRecord) this.record).getSSTIndex());
        }
        FormulaRecordAggregate formulaRecordAggregate = (FormulaRecordAggregate) this.record;
        checkFormulaCachedValueType(1, formulaRecordAggregate.getFormulaRecord());
        return formulaRecordAggregate.getStringValue();
    }

    public double getNumericCellValue() {
        short s = this.cellType;
        if (s == 0) {
            return ((NumberRecord) this.record).getValue();
        }
        if (s == 2) {
            FormulaRecord formulaRecord = ((FormulaRecordAggregate) this.record).getFormulaRecord();
            checkFormulaCachedValueType(0, formulaRecord);
            return formulaRecord.getValue();
        } else if (s == 3) {
            return 0.0d;
        } else {
            throw typeMismatch(0, this.cellType, false);
        }
    }

    public boolean getBooleanCellValue() {
        short s = this.cellType;
        if (s == 2) {
            FormulaRecord formulaRecord = ((FormulaRecordAggregate) this.record).getFormulaRecord();
            checkFormulaCachedValueType(4, formulaRecord);
            return formulaRecord.getCachedBooleanValue();
        } else if (s == 3) {
            return false;
        } else {
            if (s == 4) {
                return ((BoolErrRecord) this.record).getBooleanValue();
            }
            throw typeMismatch(4, this.cellType, false);
        }
    }

    public byte getErrorCellValue() {
        short s = this.cellType;
        if (s == 2) {
            FormulaRecord formulaRecord = ((FormulaRecordAggregate) this.record).getFormulaRecord();
            checkFormulaCachedValueType(5, formulaRecord);
            return (byte) formulaRecord.getCachedErrorValue();
        } else if (s == 5) {
            return ((BoolErrRecord) this.record).getErrorValue();
        } else {
            throw typeMismatch(5, this.cellType, false);
        }
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

    public int getFormulaCachedValueType() {
        return ((FormulaRecordAggregate) this.record).getFormulaRecord().getCachedResultType();
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

    public static int determineType(CellValueRecordInterface cellValueRecordInterface) {
        if (cellValueRecordInterface instanceof FormulaRecordAggregate) {
            return 2;
        }
        Record record2 = (Record) cellValueRecordInterface;
        short sid = record2.getSid();
        if (sid == 253) {
            return 1;
        }
        if (sid == 513) {
            return 3;
        }
        switch (sid) {
            case TIFFConstants.TIFFTAG_JPEGRESTARTINTERVAL:
                return 0;
            case 516:
                return 1;
            case TIFFConstants.TIFFTAG_JPEGLOSSLESSPREDICTORS:
                return ((BoolErrRecord) record2).isBoolean() ? 4 : 5;
            default:
                throw new RuntimeException("Bad cell value rec (" + cellValueRecordInterface.getClass().getName() + ")");
        }
    }

    public CellValueRecordInterface getCellValueRecord() {
        return this.record;
    }

    public void setCellType(int i, boolean z) {
        setCellType(i, z, this.record.getRow(), this.record.getColumn(), this.record.getXFIndex());
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
                if (i != this.cellType) {
                    numberRecord = new NumberRecord();
                } else {
                    numberRecord = (NumberRecord) this.record;
                }
                numberRecord.setColumn(s);
                numberRecord.setXFIndex(s2);
                numberRecord.setRow(i2);
                this.record = numberRecord;
            } else if (i == 1) {
                if (i == this.cellType) {
                    labelSSTRecord = (LabelSSTRecord) this.record;
                } else {
                    labelSSTRecord = new LabelSSTRecord();
                    labelSSTRecord.setColumn(s);
                    labelSSTRecord.setRow(i2);
                    labelSSTRecord.setXFIndex(s2);
                }
                this.record = labelSSTRecord;
            } else if (i == 2) {
                if (this.cellType != i) {
                    formulaRecordAggregate = ((ASheet) this.sheet).getInternalSheet().getRowsAggregate().createFormula(i2, s);
                } else {
                    formulaRecordAggregate = (FormulaRecordAggregate) this.record;
                    formulaRecordAggregate.setRow(i2);
                    formulaRecordAggregate.setColumn(s);
                }
                formulaRecordAggregate.setXFIndex(s2);
                this.record = formulaRecordAggregate;
            } else if (i == 3) {
                if (this.cellType != i) {
                    blankRecord = new BlankRecord();
                } else {
                    blankRecord = (BlankRecord) this.record;
                }
                blankRecord.setColumn(s);
                blankRecord.setXFIndex(s2);
                blankRecord.setRow(i2);
                this.record = blankRecord;
            } else if (i == 4) {
                if (i != this.cellType) {
                    boolErrRecord = new BoolErrRecord();
                } else {
                    boolErrRecord = (BoolErrRecord) this.record;
                }
                boolErrRecord.setColumn(s);
                boolErrRecord.setXFIndex(s2);
                boolErrRecord.setRow(i2);
                this.record = boolErrRecord;
            } else if (i == 5) {
                if (i != this.cellType) {
                    boolErrRecord2 = new BoolErrRecord();
                } else {
                    boolErrRecord2 = (BoolErrRecord) this.record;
                }
                boolErrRecord2.setColumn(s);
                boolErrRecord2.setXFIndex(s2);
                boolErrRecord2.setRow(i2);
                this.record = boolErrRecord2;
            }
            this.cellType = (short) i;
            return;
        }
        throw new RuntimeException("I have no idea what type that is!");
    }

    public void setCellValue(double d) {
        short s = this.cellType;
        if (s == 0) {
            ((NumberRecord) this.record).setValue(d);
            this.value = Double.valueOf(d);
        } else if (s == 1) {
            this.value = Integer.valueOf(Math.round((float) d));
        }
    }

    public void setCellValue(boolean z) {
        if (this.cellType == 4) {
            ((BoolErrRecord) this.record).setValue(z);
            this.value = Boolean.valueOf(z);
        }
    }

    public void setCellValue(String str) {
        HSSFRichTextString hSSFRichTextString = str == null ? null : new HSSFRichTextString(str);
        int row = this.record.getRow();
        short column = this.record.getColumn();
        short xFIndex = this.record.getXFIndex();
        if (hSSFRichTextString == null) {
            setCellType(3, false, row, column, xFIndex);
        } else if (hSSFRichTextString.length() <= SpreadsheetVersion.EXCEL97.getMaxTextLength()) {
            int addSSTString = ((AWorkbook) this.sheet.getWorkbook()).getInternalWorkbook().addSSTString(hSSFRichTextString.getUnicodeString());
            ((LabelSSTRecord) this.record).setSSTIndex(addSSTString);
            this.value = Integer.valueOf(addSSTString);
        } else {
            throw new IllegalArgumentException("The maximum length of cell contents (text) is 32,767 characters");
        }
    }

    public void setCellErrorValue(byte b) {
        if (this.cellType == 5) {
            ((BoolErrRecord) this.record).setValue(b);
            this.value = Byte.valueOf(b);
        }
    }

    public void dispose() {
        super.dispose();
        this.record = null;
    }
}
