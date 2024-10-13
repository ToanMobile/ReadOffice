package com.app.office.fc.hssf.record.aggregates;

import com.app.office.fc.hssf.formula.FormulaShifter;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.model.RecordStream;
import com.app.office.fc.hssf.record.BlankRecord;
import com.app.office.fc.hssf.record.CellValueRecordInterface;
import com.app.office.fc.hssf.record.FormulaRecord;
import com.app.office.fc.hssf.record.MulBlankRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.RecordBase;
import com.app.office.fc.hssf.record.StringRecord;
import com.app.office.fc.hssf.record.aggregates.RecordAggregate;
import java.util.ArrayList;
import java.util.Iterator;

public final class ValueRecordsAggregate implements Iterable<CellValueRecordInterface> {
    private static final int INDEX_NOT_SET = -1;
    private static final int MAX_ROW_INDEX = 65535;
    private int firstcell;
    private int lastcell;
    /* access modifiers changed from: private */
    public CellValueRecordInterface[][] records;

    public ValueRecordsAggregate() {
        this(-1, -1, new CellValueRecordInterface[30][]);
    }

    private ValueRecordsAggregate(int i, int i2, CellValueRecordInterface[][] cellValueRecordInterfaceArr) {
        this.firstcell = -1;
        this.lastcell = -1;
        this.firstcell = i;
        this.lastcell = i2;
        this.records = cellValueRecordInterfaceArr;
    }

    public void insertCell(CellValueRecordInterface cellValueRecordInterface) {
        short column = cellValueRecordInterface.getColumn();
        int row = cellValueRecordInterface.getRow();
        CellValueRecordInterface[][] cellValueRecordInterfaceArr = this.records;
        if (row >= cellValueRecordInterfaceArr.length) {
            int length = cellValueRecordInterfaceArr.length * 2;
            int i = row + 1;
            if (length < i) {
                length = i;
            }
            CellValueRecordInterface[][] cellValueRecordInterfaceArr2 = new CellValueRecordInterface[length][];
            this.records = cellValueRecordInterfaceArr2;
            System.arraycopy(cellValueRecordInterfaceArr, 0, cellValueRecordInterfaceArr2, 0, cellValueRecordInterfaceArr.length);
        }
        CellValueRecordInterface[][] cellValueRecordInterfaceArr3 = this.records;
        CellValueRecordInterface[] cellValueRecordInterfaceArr4 = cellValueRecordInterfaceArr3[row];
        if (cellValueRecordInterfaceArr4 == null) {
            int i2 = column + 1;
            if (i2 < 10) {
                i2 = 10;
            }
            cellValueRecordInterfaceArr4 = new CellValueRecordInterface[i2];
            cellValueRecordInterfaceArr3[row] = cellValueRecordInterfaceArr4;
        }
        if (column >= cellValueRecordInterfaceArr4.length) {
            int length2 = cellValueRecordInterfaceArr4.length * 2;
            int i3 = column + 1;
            if (length2 < i3) {
                length2 = i3;
            }
            CellValueRecordInterface[] cellValueRecordInterfaceArr5 = new CellValueRecordInterface[length2];
            System.arraycopy(cellValueRecordInterfaceArr4, 0, cellValueRecordInterfaceArr5, 0, cellValueRecordInterfaceArr4.length);
            this.records[row] = cellValueRecordInterfaceArr5;
            cellValueRecordInterfaceArr4 = cellValueRecordInterfaceArr5;
        }
        cellValueRecordInterfaceArr4[column] = cellValueRecordInterface;
        int i4 = this.firstcell;
        if (column < i4 || i4 == -1) {
            this.firstcell = column;
        }
        int i5 = this.lastcell;
        if (column > i5 || i5 == -1) {
            this.lastcell = column;
        }
    }

    public void removeCell(CellValueRecordInterface cellValueRecordInterface) {
        if (cellValueRecordInterface != null) {
            int row = cellValueRecordInterface.getRow();
            CellValueRecordInterface[][] cellValueRecordInterfaceArr = this.records;
            if (row < cellValueRecordInterfaceArr.length) {
                CellValueRecordInterface[] cellValueRecordInterfaceArr2 = cellValueRecordInterfaceArr[row];
                if (cellValueRecordInterfaceArr2 != null) {
                    short column = cellValueRecordInterface.getColumn();
                    if (column < cellValueRecordInterfaceArr2.length) {
                        cellValueRecordInterfaceArr2[column] = null;
                        return;
                    }
                    throw new RuntimeException("cell column is out of range");
                }
                throw new RuntimeException("cell row is already empty");
            }
            throw new RuntimeException("cell row is out of range");
        }
        throw new IllegalArgumentException("cell must not be null");
    }

