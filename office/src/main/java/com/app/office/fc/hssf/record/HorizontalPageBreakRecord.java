package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.record.PageBreakRecord;
import java.util.Iterator;

public final class HorizontalPageBreakRecord extends PageBreakRecord {
    public static final short sid = 27;

    public short getSid() {
        return 27;
    }

    public HorizontalPageBreakRecord() {
    }

    public HorizontalPageBreakRecord(RecordInputStream recordInputStream) {
        super(recordInputStream);
    }

    public Object clone() {
        HorizontalPageBreakRecord horizontalPageBreakRecord = new HorizontalPageBreakRecord();
        Iterator<PageBreakRecord.Break> breaksIterator = getBreaksIterator();
        while (breaksIterator.hasNext()) {
            PageBreakRecord.Break next = breaksIterator.next();
            horizontalPageBreakRecord.addBreak(next.main, next.subFrom, next.subTo);
        }
        return horizontalPageBreakRecord;
    }
}
