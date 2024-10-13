package com.app.office.fc.hssf.record.aggregates;

import com.app.office.fc.hssf.model.RecordStream;
import com.app.office.fc.hssf.record.DVALRecord;
import com.app.office.fc.hssf.record.DVRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.aggregates.RecordAggregate;
import java.util.ArrayList;
import java.util.List;

public final class DataValidityTable extends RecordAggregate {
    private final DVALRecord _headerRec;
    private final List _validationList;

    public DataValidityTable(RecordStream recordStream) {
        this._headerRec = (DVALRecord) recordStream.getNext();
        ArrayList arrayList = new ArrayList();
        while (recordStream.peekNextClass() == DVRecord.class) {
            arrayList.add(recordStream.getNext());
        }
        this._validationList = arrayList;
    }

    public DataValidityTable() {
        this._headerRec = new DVALRecord();
        this._validationList = new ArrayList();
    }

    public void visitContainedRecords(RecordAggregate.RecordVisitor recordVisitor) {
        if (!this._validationList.isEmpty()) {
            recordVisitor.visitRecord(this._headerRec);
            for (int i = 0; i < this._validationList.size(); i++) {
                recordVisitor.visitRecord((Record) this._validationList.get(i));
            }
        }
    }

    public void addDataValidation(DVRecord dVRecord) {
        this._validationList.add(dVRecord);
        this._headerRec.setDVRecNo(this._validationList.size());
    }
}
