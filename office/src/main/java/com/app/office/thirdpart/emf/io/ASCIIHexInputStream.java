package com.app.office.thirdpart.emf.io;

import java.io.IOException;
import java.io.InputStream;

public class ASCIIHexInputStream extends DecodingInputStream {
    private boolean endReached;
    private boolean ignoreIllegalChars;
    private InputStream in;
    private int lineNo;
    private int prev;

    public ASCIIHexInputStream(InputStream inputStream) {
        this(inputStream, false);
    }

    public ASCIIHexInputStream(InputStream inputStream, boolean z) {
        this.in = inputStream;
        this.ignoreIllegalChars = z;
        this.endReached = false;
        this.prev = -1;
        this.lineNo = 1;
    }

    public int read() throws IOException {
        int readPart;
        if (this.endReached || (readPart = readPart()) == -1) {
            return -1;
        }
        int readPart2 = readPart();
        if (readPart2 == -1) {
            readPart2 = 0;
        }
        return ((readPart << 4) | readPart2) & 255;
    }

    public int getLineNo() {
        return this.lineNo;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0056, code lost:
        return 13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0057, code lost:
        return 12;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int readPart() throws java.io.IOException, com.app.office.thirdpart.emf.io.EncodingException {
        /*
            r8 = this;
        L_0x0000:
            java.io.InputStream r0 = r8.in
            int r0 = r0.read()
            r1 = -1
            r2 = 1
            if (r0 == r1) goto L_0x008b
            if (r0 == 0) goto L_0x0087
            r3 = 9
            if (r0 == r3) goto L_0x0087
            r4 = 10
            r5 = 13
            if (r0 == r4) goto L_0x007a
            r6 = 12
            if (r0 == r6) goto L_0x0087
            if (r0 == r5) goto L_0x0072
            r7 = 32
            if (r0 == r7) goto L_0x0087
            r7 = 62
            if (r0 == r7) goto L_0x006f
            switch(r0) {
                case 48: goto L_0x006d;
                case 49: goto L_0x006c;
                case 50: goto L_0x006a;
                case 51: goto L_0x0068;
                case 52: goto L_0x0066;
                case 53: goto L_0x0064;
                case 54: goto L_0x0062;
                case 55: goto L_0x0060;
                case 56: goto L_0x005d;
                case 57: goto L_0x005c;
                default: goto L_0x0027;
            }
        L_0x0027:
            switch(r0) {
                case 65: goto L_0x005b;
                case 66: goto L_0x0058;
                case 67: goto L_0x0057;
                case 68: goto L_0x0056;
                case 69: goto L_0x0053;
                case 70: goto L_0x0050;
                default: goto L_0x002a;
            }
        L_0x002a:
            switch(r0) {
                case 97: goto L_0x005b;
                case 98: goto L_0x0058;
                case 99: goto L_0x0057;
                case 100: goto L_0x0056;
                case 101: goto L_0x0053;
                case 102: goto L_0x0050;
                default: goto L_0x002d;
            }
        L_0x002d:
            boolean r1 = r8.ignoreIllegalChars
            if (r1 == 0) goto L_0x0034
            r8.prev = r0
            goto L_0x0000
        L_0x0034:
            com.app.office.thirdpart.emf.io.EncodingException r1 = new com.app.office.thirdpart.emf.io.EncodingException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Illegal char "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = " in HexStream"
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        L_0x0050:
            r0 = 15
            return r0
        L_0x0053:
            r0 = 14
            return r0
        L_0x0056:
            return r5
        L_0x0057:
            return r6
        L_0x0058:
            r0 = 11
            return r0
        L_0x005b:
            return r4
        L_0x005c:
            return r3
        L_0x005d:
            r0 = 8
            return r0
        L_0x0060:
            r0 = 7
            return r0
        L_0x0062:
            r0 = 6
            return r0
        L_0x0064:
            r0 = 5
            return r0
        L_0x0066:
            r0 = 4
            return r0
        L_0x0068:
            r0 = 3
            return r0
        L_0x006a:
            r0 = 2
            return r0
        L_0x006c:
            return r2
        L_0x006d:
            r0 = 0
            return r0
        L_0x006f:
            r8.endReached = r2
            return r1
        L_0x0072:
            int r1 = r8.lineNo
            int r1 = r1 + r2
            r8.lineNo = r1
            r8.prev = r0
            goto L_0x0000
        L_0x007a:
            int r1 = r8.prev
            if (r1 == r5) goto L_0x0083
            int r1 = r8.lineNo
            int r1 = r1 + r2
            r8.lineNo = r1
        L_0x0083:
            r8.prev = r0
            goto L_0x0000
        L_0x0087:
            r8.prev = r0
            goto L_0x0000
        L_0x008b:
            r8.endReached = r2
            boolean r0 = r8.ignoreIllegalChars
            if (r0 == 0) goto L_0x0092
            return r1
        L_0x0092:
            com.app.office.thirdpart.emf.io.EncodingException r0 = new com.app.office.thirdpart.emf.io.EncodingException
            java.lang.String r1 = "missing '>' at end of ASCII HEX stream"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.thirdpart.emf.io.ASCIIHexInputStream.readPart():int");
    }
}
