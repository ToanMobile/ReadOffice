package com.app.office.fc.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HexRead {
    public static byte[] readData(String str) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(str));
        try {
            return readData((InputStream) fileInputStream, -1);
        } finally {
            fileInputStream.close();
        }
    }

    public static byte[] readData(InputStream inputStream, String str) throws IOException {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            int read = inputStream.read();
            boolean z = false;
            while (read != -1) {
                if (read == 10 || read == 13) {
                    stringBuffer = new StringBuffer();
                } else {
                    if (read == 91) {
                        z = true;
                    } else if (read != 93) {
                        if (z) {
                            stringBuffer.append((char) read);
                        }
                    } else if (stringBuffer.toString().equals(str)) {
                        return readData(inputStream, 91);
                    } else {
                        stringBuffer = new StringBuffer();
                    }
                    read = inputStream.read();
                }
                z = false;
                read = inputStream.read();
            }
            inputStream.close();
            throw new IOException("Section '" + str + "' not found");
        } finally {
            inputStream.close();
        }
    }

    public static byte[] readData(String str, String str2) throws IOException {
        return readData((InputStream) new FileInputStream(new File(str)), str2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0009, code lost:
        continue;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] readData(java.io.InputStream r8, int r9) throws java.io.IOException {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            r2 = 0
        L_0x0007:
            r3 = 0
            r4 = 0
        L_0x0009:
            if (r2 != 0) goto L_0x0057
            int r5 = r8.read()
            r6 = 97
            if (r5 != r9) goto L_0x0014
            goto L_0x0057
        L_0x0014:
            r7 = -1
            if (r5 == r7) goto L_0x0055
            r7 = 35
            if (r5 == r7) goto L_0x0051
            r7 = 2
            switch(r5) {
                case 48: goto L_0x003d;
                case 49: goto L_0x003d;
                case 50: goto L_0x003d;
                case 51: goto L_0x003d;
                case 52: goto L_0x003d;
                case 53: goto L_0x003d;
                case 54: goto L_0x003d;
                case 55: goto L_0x003d;
                case 56: goto L_0x003d;
                case 57: goto L_0x003d;
                default: goto L_0x001f;
            }
        L_0x001f:
            switch(r5) {
                case 65: goto L_0x0026;
                case 66: goto L_0x0026;
                case 67: goto L_0x0026;
                case 68: goto L_0x0026;
                case 69: goto L_0x0026;
                case 70: goto L_0x0026;
                default: goto L_0x0022;
            }
        L_0x0022:
            switch(r5) {
                case 97: goto L_0x0028;
                case 98: goto L_0x0028;
                case 99: goto L_0x0028;
                case 100: goto L_0x0028;
                case 101: goto L_0x0028;
                case 102: goto L_0x0028;
                default: goto L_0x0025;
            }
        L_0x0025:
            goto L_0x0009
        L_0x0026:
            r6 = 65
        L_0x0028:
            int r3 = r3 << 4
            byte r3 = (byte) r3
            int r5 = r5 + 10
            int r5 = r5 - r6
            byte r5 = (byte) r5
            int r3 = r3 + r5
            byte r3 = (byte) r3
            int r4 = r4 + 1
            if (r4 != r7) goto L_0x0009
            java.lang.Byte r3 = java.lang.Byte.valueOf(r3)
            r0.add(r3)
            goto L_0x0007
        L_0x003d:
            int r3 = r3 << 4
            byte r3 = (byte) r3
            int r5 = r5 + -48
            byte r5 = (byte) r5
            int r3 = r3 + r5
            byte r3 = (byte) r3
            int r4 = r4 + 1
            if (r4 != r7) goto L_0x0009
            java.lang.Byte r3 = java.lang.Byte.valueOf(r3)
            r0.add(r3)
            goto L_0x0007
        L_0x0051:
            readToEOL(r8)
            goto L_0x0009
        L_0x0055:
            r2 = 1
            goto L_0x0009
        L_0x0057:
            java.lang.Byte[] r8 = new java.lang.Byte[r1]
            java.lang.Object[] r8 = r0.toArray(r8)
            java.lang.Byte[] r8 = (java.lang.Byte[]) r8
            int r9 = r8.length
            byte[] r9 = new byte[r9]
        L_0x0062:
            int r0 = r8.length
            if (r1 >= r0) goto L_0x0070
            r0 = r8[r1]
            byte r0 = r0.byteValue()
            r9[r1] = r0
            int r1 = r1 + 1
            goto L_0x0062
        L_0x0070:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.util.HexRead.readData(java.io.InputStream, int):byte[]");
    }

    public static byte[] readFromString(String str) {
        try {
            return readData((InputStream) new ByteArrayInputStream(str.getBytes()), -1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void readToEOL(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        while (read != -1 && read != 10 && read != 13) {
            read = inputStream.read();
        }
    }
}
