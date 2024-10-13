package com.app.office.thirdpart.mozilla.intl.chardet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class CharsetDetector {
    public static String charsetStr = null;
    public static boolean found = false;

    private CharsetDetector() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006b, code lost:
        if (r7 == false) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x006d, code lost:
        return "ASCII";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0070, code lost:
        if (found != false) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0072, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0076, code lost:
        return charsetStr;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String detect(java.io.BufferedInputStream r13) throws java.lang.Exception {
        /*
            r0 = 0
            found = r0
            java.lang.String r1 = "ASCII"
            charsetStr = r1
            com.app.office.thirdpart.mozilla.intl.chardet.nsDetector r2 = new com.app.office.thirdpart.mozilla.intl.chardet.nsDetector
            r2.<init>(r0)
            com.app.office.thirdpart.mozilla.intl.chardet.CharsetDetector$1 r3 = new com.app.office.thirdpart.mozilla.intl.chardet.CharsetDetector$1
            r3.<init>()
            r2.Init(r3)
            r3 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r3]
            r5 = 1
            r6 = 0
            r7 = 1
            r8 = 0
        L_0x001c:
            int r9 = r13.read(r4, r0, r3)
            r10 = -1
            if (r9 == r10) goto L_0x0068
            r11 = 50
            if (r6 > r11) goto L_0x0068
            if (r6 != 0) goto L_0x0057
            byte r11 = r4[r0]
            r12 = -2
            if (r11 != r10) goto L_0x0032
            byte r11 = r4[r5]
            if (r11 == r12) goto L_0x003a
        L_0x0032:
            byte r11 = r4[r5]
            if (r11 != r12) goto L_0x003f
            byte r11 = r4[r0]
            if (r11 != r10) goto L_0x003f
        L_0x003a:
            java.lang.String r13 = "Unicode"
            charsetStr = r13
            return r13
        L_0x003f:
            byte r10 = r4[r0]
            r11 = -17
            if (r10 != r11) goto L_0x0057
            byte r10 = r4[r5]
            r11 = -69
            if (r10 != r11) goto L_0x0057
            r10 = 2
            byte r10 = r4[r10]
            r11 = -65
            if (r10 != r11) goto L_0x0057
            java.lang.String r13 = "UTF-8"
            charsetStr = r13
            return r13
        L_0x0057:
            if (r7 == 0) goto L_0x005d
            boolean r7 = r2.isAscii(r4, r9)
        L_0x005d:
            if (r7 != 0) goto L_0x0065
            if (r8 != 0) goto L_0x0065
            boolean r8 = r2.DoIt(r4, r9, r0)
        L_0x0065:
            int r6 = r6 + 1
            goto L_0x001c
        L_0x0068:
            r2.DataEnd()
            if (r7 == 0) goto L_0x006e
            return r1
        L_0x006e:
            boolean r13 = found
            if (r13 != 0) goto L_0x0074
            r13 = 0
            return r13
        L_0x0074:
            java.lang.String r13 = charsetStr
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.thirdpart.mozilla.intl.chardet.CharsetDetector.detect(java.io.BufferedInputStream):java.lang.String");
    }

    public static String detect(String str) throws Exception {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
        String detect = detect(bufferedInputStream);
        bufferedInputStream.close();
        return detect;
    }
}