    public void removeAllCellsValuesForRow(int i) {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException("Specified rowIndex " + i + " is outside the allowable range (0.." + 65535 + ")");
        }
        CellValueRecordInterface[][] cellValueRecordInterfaceArr = this.records;
        if (i < cellValueRecordInterfaceArr.length) {
            cellValueRecordInterfaceArr[i] = null;
        }
    }

    public int getPhysicalNumberOfCells() {
        int i = 0;
        int i2 = 0;
        while (true) {
            CellValueRecordInterface[][] cellValueRecordInterfaceArr = this.records;
            if (i >= cellValueRecordInterfaceArr.length) {
                return i2;
            }
            CellValueRecordInterface[] cellValueRecordInterfaceArr2 = cellValueRecordInterfaceArr[i];
            if (cellValueRecordInterfaceArr2 != null) {
                for (CellValueRecordInterface cellValueRecordInterface : cellValueRecordInterfaceArr2) {
                    if (cellValueRecordInterface != null) {
                        i2++;
                    }
                }
            }
            i++;
        }
    }

    public int getFirstCellNum() {
        return this.firstcell;
    }

    public int getLastCellNum() {
        return this.lastcell;
    }

    public void addMultipleBlanks(MulBlankRecord mulBlankRecord) {
        for (int i = 0; i < mulBlankRecord.getNumColumns(); i++) {
            BlankRecord blankRecord = new BlankRecord();
            blankRecord.setColumn((short) (mulBlankRecord.getFirstColumn() + i));
            blankRecord.setRow(mulBlankRecord.getRow());
            blankRecord.setXFIndex(mulBlankRecord.getXFAt(i));
            insertCell(blankRecord);
        }
    }

    public void construct(CellValueRecordInterface cellValueRecordInterface, RecordStream recordStream, SharedValueManager sharedValueManager) {
        if (cellValueRecordInterface instanceof FormulaRecord) {
            insertCell(new FormulaRecordAggregate((FormulaRecord) cellValueRecordInterface, recordStream.peekNextClass() == StringRecord.class ? (StringRecord) recordStream.getNext() : null, sharedValueManager));
        } else {
            insertCell(cellValueRecordInterface);
        }
    }

    public int getRowCellBlockSize(int i, int i2) {
        int i3 = 0;
        while (i <= i2) {
            CellValueRecordInterface[][] cellValueRecordInterfaceArr = this.records;
            if (i >= cellValueRecordInterfaceArr.length) {
                break;
            }
            i3 += getRowSerializedSize(cellValueRecordInterfaceArr[i]);
            i++;
        }
        return i3;
    }

    public boolean rowHasCells(int i) {
        CellValueRecordInterface[] cellValueRecordInterfaceArr;
        CellValueRecordInterface[][] cellValueRecordInterfaceArr2 = this.records;
        if (i >= cellValueRecordInterfaceArr2.length || (cellValueRecordInterfaceArr = cellValueRecordInterfaceArr2[i]) == null) {
            return false;
        }
        for (CellValueRecordInterface cellValueRecordInterface : cellValueRecordInterfaceArr) {
            if (cellValueRecordInterface != null) {
                return true;
            }
        }
        return false;
    }

    private static int getRowSerializedSize(CellValueRecordInterface[] cellValueRecordInterfaceArr) {
        int i = 0;
        if (cellValueRecordInterfaceArr == null) {
            return 0;
        }
        int i2 = 0;
        while (i < cellValueRecordInterfaceArr.length) {
            RecordBase recordBase = (RecordBase) cellValueRecordInterfaceArr[i];
            if (recordBase != null) {
                int countBlanks = countBlanks(cellValueRecordInterfaceArr, i);
                if (countBlanks > 1) {
                    i2 += (countBlanks * 2) + 10;
                    i += countBlanks - 1;
                } else {
                    i2 += recordBase.getRecordSize();
                }
            }
            i++;
        }
        return i2;
    }

    public void visitCellsForRow(int i, RecordAggregate.RecordVisitor recordVisitor) {
        CellValueRecordInterface[] cellValueRecordInterfaceArr = this.records[i];
        if (cellValueRecordInterfaceArr != null) {
            int i2 = 0;
            while (i2 < cellValueRecordInterfaceArr.length) {
                RecordBase recordBase = (RecordBase) cellValueRecordInterfaceArr[i2];
                if (recordBase != null) {
                    int countBlanks = countBlanks(cellValueRecordInterfaceArr, i2);
                    if (countBlanks > 1) {
                        recordVisitor.visitRecord(createMBR(cellValueRecordInterfaceArr, i2, countBlanks));
                        i2 += countBlanks - 1;
                    } else if (recordBase instanceof RecordAggregate) {
                        ((RecordAggregate) recordBase).visitContainedRecords(recordVisitor);
                    } else {
                        recordVisitor.visitRecord((Record) recordBase);
                    }
                }
                i2++;
            }
            return;
        }
        throw new IllegalArgumentException("Row [" + i + "] is empty");
    }

    private static int countBlanks(CellValueRecordInterface[] cellValueRecordInterfaceArr, int i) {
        int i2 = i;
        while (i2 < cellValueRecordInterfaceArr.length && (cellValueRecordInterfaceArr[i2] instanceof BlankRecord)) {
            i2++;
        }
        return i2 - i;
    }

    private MulBlankRecord createMBR(CellValueRecordInterface[] cellValueRecordInterfaceArr, int i, int i2) {
        short[] sArr = new short[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            sArr[i3] = cellValueRecordInterfaceArr[i + i3].getXFIndex();
        }
        return new MulBlankRecord(cellValueRecordInterfaceArr[i].getRow(), i, sArr);
    }

    public void updateFormulasAfterRowShift(FormulaShifter formulaShifter, int i) {
        int i2 = 0;
        while (true) {
            CellValueRecordInterface[][] cellValueRecordInterfaceArr = this.records;
            if (i2 < cellValueRecordInterfaceArr.length) {
                CellValueRecordInterface[] cellValueRecordInterfaceArr2 = cellValueRecordInterfaceArr[i2];
                if (cellValueRecordInterfaceArr2 != null) {
                    for (CellValueRecordInterface cellValueRecordInterface : cellValueRecordInterfaceArr2) {
                        if (cellValueRecordInterface instanceof FormulaRecordAggregate) {
                            FormulaRecord formulaRecord = ((FormulaRecordAggregate) cellValueRecordInterface).getFormulaRecord();
                            Ptg[] parsedExpression = formulaRecord.getParsedExpression();
                            if (formulaShifter.adjustFormula(parsedExpression, i)) {
                                formulaRecord.setParsedExpression(parsedExpression);
                            }
                        }
                    }
                }
                i2++;
            } else {
                return;
            }
        }
    }

    class ValueIterator implements Iterator<CellValueRecordInterface> {
        int curColIndex = -1;
        int curRowIndex = 0;
        int nextColIndex = -1;
        int nextRowIndex = 0;

        public ValueIterator() {
            getNextPos();
        }

        /* access modifiers changed from: package-private */
        public void getNextPos() {
            if (this.nextRowIndex < ValueRecordsAggregate.this.records.length) {
                while (this.nextRowIndex < ValueRecordsAggregate.this.records.length) {
                    this.nextColIndex++;
                    if (ValueRecordsAggregate.this.records[this.nextRowIndex] == null || this.nextColIndex >= ValueRecordsAggregate.this.records[this.nextRowIndex].length) {
                        this.nextRowIndex++;
                        this.nextColIndex = -1;
                    } else if (ValueRecordsAggregate.this.records[this.nextRowIndex][this.nextColIndex] != null) {
                        return;
                    }
                }
            }
        }

        public boolean hasNext() {
            return this.nextRowIndex < ValueRecordsAggregate.this.records.length;
        }

        public CellValueRecordInterface next() {
            if (hasNext()) {
                this.curRowIndex = this.nextRowIndex;
                this.curColIndex = this.nextColIndex;
                CellValueRecordInterface cellValueRecordInterface = ValueRecordsAggregate.this.records[this.curRowIndex][this.curColIndex];
                getNextPos();
                return cellValueRecordInterface;
            }
            throw new IndexOutOfBoundsException("iterator has no next");
        }

        public void remove() {
            ValueRecordsAggregate.this.records[this.curRowIndex][this.curColIndex] = null;
        }
    }

    public Iterator<CellValueRecordInterface> iterator() {
        return new ValueIterator();
    }

    @Deprecated
    public CellValueRecordInterface[] getValueRecords() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            CellValueRecordInterface[][] cellValueRecordInterfaceArr = this.records;
            if (i < cellValueRecordInterfaceArr.length) {
                CellValueRecordInterface[] cellValueRecordInterfaceArr2 = cellValueRecordInterfaceArr[i];
                if (cellValueRecordInterfaceArr2 != null) {
                    for (CellValueRecordInterface cellValueRecordInterface : cellValueRecordInterfaceArr2) {
                        if (cellValueRecordInterface != null) {
                            arrayList.add(cellValueRecordInterface);
                        }
                    }
                }
                i++;
            } else {
                CellValueRecordInterface[] cellValueRecordInterfaceArr3 = new CellValueRecordInterface[arrayList.size()];
                arrayList.toArray(cellValueRecordInterfaceArr3);
                return cellValueRecordInterfaceArr3;
            }
        }
    }

    public Object clone() {
        throw new RuntimeException("clone() should not be called.  ValueRecordsAggregate should be copied via Sheet.cloneSheet()");
    }

    public void dispose() {
        this.records = null;
    }
}
