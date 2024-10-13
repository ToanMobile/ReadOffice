package com.app.office.fc.ss.format;

import androidx.exifinterface.media.ExifInterface;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import com.app.office.fc.ss.format.CellFormatPart;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.Collections;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;

public class CellNumberFormatter extends CellFormatter {
    /* access modifiers changed from: private */
    public static final CellFormatter SIMPLE_FLOAT = new CellNumberFormatter("#.#");
    /* access modifiers changed from: private */
    public static final CellFormatter SIMPLE_INT = new CellNumberFormatter("#");
    static final CellFormatter SIMPLE_NUMBER = new CellFormatter("General") {
        public void formatValue(StringBuffer stringBuffer, Object obj) {
            if (obj != null) {
                if (!(obj instanceof Number)) {
                    CellTextFormatter.SIMPLE_TEXT.formatValue(stringBuffer, obj);
                } else if (((Number) obj).doubleValue() % 1.0d == 0.0d) {
                    CellNumberFormatter.SIMPLE_INT.formatValue(stringBuffer, obj);
                } else {
                    CellNumberFormatter.SIMPLE_FLOAT.formatValue(stringBuffer, obj);
                }
            }
        }

        public void simpleValue(StringBuffer stringBuffer, Object obj) {
            formatValue(stringBuffer, obj);
        }
    };
    private Special afterFractional;
    private Special afterInteger;
    private DecimalFormat decimalFmt;
    /* access modifiers changed from: private */
    public Special decimalPoint;
    private String denominatorFmt;
    private List<Special> denominatorSpecials;
    private final String desc;
    /* access modifiers changed from: private */
    public Special exponent;
    private List<Special> exponentDigitSpecials;
    private List<Special> exponentSpecials;
    private List<Special> fractionalSpecials;
    /* access modifiers changed from: private */
    public boolean improperFraction;
    private boolean integerCommas;
    private List<Special> integerSpecials;
    private int maxDenominator;
    /* access modifiers changed from: private */
    public Special numerator;
    private String numeratorFmt;
    private List<Special> numeratorSpecials;
    private String printfFmt;
    private double scale = 1.0d;
    /* access modifiers changed from: private */
    public Special slash;
    /* access modifiers changed from: private */
    public final List<Special> specials;

    static /* synthetic */ double access$1034(CellNumberFormatter cellNumberFormatter, double d) {
        double d2 = cellNumberFormatter.scale * d;
        cellNumberFormatter.scale = d2;
        return d2;
    }

    static class Special {
        final char ch;
        int pos;

        Special(char c, int i) {
            this.ch = c;
            this.pos = i;
        }

        public String toString() {
            return "'" + this.ch + "' @ " + this.pos;
        }
    }

    static class StringMod implements Comparable<StringMod> {
        public static final int AFTER = 2;
        public static final int BEFORE = 1;
        public static final int REPLACE = 3;
        Special end;
        boolean endInclusive;
        final int op;
        final Special special;
        boolean startInclusive;
        CharSequence toAdd;

        private StringMod(Special special2, CharSequence charSequence, int i) {
            this.special = special2;
            this.toAdd = charSequence;
            this.op = i;
        }

        public StringMod(Special special2, boolean z, Special special3, boolean z2, char c) {
            this(special2, z, special3, z2);
            this.toAdd = c + "";
        }

        public StringMod(Special special2, boolean z, Special special3, boolean z2) {
            this.special = special2;
            this.startInclusive = z;
            this.end = special3;
            this.endInclusive = z2;
            this.op = 3;
            this.toAdd = "";
        }

        public int compareTo(StringMod stringMod) {
            int i = this.special.pos - stringMod.special.pos;
            if (i != 0) {
                return i;
            }
            return this.op - stringMod.op;
        }

        public boolean equals(Object obj) {
            try {
                return compareTo((StringMod) obj) == 0;
            } catch (RuntimeException unused) {
                return false;
            }
        }

        public int hashCode() {
            return this.special.hashCode() + this.op;
        }
    }

    private class NumPartHandler implements CellFormatPart.PartHandler {
        private char insertSignForExponent;

        private NumPartHandler() {
        }

