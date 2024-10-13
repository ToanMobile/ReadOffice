package com.app.office.fc.hslf.util;

import com.app.office.fc.util.LittleEndian;
import java.util.Date;
import java.util.GregorianCalendar;

public final class SystemTimeUtils {
    public static Date getDate(byte[] bArr) {
        return getDate(bArr, 0);
    }

    public static Date getDate(byte[] bArr, int i) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(1, LittleEndian.getShort(bArr, i));
        gregorianCalendar.set(2, LittleEndian.getShort(bArr, i + 2) - 1);
        gregorianCalendar.set(5, LittleEndian.getShort(bArr, i + 6));
        gregorianCalendar.set(11, LittleEndian.getShort(bArr, i + 8));
        gregorianCalendar.set(12, LittleEndian.getShort(bArr, i + 10));
        gregorianCalendar.set(13, LittleEndian.getShort(bArr, i + 12));
        gregorianCalendar.set(14, LittleEndian.getShort(bArr, i + 14));
        return gregorianCalendar.getTime();
    }

    public static void storeDate(Date date, byte[] bArr) {
        storeDate(date, bArr, 0);
    }

    public static void storeDate(Date date, byte[] bArr, int i) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        LittleEndian.putShort(bArr, i + 0, (short) gregorianCalendar.get(1));
        LittleEndian.putShort(bArr, i + 2, (short) (gregorianCalendar.get(2) + 1));
        LittleEndian.putShort(bArr, i + 4, (short) (gregorianCalendar.get(7) - 1));
        LittleEndian.putShort(bArr, i + 6, (short) gregorianCalendar.get(5));
        LittleEndian.putShort(bArr, i + 8, (short) gregorianCalendar.get(11));
        LittleEndian.putShort(bArr, i + 10, (short) gregorianCalendar.get(12));
        LittleEndian.putShort(bArr, i + 12, (short) gregorianCalendar.get(13));
        LittleEndian.putShort(bArr, i + 14, (short) gregorianCalendar.get(14));
    }
}
