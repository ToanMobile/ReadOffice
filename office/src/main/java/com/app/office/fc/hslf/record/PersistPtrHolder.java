package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.POILogger;
import java.util.Enumeration;
import java.util.Hashtable;

public final class PersistPtrHolder extends PositionDependentRecordAtom {
    private byte[] _header;
    private byte[] _ptrData;
    private Hashtable<Integer, Integer> _slideLocations;
    private Hashtable<Integer, Integer> _slideOffsetDataLocation;
    private long _type;

    public int[] getKnownSlideIDs() {
        int size = this._slideLocations.size();
        int[] iArr = new int[size];
        Enumeration<Integer> keys = this._slideLocations.keys();
        for (int i = 0; i < size; i++) {
            iArr[i] = keys.nextElement().intValue();
        }
        return iArr;
    }

    public Hashtable<Integer, Integer> getSlideLocationsLookup() {
        return this._slideLocations;
    }

    public Hashtable<Integer, Integer> getSlideOffsetDataLocationsLookup() {
        return this._slideOffsetDataLocation;
    }

    public void addSlideLookup(int i, int i2) {
        byte[] bArr = this._ptrData;
        int length = bArr.length + 8;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        this._slideLocations.put(Integer.valueOf(i), Integer.valueOf(i2));
        this._slideOffsetDataLocation.put(Integer.valueOf(i), Integer.valueOf(this._ptrData.length + 4));
        LittleEndian.putInt(bArr2, length - 8, i + 1048576);
        LittleEndian.putInt(bArr2, length - 4, i2);
        this._ptrData = bArr2;
        LittleEndian.putInt(this._header, 4, length);
    }

    protected PersistPtrHolder(byte[] bArr, int i, int i2) {
        i2 = i2 < 8 ? 8 : i2;
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._type = (long) LittleEndian.getUShort(this._header, 2);
        this._slideLocations = new Hashtable<>();
        this._slideOffsetDataLocation = new Hashtable<>();
        byte[] bArr3 = new byte[(i2 - 8)];
        this._ptrData = bArr3;
        System.arraycopy(bArr, i + 8, bArr3, 0, bArr3.length);
        int i3 = 0;
        while (true) {
            byte[] bArr4 = this._ptrData;
            if (i3 < bArr4.length) {
                long uInt = LittleEndian.getUInt(bArr4, i3);
                int i4 = (int) (uInt >> 20);
                int i5 = (int) (uInt - ((long) (i4 << 20)));
                i3 += 4;
                for (int i6 = 0; i6 < i4; i6++) {
                    int i7 = i5 + i6;
                    this._slideLocations.put(Integer.valueOf(i7), Integer.valueOf((int) LittleEndian.getUInt(this._ptrData, i3)));
                    this._slideOffsetDataLocation.put(Integer.valueOf(i7), Integer.valueOf(i3));
                    i3 += 4;
                }
            } else {
                return;
            }
        }
    }

    public long getRecordType() {
        return this._type;
    }

    public void updateOtherRecordReferences(Hashtable<Integer, Integer> hashtable) {
        int[] knownSlideIDs = getKnownSlideIDs();
        for (int valueOf : knownSlideIDs) {
            Integer valueOf2 = Integer.valueOf(valueOf);
            Integer num = this._slideLocations.get(valueOf2);
            Integer num2 = hashtable.get(num);
            if (num2 == null) {
                this.logger.log(POILogger.WARN, (Object) "Couldn't find the new location of the \"slide\" with id " + valueOf2 + " that used to be at " + num);
                this.logger.log(POILogger.WARN, (Object) "Not updating the position of it, you probably won't be able to find it any more (if you ever could!)");
            } else {
                num = num2;
            }
            LittleEndian.putInt(this._ptrData, this._slideOffsetDataLocation.get(valueOf2).intValue(), num.intValue());
            this._slideLocations.remove(valueOf2);
            this._slideLocations.put(valueOf2, num);
        }
    }

    public void dispose() {
        this._header = null;
        this._ptrData = null;
    }
}
