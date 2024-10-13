package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.LittleEndian;
import java.util.Calendar;

public final class DateAndTime implements Cloneable {
    public static final int SIZE = 4;
    private static final BitField _dom = BitFieldFactory.getInstance(63488);
    private static final BitField _hours = BitFieldFactory.getInstance(1984);
    private static final BitField _minutes = BitFieldFactory.getInstance(63);
    private static final BitField _months = BitFieldFactory.getInstance(15);
    private static final BitField _weekday = BitFieldFactory.getInstance(57344);
    private static final BitField _years = BitFieldFactory.getInstance(8176);
    private short _info;
    private short _info2;

    public DateAndTime() {
    }

    public DateAndTime(byte[] bArr, int i) {
        this._info = LittleEndian.getShort(bArr, i);
        this._info2 = LittleEndian.getShort(bArr, i + 2);
    }

    public Calendar getDate() {
        Calendar instance = Calendar.getInstance();
        instance.set(_years.getValue(this._info2) + 1900, _months.getValue(this._info2) - 1, _dom.getValue(this._info), _hours.getValue(this._info), _minutes.getValue(this._info), 0);
        instance.set(14, 0);
        return instance;
    }

    public void serialize(byte[] bArr, int i) {
        LittleEndian.putShort(bArr, i, this._info);
        LittleEndian.putShort(bArr, i + 2, this._info2);
    }

    public boolean equals(Object obj) {
        DateAndTime dateAndTime = (DateAndTime) obj;
        return this._info == dateAndTime._info && this._info2 == dateAndTime._info2;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean isEmpty() {
        return this._info == 0 && this._info2 == 0;
    }

    public String toString() {
        if (isEmpty()) {
            return "[DTTM] EMPTY";
        }
        return "[DTTM] " + getDate();
    }
}
