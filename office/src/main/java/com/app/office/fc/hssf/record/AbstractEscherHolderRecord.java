package com.app.office.fc.hssf.record;

import com.app.office.fc.ddf.DefaultEscherRecordFactory;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.NullEscherSerializationListener;
import com.app.office.fc.hssf.util.LazilyConcatenatedByteArray;
import com.app.office.fc.util.LittleEndian;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEscherHolderRecord extends Record {
    private static boolean DESERIALISE;
    private List<EscherRecord> escherRecords = new ArrayList();
    private LazilyConcatenatedByteArray rawDataContainer = new LazilyConcatenatedByteArray();

    /* access modifiers changed from: protected */
    public abstract String getRecordName();

    public abstract short getSid();

    static {
        try {
            DESERIALISE = System.getProperty("poi.deserialize.escher") != null;
        } catch (SecurityException unused) {
            DESERIALISE = false;
        }
    }

    public AbstractEscherHolderRecord() {
    }

    public AbstractEscherHolderRecord(RecordInputStream recordInputStream) {
        if (!DESERIALISE) {
            this.rawDataContainer.concatenate(recordInputStream.readRemainder());
            return;
        }
        byte[] readAllContinuedRemainder = recordInputStream.readAllContinuedRemainder();
        convertToEscherRecords(0, readAllContinuedRemainder.length, readAllContinuedRemainder);
    }

    /* access modifiers changed from: protected */
    public void convertRawBytesToEscherRecords() {
        byte[] rawData = getRawData();
        convertToEscherRecords(0, rawData.length, rawData);
    }

    private void convertToEscherRecords(int i, int i2, byte[] bArr) {
        this.escherRecords.clear();
        DefaultEscherRecordFactory defaultEscherRecordFactory = new DefaultEscherRecordFactory();
        int i3 = i;
        while (i3 < i + i2) {
            EscherRecord createRecord = defaultEscherRecordFactory.createRecord(bArr, i3);
            int fillFields = createRecord.fillFields(bArr, i3, defaultEscherRecordFactory);
            this.escherRecords.add(createRecord);
            i3 += fillFields;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String property = System.getProperty("line.separator");
        stringBuffer.append('[' + getRecordName() + ']' + property);
        if (this.escherRecords.size() == 0) {
            stringBuffer.append("No Escher Records Decoded" + property);
        }
        for (EscherRecord obj : this.escherRecords) {
            stringBuffer.append(obj.toString());
        }
        stringBuffer.append("[/" + getRecordName() + ']' + property);
        return stringBuffer.toString();
    }

    public int serialize(int i, byte[] bArr) {
        int i2 = i + 0;
        LittleEndian.putShort(bArr, i2, getSid());
        int i3 = i + 2;
        LittleEndian.putShort(bArr, i3, (short) (getRecordSize() - 4));
        byte[] rawData = getRawData();
        if (this.escherRecords.size() != 0 || rawData == null) {
            LittleEndian.putShort(bArr, i2, getSid());
            LittleEndian.putShort(bArr, i3, (short) (getRecordSize() - 4));
            int i4 = i + 4;
            for (EscherRecord serialize : this.escherRecords) {
                i4 += serialize.serialize(i4, bArr, new NullEscherSerializationListener());
            }
            return getRecordSize();
        }
        LittleEndian.putShort(bArr, i2, getSid());
        LittleEndian.putShort(bArr, i3, (short) (getRecordSize() - 4));
        System.arraycopy(rawData, 0, bArr, i + 4, rawData.length);
        return rawData.length + 4;
    }

    public int getRecordSize() {
        byte[] rawData = getRawData();
        if (this.escherRecords.size() == 0 && rawData != null) {
            return rawData.length;
        }
        int i = 0;
        for (EscherRecord recordSize : this.escherRecords) {
            i += recordSize.getRecordSize();
        }
        return i;
    }

    public Object clone() {
        return cloneViaReserialise();
    }

    public void addEscherRecord(int i, EscherRecord escherRecord) {
        this.escherRecords.add(i, escherRecord);
    }

    public boolean addEscherRecord(EscherRecord escherRecord) {
        return this.escherRecords.add(escherRecord);
    }

    public List<EscherRecord> getEscherRecords() {
        return this.escherRecords;
    }

    public void clearEscherRecords() {
        this.escherRecords.clear();
    }

    public EscherContainerRecord getEscherContainer() {
        for (EscherRecord next : this.escherRecords) {
            if (next instanceof EscherContainerRecord) {
                return (EscherContainerRecord) next;
            }
        }
        return null;
    }

    public List<EscherContainerRecord> getgetEscherContainers() {
        ArrayList arrayList = new ArrayList();
        for (EscherRecord next : this.escherRecords) {
            if (next instanceof EscherContainerRecord) {
                arrayList.add((EscherContainerRecord) next);
            }
        }
        return arrayList;
    }

    public EscherRecord findFirstWithId(short s) {
        return findFirstWithId(s, getEscherRecords());
    }

    private EscherRecord findFirstWithId(short s, List<EscherRecord> list) {
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

    public EscherRecord getEscherRecord(int i) {
        return this.escherRecords.get(i);
    }

    public void join(AbstractEscherHolderRecord abstractEscherHolderRecord) {
        this.rawDataContainer.concatenate(abstractEscherHolderRecord.getRawData());
    }

    public void processContinueRecord(byte[] bArr) {
        this.rawDataContainer.concatenate(bArr);
    }

    public byte[] getRawData() {
        return this.rawDataContainer.toArray();
    }

    public void setRawData(byte[] bArr) {
        this.rawDataContainer.clear();
        this.rawDataContainer.concatenate(bArr);
    }

    public void decode() {
        byte[] rawData = getRawData();
        convertToEscherRecords(0, rawData.length, rawData);
    }
}
