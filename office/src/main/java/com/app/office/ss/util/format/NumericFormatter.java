package com.app.office.ss.util.format;

import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.exifinterface.media.ExifInterface;
import com.itextpdf.text.pdf.Barcode128;
import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Workbook;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumericFormatter {
    private static final Pattern amPmPattern = Pattern.compile("((A|P)[M/P]*)", 2);
    private static NumericFormatter cf = new NumericFormatter();
    private static final Pattern colorPattern = Pattern.compile("(\\[BLACK\\])|(\\[BLUE\\])|(\\[CYAN\\])|(\\[GREEN\\])|(\\[MAGENTA\\])|(\\[RED\\])|(\\[WHITE\\])|(\\[YELLOW\\])|(\\[COLOR\\s*\\d\\])|(\\[COLOR\\s*[0-5]\\d\\])", 2);
    private final Map<String, Format> formats = new HashMap();

    private NumericFormatter() {
        Format format = ZipPlusFourFormat.instance;
        addFormat("00000\\-0000", format);
        addFormat("00000-0000", format);
        Format format2 = PhoneFormat.instance;
        addFormat("[<=9999999]###\\-####;\\(###\\)\\ ###\\-####", format2);
        addFormat("[<=9999999]###-####;(###) ###-####", format2);
        addFormat("###\\-####;\\(###\\)\\ ###\\-####", format2);
        addFormat("###-####;(###) ###-####", format2);
        addFormat("[<=9999999]000\\-0000;\\(000\\)\\ 000\\-0000", format2);
        addFormat("[<=9999999]000-0000;(000) 000-0000", format2);
        addFormat("000\\-0000;\\(000\\)\\ 000\\-0000", format2);
        addFormat("000-0000;(000) 000-0000", format2);
        Format format3 = SSNFormat.instance;
        addFormat("000\\-00\\-0000", format3);
        addFormat("000-00-0000", format3);
    }

    public static NumericFormatter instance() {
        return cf;
    }

    public void addFormat(String str, Format format) {
        this.formats.put(str, format);
    }

    public short getNumericCellType(String str) {
        int length = str.length();
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("General")) {
            return 6;
        }
        if ("@".equals(str)) {
            return 11;
        }
        if (str.replace("?/", "").length() < length) {
            return 9;
        }
        if (str.indexOf(42) > -1) {
            return 8;
        }
        String validateDatePattern = validateDatePattern(str);
        if (validateDatePattern == null || validateDatePattern.length() == 0 || validateDatePattern.equalsIgnoreCase("General")) {
            return 6;
        }
        return DateTimeFormat.isDateTimeFormat(validateDatePattern) ? (short) 10 : 7;
    }

    private String validatePattern(String str) {
        String str2 = "";
        String replace = str.replace(";@", str2);
        int indexOf = replace.indexOf(34);
        while (indexOf >= 0) {
            String substring = replace.substring(0, indexOf);
            String substring2 = replace.substring(indexOf + 1, replace.length());
            int indexOf2 = substring2.indexOf(34);
            if (indexOf2 >= 0) {
                substring = deleteInvalidateChars(substring);
            }
            str2 = str2 + substring + substring2.substring(0, indexOf2);
            replace = substring2.substring(indexOf2 + 1, substring2.length());
            indexOf = replace.indexOf(34);
        }
        return str2 + deleteInvalidateChars(replace);
    }

    private String deleteInvalidateChars(String str) {
        return str != null ? str.replaceAll("\\\\-", "-").replaceAll("\\\\,", ",").replaceAll("\\\\\\.", ".").replaceAll("\\\\ ", " ").replaceAll("\\\\/", PackagingURIHelper.FORWARD_SLASH_STRING).replaceAll("\"/\"", PackagingURIHelper.FORWARD_SLASH_STRING).replace("_-", " ").replace("_(", " ").replace("_)", "").replace("\\(", "(").replace("\\)", ")").replace("\\", "").replace("_", "") : str;
    }

    private String validateDatePattern(String str) {
        String validatePattern = validatePattern(str);
        boolean z = false;
        while (amPmPattern.matcher(validatePattern).find()) {
            z = true;
        }
        StringBuffer stringBuffer = new StringBuffer();
        char[] charArray = validatePattern.toCharArray();
        ArrayList arrayList = new ArrayList();
        boolean z2 = false;
        boolean z3 = true;
        for (char c : charArray) {
            if (c == '[' && !z2) {
                stringBuffer.append(c);
                z2 = true;
            } else if (c != ']' || !z2) {
                if (z2) {
                    if (c == 'h' || c == 'H') {
                        stringBuffer.append('H');
                    } else if (c == 'm' || c == 'M') {
                        stringBuffer.append('m');
                    } else if (c == 's' || c == 'S') {
                        stringBuffer.append('s');
                    } else {
                        stringBuffer.append(c);
                    }
                } else if (c == 'h' || c == 'H') {
                    if (z) {
                        stringBuffer.append(Barcode128.START_B);
                    } else {
                        stringBuffer.append('H');
                    }
                } else if (c != 'm' && c != 'M') {
                    if (c == 's' || c == 'S') {
                        stringBuffer.append('s');
                        for (int i = 0; i < arrayList.size(); i++) {
                            int intValue = ((Integer) arrayList.get(i)).intValue();
                            if (stringBuffer.charAt(intValue) == 'M') {
                                stringBuffer.replace(intValue, intValue + 1, "m");
                            }
                        }
                        arrayList.clear();
                    } else if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
                        stringBuffer.append(c);
                    } else {
                        arrayList.clear();
                        if (c == 'y' || c == 'Y') {
                            stringBuffer.append('y');
                        } else if (c == 'd' || c == 'D') {
                            stringBuffer.append(Barcode128.CODE_AC_TO_B);
                        } else {
                            stringBuffer.append(c);
                        }
                    }
                    z3 = true;
                } else if (z3) {
                    stringBuffer.append('M');
                    arrayList.add(Integer.valueOf(stringBuffer.length() - 1));
                } else {
                    stringBuffer.append('m');
                }
            } else {
                stringBuffer.append(c);
                z2 = false;
            }
            z3 = false;
        }
        String stringBuffer2 = stringBuffer.toString();
        int indexOf = stringBuffer2.indexOf(91);
        while (indexOf > -1) {
            int indexOf2 = stringBuffer2.indexOf(93);
            stringBuffer2 = stringBuffer2.substring(0, indexOf) + stringBuffer2.substring(indexOf2 + 1, stringBuffer2.length());
            indexOf = stringBuffer2.indexOf(91);
        }
        return stringBuffer2;
    }

    public static int getNegativeColor(Cell cell) {
        String formatCode = cell.getCellStyle().getFormatCode();
        Workbook workbook = cell.getSheet().getWorkbook();
        Matcher matcher = colorPattern.matcher(formatCode);
        if (matcher.find()) {
            String group = matcher.group();
            if (formatCode.indexOf(group) == -1) {
                return -16777216;
            }
            if (group.equals("[Red]")) {
                return SupportMenu.CATEGORY_MASK;
            }
            if (group.equals("[Blue]")) {
                return -16776961;
            }
            if (group.equals("[Cyan]")) {
                return -16711681;
            }
            if (group.equals("[Green]")) {
                return -16711936;
            }
            if (group.equals("[Magenta]")) {
                return -65281;
            }
            if (group.equals("[Black]")) {
                return -16777216;
            }
            if (group.equals("[White]")) {
                return -1;
            }
            if (group.equals("[Yellow]")) {
                return InputDeviceCompat.SOURCE_ANY;
            }
            if (group.equals("[Color n]")) {
                return workbook.getColor((Integer.parseInt(group.replace("[Color ", "").replace("]", "")) + 8) - 1);
            }
        }
        return workbook.getColor(workbook.getFont(cell.getCellStyle().getFontIndex()).getColorIndex());
    }

    private String getMoneySymbol(String str) {
        int indexOf = str.indexOf("[");
        int indexOf2 = str.indexOf("]");
        while (indexOf >= 0 && indexOf2 >= 0) {
            String substring = str.substring(indexOf, indexOf2 + 1);
            int indexOf3 = substring.indexOf("$");
            if (indexOf3 >= 0) {
                int indexOf4 = substring.indexOf(45);
                if (indexOf4 < 0) {
                    indexOf4 = substring.indexOf("]");
                }
                String substring2 = substring.substring(indexOf3 + 1, indexOf4);
                if (substring2 != null) {
                    return substring2;
                }
            }
            str = str.replace(substring, "");
            indexOf = str.indexOf("[");
            indexOf2 = str.indexOf("]");
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0032, code lost:
        r5 = r5.substring(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0024, code lost:
        if (r0.length() == 8) goto L_0x0028;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isNegativeFirst(java.lang.String r5) {
        /*
            r4 = this;
            java.lang.String r0 = "["
            int r0 = r5.indexOf(r0)
            java.lang.String r1 = "]"
            int r1 = r5.indexOf(r1)
            r2 = 1
            if (r0 < 0) goto L_0x0027
            if (r1 < 0) goto L_0x0027
            int r1 = r1 + r2
            java.lang.String r0 = r5.substring(r0, r1)
            java.lang.String r1 = "$"
            int r1 = r0.indexOf(r1)
            if (r1 < 0) goto L_0x0027
            int r1 = r0.length()
            r3 = 8
            if (r1 != r3) goto L_0x0027
            goto L_0x0028
        L_0x0027:
            r0 = 0
        L_0x0028:
            if (r0 == 0) goto L_0x0046
            r1 = 59
            int r1 = r5.indexOf(r1)
            if (r1 < 0) goto L_0x0046
            java.lang.String r5 = r5.substring(r1)
            int r0 = r5.indexOf(r0)
            if (r0 <= 0) goto L_0x0046
            int r0 = r0 - r2
            char r5 = r5.charAt(r0)
            r0 = 45
            if (r5 != r0) goto L_0x0046
            return r2
        L_0x0046:
            r5 = 0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.util.format.NumericFormatter.isNegativeFirst(java.lang.String):boolean");
    }

    private String processMoneyAndNegative(String str) {
        int indexOf = str.indexOf("[");
        int indexOf2 = str.indexOf("]");
        while (indexOf >= 0 && indexOf2 >= 0) {
            str = str.replace(str.substring(indexOf, indexOf2 + 1), "");
            indexOf = str.indexOf("[");
            indexOf2 = str.indexOf("]");
        }
        return str;
    }

    public String getFormatContents(String str, Date date) {
        try {
            return new DateTimeFormat(validateDatePattern(str)).format(date);
        } catch (Exception unused) {
            return new DateTimeFormat("m/d/yy").format(date);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0048, code lost:
        r8 = r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getFormatContents(java.lang.String r17, double r18, short r20) {
        /*
            r16 = this;
            r0 = r16
            r1 = r18
            java.util.Map<java.lang.String, java.text.Format> r3 = r0.formats
            r4 = r17
            java.lang.Object r3 = r3.get(r4)
            java.text.Format r3 = (java.text.Format) r3
            if (r3 == 0) goto L_0x0019
            java.lang.Double r1 = java.lang.Double.valueOf(r18)
            java.lang.String r1 = r3.format(r1)
            return r1
        L_0x0019:
            java.lang.String r3 = r16.validatePattern(r17)
            r4 = 4472406533629990549(0x3e112e0be826d695, double:1.0E-9)
            r6 = 1
            r7 = 0
            java.lang.String r8 = ""
            r9 = 0
            java.lang.String r11 = "-"
            switch(r20) {
                case 6: goto L_0x011f;
                case 7: goto L_0x0090;
                case 8: goto L_0x004b;
                case 9: goto L_0x002f;
                case 10: goto L_0x002d;
                case 11: goto L_0x011f;
                default: goto L_0x002d;
            }
        L_0x002d:
            goto L_0x0192
        L_0x002f:
            com.app.office.ss.util.format.FractionalFormat r4 = new com.app.office.ss.util.format.FractionalFormat     // Catch:{ IllegalArgumentException -> 0x0146 }
            r4.<init>(r3)     // Catch:{ IllegalArgumentException -> 0x0146 }
            java.lang.Double r5 = java.lang.Double.valueOf(r18)     // Catch:{ IllegalArgumentException -> 0x0146 }
            java.lang.String r4 = r4.format(r5)     // Catch:{ IllegalArgumentException -> 0x0146 }
            int r5 = r4.length()     // Catch:{ IllegalArgumentException -> 0x0146 }
            if (r5 != 0) goto L_0x0048
            java.lang.String r8 = java.lang.String.valueOf(r7)     // Catch:{ IllegalArgumentException -> 0x0146 }
            goto L_0x0192
        L_0x0048:
            r8 = r4
            goto L_0x0192
        L_0x004b:
            java.lang.String r12 = r0.getMoneySymbol(r3)     // Catch:{ IllegalArgumentException -> 0x0146 }
            boolean r13 = r0.isNegativeFirst(r3)     // Catch:{ IllegalArgumentException -> 0x0146 }
            java.lang.String r3 = r0.processMoneyAndNegative(r3)     // Catch:{ IllegalArgumentException -> 0x0146 }
            int r14 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r14 <= 0) goto L_0x005d
            double r1 = r1 + r4
            goto L_0x0062
        L_0x005d:
            int r14 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r14 >= 0) goto L_0x0062
            double r1 = r1 - r4
        L_0x0062:
            com.app.office.ss.util.format.AccountFormat r4 = com.app.office.ss.util.format.AccountFormat.instance()     // Catch:{ IllegalArgumentException -> 0x0146 }
            java.lang.String r4 = r4.format(r3, r1)     // Catch:{ IllegalArgumentException -> 0x0146 }
            if (r12 == 0) goto L_0x0048
            int r5 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r5 >= 0) goto L_0x008a
            if (r13 == 0) goto L_0x008a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x0146 }
            r5.<init>()     // Catch:{ IllegalArgumentException -> 0x0146 }
            r5.append(r11)     // Catch:{ IllegalArgumentException -> 0x0146 }
            r5.append(r12)     // Catch:{ IllegalArgumentException -> 0x0146 }
            java.lang.String r4 = r4.replace(r11, r8)     // Catch:{ IllegalArgumentException -> 0x0146 }
            r5.append(r4)     // Catch:{ IllegalArgumentException -> 0x0146 }
            java.lang.String r8 = r5.toString()     // Catch:{ IllegalArgumentException -> 0x0146 }
            goto L_0x0192
        L_0x008a:
            java.lang.String r8 = r12.concat(r4)     // Catch:{ IllegalArgumentException -> 0x0146 }
            goto L_0x0192
        L_0x0090:
            java.lang.String r12 = r0.getMoneySymbol(r3)     // Catch:{ IllegalArgumentException -> 0x0146 }
            boolean r13 = r0.isNegativeFirst(r3)     // Catch:{ IllegalArgumentException -> 0x0146 }
            java.lang.String r3 = r0.processMoneyAndNegative(r3)     // Catch:{ IllegalArgumentException -> 0x0146 }
            int r14 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r14 >= 0) goto L_0x00b5
            java.lang.String r14 = ";"
            java.lang.String[] r14 = r3.split(r14)     // Catch:{ IllegalArgumentException -> 0x0146 }
            int r15 = r14.length     // Catch:{ IllegalArgumentException -> 0x0146 }
            r4 = 2
            if (r15 != r4) goto L_0x00b5
            r4 = r14[r7]     // Catch:{ IllegalArgumentException -> 0x0146 }
            r5 = r14[r6]     // Catch:{ IllegalArgumentException -> 0x0146 }
            boolean r4 = r4.equals(r5)     // Catch:{ IllegalArgumentException -> 0x0146 }
            if (r4 == 0) goto L_0x00b5
            double r1 = -r1
        L_0x00b5:
            java.text.DecimalFormat r4 = new java.text.DecimalFormat     // Catch:{ IllegalArgumentException -> 0x0146 }
            r4.<init>(r3)     // Catch:{ IllegalArgumentException -> 0x0146 }
            int r5 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r5 <= 0) goto L_0x00c5
            r14 = 4472406533629990549(0x3e112e0be826d695, double:1.0E-9)
            double r1 = r1 + r14
            goto L_0x00cf
        L_0x00c5:
            r14 = 4472406533629990549(0x3e112e0be826d695, double:1.0E-9)
            int r5 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r5 >= 0) goto L_0x00cf
            double r1 = r1 - r14
        L_0x00cf:
            java.lang.Double r5 = java.lang.Double.valueOf(r1)     // Catch:{ IllegalArgumentException -> 0x0146 }
            java.lang.String r4 = r4.format(r5)     // Catch:{ IllegalArgumentException -> 0x0146 }
            if (r12 == 0) goto L_0x0048
            char r5 = r4.charAt(r7)     // Catch:{ IllegalArgumentException -> 0x0146 }
            r14 = 40
            if (r5 != r14) goto L_0x00fb
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x0146 }
            r5.<init>()     // Catch:{ IllegalArgumentException -> 0x0146 }
            java.lang.String r9 = "("
            r5.append(r9)     // Catch:{ IllegalArgumentException -> 0x0146 }
            r5.append(r12)     // Catch:{ IllegalArgumentException -> 0x0146 }
            java.lang.String r4 = r4.substring(r6)     // Catch:{ IllegalArgumentException -> 0x0146 }
            r5.append(r4)     // Catch:{ IllegalArgumentException -> 0x0146 }
            java.lang.String r8 = r5.toString()     // Catch:{ IllegalArgumentException -> 0x0146 }
            goto L_0x0192
        L_0x00fb:
            int r5 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r5 >= 0) goto L_0x0119
            if (r13 == 0) goto L_0x0119
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x0146 }
            r5.<init>()     // Catch:{ IllegalArgumentException -> 0x0146 }
            r5.append(r11)     // Catch:{ IllegalArgumentException -> 0x0146 }
            r5.append(r12)     // Catch:{ IllegalArgumentException -> 0x0146 }
            java.lang.String r4 = r4.replace(r11, r8)     // Catch:{ IllegalArgumentException -> 0x0146 }
            r5.append(r4)     // Catch:{ IllegalArgumentException -> 0x0146 }
            java.lang.String r8 = r5.toString()     // Catch:{ IllegalArgumentException -> 0x0146 }
            goto L_0x0192
        L_0x0119:
            java.lang.String r8 = r12.concat(r4)     // Catch:{ IllegalArgumentException -> 0x0146 }
            goto L_0x0192
        L_0x011f:
            java.lang.String r4 = java.lang.String.valueOf(r18)     // Catch:{ IllegalArgumentException -> 0x0146 }
            java.lang.String r5 = "E"
            boolean r5 = r4.contains(r5)     // Catch:{ IllegalArgumentException -> 0x0146 }
            if (r5 != 0) goto L_0x0141
            r5 = 46
            int r5 = r4.indexOf(r5)     // Catch:{ IllegalArgumentException -> 0x0146 }
            if (r5 <= 0) goto L_0x0141
            int r9 = r4.length()     // Catch:{ IllegalArgumentException -> 0x0146 }
            int r9 = r9 - r5
            r10 = 10
            if (r9 <= r10) goto L_0x0141
            int r5 = r5 + r10
            java.lang.String r4 = r4.substring(r7, r5)     // Catch:{ IllegalArgumentException -> 0x0146 }
        L_0x0141:
            java.lang.String r8 = r0.delLastZero(r4)     // Catch:{ IllegalArgumentException -> 0x0146 }
            goto L_0x0192
        L_0x0146:
            java.lang.String r4 = "0"
            java.lang.String r4 = r3.replace(r4, r8)
            java.lang.String r4 = r4.replace(r11, r8)
            int r4 = r4.length()
            if (r4 != 0) goto L_0x018d
            java.text.DecimalFormat r4 = new java.text.DecimalFormat
            java.lang.String r5 = r3.replace(r11, r8)
            r4.<init>(r5)
            java.lang.Double r1 = java.lang.Double.valueOf(r1)
            java.lang.String r1 = r4.format(r1)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r1)
            java.lang.String[] r1 = r3.split(r11)
            int r3 = r1.length
            int r3 = r3 - r6
        L_0x0173:
            if (r3 <= 0) goto L_0x0188
            r4 = r1[r3]
            int r4 = r4.length()
            int r7 = r7 + r4
            int r4 = r2.length()
            int r4 = r4 - r7
            r2.insert(r4, r11)
            int r7 = r7 + r6
            int r3 = r3 + -1
            goto L_0x0173
        L_0x0188:
            java.lang.String r1 = r2.toString()
            goto L_0x0193
        L_0x018d:
            java.lang.String r1 = java.lang.String.valueOf(r1)
            goto L_0x0193
        L_0x0192:
            r1 = r8
        L_0x0193:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.util.format.NumericFormatter.getFormatContents(java.lang.String, double, short):java.lang.String");
    }

    private String delLastZero(String str) {
        int indexOf;
        if (str == null || str.length() <= 1 || str.contains(ExifInterface.LONGITUDE_EAST) || str.charAt(str.length() - 1) != '0' || (indexOf = str.indexOf(46)) <= 0) {
            return str;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length - 1;
        while (length > indexOf && charArray[length] == '0') {
            length--;
        }
        if (charArray[length] == '.') {
            length--;
        }
        return String.valueOf(charArray, 0, length + 1);
    }

    static DecimalFormat createIntegerOnlyFormat(String str) {
        DecimalFormat decimalFormat = new DecimalFormat(str);
        decimalFormat.setParseIntegerOnly(true);
        return decimalFormat;
    }

    private static final class SSNFormat extends Format {
        private static final DecimalFormat df = NumericFormatter.createIntegerOnlyFormat("000000000");
        public static final Format instance = new SSNFormat();

        private SSNFormat() {
        }

        public static String format(Number number) {
            String format = df.format(number);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(format.substring(0, 3));
            stringBuffer.append('-');
            stringBuffer.append(format.substring(3, 5));
            stringBuffer.append('-');
            stringBuffer.append(format.substring(5, 9));
            return stringBuffer.toString();
        }

        public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            stringBuffer.append(format((Number) obj));
            return stringBuffer;
        }

        public Object parseObject(String str, ParsePosition parsePosition) {
            return df.parseObject(str, parsePosition);
        }
    }

    private static final class ZipPlusFourFormat extends Format {
        private static final DecimalFormat df = NumericFormatter.createIntegerOnlyFormat("000000000");
        public static final Format instance = new ZipPlusFourFormat();

        private ZipPlusFourFormat() {
        }

        public static String format(Number number) {
            String format = df.format(number);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(format.substring(0, 5));
            stringBuffer.append('-');
            stringBuffer.append(format.substring(5, 9));
            return stringBuffer.toString();
        }

        public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            stringBuffer.append(format((Number) obj));
            return stringBuffer;
        }

        public Object parseObject(String str, ParsePosition parsePosition) {
            return df.parseObject(str, parsePosition);
        }
    }

    private static final class PhoneFormat extends Format {
        private static final DecimalFormat df = NumericFormatter.createIntegerOnlyFormat("##########");
        public static final Format instance = new PhoneFormat();

        private PhoneFormat() {
        }

        public static String format(Number number) {
            String format = df.format(number);
            StringBuffer stringBuffer = new StringBuffer();
            int length = format.length();
            if (length <= 4) {
                return format;
            }
            int i = length - 4;
            String substring = format.substring(i, length);
            int i2 = length - 7;
            String substring2 = format.substring(Math.max(0, i2), i);
            String substring3 = format.substring(Math.max(0, length - 10), Math.max(0, i2));
            if (substring3 != null && substring3.trim().length() > 0) {
                stringBuffer.append('(');
                stringBuffer.append(substring3);
                stringBuffer.append(") ");
            }
            if (substring2 != null && substring2.trim().length() > 0) {
                stringBuffer.append(substring2);
                stringBuffer.append('-');
            }
            stringBuffer.append(substring);
            return stringBuffer.toString();
        }

        public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            stringBuffer.append(format((Number) obj));
            return stringBuffer;
        }

        public Object parseObject(String str, ParsePosition parsePosition) {
            return df.parseObject(str, parsePosition);
        }
    }

    private static final class ConstantStringFormat extends Format {
        private static final DecimalFormat df = NumericFormatter.createIntegerOnlyFormat("##########");
        private final String str;

        public ConstantStringFormat(String str2) {
            this.str = str2;
        }

        public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            stringBuffer.append(this.str);
            return stringBuffer;
        }

        public Object parseObject(String str2, ParsePosition parsePosition) {
            return df.parseObject(str2, parsePosition);
        }
    }
}
