package com.app.office.fc.ss.format;

import com.app.office.fc.ss.format.CellFormatPart;
import java.text.AttributedCharacterIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

public class CellDateFormatter extends CellFormatter {
    private static final Date EXCEL_EPOCH_DATE;
    private static final long EXCEL_EPOCH_TIME;
    private static final CellFormatter SIMPLE_DATE = new CellDateFormatter("mm/d/y");
    /* access modifiers changed from: private */
    public boolean amPmUpper;
    private final DateFormat dateFmt;
    /* access modifiers changed from: private */
    public String sFmt;
    /* access modifiers changed from: private */
    public boolean showAmPm;
    /* access modifiers changed from: private */
    public boolean showM;

    static {
        Calendar instance = Calendar.getInstance();
        instance.set(1904, 0, 1, 0, 0, 0);
        EXCEL_EPOCH_DATE = instance.getTime();
        EXCEL_EPOCH_TIME = instance.getTimeInMillis();
    }

    private class DatePartHandler implements CellFormatPart.PartHandler {
        private int hLen;
        private int hStart;
        private int mLen;
        private int mStart;

        private DatePartHandler() {
            this.mStart = -1;
            this.hStart = -1;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: boolean} */
        /* JADX WARNING: type inference failed for: r6v1 */
        /* JADX WARNING: type inference failed for: r6v5 */
        /* JADX WARNING: type inference failed for: r6v8, types: [int] */
        /* JADX WARNING: type inference failed for: r6v10 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.String handlePart(java.util.regex.Matcher r4, java.lang.String r5, com.app.office.fc.ss.format.CellFormatType r6, java.lang.StringBuffer r7) {
            /*
                r3 = this;
                int r4 = r7.length()
                r6 = 0
                char r0 = r5.charAt(r6)
                r1 = 109(0x6d, float:1.53E-43)
                r2 = -1
                switch(r0) {
                    case 48: goto L_0x00a9;
                    case 65: goto L_0x0070;
                    case 68: goto L_0x0055;
                    case 72: goto L_0x0046;
                    case 77: goto L_0x0039;
                    case 80: goto L_0x0070;
                    case 83: goto L_0x0021;
                    case 89: goto L_0x0011;
                    case 97: goto L_0x0070;
                    case 100: goto L_0x0055;
                    case 104: goto L_0x0046;
                    case 109: goto L_0x0039;
                    case 112: goto L_0x0070;
                    case 115: goto L_0x0021;
                    case 121: goto L_0x0011;
                    default: goto L_0x000f;
                }
            L_0x000f:
                goto L_0x00dd
            L_0x0011:
                r3.mStart = r2
                int r4 = r5.length()
                r6 = 3
                if (r4 != r6) goto L_0x001c
                java.lang.String r5 = "yyyy"
            L_0x001c:
                java.lang.String r4 = r5.toLowerCase()
                return r4
            L_0x0021:
                int r4 = r3.mStart
                if (r4 < 0) goto L_0x0034
            L_0x0025:
                int r4 = r3.mLen
                if (r6 >= r4) goto L_0x0032
                int r4 = r3.mStart
                int r4 = r4 + r6
                r7.setCharAt(r4, r1)
                int r6 = r6 + 1
                goto L_0x0025
            L_0x0032:
                r3.mStart = r2
            L_0x0034:
                java.lang.String r4 = r5.toLowerCase()
                return r4
            L_0x0039:
                r3.mStart = r4
                int r4 = r5.length()
                r3.mLen = r4
                java.lang.String r4 = r5.toUpperCase()
                return r4
            L_0x0046:
                r3.mStart = r2
                r3.hStart = r4
                int r4 = r5.length()
                r3.hLen = r4
                java.lang.String r4 = r5.toLowerCase()
                return r4
            L_0x0055:
                r3.mStart = r2
                int r4 = r5.length()
                r6 = 2
                if (r4 > r6) goto L_0x0063
                java.lang.String r4 = r5.toLowerCase()
                return r4
            L_0x0063:
                java.lang.String r4 = r5.toLowerCase()
                r5 = 100
                r6 = 69
                java.lang.String r4 = r4.replace(r5, r6)
                return r4
            L_0x0070:
                int r4 = r5.length()
                r7 = 1
                if (r4 <= r7) goto L_0x00dd
                r3.mStart = r2
                com.app.office.fc.ss.format.CellDateFormatter r4 = com.app.office.fc.ss.format.CellDateFormatter.this
                boolean unused = r4.showAmPm = r7
                com.app.office.fc.ss.format.CellDateFormatter r4 = com.app.office.fc.ss.format.CellDateFormatter.this
                char r0 = r5.charAt(r7)
                char r0 = java.lang.Character.toLowerCase(r0)
                if (r0 != r1) goto L_0x008c
                r0 = 1
                goto L_0x008d
            L_0x008c:
                r0 = 0
            L_0x008d:
                boolean unused = r4.showM = r0
                com.app.office.fc.ss.format.CellDateFormatter r4 = com.app.office.fc.ss.format.CellDateFormatter.this
                boolean r0 = r4.showM
                if (r0 != 0) goto L_0x00a2
                char r5 = r5.charAt(r6)
                boolean r5 = java.lang.Character.isUpperCase(r5)
                if (r5 == 0) goto L_0x00a3
            L_0x00a2:
                r6 = 1
            L_0x00a3:
                boolean unused = r4.amPmUpper = r6
                java.lang.String r4 = "a"
                return r4
            L_0x00a9:
                r3.mStart = r2
                int r4 = r5.length()
                com.app.office.fc.ss.format.CellDateFormatter r6 = com.app.office.fc.ss.format.CellDateFormatter.this
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                java.lang.String r0 = "%0"
                r7.append(r0)
                int r0 = r4 + 2
                r7.append(r0)
                java.lang.String r0 = "."
                r7.append(r0)
                r7.append(r4)
                java.lang.String r4 = "f"
                r7.append(r4)
                java.lang.String r4 = r7.toString()
                java.lang.String unused = r6.sFmt = r4
                r4 = 48
                r6 = 83
                java.lang.String r4 = r5.replace(r4, r6)
                return r4
            L_0x00dd:
                r4 = 0
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ss.format.CellDateFormatter.DatePartHandler.handlePart(java.util.regex.Matcher, java.lang.String, com.app.office.fc.ss.format.CellFormatType, java.lang.StringBuffer):java.lang.String");
        }

        public void finish(StringBuffer stringBuffer) {
            if (this.hStart >= 0 && !CellDateFormatter.this.showAmPm) {
                for (int i = 0; i < this.hLen; i++) {
                    stringBuffer.setCharAt(this.hStart + i, 'H');
                }
            }
        }
    }

    public CellDateFormatter(String str) {
        super(str);
        DatePartHandler datePartHandler = new DatePartHandler();
        StringBuffer parseFormat = CellFormatPart.parseFormat(str, CellFormatType.DATE, datePartHandler);
        datePartHandler.finish(parseFormat);
        this.dateFmt = new SimpleDateFormat(parseFormat.toString());
    }

    public void formatValue(StringBuffer stringBuffer, Object obj) {
        if (obj == null) {
            obj = Double.valueOf(0.0d);
        }
        if (obj instanceof Number) {
            double doubleValue = ((Number) obj).doubleValue();
            if (doubleValue == 0.0d) {
                obj = EXCEL_EPOCH_DATE;
            } else {
                obj = new Date((long) (((double) EXCEL_EPOCH_TIME) + doubleValue));
            }
        }
        AttributedCharacterIterator formatToCharacterIterator = this.dateFmt.formatToCharacterIterator(obj);
        formatToCharacterIterator.first();
        boolean z = false;
        boolean z2 = false;
        for (char first = formatToCharacterIterator.first(); first != 65535; first = formatToCharacterIterator.next()) {
            if (formatToCharacterIterator.getAttribute(DateFormat.Field.MILLISECOND) != null) {
                if (!z) {
                    int length = stringBuffer.length();
                    new Formatter(stringBuffer).format(LOCALE, this.sFmt, new Object[]{Double.valueOf(((double) (((Date) obj).getTime() % 1000)) / 1000.0d)});
                    stringBuffer.delete(length, length + 2);
                    z = true;
                }
            } else if (formatToCharacterIterator.getAttribute(DateFormat.Field.AM_PM) == null) {
                stringBuffer.append(first);
            } else if (!z2) {
                if (this.showAmPm) {
                    if (this.amPmUpper) {
                        stringBuffer.append(Character.toUpperCase(first));
                        if (this.showM) {
                            stringBuffer.append('M');
                        }
                    } else {
                        stringBuffer.append(Character.toLowerCase(first));
                        if (this.showM) {
                            stringBuffer.append('m');
                        }
                    }
                }
                z2 = true;
            }
        }
    }

    public void simpleValue(StringBuffer stringBuffer, Object obj) {
        SIMPLE_DATE.formatValue(stringBuffer, obj);
    }
}
