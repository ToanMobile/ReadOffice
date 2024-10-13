package com.app.office.fc.ss.format;

public class CellGeneralFormatter extends CellFormatter {
    public CellGeneralFormatter() {
        super("General");
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void formatValue(java.lang.StringBuffer r11, java.lang.Object r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof java.lang.Number
            if (r0 == 0) goto L_0x0079
            r0 = r12
            java.lang.Number r0 = (java.lang.Number) r0
            double r0 = r0.doubleValue()
            r2 = 0
            r4 = 48
            int r5 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r5 != 0) goto L_0x0017
            r11.append(r4)
            return
        L_0x0017:
            double r2 = java.lang.Math.abs(r0)
            double r2 = java.lang.Math.log10(r2)
            r5 = 4621819117588971520(0x4024000000000000, double:10.0)
            r7 = 0
            r8 = 1
            int r9 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r9 > 0) goto L_0x003b
            r5 = -4602115869219225600(0xc022000000000000, double:-9.0)
            int r9 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r9 >= 0) goto L_0x002e
            goto L_0x003b
        L_0x002e:
            long r2 = (long) r0
            double r2 = (double) r2
            int r5 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r5 == 0) goto L_0x0037
            java.lang.String r0 = "%1.9f"
            goto L_0x003d
        L_0x0037:
            java.lang.String r0 = "%1.0f"
            r1 = 0
            goto L_0x003e
        L_0x003b:
            java.lang.String r0 = "%1.5E"
        L_0x003d:
            r1 = 1
        L_0x003e:
            java.util.Formatter r2 = new java.util.Formatter
            r2.<init>(r11)
            java.util.Locale r3 = LOCALE
            java.lang.Object[] r5 = new java.lang.Object[r8]
            r5[r7] = r12
            r2.format(r3, r0, r5)
            if (r1 == 0) goto L_0x0080
            java.lang.String r12 = "E"
            boolean r0 = r0.endsWith(r12)
            if (r0 == 0) goto L_0x005b
            int r12 = r11.lastIndexOf(r12)
            goto L_0x005f
        L_0x005b:
            int r12 = r11.length()
        L_0x005f:
            int r12 = r12 - r8
        L_0x0060:
            char r0 = r11.charAt(r12)
            if (r0 != r4) goto L_0x006d
            int r0 = r12 + -1
            r11.deleteCharAt(r12)
            r12 = r0
            goto L_0x0060
        L_0x006d:
            char r0 = r11.charAt(r12)
            r1 = 46
            if (r0 != r1) goto L_0x0080
            r11.deleteCharAt(r12)
            goto L_0x0080
        L_0x0079:
            java.lang.String r12 = r12.toString()
            r11.append(r12)
        L_0x0080:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ss.format.CellGeneralFormatter.formatValue(java.lang.StringBuffer, java.lang.Object):void");
    }

    public void simpleValue(StringBuffer stringBuffer, Object obj) {
        formatValue(stringBuffer, obj);
    }
}
