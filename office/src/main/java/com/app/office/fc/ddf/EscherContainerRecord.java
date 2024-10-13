package com.app.office.fc.ddf;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public final class EscherContainerRecord extends EscherRecord {
    public static final short BSTORE_CONTAINER = -4095;
    public static final short DGG_CONTAINER = -4096;
    public static final short DG_CONTAINER = -4094;
    public static final short SOLVER_CONTAINER = -4091;
    public static final short SPGR_CONTAINER = -4093;
    public static final short SP_CONTAINER = -4092;
    private final List<EscherRecord> _childRecords = new ArrayList();

    public int fillFields(byte[] bArr, int i, EscherRecordFactory escherRecordFactory) {
        int readHeader = readHeader(bArr, i);
        int i2 = 8;
        int i3 = i + 8;
        while (readHeader > 0 && i3 < bArr.length) {
            EscherRecord createRecord = escherRecordFactory.createRecord(bArr, i3);
            int fillFields = createRecord.fillFields(bArr, i3, escherRecordFactory);
            i2 += fillFields;
            i3 += fillFields;
            readHeader -= fillFields;
            addChildRecord(createRecord);
            int length = bArr.length;
        }
        return i2;
    }

    public int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener) {
        escherSerializationListener.beforeRecordSerialize(i, getRecordId(), this);
        LittleEndian.putShort(bArr, i, getOptions());
        LittleEndian.putShort(bArr, i + 2, getRecordId());
        int i2 = 0;
        for (EscherRecord recordSize : this._childRecords) {
            i2 += recordSize.getRecordSize();
        }
        LittleEndian.putInt(bArr, i + 4, i2);
        int i3 = i + 8;
        for (EscherRecord serialize : this._childRecords) {
            i3 += serialize.serialize(i3, bArr, escherSerializationListener);
        }
        int i4 = i3 - i;
        escherSerializationListener.afterRecordSerialize(i3, getRecordId(), i4, this);
        return i4;
    }

    public int getRecordSize() {
        int i = 0;
        for (EscherRecord recordSize : this._childRecords) {
            i += recordSize.getRecordSize();
        }
        return i + 8;
    }

    public boolean hasChildOfType(short s) {
        for (EscherRecord recordId : this._childRecords) {
            if (recordId.getRecordId() == s) {
                return true;
            }
        }
        return false;
    }

    public EscherRecord getChild(int i) {
        return this._childRecords.get(i);
    }

    public List<EscherRecord> getChildRecords() {
        return new ArrayList(this._childRecords);
    }

    public Iterator<EscherRecord> getChildIterator() {
        return new ReadOnlyIterator(this._childRecords);
    }

    private static final class ReadOnlyIterator implements Iterator<EscherRecord> {
        private int _index = 0;
        private final List<EscherRecord> _list;

        public ReadOnlyIterator(List<EscherRecord> list) {
            this._list = list;
        }

        public boolean hasNext() {
            return this._index < this._list.size();
        }

        public EscherRecord next() {
            if (hasNext()) {
                List<EscherRecord> list = this._list;
                int i = this._index;
                this._index = i + 1;
                return list.get(i);
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public void setChildRecords(List<EscherRecord> list) {
        List<EscherRecord> list2 = this._childRecords;
        if (list != list2) {
            list2.clear();
            this._childRecords.addAll(list);
            return;
        }
        throw new IllegalStateException("Child records private data member has escaped");
    }

    public boolean removeChildRecord(EscherRecord escherRecord) {
        return this._childRecords.remove(escherRecord);
    }

    public List<EscherContainerRecord> getChildContainers() {
        ArrayList arrayList = new ArrayList();
        for (EscherRecord next : this._childRecords) {
            if (next instanceof EscherContainerRecord) {
                arrayList.add((EscherContainerRecord) next);
            }
        }
        return arrayList;
    }

    public String getRecordName() {
        switch (getRecordId()) {
            case -4096:
                return "DggContainer";
            case -4095:
                return "BStoreContainer";
            case -4094:
                return "DgContainer";
            case -4093:
                return "SpgrContainer";
            case -4092:
                return "SpContainer";
            case -4091:
                return "SolverContainer";
            default:
                return "Container 0x" + HexDump.toHex(getRecordId());
        }
    }

    public void display(PrintWriter printWriter, int i) {
        super.display(printWriter, i);
        for (EscherRecord display : this._childRecords) {
            display.display(printWriter, i + 1);
        }
    }

    public void addChildRecord(EscherRecord escherRecord) {
        this._childRecords.add(escherRecord);
    }

    public void addChildBefore(EscherRecord escherRecord, int i) {
        int i2 = 0;
        while (i2 < this._childRecords.size()) {
            if (this._childRecords.get(i2).getRecordId() == i) {
                this._childRecords.add(i2, escherRecord);
                i2++;
            }
            i2++;
        }
    }

    public String toString() {
        String property = System.getProperty("line.separator");
        StringBuffer stringBuffer = new StringBuffer();
        if (this._childRecords.size() > 0) {
            stringBuffer.append("  children: " + property);
            int i = 0;
            for (EscherRecord valueOf : this._childRecords) {
                stringBuffer.append("   Child " + i + ":" + property);
                String replaceAll = String.valueOf(valueOf).replaceAll("\n", "\n    ");
                stringBuffer.append("    ");
                stringBuffer.append(replaceAll);
                stringBuffer.append(property);
                i++;
            }
        }
        return getClass().getName() + " (" + getRecordName() + "):" + property + "  isContainer: " + isContainerRecord() + property + "  options: 0x" + HexDump.toHex(getOptions()) + property + "  recordId: 0x" + HexDump.toHex(getRecordId()) + property + "  numchildren: " + this._childRecords.size() + property + stringBuffer.toString();
    }

    public <T extends EscherRecord> T getChildById(short s) {
        Iterator<EscherRecord> it = this._childRecords.iterator();
        while (it.hasNext()) {
            T t = (EscherRecord) it.next();
            if (t.getRecordId() == s) {
                return t;
            }
        }
        return null;
    }

    public void getRecordsById(short s, List<EscherRecord> list) {
        for (EscherRecord next : this._childRecords) {
            if (next instanceof EscherContainerRecord) {
                ((EscherContainerRecord) next).getRecordsById(s, list);
            } else if (next.getRecordId() == s) {
                list.add(next);
            }
        }
    }

    public void dispose() {
        if (this._childRecords != null) {
            for (int i = 0; i < this._childRecords.size(); i++) {
                this._childRecords.get(i).dispose();
            }
            this._childRecords.clear();
        }
    }
}
