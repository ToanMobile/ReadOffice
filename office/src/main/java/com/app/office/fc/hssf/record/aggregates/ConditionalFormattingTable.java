package com.app.office.fc.hssf.record.aggregates;

import com.app.office.fc.hssf.formula.FormulaShifter;
import com.app.office.fc.hssf.model.RecordStream;
import com.app.office.fc.hssf.record.CFHeaderRecord;
import com.app.office.fc.hssf.record.aggregates.RecordAggregate;
import java.util.ArrayList;
import java.util.List;

public final class ConditionalFormattingTable extends RecordAggregate {
    private final List _cfHeaders;

    public ConditionalFormattingTable() {
        this._cfHeaders = new ArrayList();
    }

    public ConditionalFormattingTable(RecordStream recordStream) {
        ArrayList arrayList = new ArrayList();
        while (recordStream.peekNextClass() == CFHeaderRecord.class) {
            arrayList.add(CFRecordsAggregate.createCFAggregate(recordStream));
        }
        this._cfHeaders = arrayList;
    }

    public void visitContainedRecords(RecordAggregate.RecordVisitor recordVisitor) {
        for (int i = 0; i < this._cfHeaders.size(); i++) {
            ((CFRecordsAggregate) this._cfHeaders.get(i)).visitContainedRecords(recordVisitor);
        }
    }

    public int add(CFRecordsAggregate cFRecordsAggregate) {
        this._cfHeaders.add(cFRecordsAggregate);
        return this._cfHeaders.size() - 1;
    }

    public int size() {
        return this._cfHeaders.size();
    }

    public CFRecordsAggregate get(int i) {
        checkIndex(i);
        return (CFRecordsAggregate) this._cfHeaders.get(i);
    }

    public void remove(int i) {
        checkIndex(i);
        this._cfHeaders.remove(i);
    }

    private void checkIndex(int i) {
        if (i < 0 || i >= this._cfHeaders.size()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Specified CF index ");
            sb.append(i);
            sb.append(" is outside the allowable range (0..");
            sb.append(this._cfHeaders.size() - 1);
            sb.append(")");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public void updateFormulasAfterCellShift(FormulaShifter formulaShifter, int i) {
        int i2 = 0;
        while (i2 < this._cfHeaders.size()) {
            if (!((CFRecordsAggregate) this._cfHeaders.get(i2)).updateFormulasAfterCellShift(formulaShifter, i)) {
                this._cfHeaders.remove(i2);
                i2--;
            }
            i2++;
        }
    }
}
