package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.record.PageBreakRecord;
import java.util.Iterator;

public final class VerticalPageBreakRecord extends PageBreakRecord {
    public static final short sid = 26;

    public short getSid() {
        return 26;
    }

    public VerticalPageBreakRecord() {
    }

    public VerticalPageBreakRecord(RecordInputStream recordInputStream) {
        super(recordInputStream);
    }

    public Object clone() {
        VerticalPageBreakRecord verticalPageBreakRecord = new VerticalPageBreakRecord();
        Iterator<PageBreakRecord.Break> breaksIterator = getBreaksIterator();
        while (breaksIterator.hasNext()) {
            PageBreakRecord.Break next = breaksIterator.next();
            verticalPageBreakRecord.addBreak(next.main, next.subFrom, next.subTo);
        }
        return verticalPageBreakRecord;
    }
}
