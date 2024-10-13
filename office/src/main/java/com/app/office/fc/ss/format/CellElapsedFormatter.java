package com.app.office.fc.ss.format;

import com.app.office.fc.ss.format.CellFormatPart;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CellElapsedFormatter extends CellFormatter {
    private static final double HOUR__FACTOR = 0.041666666666666664d;
    private static final double MIN__FACTOR = 6.944444444444444E-4d;
    /* access modifiers changed from: private */
    public static final Pattern PERCENTS = Pattern.compile("%");
    private static final double SEC__FACTOR = 1.1574074074074073E-5d;
    private final String printfFmt;
    private final List<TimeSpec> specs;
    /* access modifiers changed from: private */
    public TimeSpec topmost;

    private static class TimeSpec {
        final double factor;
        final int len;
        double modBy = 0.0d;
        final int pos;
        final char type;

        public TimeSpec(char c, int i, int i2, double d) {
            this.type = c;
            this.pos = i;
            this.len = i2;
            this.factor = d;
        }

        public long valueFor(double d) {
            double d2;
            double d3 = this.modBy;
            if (d3 == 0.0d) {
                d2 = d / this.factor;
            } else {
                d2 = (d / this.factor) % d3;
            }
            return this.type == '0' ? Math.round(d2) : (long) d2;
        }
    }

    private class ElapsedPartHandler implements CellFormatPart.PartHandler {
        private ElapsedPartHandler() {
        }

        public String handlePart(Matcher matcher, String str, CellFormatType cellFormatType, StringBuffer stringBuffer) {
            int length = stringBuffer.length();
            char charAt = str.charAt(0);
            if (charAt == 10) {
                return "%n";
            }
            if (charAt == '\"') {
                str = str.substring(1, str.length() - 1);
            } else if (charAt != '*') {
                if (charAt != '0') {
                    if (charAt == '_') {
                        return null;
                    }
                    if (!(charAt == 'h' || charAt == 'm' || charAt == 's')) {
                        if (charAt != '[') {
                            if (charAt == '\\') {
                                str = str.substring(1);
                            }
                        } else if (str.length() >= 3) {
                            if (CellElapsedFormatter.this.topmost == null) {
                                String lowerCase = str.toLowerCase();
                                int length2 = lowerCase.length() - 2;
                                CellElapsedFormatter cellElapsedFormatter = CellElapsedFormatter.this;
                                TimeSpec unused = cellElapsedFormatter.topmost = cellElapsedFormatter.assignSpec(lowerCase.charAt(1), length, length2);
                                return lowerCase.substring(1, length2 + 1);
                            }
                            throw new IllegalArgumentException("Duplicate '[' times in format");
                        }
                    }
                }
                String lowerCase2 = str.toLowerCase();
                TimeSpec unused2 = CellElapsedFormatter.this.assignSpec(lowerCase2.charAt(0), length, lowerCase2.length());
                return lowerCase2;
            } else if (str.length() > 1) {
                str = CellFormatPart.expandChar(str);
            }
            return CellElapsedFormatter.PERCENTS.matcher(str).replaceAll("%%");
        }
    }

    public CellElapsedFormatter(String str) {
        super(str);
        ArrayList arrayList = new ArrayList();
        this.specs = arrayList;
        StringBuffer parseFormat = CellFormatPart.parseFormat(str, CellFormatType.ELAPSED, new ElapsedPartHandler());
        ListIterator listIterator = arrayList.listIterator(arrayList.size());
        while (listIterator.hasPrevious()) {
            TimeSpec timeSpec = (TimeSpec) listIterator.previous();
            int i = timeSpec.pos;
            int i2 = timeSpec.pos + timeSpec.len;
            parseFormat.replace(i, i2, "%0" + timeSpec.len + "d");
            if (timeSpec.type != this.topmost.type) {
                timeSpec.modBy = modFor(timeSpec.type, timeSpec.len);
            }
        }
        this.printfFmt = parseFormat.toString();
    }

    /* access modifiers changed from: private */
    public TimeSpec assignSpec(char c, int i, int i2) {
        TimeSpec timeSpec = new TimeSpec(c, i, i2, factorFor(c, i2));
        this.specs.add(timeSpec);
        return timeSpec;
    }

    private static double factorFor(char c, int i) {
        if (c == '0') {
            return SEC__FACTOR / Math.pow(10.0d, (double) i);
        }
        if (c == 'h') {
            return HOUR__FACTOR;
        }
        if (c == 'm') {
            return MIN__FACTOR;
        }
        if (c == 's') {
            return SEC__FACTOR;
        }
        throw new IllegalArgumentException("Uknown elapsed time spec: " + c);
    }

    private static double modFor(char c, int i) {
        if (c == '0') {
            return Math.pow(10.0d, (double) i);
        }
        if (c == 'h') {
            return 24.0d;
        }
        if (c == 'm' || c == 's') {
            return 60.0d;
        }
        throw new IllegalArgumentException("Uknown elapsed time spec: " + c);
    }

    public void formatValue(StringBuffer stringBuffer, Object obj) {
        double doubleValue = ((Number) obj).doubleValue();
        if (doubleValue < 0.0d) {
            stringBuffer.append('-');
            doubleValue = -doubleValue;
        }
        Long[] lArr = new Long[this.specs.size()];
        for (int i = 0; i < this.specs.size(); i++) {
            lArr[i] = Long.valueOf(this.specs.get(i).valueFor(doubleValue));
        }
        new Formatter(stringBuffer).format(this.printfFmt, lArr);
    }

    public void simpleValue(StringBuffer stringBuffer, Object obj) {
        formatValue(stringBuffer, obj);
    }
}