        public String handlePart(Matcher matcher, String str, CellFormatType cellFormatType, StringBuffer stringBuffer) {
            int length = stringBuffer.length();
            char charAt = str.charAt(0);
            if (charAt != '#') {
                if (charAt == '%') {
                    CellNumberFormatter.access$1034(CellNumberFormatter.this, 100.0d);
                } else if (charAt != '?') {
                    if (charAt != 'E' && charAt != 'e') {
                        switch (charAt) {
                            case '.':
                                if (CellNumberFormatter.this.decimalPoint == null && CellNumberFormatter.this.specials.size() > 0) {
                                    CellNumberFormatter.this.specials.add(CellNumberFormatter.this.decimalPoint = new Special('.', length));
                                    break;
                                }
                            case '/':
                                if (CellNumberFormatter.this.slash == null && CellNumberFormatter.this.specials.size() > 0) {
                                    CellNumberFormatter cellNumberFormatter = CellNumberFormatter.this;
                                    Special unused = cellNumberFormatter.numerator = cellNumberFormatter.previousNumber();
                                    if (CellNumberFormatter.this.numerator == CellNumberFormatter.firstDigit(CellNumberFormatter.this.specials)) {
                                        boolean unused2 = CellNumberFormatter.this.improperFraction = true;
                                    }
                                    CellNumberFormatter.this.specials.add(CellNumberFormatter.this.slash = new Special('.', length));
                                    break;
                                }
                            case '0':
                                break;
                            default:
                                return null;
                        }
                    } else if (CellNumberFormatter.this.exponent == null && CellNumberFormatter.this.specials.size() > 0) {
                        CellNumberFormatter.this.specials.add(CellNumberFormatter.this.exponent = new Special('.', length));
                        this.insertSignForExponent = str.charAt(1);
                        return str.substring(0, 1);
                    }
                }
                return str;
            }
            if (this.insertSignForExponent != 0) {
                CellNumberFormatter.this.specials.add(new Special(this.insertSignForExponent, length));
                stringBuffer.append(this.insertSignForExponent);
                this.insertSignForExponent = 0;
                length++;
            }
            for (int i = 0; i < str.length(); i++) {
                CellNumberFormatter.this.specials.add(new Special(str.charAt(i), length + i));
            }
            return str;
        }
    }

    public CellNumberFormatter(String str) {
        super(str);
        int i;
        int i2;
        LinkedList linkedList = new LinkedList();
        this.specials = linkedList;
        StringBuffer parseFormat = CellFormatPart.parseFormat(str, CellFormatType.NUMBER, new NumPartHandler());
        if (!((this.decimalPoint == null && this.exponent == null) || this.slash == null)) {
            this.slash = null;
            this.numerator = null;
        }
        interpretCommas(parseFormat);
        if (this.decimalPoint == null) {
            i2 = 0;
            i = 0;
        } else {
            i2 = interpretPrecision();
            i = i2 + 1;
            if (i2 == 0) {
                linkedList.remove(this.decimalPoint);
                this.decimalPoint = null;
            }
        }
        boolean z = true;
        if (i2 == 0) {
            this.fractionalSpecials = Collections.emptyList();
        } else {
            this.fractionalSpecials = linkedList.subList(linkedList.indexOf(this.decimalPoint) + 1, fractionalEnd());
        }
        Special special = this.exponent;
        if (special == null) {
            this.exponentSpecials = Collections.emptyList();
        } else {
            int indexOf = linkedList.indexOf(special);
            this.exponentSpecials = specialsFor(indexOf, 2);
            this.exponentDigitSpecials = specialsFor(indexOf + 2);
        }
        if (this.slash == null) {
            this.numeratorSpecials = Collections.emptyList();
            this.denominatorSpecials = Collections.emptyList();
        } else {
            Special special2 = this.numerator;
            if (special2 == null) {
                this.numeratorSpecials = Collections.emptyList();
            } else {
                this.numeratorSpecials = specialsFor(linkedList.indexOf(special2));
            }
            List<Special> specialsFor = specialsFor(linkedList.indexOf(this.slash) + 1);
            this.denominatorSpecials = specialsFor;
            if (specialsFor.isEmpty()) {
                this.numeratorSpecials = Collections.emptyList();
            } else {
                this.maxDenominator = maxValue(this.denominatorSpecials);
                this.numeratorFmt = singleNumberFormat(this.numeratorSpecials);
                this.denominatorFmt = singleNumberFormat(this.denominatorSpecials);
            }
        }
        this.integerSpecials = linkedList.subList(0, integerEnd());
        if (this.exponent == null) {
            StringBuffer stringBuffer = new StringBuffer("%");
            stringBuffer.append('0');
            stringBuffer.append(calculateIntegerPartWidth() + i);
            stringBuffer.append('.');
            stringBuffer.append(i2);
            stringBuffer.append("f");
            this.printfFmt = stringBuffer.toString();
        } else {
            StringBuffer stringBuffer2 = new StringBuffer();
            List<Special> list = this.integerSpecials;
            if (list.size() == 1) {
                stringBuffer2.append("0");
                z = false;
            } else {
                for (Special isDigitFmt : list) {
                    if (isDigitFmt(isDigitFmt)) {
                        stringBuffer2.append(z ? '#' : '0');
                        z = false;
                    }
                }
            }
            if (this.fractionalSpecials.size() > 0) {
                stringBuffer2.append('.');
                for (Special isDigitFmt2 : this.fractionalSpecials) {
                    if (isDigitFmt(isDigitFmt2)) {
                        if (!z) {
                            stringBuffer2.append('0');
                        }
                        z = false;
                    }
                }
            }
            stringBuffer2.append('E');
            List<Special> list2 = this.exponentSpecials;
            placeZeros(stringBuffer2, list2.subList(2, list2.size()));
            this.decimalFmt = new DecimalFormat(stringBuffer2.toString());
        }
        if (this.exponent != null) {
            this.scale = 1.0d;
        }
        this.desc = parseFormat.toString();
    }

    private static void placeZeros(StringBuffer stringBuffer, List<Special> list) {
        for (Special isDigitFmt : list) {
            if (isDigitFmt(isDigitFmt)) {
                stringBuffer.append('0');
            }
        }
    }

