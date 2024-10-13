package com.app.office.fc.hssf.record.aggregates;

import com.app.office.fc.hssf.record.ArrayRecord;
import com.app.office.fc.hssf.record.SharedFormulaRecord;
import com.app.office.fc.hssf.record.SharedValueRecordBase;
import com.app.office.fc.hssf.record.TableRecord;
import com.app.office.fc.hssf.util.CellRangeAddress8Bit;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import com.app.office.fc.ss.util.CellReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SharedValueManager {
    private final List<ArrayRecord> _arrayRecords;
    private final Map<SharedFormulaRecord, SharedFormulaGroup> _groupsBySharedFormulaRecord;
    private Map<Integer, SharedFormulaGroup> _groupsCache;
    private final TableRecord[] _tableRecords;

    private static final class SharedFormulaGroup {
        /* access modifiers changed from: private */
        public final CellReference _firstCell;
        private final FormulaRecordAggregate[] _frAggs;
        private int _numberOfFormulas;
        private final SharedFormulaRecord _sfr;

        public SharedFormulaGroup(SharedFormulaRecord sharedFormulaRecord, CellReference cellReference) {
            if (sharedFormulaRecord.isInRange(cellReference.getRow(), cellReference.getCol())) {
                this._sfr = sharedFormulaRecord;
                this._firstCell = cellReference;
                this._frAggs = new FormulaRecordAggregate[(((sharedFormulaRecord.getLastColumn() - sharedFormulaRecord.getFirstColumn()) + 1) * ((sharedFormulaRecord.getLastRow() - sharedFormulaRecord.getFirstRow()) + 1))];
                this._numberOfFormulas = 0;
                return;
            }
            throw new IllegalArgumentException("First formula cell " + cellReference.formatAsString() + " is not shared formula range " + sharedFormulaRecord.getRange().toString() + ".");
        }

        public void add(FormulaRecordAggregate formulaRecordAggregate) {
            if (this._numberOfFormulas != 0 || (this._firstCell.getRow() == formulaRecordAggregate.getRow() && this._firstCell.getCol() == formulaRecordAggregate.getColumn())) {
                int i = this._numberOfFormulas;
                FormulaRecordAggregate[] formulaRecordAggregateArr = this._frAggs;
                if (i < formulaRecordAggregateArr.length) {
                    this._numberOfFormulas = i + 1;
                    formulaRecordAggregateArr[i] = formulaRecordAggregate;
                    return;
                }
                throw new RuntimeException("Too many formula records for shared formula group");
            }
            throw new IllegalStateException("shared formula coding error: " + this._firstCell.getCol() + PackagingURIHelper.FORWARD_SLASH_CHAR + this._firstCell.getRow() + " != " + formulaRecordAggregate.getColumn() + PackagingURIHelper.FORWARD_SLASH_CHAR + formulaRecordAggregate.getRow());
        }

        public void unlinkSharedFormulas() {
            for (int i = 0; i < this._numberOfFormulas; i++) {
                this._frAggs[i].unlinkSharedFormula();
            }
        }

        public SharedFormulaRecord getSFR() {
            return this._sfr;
        }

        public final String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append(" [");
            stringBuffer.append(this._sfr.getRange().toString());
            stringBuffer.append("]");
            return stringBuffer.toString();
        }
    }

    public static SharedValueManager createEmpty() {
        return new SharedValueManager(new SharedFormulaRecord[0], new CellReference[0], new ArrayRecord[0], new TableRecord[0]);
    }

    private SharedValueManager(SharedFormulaRecord[] sharedFormulaRecordArr, CellReference[] cellReferenceArr, ArrayRecord[] arrayRecordArr, TableRecord[] tableRecordArr) {
        int length = sharedFormulaRecordArr.length;
        if (length == cellReferenceArr.length) {
            this._arrayRecords = toList(arrayRecordArr);
            this._tableRecords = tableRecordArr;
            HashMap hashMap = new HashMap((length * 3) / 2);
            for (int i = 0; i < length; i++) {
                SharedFormulaRecord sharedFormulaRecord = sharedFormulaRecordArr[i];
                hashMap.put(sharedFormulaRecord, new SharedFormulaGroup(sharedFormulaRecord, cellReferenceArr[i]));
            }
            this._groupsBySharedFormulaRecord = hashMap;
            return;
        }
        throw new IllegalArgumentException("array sizes don't match: " + length + "!=" + cellReferenceArr.length + ".");
    }

    private static <Z> List<Z> toList(Z[] zArr) {
        ArrayList arrayList = new ArrayList(zArr.length);
        for (Z add : zArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public static SharedValueManager create(SharedFormulaRecord[] sharedFormulaRecordArr, CellReference[] cellReferenceArr, ArrayRecord[] arrayRecordArr, TableRecord[] tableRecordArr) {
        if (sharedFormulaRecordArr.length + cellReferenceArr.length + arrayRecordArr.length + tableRecordArr.length < 1) {
            return createEmpty();
        }
        return new SharedValueManager(sharedFormulaRecordArr, cellReferenceArr, arrayRecordArr, tableRecordArr);
    }

    public SharedFormulaRecord linkSharedFormulaRecord(CellReference cellReference, FormulaRecordAggregate formulaRecordAggregate) {
        SharedFormulaGroup findFormulaGroupForCell = findFormulaGroupForCell(cellReference);
        findFormulaGroupForCell.add(formulaRecordAggregate);
        return findFormulaGroupForCell.getSFR();
    }

    private SharedFormulaGroup findFormulaGroupForCell(CellReference cellReference) {
        if (this._groupsCache == null) {
            this._groupsCache = new HashMap(this._groupsBySharedFormulaRecord.size());
            for (SharedFormulaGroup next : this._groupsBySharedFormulaRecord.values()) {
                this._groupsCache.put(getKeyForCache(next._firstCell), next);
            }
        }
        SharedFormulaGroup sharedFormulaGroup = this._groupsCache.get(getKeyForCache(cellReference));
        if (sharedFormulaGroup != null) {
            return sharedFormulaGroup;
        }
        throw new RuntimeException("Failed to find a matching shared formula record");
    }

    private Integer getKeyForCache(CellReference cellReference) {
        return new Integer(cellReference.getRow() | ((cellReference.getCol() + 1) << 16));
    }

    public SharedValueRecordBase getRecordForFirstCell(FormulaRecordAggregate formulaRecordAggregate) {
        SharedFormulaGroup findFormulaGroupForCell;
        CellReference expReference = formulaRecordAggregate.getFormulaRecord().getFormula().getExpReference();
        if (expReference == null) {
            return null;
        }
        int row = expReference.getRow();
        short col = expReference.getCol();
        if (formulaRecordAggregate.getRow() == row && formulaRecordAggregate.getColumn() == col) {
            if (!this._groupsBySharedFormulaRecord.isEmpty() && (findFormulaGroupForCell = findFormulaGroupForCell(expReference)) != null) {
                return findFormulaGroupForCell.getSFR();
            }
            for (TableRecord tableRecord : this._tableRecords) {
                if (tableRecord.isFirstCell(row, col)) {
                    return tableRecord;
                }
            }
            for (ArrayRecord next : this._arrayRecords) {
                if (next.isFirstCell(row, col)) {
                    return next;
                }
            }
        }
        return null;
    }

    public void unlink(SharedFormulaRecord sharedFormulaRecord) {
        SharedFormulaGroup remove = this._groupsBySharedFormulaRecord.remove(sharedFormulaRecord);
        if (remove != null) {
            this._groupsCache = null;
            remove.unlinkSharedFormulas();
            return;
        }
        throw new IllegalStateException("Failed to find formulas for shared formula");
    }

    public void addArrayRecord(ArrayRecord arrayRecord) {
        this._arrayRecords.add(arrayRecord);
    }

    public CellRangeAddress8Bit removeArrayFormula(int i, int i2) {
        for (ArrayRecord next : this._arrayRecords) {
            if (next.isInRange(i, i2)) {
                this._arrayRecords.remove(next);
                return next.getRange();
            }
        }
        String formatAsString = new CellReference(i, i2, false, false).formatAsString();
        throw new IllegalArgumentException("Specified cell " + formatAsString + " is not part of an array formula.");
    }

    public ArrayRecord getArrayRecord(int i, int i2) {
        for (ArrayRecord next : this._arrayRecords) {
            if (next.isFirstCell(i, i2)) {
                return next;
            }
        }
        return null;
    }
}
