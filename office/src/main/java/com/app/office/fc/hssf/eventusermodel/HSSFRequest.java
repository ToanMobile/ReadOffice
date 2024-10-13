package com.app.office.fc.hssf.eventusermodel;

import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.RecordFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HSSFRequest {
    private final Map<Short, List<HSSFListener>> _records = new HashMap(50);

    public void addListener(HSSFListener hSSFListener, short s) {
        List list = this._records.get(Short.valueOf(s));
        if (list == null) {
            list = new ArrayList(1);
            this._records.put(Short.valueOf(s), list);
        }
        list.add(hSSFListener);
    }

    public void addListenerForAllRecords(HSSFListener hSSFListener) {
        short[] allKnownRecordSIDs = RecordFactory.getAllKnownRecordSIDs();
        for (short addListener : allKnownRecordSIDs) {
            addListener(hSSFListener, addListener);
        }
    }

    /* access modifiers changed from: protected */
    public short processRecord(Record record) throws HSSFUserException {
        List<HSSFListener> list = this._records.get(Short.valueOf(record.getSid()));
        if (list == null) {
            return 0;
        }
        List list2 = list;
        short s = 0;
        for (int i = 0; i < list2.size(); i++) {
            Object obj = list2.get(i);
            if (obj instanceof AbortableHSSFListener) {
                s = ((AbortableHSSFListener) obj).abortableProcessRecord(record);
                if (s != 0) {
                    break;
                }
            } else {
                ((HSSFListener) obj).processRecord(record);
            }
        }
        return s;
    }
}