    /* access modifiers changed from: private */
    public static Special firstDigit(List<Special> list) {
        for (Special next : list) {
            if (isDigitFmt(next)) {
                return next;
            }
        }
        return null;
    }

    static StringMod insertMod(Special special, CharSequence charSequence, int i) {
        return new StringMod(special, charSequence, i);
    }

    static StringMod deleteMod(Special special, boolean z, Special special2, boolean z2) {
        return new StringMod(special, z, special2, z2);
    }

    static StringMod replaceMod(Special special, boolean z, Special special2, boolean z2, char c) {
        return new StringMod(special, z, special2, z2, c);
    }

    private static String singleNumberFormat(List<Special> list) {
        return "%0" + list.size() + "d";
    }

    private static int maxValue(List<Special> list) {
        return (int) Math.round(Math.pow(10.0d, (double) list.size()) - 1.0d);
    }

    private List<Special> specialsFor(int i, int i2) {
        if (i >= this.specials.size()) {
            return Collections.emptyList();
        }
        int i3 = i2 + i;
        ListIterator<Special> listIterator = this.specials.listIterator(i3);
        Special next = listIterator.next();
        while (listIterator.hasNext()) {
            Special next2 = listIterator.next();
            if (!isDigitFmt(next2) || next2.pos - next.pos > 1) {
                break;
            }
            i3++;
            next = next2;
        }
        return this.specials.subList(i, i3 + 1);
    }

    private List<Special> specialsFor(int i) {
        return specialsFor(i, 0);
    }

    private static boolean isDigitFmt(Special special) {
        return special.ch == '0' || special.ch == '?' || special.ch == '#';
    }

