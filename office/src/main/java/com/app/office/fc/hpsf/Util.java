package com.app.office.fc.hpsf;

import androidx.work.WorkRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Date;

public class Util {
    public static final long EPOCH_DIFF = 11644473600000L;

    public static boolean equal(byte[] bArr, byte[] bArr2) {
        if (bArr.length != bArr2.length) {
            return false;
        }
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void copy(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        for (int i4 = 0; i4 < i2; i4++) {
            bArr2[i3 + i4] = bArr[i + i4];
        }
    }

    public static byte[] cat(byte[][] bArr) {
        int i = 0;
        for (byte[] length : bArr) {
            i += length.length;
        }
        byte[] bArr2 = new byte[i];
        int i2 = 0;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            int i4 = 0;
            while (i4 < bArr[i3].length) {
                bArr2[i2] = bArr[i3][i4];
                i4++;
                i2++;
            }
        }
        return bArr2;
    }

    public static byte[] copy(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        copy(bArr, i, i2, bArr2, 0);
        return bArr2;
    }

    public static Date filetimeToDate(int i, int i2) {
        return filetimeToDate((((long) i2) & 4294967295L) | (((long) i) << 32));
    }

    public static Date filetimeToDate(long j) {
        return new Date((j / WorkRequest.MIN_BACKOFF_MILLIS) - EPOCH_DIFF);
    }

    public static long dateToFileTime(Date date) {
        return (date.getTime() + EPOCH_DIFF) * WorkRequest.MIN_BACKOFF_MILLIS;
    }

    public static boolean equals(Collection<?> collection, Collection<?> collection2) {
        return internalEquals(collection.toArray(), collection2.toArray());
    }

    public static boolean equals(Object[] objArr, Object[] objArr2) {
        return internalEquals((Object[]) objArr.clone(), (Object[]) objArr2.clone());
    }

    private static boolean internalEquals(Object[] objArr, Object[] objArr2) {
        for (Object obj : objArr) {
            boolean z = false;
            int i = 0;
            while (!z && i < objArr.length) {
                if (obj.equals(objArr2[i])) {
                    objArr2[i] = null;
                    z = true;
                }
                i++;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public static byte[] pad4(byte[] bArr) {
        int length = bArr.length % 4;
        if (length == 0) {
            return bArr;
        }
        byte[] bArr2 = new byte[(bArr.length + (4 - length))];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    public static char[] pad4(char[] cArr) {
        int length = cArr.length % 4;
        if (length == 0) {
            return cArr;
        }
        char[] cArr2 = new char[(cArr.length + (4 - length))];
        System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
        return cArr2;
    }

    public static char[] pad4(String str) {
        return pad4(str.toCharArray());
    }

    public static String toString(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        printWriter.close();
        try {
            stringWriter.close();
            return stringWriter.toString();
        } catch (IOException e) {
            StringBuffer stringBuffer = new StringBuffer(th.getMessage());
            stringBuffer.append("\n");
            stringBuffer.append("Could not create a stacktrace. Reason: ");
            stringBuffer.append(e.getMessage());
            return stringBuffer.toString();
        }
    }
}
