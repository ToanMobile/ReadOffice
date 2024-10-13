package com.app.office.fc.hssf.record.aggregates;

import com.app.office.fc.hssf.model.RecordStream;
import com.app.office.fc.hssf.record.BOFRecord;
import com.app.office.fc.hssf.record.EOFRecord;
import com.app.office.fc.hssf.record.HeaderFooterRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.RecordBase;
import com.app.office.fc.hssf.record.aggregates.RecordAggregate;
import java.util.ArrayList;
import java.util.List;

public final class ChartSubstreamRecordAggregate extends RecordAggregate {
    private final BOFRecord _bofRec;
    private PageSettingsBlock _psBlock;
    private final List<RecordBase> _recs;

    public ChartSubstreamRecordAggregate(RecordStream recordStream) {
        this._bofRec = (BOFRecord) recordStream.getNext();
        ArrayList arrayList = new ArrayList();
        while (recordStream.peekNextClass() != EOFRecord.class) {
            if (!PageSettingsBlock.isComponentRecord(recordStream.peekNextSid())) {
                arrayList.add(recordStream.getNext());
            } else if (this._psBlock == null) {
                PageSettingsBlock pageSettingsBlock = new PageSettingsBlock(recordStream);
                this._psBlock = pageSettingsBlock;
                arrayList.add(pageSettingsBlock);
            } else if (recordStream.peekNextSid() == 2204) {
                this._psBlock.addLateHeaderFooter((HeaderFooterRecord) recordStream.getNext());
            } else {
                throw new IllegalStateException("Found more than one PageSettingsBlock in chart sub-stream");
            }
        }
        this._recs = arrayList;
        if (!(recordStream.getNext() instanceof EOFRecord)) {
            throw new IllegalStateException("Bad chart EOF");
        }
    }

    public void visitContainedRecords(RecordAggregate.RecordVisitor recordVisitor) {
        if (!this._recs.isEmpty()) {
            recordVisitor.visitRecord(this._bofRec);
            for (int i = 0; i < this._recs.size(); i++) {
                RecordBase recordBase = this._recs.get(i);
                if (recordBase instanceof RecordAggregate) {
                    ((RecordAggregate) recordBase).visitContainedRecords(recordVisitor);
                } else {
                    recordVisitor.visitRecord((Record) recordBase);
                }
            }
            recordVisitor.visitRecord(EOFRecord.instance);
        }
    }
}