    /* access modifiers changed from: private */
    public Special previousNumber() {
        List<Special> list = this.specials;
        ListIterator<Special> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            Special previous = listIterator.previous();
            if (isDigitFmt(previous)) {
                while (listIterator.hasPrevious()) {
                    Special previous2 = listIterator.previous();
                    if (previous.pos - previous2.pos > 1 || !isDigitFmt(previous2)) {
                        break;
                    }
                    previous = previous2;
                }
                return previous;
            }
        }
        return null;
    }

    private int calculateIntegerPartWidth() {
        Special next;
        ListIterator<Special> listIterator = this.specials.listIterator();
        int i = 0;
        while (listIterator.hasNext() && (next = listIterator.next()) != this.afterInteger) {
            if (isDigitFmt(next)) {
                i++;
            }
        }
        return i;
    }

    private int interpretPrecision() {
        Special special = this.decimalPoint;
        if (special == null) {
            return -1;
        }
        int i = 0;
        List<Special> list = this.specials;
        ListIterator<Special> listIterator = list.listIterator(list.indexOf(special));
        if (listIterator.hasNext()) {
            listIterator.next();
        }
        while (listIterator.hasNext() && isDigitFmt(listIterator.next())) {
            i++;
        }
        return i;
    }

    private void interpretCommas(StringBuffer stringBuffer) {
        ListIterator<Special> listIterator = this.specials.listIterator(integerEnd());
        int i = 0;
        this.integerCommas = false;
        boolean z = true;
        while (listIterator.hasPrevious()) {
            if (listIterator.previous().ch != ',') {
                z = false;
            } else if (z) {
                this.scale /= 1000.0d;
            } else {
                this.integerCommas = true;
            }
        }
        if (this.decimalPoint != null) {
            ListIterator<Special> listIterator2 = this.specials.listIterator(fractionalEnd());
            while (listIterator2.hasPrevious() && listIterator2.previous().ch == ',') {
                this.scale /= 1000.0d;
            }
        }
        ListIterator<Special> listIterator3 = this.specials.listIterator();
        while (listIterator3.hasNext()) {
            Special next = listIterator3.next();
            next.pos -= i;
            if (next.ch == ',') {
                i++;
                listIterator3.remove();
                stringBuffer.deleteCharAt(next.pos);
            }
        }
    }

    private int integerEnd() {
        Special special = this.decimalPoint;
        if (special != null) {
            this.afterInteger = special;
        } else {
            Special special2 = this.exponent;
            if (special2 != null) {
                this.afterInteger = special2;
            } else {
                Special special3 = this.numerator;
                if (special3 != null) {
                    this.afterInteger = special3;
                } else {
                    this.afterInteger = null;
                }
            }
        }
        Special special4 = this.afterInteger;
        return special4 == null ? this.specials.size() : this.specials.indexOf(special4);
    }

    private int fractionalEnd() {
        Special special = this.exponent;
        if (special != null) {
            this.afterFractional = special;
        } else {
            Special special2 = this.numerator;
            if (special2 != null) {
                this.afterInteger = special2;
            } else {
                this.afterFractional = null;
            }
        }
        Special special3 = this.afterFractional;
        return special3 == null ? this.specials.size() : this.specials.indexOf(special3);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00e9, code lost:
        if (r2.startInclusive == false) goto L_0x00eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00eb, code lost:
        r3 = r3 + 1;
        r13 = r13 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00f3, code lost:
        if (r4.get(r3) == false) goto L_0x00f6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00f6, code lost:
        r15 = r2.end.pos;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00fc, code lost:
        if (r2.endInclusive == false) goto L_0x0100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00fe, code lost:
        r15 = r15 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0100, code lost:
        r11 = r15 + r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0102, code lost:
        if (r13 >= r11) goto L_0x0124;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0104, code lost:
        r17 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x010a, code lost:
        if (r2.toAdd != "") goto L_0x0111;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x010c, code lost:
        r14.delete(r13, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0111, code lost:
        r0 = r2.toAdd.charAt(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0118, code lost:
        if (r13 >= r11) goto L_0x0120;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x011a, code lost:
        r14.setCharAt(r13, r0);
        r13 = r13 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0120, code lost:
        r4.set(r3, r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0124, code lost:
        r17 = r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00ab  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x016c  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0174  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0183  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void formatValue(java.lang.StringBuffer r19, java.lang.Object r20) {
        /*
            r18 = this;
            r8 = r18
            r9 = r19
            r0 = r20
            java.lang.Number r0 = (java.lang.Number) r0
            double r0 = r0.doubleValue()
            double r2 = r8.scale
            double r0 = r0 * r2
            r2 = 0
            r10 = 0
            r11 = 1
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 >= 0) goto L_0x001a
            r12 = 1
            goto L_0x001b
        L_0x001a:
            r12 = 0
        L_0x001b:
            if (r12 == 0) goto L_0x001e
            double r0 = -r0
        L_0x001e:
            com.app.office.fc.ss.format.CellNumberFormatter$Special r4 = r8.slash
            if (r4 == 0) goto L_0x002f
            boolean r4 = r8.improperFraction
            if (r4 == 0) goto L_0x0029
            r4 = r0
            r1 = r2
            goto L_0x0031
        L_0x0029:
            r2 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r2 = r0 % r2
            long r0 = (long) r0
            double r0 = (double) r0
        L_0x002f:
            r4 = r2
            r1 = r0
        L_0x0031:
            java.util.TreeSet r13 = new java.util.TreeSet
            r13.<init>()
            java.lang.StringBuffer r14 = new java.lang.StringBuffer
            java.lang.String r0 = r8.desc
            r14.<init>(r0)
            com.app.office.fc.ss.format.CellNumberFormatter$Special r0 = r8.exponent
            if (r0 == 0) goto L_0x0045
            r8.writeScientific(r1, r14, r13)
            goto L_0x0087
        L_0x0045:
            boolean r0 = r8.improperFraction
            if (r0 == 0) goto L_0x0052
            r3 = 0
            r0 = r18
            r6 = r14
            r7 = r13
            r0.writeFraction(r1, r3, r4, r6, r7)
            goto L_0x0087
        L_0x0052:
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>()
            java.util.Formatter r0 = new java.util.Formatter
            r0.<init>(r3)
            java.util.Locale r6 = LOCALE
            java.lang.String r7 = r8.printfFmt
            java.lang.Object[] r15 = new java.lang.Object[r11]
            java.lang.Double r16 = java.lang.Double.valueOf(r1)
            r15[r10] = r16
            r0.format(r6, r7, r15)
            com.app.office.fc.ss.format.CellNumberFormatter$Special r0 = r8.numerator
            if (r0 != 0) goto L_0x0080
            r8.writeFractional(r3, r14)
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r4 = r8.integerSpecials
            boolean r5 = r8.integerCommas
            r0 = r18
            r1 = r3
            r2 = r14
            r3 = r4
            r4 = r13
            r0.writeInteger(r1, r2, r3, r4, r5)
            goto L_0x0087
        L_0x0080:
            r0 = r18
            r6 = r14
            r7 = r13
            r0.writeFraction(r1, r3, r4, r6, r7)
        L_0x0087:
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r0 = r8.specials
            java.util.ListIterator r0 = r0.listIterator()
            java.util.Iterator r1 = r13.iterator()
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x009e
            java.lang.Object r2 = r1.next()
            com.app.office.fc.ss.format.CellNumberFormatter$StringMod r2 = (com.app.office.fc.ss.format.CellNumberFormatter.StringMod) r2
            goto L_0x009f
        L_0x009e:
            r2 = 0
        L_0x009f:
            java.util.BitSet r4 = new java.util.BitSet
            r4.<init>()
            r5 = 0
        L_0x00a5:
            boolean r6 = r0.hasNext()
            if (r6 == 0) goto L_0x0181
            java.lang.Object r6 = r0.next()
            com.app.office.fc.ss.format.CellNumberFormatter$Special r6 = (com.app.office.fc.ss.format.CellNumberFormatter.Special) r6
            int r7 = r6.pos
            int r7 = r7 + r5
            int r13 = r6.pos
            boolean r13 = r4.get(r13)
            if (r13 != 0) goto L_0x00ce
            char r13 = r14.charAt(r7)
            r15 = 35
            if (r13 != r15) goto L_0x00ce
            r14.deleteCharAt(r7)
            int r5 = r5 + -1
            int r7 = r6.pos
            r4.set(r7)
        L_0x00ce:
            if (r2 == 0) goto L_0x017a
            com.app.office.fc.ss.format.CellNumberFormatter$Special r7 = r2.special
            if (r6 != r7) goto L_0x017a
            int r7 = r14.length()
            int r13 = r6.pos
            int r13 = r13 + r5
            int r15 = r2.op
            if (r15 == r11) goto L_0x0157
            r3 = 2
            if (r15 == r3) goto L_0x0140
            r3 = 3
            if (r15 != r3) goto L_0x0127
            int r3 = r6.pos
            boolean r15 = r2.startInclusive
            if (r15 != 0) goto L_0x00ef
        L_0x00eb:
            int r3 = r3 + 1
            int r13 = r13 + 1
        L_0x00ef:
            boolean r15 = r4.get(r3)
            if (r15 == 0) goto L_0x00f6
            goto L_0x00eb
        L_0x00f6:
            com.app.office.fc.ss.format.CellNumberFormatter$Special r15 = r2.end
            int r15 = r15.pos
            boolean r11 = r2.endInclusive
            if (r11 == 0) goto L_0x0100
            int r15 = r15 + 1
        L_0x0100:
            int r11 = r15 + r5
            if (r13 >= r11) goto L_0x0124
            java.lang.CharSequence r10 = r2.toAdd
            r17 = r0
            java.lang.String r0 = ""
            if (r10 != r0) goto L_0x0111
            r14.delete(r13, r11)
            r10 = 0
            goto L_0x0120
        L_0x0111:
            java.lang.CharSequence r0 = r2.toAdd
            r10 = 0
            char r0 = r0.charAt(r10)
        L_0x0118:
            if (r13 >= r11) goto L_0x0120
            r14.setCharAt(r13, r0)
            int r13 = r13 + 1
            goto L_0x0118
        L_0x0120:
            r4.set(r3, r15)
            goto L_0x0160
        L_0x0124:
            r17 = r0
            goto L_0x0160
        L_0x0127:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "Unknown op: "
            r1.append(r3)
            int r2 = r2.op
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0140:
            r17 = r0
            java.lang.CharSequence r0 = r2.toAdd
            java.lang.String r3 = ","
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0155
            int r0 = r6.pos
            boolean r0 = r4.get(r0)
            if (r0 == 0) goto L_0x0155
            goto L_0x0160
        L_0x0155:
            r0 = 1
            goto L_0x015a
        L_0x0157:
            r17 = r0
            r0 = 0
        L_0x015a:
            int r13 = r13 + r0
            java.lang.CharSequence r0 = r2.toAdd
            r14.insert(r13, r0)
        L_0x0160:
            int r0 = r14.length()
            int r0 = r0 - r7
            int r5 = r5 + r0
            boolean r0 = r1.hasNext()
            if (r0 == 0) goto L_0x0174
            java.lang.Object r0 = r1.next()
            com.app.office.fc.ss.format.CellNumberFormatter$StringMod r0 = (com.app.office.fc.ss.format.CellNumberFormatter.StringMod) r0
            r2 = r0
            goto L_0x0175
        L_0x0174:
            r2 = 0
        L_0x0175:
            r0 = r17
            r11 = 1
            goto L_0x00ce
        L_0x017a:
            r17 = r0
            r0 = r17
            r11 = 1
            goto L_0x00a5
        L_0x0181:
            if (r12 == 0) goto L_0x0188
            r0 = 45
            r9.append(r0)
        L_0x0188:
            r9.append(r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ss.format.CellNumberFormatter.formatValue(java.lang.StringBuffer, java.lang.Object):void");
    }

    private void writeScientific(double d, StringBuffer stringBuffer, Set<StringMod> set) {
        StringBuffer stringBuffer2 = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(1);
        this.decimalFmt.format(d, stringBuffer2, fieldPosition);
        writeInteger(stringBuffer2, stringBuffer, this.integerSpecials, set, this.integerCommas);
        writeFractional(stringBuffer2, stringBuffer);
        int endIndex = fieldPosition.getEndIndex() + 1;
        char charAt = stringBuffer2.charAt(endIndex);
        if (charAt != '-') {
            stringBuffer2.insert(endIndex, '+');
            charAt = '+';
        }
        Special next = this.exponentSpecials.listIterator(1).next();
        char c = next.ch;
        if (charAt == '-' || c == '+') {
            set.add(replaceMod(next, true, next, true, charAt));
        } else {
            set.add(deleteMod(next, true, next, true));
        }
        writeInteger(new StringBuffer(stringBuffer2.substring(endIndex + 1)), stringBuffer, this.exponentDigitSpecials, set, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00aa, code lost:
        if (hasChar('0', r7.numeratorSpecials) == false) goto L_0x00ac;
     */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00c6 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00f7  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0137 A[Catch:{ RuntimeException -> 0x015e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void writeFraction(double r17, java.lang.StringBuffer r19, double r20, java.lang.StringBuffer r22, java.util.Set<com.app.office.fc.ss.format.CellNumberFormatter.StringMod> r23) {
        /*
            r16 = this;
            r7 = r16
            r8 = r20
            r0 = r23
            boolean r1 = r7.improperFraction
            r10 = 0
            r12 = 1
            if (r1 != 0) goto L_0x010b
            r13 = 32
            r14 = 2
            r15 = 63
            r1 = 48
            r6 = 0
            int r2 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r2 != 0) goto L_0x0074
            java.util.List[] r3 = new java.util.List[r12]
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r4 = r7.numeratorSpecials
            r3[r6] = r4
            boolean r3 = hasChar(r1, r3)
            if (r3 != 0) goto L_0x0074
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r4 = r7.integerSpecials
            r8 = 0
            r1 = r16
            r2 = r19
            r3 = r22
            r5 = r23
            r9 = 0
            r6 = r8
            r1.writeInteger(r2, r3, r4, r5, r6)
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r1 = r7.integerSpecials
            int r2 = r1.size()
            int r2 = r2 - r12
            java.lang.Object r1 = r1.get(r2)
            com.app.office.fc.ss.format.CellNumberFormatter$Special r1 = (com.app.office.fc.ss.format.CellNumberFormatter.Special) r1
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r2 = r7.denominatorSpecials
            int r3 = r2.size()
            int r3 = r3 - r12
            java.lang.Object r2 = r2.get(r3)
            com.app.office.fc.ss.format.CellNumberFormatter$Special r2 = (com.app.office.fc.ss.format.CellNumberFormatter.Special) r2
            r3 = 3
            java.util.List[] r3 = new java.util.List[r3]
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r4 = r7.integerSpecials
            r3[r9] = r4
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r4 = r7.numeratorSpecials
            r3[r12] = r4
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r4 = r7.denominatorSpecials
            r3[r14] = r4
            boolean r3 = hasChar(r15, r3)
            if (r3 == 0) goto L_0x006c
            com.app.office.fc.ss.format.CellNumberFormatter$StringMod r1 = replaceMod(r1, r9, r2, r12, r13)
            r0.add(r1)
            goto L_0x0073
        L_0x006c:
            com.app.office.fc.ss.format.CellNumberFormatter$StringMod r1 = deleteMod(r1, r9, r2, r12)
            r0.add(r1)
        L_0x0073:
            return
        L_0x0074:
            r3 = 0
            int r4 = (r17 > r10 ? 1 : (r17 == r10 ? 0 : -1))
            if (r4 != 0) goto L_0x007d
            if (r2 != 0) goto L_0x007d
            r6 = 1
            goto L_0x007e
        L_0x007d:
            r6 = 0
        L_0x007e:
            if (r2 != 0) goto L_0x008f
            java.util.List[] r2 = new java.util.List[r12]
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r5 = r7.numeratorSpecials
            r2[r3] = r5
            boolean r2 = hasChar(r1, r2)
            if (r2 == 0) goto L_0x008d
            goto L_0x008f
        L_0x008d:
            r2 = 0
            goto L_0x0090
        L_0x008f:
            r2 = 1
        L_0x0090:
            if (r6 == 0) goto L_0x00ae
            r5 = 35
            java.util.List[] r10 = new java.util.List[r12]
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r11 = r7.integerSpecials
            r10[r3] = r11
            boolean r5 = hasOnly(r5, r10)
            if (r5 != 0) goto L_0x00ac
            java.util.List[] r5 = new java.util.List[r12]
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r10 = r7.numeratorSpecials
            r5[r3] = r10
            boolean r5 = hasChar(r1, r5)
            if (r5 != 0) goto L_0x00ae
        L_0x00ac:
            r5 = 1
            goto L_0x00af
        L_0x00ae:
            r5 = 0
        L_0x00af:
            if (r6 != 0) goto L_0x00c3
            if (r4 != 0) goto L_0x00c3
            if (r2 == 0) goto L_0x00c3
            java.util.List[] r2 = new java.util.List[r12]
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r4 = r7.integerSpecials
            r2[r3] = r4
            boolean r1 = hasChar(r1, r2)
            if (r1 != 0) goto L_0x00c3
            r6 = 1
            goto L_0x00c4
        L_0x00c3:
            r6 = 0
        L_0x00c4:
            if (r5 != 0) goto L_0x00da
            if (r6 == 0) goto L_0x00c9
            goto L_0x00da
        L_0x00c9:
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r4 = r7.integerSpecials
            r6 = 0
            r1 = r16
            r2 = r19
            r3 = r22
            r5 = r23
            r1.writeInteger(r2, r3, r4, r5, r6)
        L_0x00d7:
            r1 = 0
            goto L_0x010c
        L_0x00da:
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r1 = r7.integerSpecials
            int r2 = r1.size()
            int r2 = r2 - r12
            java.lang.Object r1 = r1.get(r2)
            com.app.office.fc.ss.format.CellNumberFormatter$Special r1 = (com.app.office.fc.ss.format.CellNumberFormatter.Special) r1
            java.util.List[] r2 = new java.util.List[r14]
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r4 = r7.integerSpecials
            r2[r3] = r4
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r4 = r7.numeratorSpecials
            r2[r12] = r4
            boolean r2 = hasChar(r15, r2)
            if (r2 == 0) goto L_0x0101
            com.app.office.fc.ss.format.CellNumberFormatter$Special r2 = r7.numerator
            com.app.office.fc.ss.format.CellNumberFormatter$StringMod r1 = replaceMod(r1, r12, r2, r3, r13)
            r0.add(r1)
            goto L_0x00d7
        L_0x0101:
            com.app.office.fc.ss.format.CellNumberFormatter$Special r2 = r7.numerator
            com.app.office.fc.ss.format.CellNumberFormatter$StringMod r1 = deleteMod(r1, r12, r2, r3)
            r0.add(r1)
            goto L_0x00d7
        L_0x010b:
            r1 = r10
        L_0x010c:
            int r3 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r3 == 0) goto L_0x012e
            boolean r3 = r7.improperFraction     // Catch:{ RuntimeException -> 0x015e }
            if (r3 == 0) goto L_0x011d
            r3 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r3 = r8 % r3
            int r5 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r5 != 0) goto L_0x011d
            goto L_0x012e
        L_0x011d:
            com.app.office.fc.ss.format.CellNumberFormatter$Fraction r1 = new com.app.office.fc.ss.format.CellNumberFormatter$Fraction     // Catch:{ RuntimeException -> 0x015e }
            int r2 = r7.maxDenominator     // Catch:{ RuntimeException -> 0x015e }
            r1.<init>(r8, r2)     // Catch:{ RuntimeException -> 0x015e }
            int r2 = r1.getNumerator()     // Catch:{ RuntimeException -> 0x015e }
            int r1 = r1.getDenominator()     // Catch:{ RuntimeException -> 0x015e }
            r12 = r1
            goto L_0x0133
        L_0x012e:
            long r1 = java.lang.Math.round(r20)     // Catch:{ RuntimeException -> 0x015e }
            int r2 = (int) r1     // Catch:{ RuntimeException -> 0x015e }
        L_0x0133:
            boolean r1 = r7.improperFraction     // Catch:{ RuntimeException -> 0x015e }
            if (r1 == 0) goto L_0x0141
            long r1 = (long) r2     // Catch:{ RuntimeException -> 0x015e }
            double r3 = (double) r12     // Catch:{ RuntimeException -> 0x015e }
            double r3 = r3 * r17
            long r3 = java.lang.Math.round(r3)     // Catch:{ RuntimeException -> 0x015e }
            long r1 = r1 + r3
            int r2 = (int) r1     // Catch:{ RuntimeException -> 0x015e }
        L_0x0141:
            r3 = r2
            java.lang.String r2 = r7.numeratorFmt     // Catch:{ RuntimeException -> 0x015e }
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r5 = r7.numeratorSpecials     // Catch:{ RuntimeException -> 0x015e }
            r1 = r16
            r4 = r22
            r6 = r23
            r1.writeSingleInteger(r2, r3, r4, r5, r6)     // Catch:{ RuntimeException -> 0x015e }
            java.lang.String r2 = r7.denominatorFmt     // Catch:{ RuntimeException -> 0x015e }
            java.util.List<com.app.office.fc.ss.format.CellNumberFormatter$Special> r5 = r7.denominatorSpecials     // Catch:{ RuntimeException -> 0x015e }
            r1 = r16
            r3 = r12
            r4 = r22
            r6 = r23
            r1.writeSingleInteger(r2, r3, r4, r5, r6)     // Catch:{ RuntimeException -> 0x015e }
            goto L_0x0162
        L_0x015e:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0162:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.ss.format.CellNumberFormatter.writeFraction(double, java.lang.StringBuffer, double, java.lang.StringBuffer, java.util.Set):void");
    }

    private static boolean hasChar(char c, List<Special>... listArr) {
        for (List<Special> it : listArr) {
            for (Special special : it) {
                if (special.ch == c) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasOnly(char c, List<Special>... listArr) {
        for (List<Special> it : listArr) {
            for (Special special : it) {
                if (special.ch != c) {
                    return false;
                }
            }
        }
        return true;
    }

    private void writeSingleInteger(String str, int i, StringBuffer stringBuffer, List<Special> list, Set<StringMod> set) {
        StringBuffer stringBuffer2 = new StringBuffer();
        new Formatter(stringBuffer2).format(LOCALE, str, new Object[]{Integer.valueOf(i)});
        writeInteger(stringBuffer2, stringBuffer, list, set, false);
    }

    private void writeInteger(StringBuffer stringBuffer, StringBuffer stringBuffer2, List<Special> list, Set<StringMod> set, boolean z) {
        boolean z2;
        int i;
        StringBuffer stringBuffer3 = stringBuffer;
        List<Special> list2 = list;
        Set<StringMod> set2 = set;
        int indexOf = stringBuffer3.indexOf(".") - 1;
        if (indexOf < 0) {
            if (this.exponent == null || list2 != this.integerSpecials) {
                i = stringBuffer.length();
            } else {
                i = stringBuffer3.indexOf(ExifInterface.LONGITUDE_EAST);
            }
            indexOf = i - 1;
        }
        int i2 = 0;
        while (i2 < indexOf && ((r10 = stringBuffer3.charAt(i2)) == '0' || r10 == ',')) {
            i2++;
        }
        ListIterator<Special> listIterator = list2.listIterator(list.size());
        Special special = null;
        int i3 = 0;
        while (listIterator.hasPrevious()) {
            char charAt = indexOf >= 0 ? stringBuffer3.charAt(indexOf) : '0';
            Special previous = listIterator.previous();
            boolean z3 = z && i3 > 0 && i3 % 3 == 0;
            if (charAt != '0' || previous.ch == '0' || previous.ch == '?' || indexOf >= i2) {
                z2 = previous.ch == '?' && indexOf < i2;
                int i4 = previous.pos;
                if (z2) {
                    charAt = ' ';
                }
                stringBuffer2.setCharAt(i4, charAt);
                special = previous;
            } else {
                StringBuffer stringBuffer4 = stringBuffer2;
                z2 = false;
            }
            if (z3) {
                set2.add(insertMod(previous, z2 ? " " : ",", 2));
            }
            i3++;
            indexOf--;
        }
        if (indexOf >= 0) {
            int i5 = indexOf + 1;
            StringBuffer stringBuffer5 = new StringBuffer(stringBuffer3.substring(0, i5));
            if (z) {
                while (i5 > 0) {
                    if (i3 > 0 && i3 % 3 == 0) {
                        stringBuffer5.insert(i5, ',');
                    }
                    i3++;
                    i5--;
                }
            }
            set2.add(insertMod(special, stringBuffer5, 1));
        }
    }

    private void writeFractional(StringBuffer stringBuffer, StringBuffer stringBuffer2) {
        int i;
        if (this.fractionalSpecials.size() > 0) {
            int indexOf = stringBuffer.indexOf(".") + 1;
            if (this.exponent != null) {
                i = stringBuffer.indexOf("e");
            } else {
                i = stringBuffer.length();
            }
            while (true) {
                i--;
                if (i <= indexOf || stringBuffer.charAt(i) != '0') {
                    ListIterator<Special> listIterator = this.fractionalSpecials.listIterator();
                }
            }
            ListIterator<Special> listIterator2 = this.fractionalSpecials.listIterator();
            while (listIterator2.hasNext()) {
                Special next = listIterator2.next();
                char charAt = stringBuffer.charAt(indexOf);
                if (charAt != '0' || next.ch == '0' || indexOf < i) {
                    stringBuffer2.setCharAt(next.pos, charAt);
                } else if (next.ch == '?') {
                    stringBuffer2.setCharAt(next.pos, ' ');
                }
                indexOf++;
            }
        }
    }

    public void simpleValue(StringBuffer stringBuffer, Object obj) {
        SIMPLE_NUMBER.formatValue(stringBuffer, obj);
    }

    private static class Fraction {
        private final int denominator;
        private final int numerator;

        private Fraction(double d, double d2, int i, int i2) {
            long j;
            long j2;
            long j3;
            long j4;
            double d3 = d;
            int i3 = i;
            int i4 = i2;
            long floor = (long) Math.floor(d);
            String str = ")";
            String str2 = PackagingURIHelper.FORWARD_SLASH_STRING;
            if (floor > 2147483647L) {
                throw new IllegalArgumentException("Overflow trying to convert " + d3 + " to fraction (" + floor + str2 + 1 + str);
            } else if (Math.abs(((double) floor) - d3) < d2) {
                this.numerator = (int) floor;
                this.denominator = 1;
            } else {
                int i5 = 0;
                double d4 = d3;
                long j5 = 0;
                long j6 = 1;
                long j7 = 1;
                boolean z = false;
                long j8 = floor;
                while (true) {
                    int i6 = i5 + 1;
                    double d5 = 1.0d / (d4 - ((double) floor));
                    long floor2 = (long) Math.floor(d5);
                    long j9 = floor;
                    j = (floor2 * j8) + j6;
                    long j10 = j8;
                    j2 = (floor2 * j7) + j5;
                    if (j > 2147483647L || j2 > 2147483647L) {
                    } else {
                        long j11 = floor2;
                        String str3 = str;
                        String str4 = str2;
                        double d6 = ((double) j) / ((double) j2);
                        if (i6 >= i4 || Math.abs(d6 - d3) <= d2 || j2 >= ((long) i3)) {
                            j3 = j7;
                            j11 = j9;
                            j4 = j10;
                            z = true;
                        } else {
                            j4 = j;
                            j3 = j2;
                            j5 = j7;
                            d4 = d5;
                            j6 = j10;
                        }
                        if (!z) {
                            j8 = j4;
                            i5 = i6;
                            j7 = j3;
                            floor = j11;
                            str = str3;
                            str2 = str4;
                        } else if (i6 >= i4) {
                            throw new RuntimeException("Unable to convert " + d3 + " to fraction after " + i4 + " iterations");
                        } else if (j2 < ((long) i3)) {
                            this.numerator = (int) j;
                            this.denominator = (int) j2;
                            return;
                        } else {
                            this.numerator = (int) j4;
                            this.denominator = (int) j3;
                            return;
                        }
                    }
                }
                throw new RuntimeException("Overflow trying to convert " + d3 + " to fraction (" + j + str2 + j2 + str);
            }
        }

        public Fraction(double d, int i) {
            this(d, 0.0d, i, 100);
        }

        public int getDenominator() {
            return this.denominator;
        }

        public int getNumerator() {
            return this.numerator;
        }
    }
}
