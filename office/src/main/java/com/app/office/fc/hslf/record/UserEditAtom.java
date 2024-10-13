package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.util.Hashtable;

public final class UserEditAtom extends PositionDependentRecordAtom {
    public static final int LAST_VIEW_NONE = 0;
    public static final int LAST_VIEW_NOTES = 3;
    public static final int LAST_VIEW_OUTLINE_VIEW = 2;
    public static final int LAST_VIEW_SLIDE_VIEW = 1;
    private static long _type = 4085;
    private byte[] _header;
    private int docPersistRef;
    private int lastUserEditAtomOffset;
    private short lastViewType;
    private int lastViewedSlideID;
    private int maxPersistWritten;
    private int persistPointersOffset;
    private int pptVersion;
    private byte[] reserved;

    public int getLastViewedSlideID() {
        return this.lastViewedSlideID;
    }

    public short getLastViewType() {
        return this.lastViewType;
    }

    public int getLastUserEditAtomOffset() {
        return this.lastUserEditAtomOffset;
    }

    public int getPersistPointersOffset() {
        return this.persistPointersOffset;
    }

    public int getDocPersistRef() {
        return this.docPersistRef;
    }

    public int getMaxPersistWritten() {
        return this.maxPersistWritten;
    }

    public void setLastUserEditAtomOffset(int i) {
        this.lastUserEditAtomOffset = i;
    }

    public void setPersistPointersOffset(int i) {
        this.persistPointersOffset = i;
    }

    public void setLastViewType(short s) {
        this.lastViewType = s;
    }

    public void setMaxPersistWritten(int i) {
        this.maxPersistWritten = i;
    }

    protected UserEditAtom(byte[] bArr, int i, int i2) {
        i2 = i2 < 34 ? 34 : i2;
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this.lastViewedSlideID = LittleEndian.getInt(bArr, i + 0 + 8);
        this.pptVersion = LittleEndian.getInt(bArr, i + 4 + 8);
        this.lastUserEditAtomOffset = LittleEndian.getInt(bArr, i + 8 + 8);
        this.persistPointersOffset = LittleEndian.getInt(bArr, i + 12 + 8);
        this.docPersistRef = LittleEndian.getInt(bArr, i + 16 + 8);
        this.maxPersistWritten = LittleEndian.getInt(bArr, i + 20 + 8);
        this.lastViewType = LittleEndian.getShort(bArr, i + 24 + 8);
        byte[] bArr3 = new byte[((i2 - 26) - 8)];
        this.reserved = bArr3;
        System.arraycopy(bArr, i + 26 + 8, bArr3, 0, bArr3.length);
    }

    public long getRecordType() {
        return _type;
    }

    public void updateOtherRecordReferences(Hashtable<Integer, Integer> hashtable) {
        int i = this.lastUserEditAtomOffset;
        if (i != 0) {
            Integer num = hashtable.get(Integer.valueOf(i));
            if (num != null) {
                this.lastUserEditAtomOffset = num.intValue();
            } else {
                throw new RuntimeException("Couldn't find the new location of the UserEditAtom that used to be at " + this.lastUserEditAtomOffset);
            }
        }
        Integer num2 = hashtable.get(Integer.valueOf(this.persistPointersOffset));
        if (num2 != null) {
            this.persistPointersOffset = num2.intValue();
            return;
        }
        throw new RuntimeException("Couldn't find the new location of the PersistPtr that used to be at " + this.persistPointersOffset);
    }

    public void dispose() {
        this._header = null;
        this.reserved = null;
    }
}
