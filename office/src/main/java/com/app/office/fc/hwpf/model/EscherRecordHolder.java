package com.app.office.fc.hwpf.model;

import com.app.office.fc.ddf.DefaultEscherRecordFactory;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.util.Internal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Internal
public final class EscherRecordHolder {
    private final ArrayList<EscherRecord> escherRecords;

    public EscherRecordHolder() {
        this.escherRecords = new ArrayList<>();
    }

    public EscherRecordHolder(byte[] bArr, int i, int i2) {
        this();
        fillEscherRecords(bArr, i, i2);
    }

    private void fillEscherRecords(byte[] bArr, int i, int i2) {
        DefaultEscherRecordFactory defaultEscherRecordFactory = new DefaultEscherRecordFactory();
        int i3 = i;
        while (i3 < i + i2) {
            EscherRecord createRecord = defaultEscherRecordFactory.createRecord(bArr, i3);
            this.escherRecords.add(createRecord);
            i3 += createRecord.fillFields(bArr, i3, defaultEscherRecordFactory) + 1;
        }
    }

    public List<EscherRecord> getEscherRecords() {
        return this.escherRecords;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.escherRecords.size() == 0) {
            stringBuffer.append("No Escher Records Decoded");
            stringBuffer.append("\n");
        }
        Iterator<EscherRecord> it = this.escherRecords.iterator();
        while (it.hasNext()) {
            stringBuffer.append(it.next().toString());
        }
        return stringBuffer.toString();
    }

    public EscherContainerRecord getEscherContainer() {
        Iterator<EscherRecord> it = this.escherRecords.iterator();
        while (it.hasNext()) {
            EscherRecord next = it.next();
            if (next instanceof EscherContainerRecord) {
                return (EscherContainerRecord) next;
            }
        }
        return null;
    }

    public EscherRecord findFirstWithId(short s) {
        return findFirstWithId(s, getEscherRecords());
    }

    private static EscherRecord findFirstWithId(short s, List<EscherRecord> list) {
        EscherRecord findFirstWithId;
        for (EscherRecord next : list) {
            if (next.getRecordId() == s) {
                return next;
            }
        }
        for (EscherRecord next2 : list) {
            if (next2.isContainerRecord() && (findFirstWithId = findFirstWithId(s, next2.getChildRecords())) != null) {
                return findFirstWithId;
            }
        }
        return null;
    }

    public List<? extends EscherContainerRecord> getDgContainers() {
        ArrayList arrayList = new ArrayList(1);
        for (EscherRecord next : getEscherRecords()) {
            if (next.getRecordId() == -4094) {
                arrayList.add((EscherContainerRecord) next);
            }
        }
        return arrayList;
    }

    public List<? extends EscherContainerRecord> getDggContainers() {
        ArrayList arrayList = new ArrayList(1);
        for (EscherRecord next : getEscherRecords()) {
            if (next.getRecordId() == -4096) {
                arrayList.add((EscherContainerRecord) next);
            }
        }
        return arrayList;
    }

    public List<? extends EscherContainerRecord> getBStoreContainers() {
        ArrayList arrayList = new ArrayList(1);
        for (EscherContainerRecord childRecords : getDggContainers()) {
            for (EscherRecord next : childRecords.getChildRecords()) {
                if (next.getRecordId() == -4095) {
                    arrayList.add((EscherContainerRecord) next);
                }
            }
        }
        return arrayList;
    }

    public List<? extends EscherContainerRecord> getSpgrContainers() {
        ArrayList arrayList = new ArrayList(1);
        for (EscherContainerRecord childRecords : getDgContainers()) {
            for (EscherRecord next : childRecords.getChildRecords()) {
                if (next.getRecordId() == -4093) {
                    arrayList.add((EscherContainerRecord) next);
                }
            }
        }
        return arrayList;
    }

    public List<? extends EscherContainerRecord> getSpContainers() {
        ArrayList arrayList = new ArrayList(1);
        for (EscherContainerRecord childRecords : getSpgrContainers()) {
            Iterator<EscherRecord> it = childRecords.getChildRecords().iterator();
            while (it.hasNext()) {
                arrayList.add((EscherContainerRecord) it.next());
            }
        }
        return arrayList;
    }
}
