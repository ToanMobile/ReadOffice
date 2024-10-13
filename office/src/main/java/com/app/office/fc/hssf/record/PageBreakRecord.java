package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class PageBreakRecord extends StandardRecord {
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private Map<Integer, Break> _breakMap;
    private List<Break> _breaks;

    public static final class Break {
        public static final int ENCODED_SIZE = 6;
        public int main;
        public int subFrom;
        public int subTo;

        public Break(int i, int i2, int i3) {
            this.main = i;
            this.subFrom = i2;
            this.subTo = i3;
        }

        public Break(RecordInputStream recordInputStream) {
            this.main = recordInputStream.readUShort() - 1;
            this.subFrom = recordInputStream.readUShort();
            this.subTo = recordInputStream.readUShort();
        }

        public void serialize(LittleEndianOutput littleEndianOutput) {
            littleEndianOutput.writeShort(this.main + 1);
            littleEndianOutput.writeShort(this.subFrom);
            littleEndianOutput.writeShort(this.subTo);
        }
    }

    protected PageBreakRecord() {
        this._breaks = new ArrayList();
        this._breakMap = new HashMap();
    }

    public PageBreakRecord(RecordInputStream recordInputStream) {
        short readShort = recordInputStream.readShort();
        this._breaks = new ArrayList(readShort + 2);
        this._breakMap = new HashMap();
        for (int i = 0; i < readShort; i++) {
            Break breakR = new Break(recordInputStream);
            this._breaks.add(breakR);
            this._breakMap.put(Integer.valueOf(breakR.main), breakR);
        }
    }

    public boolean isEmpty() {
        return this._breaks.isEmpty();
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return (this._breaks.size() * 6) + 2;
    }

    public final void serialize(LittleEndianOutput littleEndianOutput) {
        int size = this._breaks.size();
        littleEndianOutput.writeShort(size);
        for (int i = 0; i < size; i++) {
            this._breaks.get(i).serialize(littleEndianOutput);
        }
    }

    public int getNumBreaks() {
        return this._breaks.size();
    }

    public final Iterator<Break> getBreaksIterator() {
        return this._breaks.iterator();
    }

    public String toString() {
        String str;
        String str2;
        StringBuffer stringBuffer = new StringBuffer();
        String str3 = "row";
        if (getSid() == 27) {
            str2 = "HORIZONTALPAGEBREAK";
            str = "col";
        } else {
            str2 = "VERTICALPAGEBREAK";
            str = str3;
            str3 = "column";
        }
        stringBuffer.append("[" + str2 + "]");
        stringBuffer.append("\n");
        stringBuffer.append("     .sid        =");
        stringBuffer.append(getSid());
        stringBuffer.append("\n");
        stringBuffer.append("     .numbreaks =");
        stringBuffer.append(getNumBreaks());
        stringBuffer.append("\n");
        Iterator<Break> breaksIterator = getBreaksIterator();
        for (int i = 0; i < getNumBreaks(); i++) {
            Break next = breaksIterator.next();
            stringBuffer.append("     .");
            stringBuffer.append(str3);
            stringBuffer.append(" (zero-based) =");
            stringBuffer.append(next.main);
            stringBuffer.append("\n");
            stringBuffer.append("     .");
            stringBuffer.append(str);
            stringBuffer.append("From    =");
            stringBuffer.append(next.subFrom);
            stringBuffer.append("\n");
            stringBuffer.append("     .");
            stringBuffer.append(str);
            stringBuffer.append("To      =");
            stringBuffer.append(next.subTo);
            stringBuffer.append("\n");
        }
        stringBuffer.append("[" + str2 + "]");
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }

    public void addBreak(int i, int i2, int i3) {
        Integer valueOf = Integer.valueOf(i);
        Break breakR = this._breakMap.get(valueOf);
        if (breakR == null) {
            Break breakR2 = new Break(i, i2, i3);
            this._breakMap.put(valueOf, breakR2);
            this._breaks.add(breakR2);
            return;
        }
        breakR.main = i;
        breakR.subFrom = i2;
        breakR.subTo = i3;
    }

    public final void removeBreak(int i) {
        Integer valueOf = Integer.valueOf(i);
        this._breaks.remove(this._breakMap.get(valueOf));
        this._breakMap.remove(valueOf);
    }

    public final Break getBreak(int i) {
        return this._breakMap.get(Integer.valueOf(i));
    }

    public final int[] getBreaks() {
        int numBreaks = getNumBreaks();
        if (numBreaks < 1) {
            return EMPTY_INT_ARRAY;
        }
        int[] iArr = new int[numBreaks];
        for (int i = 0; i < numBreaks; i++) {
            iArr[i] = this._breaks.get(i).main;
        }
        return iArr;
    }
}
