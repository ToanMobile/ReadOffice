package com.app.office.ss.util.format;

import com.itextpdf.text.pdf.Barcode128;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeFormat {
    public static final int DATE_FIELD = 3;
    public static final int DAY_OF_WEEK_FIELD = 14;
    public static final int DAY_OF_WEEK_IN_MONTH_FIELD = 11;
    public static final int DAY_OF_YEAR_FIELD = 10;
    public static final int ERA_FIELD = 0;
    public static final int FULL = 0;
    public static final int HOUR0_FIELD = 16;
    public static final int HOUR1_FIELD = 15;
    public static final int HOUR_OF_DAY0_FIELD = 5;
    public static final int HOUR_OF_DAY1_FIELD = 4;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int MILLISECOND_FIELD = 8;
    public static final int MINUTE_FIELD = 6;
    public static final int MONTH_FIELD = 2;
    public static final int SECOND_FIELD = 7;
    public static final int SHORT = 3;
    public static final int TIMEZONE_FIELD = 17;
    public static final int WEEK_OF_MONTH_FIELD = 13;
    public static final int WEEK_OF_YEAR_FIELD = 12;
    public static final int YEAR_FIELD = 1;
    private static final String datePatternChars = "GyMdEDFwWazZ";
    static final String patternChars = "GyMdkHmsSEDFwWahKzZ";
    private static final String timePatternChars = "HhsSkK";
    private boolean ampm;
    protected Calendar calendar;
    private DateTimeFormatSymbols dateTimeFormatData;
    protected NumberFormat numberFormat;
    private String pattern;

    public DateTimeFormat(String str) {
        this(str, Locale.getDefault());
    }

    private DateTimeFormat(Locale locale) {
        this.ampm = false;
        NumberFormat instance = NumberFormat.getInstance(locale);
        this.numberFormat = instance;
        instance.setParseIntegerOnly(true);
        this.numberFormat.setGroupingUsed(false);
        GregorianCalendar gregorianCalendar = new GregorianCalendar(locale);
        this.calendar = gregorianCalendar;
        gregorianCalendar.add(1, -80);
    }

    private String adjust(String str) {
        if (str.contains("AM/PM") || str.contains("上午/下午")) {
            str = str.replace("AM/PM", "").replace("上午/下午", "");
            this.ampm = true;
        }
        boolean isDate = isDate(str);
        if (isTime(str) && isDate) {
            int indexOf = str.indexOf("mmm");
            while (indexOf > -1) {
                char[] charArray = str.toCharArray();
                int i = indexOf + 3;
                while (str.charAt(i) == 'm') {
                    i++;
                }
                while (indexOf < i) {
                    charArray[indexOf] = 'M';
                    indexOf++;
                }
                str = String.valueOf(charArray);
                indexOf = str.indexOf("mmm");
            }
            str.toCharArray();
            new ArrayList();
            str.indexOf(109);
            return str;
        } else if (isDate) {
            return str.replace('m', 'M');
        } else {
            return !this.ampm ? str.replace(Barcode128.START_B, 'k') : str;
        }
    }

    public DateTimeFormat(String str, Locale locale) {
        this(locale);
        String adjust = adjust(str);
        validatePattern(adjust);
        this.pattern = adjust;
        this.dateTimeFormatData = new DateTimeFormatSymbols(locale);
    }

    private void validateFormat(char c) {
        if (patternChars.indexOf(c) == -1) {
            throw new IllegalArgumentException("invalidate char");
        }
    }

    private void validatePattern(String str) {
        int length = str.length();
        int i = 0;
        char c = 65535;
        boolean z = false;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt == '\'') {
                if (i > 0) {
                    validateFormat((char) c);
                    i = 0;
                }
                c = c == charAt ? 65535 : charAt;
                z = !z;
            } else if (z || (c != charAt && ((charAt < 'a' || charAt > 'z') && (charAt < 'A' || charAt > 'Z')))) {
                if (i > 0) {
                    validateFormat((char) c);
                    i = 0;
                }
                c = 65535;
            } else if (c == charAt) {
                i++;
            } else {
                if (i > 0) {
                    validateFormat((char) c);
                }
                i = 1;
                c = charAt;
            }
        }
        if (i > 0) {
            validateFormat((char) c);
        }
        if (z) {
            throw new IllegalArgumentException("invalidate pattern");
        }
    }

    public String format(Date date) {
        return formatImpl(date, new StringBuffer()).toString();
    }

    private StringBuffer formatImpl(Date date, StringBuffer stringBuffer) {
        this.calendar.setTime(date);
        int length = this.pattern.length();
        int i = 0;
        char c = 65535;
        boolean z = false;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = this.pattern.charAt(i2);
            if (charAt == '\'') {
                if (i > 0) {
                    append(stringBuffer, (char) c, i);
                    i = 0;
                }
                if (c == charAt) {
                    stringBuffer.append('\'');
                    c = 65535;
                } else {
                    c = charAt;
                }
                z = !z;
            } else if (z || (c != charAt && ((charAt < 'a' || charAt > 'z') && (charAt < 'A' || charAt > 'Z')))) {
                if (i > 0) {
                    append(stringBuffer, (char) c, i);
                    i = 0;
                }
                stringBuffer.append((char) charAt);
                c = 65535;
            } else if (c == charAt) {
                i++;
            } else {
                if (i > 0) {
                    append(stringBuffer, (char) c, i);
                }
                i = 1;
                c = charAt;
            }
        }
        if (i > 0) {
            append(stringBuffer, (char) c, i);
        }
        if (this.ampm) {
            stringBuffer.append(this.dateTimeFormatData.formatData.getAmPmStrings()[this.calendar.get(9)]);
        }
        return stringBuffer;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void append(java.lang.StringBuffer r12, char r13, int r14) {
        /*
            r11 = this;
            java.lang.String r0 = "GyMdkHmsSEDFwWahKzZ"
            int r13 = r0.indexOf(r13)
            r0 = -1
            if (r13 == r0) goto L_0x017b
            r1 = 4
            r2 = 7
            r3 = 0
            r4 = 12
            r5 = 5
            r6 = 10
            r7 = 11
            r8 = 1
            r9 = 3
            r10 = 2
            switch(r13) {
                case 0: goto L_0x015b;
                case 1: goto L_0x0149;
                case 2: goto L_0x011e;
                case 3: goto L_0x00f7;
                case 4: goto L_0x00e8;
                case 5: goto L_0x00ca;
                case 6: goto L_0x008a;
                case 7: goto L_0x0086;
                case 8: goto L_0x0079;
                case 9: goto L_0x0019;
                case 10: goto L_0x0076;
                case 11: goto L_0x0072;
                case 12: goto L_0x006f;
                case 13: goto L_0x016f;
                case 14: goto L_0x0047;
                case 15: goto L_0x0029;
                case 16: goto L_0x0025;
                case 17: goto L_0x0020;
                case 18: goto L_0x001b;
                default: goto L_0x0019;
            }
        L_0x0019:
            goto L_0x016e
        L_0x001b:
            r11.appendNumericTimeZone(r12, r3)
            goto L_0x016e
        L_0x0020:
            r11.appendTimeZone(r12, r14, r8)
            goto L_0x016e
        L_0x0025:
            r1 = 10
            goto L_0x016f
        L_0x0029:
            boolean r13 = r11.ampm
            if (r13 == 0) goto L_0x003c
            java.util.Calendar r13 = r11.calendar
            int r13 = r13.get(r6)
            if (r13 != 0) goto L_0x0036
            goto L_0x0037
        L_0x0036:
            r4 = r13
        L_0x0037:
            r11.appendNumber(r12, r14, r4)
            goto L_0x016e
        L_0x003c:
            java.util.Calendar r13 = r11.calendar
            int r13 = r13.get(r7)
            r11.appendNumber(r12, r14, r13)
            goto L_0x016e
        L_0x0047:
            java.util.Calendar r13 = r11.calendar
            int r13 = r13.get(r2)
            if (r14 != r9) goto L_0x005e
            com.app.office.ss.util.format.DateTimeFormatSymbols r1 = r11.dateTimeFormatData
            java.text.DateFormatSymbols r1 = r1.formatData
            java.lang.String[] r1 = r1.getShortWeekdays()
            r13 = r1[r13]
            r12.append(r13)
            goto L_0x016e
        L_0x005e:
            if (r14 <= r9) goto L_0x016e
            com.app.office.ss.util.format.DateTimeFormatSymbols r1 = r11.dateTimeFormatData
            java.text.DateFormatSymbols r1 = r1.formatData
            java.lang.String[] r1 = r1.getWeekdays()
            r13 = r1[r13]
            r12.append(r13)
            goto L_0x016e
        L_0x006f:
            r1 = 3
            goto L_0x016f
        L_0x0072:
            r1 = 8
            goto L_0x016f
        L_0x0076:
            r1 = 6
            goto L_0x016f
        L_0x0079:
            java.util.Calendar r13 = r11.calendar
            r1 = 14
            int r13 = r13.get(r1)
            r11.appendNumber(r12, r14, r13)
            goto L_0x016e
        L_0x0086:
            r1 = 13
            goto L_0x016f
        L_0x008a:
            if (r14 == r9) goto L_0x00b9
            if (r14 <= r5) goto L_0x008f
            goto L_0x00b9
        L_0x008f:
            if (r14 != r1) goto L_0x00a2
            com.app.office.ss.util.format.DateTimeFormatSymbols r13 = r11.dateTimeFormatData
            java.lang.String[] r13 = r13.stdMonths
            java.util.Calendar r1 = r11.calendar
            int r1 = r1.get(r10)
            r13 = r13[r1]
            r12.append(r13)
            goto L_0x016e
        L_0x00a2:
            if (r14 != r5) goto L_0x00b5
            com.app.office.ss.util.format.DateTimeFormatSymbols r13 = r11.dateTimeFormatData
            java.lang.String[] r13 = r13.stdShortestMonths
            java.util.Calendar r1 = r11.calendar
            int r1 = r1.get(r10)
            r13 = r13[r1]
            r12.append(r13)
            goto L_0x016e
        L_0x00b5:
            r1 = 12
            goto L_0x016f
        L_0x00b9:
            com.app.office.ss.util.format.DateTimeFormatSymbols r13 = r11.dateTimeFormatData
            java.lang.String[] r13 = r13.stdShortMonths
            java.util.Calendar r1 = r11.calendar
            int r1 = r1.get(r10)
            r13 = r13[r1]
            r12.append(r13)
            goto L_0x016e
        L_0x00ca:
            boolean r13 = r11.ampm
            if (r13 == 0) goto L_0x00dd
            java.util.Calendar r13 = r11.calendar
            int r13 = r13.get(r6)
            if (r13 != 0) goto L_0x00d7
            goto L_0x00d8
        L_0x00d7:
            r4 = r13
        L_0x00d8:
            r11.appendNumber(r12, r14, r4)
            goto L_0x016e
        L_0x00dd:
            java.util.Calendar r13 = r11.calendar
            int r13 = r13.get(r7)
            r11.appendNumber(r12, r14, r13)
            goto L_0x016e
        L_0x00e8:
            java.util.Calendar r13 = r11.calendar
            int r13 = r13.get(r7)
            if (r13 != 0) goto L_0x00f2
            r13 = 24
        L_0x00f2:
            r11.appendNumber(r12, r14, r13)
            goto L_0x016e
        L_0x00f7:
            java.util.Calendar r13 = r11.calendar
            int r13 = r13.get(r2)
            com.app.office.ss.util.format.DateTimeFormatSymbols r1 = r11.dateTimeFormatData
            java.lang.String[] r1 = r1.stdShortWeekdays
            int r1 = r1.length
            if (r13 >= r1) goto L_0x016e
            if (r14 != r9) goto L_0x0110
            com.app.office.ss.util.format.DateTimeFormatSymbols r1 = r11.dateTimeFormatData
            java.lang.String[] r1 = r1.stdShortWeekdays
            r13 = r1[r13]
            r12.append(r13)
            goto L_0x016e
        L_0x0110:
            if (r14 <= r9) goto L_0x011c
            com.app.office.ss.util.format.DateTimeFormatSymbols r1 = r11.dateTimeFormatData
            java.lang.String[] r1 = r1.stdWeekdays
            r13 = r1[r13]
            r12.append(r13)
            goto L_0x016e
        L_0x011c:
            r1 = 5
            goto L_0x016f
        L_0x011e:
            java.util.Calendar r13 = r11.calendar
            int r13 = r13.get(r10)
            if (r14 > r10) goto L_0x012b
            int r13 = r13 + r8
            r11.appendNumber(r12, r14, r13)
            goto L_0x016e
        L_0x012b:
            if (r14 != r9) goto L_0x013b
            com.app.office.ss.util.format.DateTimeFormatSymbols r1 = r11.dateTimeFormatData
            java.text.DateFormatSymbols r1 = r1.formatData
            java.lang.String[] r1 = r1.getShortMonths()
            r13 = r1[r13]
            r12.append(r13)
            goto L_0x016e
        L_0x013b:
            com.app.office.ss.util.format.DateTimeFormatSymbols r1 = r11.dateTimeFormatData
            java.text.DateFormatSymbols r1 = r1.formatData
            java.lang.String[] r1 = r1.getMonths()
            r13 = r1[r13]
            r12.append(r13)
            goto L_0x016e
        L_0x0149:
            java.util.Calendar r13 = r11.calendar
            int r13 = r13.get(r8)
            if (r14 != r10) goto L_0x0157
            int r13 = r13 % 100
            r11.appendNumber(r12, r10, r13)
            goto L_0x016e
        L_0x0157:
            r11.appendNumber(r12, r14, r13)
            goto L_0x016e
        L_0x015b:
            com.app.office.ss.util.format.DateTimeFormatSymbols r13 = r11.dateTimeFormatData
            java.text.DateFormatSymbols r13 = r13.formatData
            java.lang.String[] r13 = r13.getEras()
            java.util.Calendar r1 = r11.calendar
            int r1 = r1.get(r3)
            r13 = r13[r1]
            r12.append(r13)
        L_0x016e:
            r1 = -1
        L_0x016f:
            if (r1 == r0) goto L_0x017a
            java.util.Calendar r13 = r11.calendar
            int r13 = r13.get(r1)
            r11.appendNumber(r12, r14, r13)
        L_0x017a:
            return
        L_0x017b:
            java.lang.IllegalArgumentException r12 = new java.lang.IllegalArgumentException
            java.lang.String r13 = "invalidate char"
            r12.<init>(r13)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.util.format.DateTimeFormat.append(java.lang.StringBuffer, char, int):void");
    }

    private void appendTimeZone(StringBuffer stringBuffer, int i, boolean z) {
        if (z) {
            TimeZone timeZone = this.calendar.getTimeZone();
            int i2 = 1;
            boolean z2 = this.calendar.get(16) != 0;
            if (i < 4) {
                i2 = 0;
            }
            stringBuffer.append(timeZone.getDisplayName(z2, i2, Locale.getDefault()));
            return;
        }
        appendNumericTimeZone(stringBuffer, z);
    }

    private void appendNumericTimeZone(StringBuffer stringBuffer, boolean z) {
        char c;
        int i = this.calendar.get(15) + this.calendar.get(16);
        if (i < 0) {
            c = '-';
            i = -i;
        } else {
            c = '+';
        }
        if (z) {
            stringBuffer.append("GMT");
        }
        stringBuffer.append(c);
        appendNumber(stringBuffer, 2, i / 3600000);
        if (z) {
            stringBuffer.append(':');
        }
        appendNumber(stringBuffer, 2, (i % 3600000) / 60000);
    }

    private void appendNumber(StringBuffer stringBuffer, int i, int i2) {
        int minimumIntegerDigits = this.numberFormat.getMinimumIntegerDigits();
        this.numberFormat.setMinimumIntegerDigits(i);
        this.numberFormat.format(Integer.valueOf(i2), stringBuffer, new FieldPosition(0));
        this.numberFormat.setMinimumIntegerDigits(minimumIntegerDigits);
    }

    public static boolean isDateTimeFormat(String str) {
        String replace = str.replace("E+", "");
        for (int i = 0; i < 19; i++) {
            if (replace.indexOf(patternChars.charAt(i)) > -1) {
                return true;
            }
        }
        return false;
    }

    private boolean isDate(String str) {
        str.replace("AM", "");
        String replace = str.replace("PM", "");
        for (int i = 0; i < 12; i++) {
            if (replace.indexOf(datePatternChars.charAt(i)) > -1) {
                return true;
            }
        }
        return false;
    }

    private boolean isTime(String str) {
        for (int i = 0; i < 6; i++) {
            if (str.indexOf(timePatternChars.charAt(i)) > -1) {
                return true;
            }
        }
        return false;
    }

    public void dispose() {
        this.calendar = null;
        this.numberFormat = null;
        this.dateTimeFormatData.dispose();
        this.dateTimeFormatData = null;
    }
}
