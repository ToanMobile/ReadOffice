package com.app.office.common.bulletnumber;

import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.pdf.Barcode128;
import com.app.office.simpletext.view.DocAttr;
import com.app.office.thirdpart.emf.EMFConstants;

public class ListKit {
    private static final char[] CN_SIMPLIFIED = {38646, 22777, 36144, 21441, 32902, 20237, 38470, 26578, 25420, 29590};
    private static final char[] CN_SIMPLIFIED_SERIES = {25342, 20336, 20191, 33836};
    private static final char[] CN_THOUSAND = {12295, 19968, 20108, 19977, 22235, 20116, 20845, 19971, 20843, 20061, 21313};
    private static final char[] CN_THOUSAND_SERIES = {21313, 30334, 21315, 19975};
    private static final char[] ENGLISH_LETTERS = {'a', 'b', Barcode128.CODE_AB_TO_C, Barcode128.CODE_AC_TO_B, Barcode128.CODE_BC_TO_A, Barcode128.FNC1_INDEX, Barcode128.START_A, Barcode128.START_B, Barcode128.START_C, 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final String[] ROMAN_LETTERS = {"m", "cm", "d", "cd", "c", "xc", "l", "xl", "x", "ix", "v", "iv", "i"};
    private static final int[] ROMAN_VALUES = {1000, 900, 500, EMFConstants.FW_NORMAL, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] TRADITIONAL = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    private static final String[] ZODIAC = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    private static ListKit kit = new ListKit();

    public static ListKit instance() {
        return kit;
    }

    public String getNumberStr(int i, int i2) {
        if (i2 == 22) {
            StringBuilder sb = new StringBuilder();
            sb.append(i < 10 ? "0" : "");
            sb.append(String.valueOf(i));
            return sb.toString();
        } else if (i2 == 30) {
            return i <= 10 ? TRADITIONAL[i - 1] : String.valueOf(i);
        } else {
            if (i2 == 31) {
                return i <= 12 ? ZODIAC[i - 1] : String.valueOf(i);
            }
            if (i2 == 38) {
                return getChineseLegalSimplified(i);
            }
            if (i2 == 39) {
                return getChineseCountingThousand(i);
            }
            switch (i2) {
                case 1:
                    return getRoman(i).toUpperCase();
                case 2:
                    return getRoman(i);
                case 3:
                    return getLetters(i).toUpperCase();
                case 4:
                    return getLetters(i);
                case 5:
                    return getOrdinal(i);
                case 6:
                    return getCardinalText(i);
                default:
                    return String.valueOf(i);
            }
        }
    }

    public String getLetters(int i) {
        if (i <= 0 || i > 780) {
            return String.valueOf(ENGLISH_LETTERS[0]);
        }
        int i2 = 26;
        if (i <= 26) {
            return String.valueOf(ENGLISH_LETTERS[i - 1]);
        }
        StringBuilder sb = new StringBuilder();
        int i3 = i / 26;
        int i4 = i % 26;
        if (i4 != 0) {
            i2 = i4;
        }
        for (int i5 = 0; i5 < i3; i5++) {
            sb.append(ENGLISH_LETTERS[i2 - 1]);
        }
        return sb.toString();
    }

    public String getRoman(int i) {
        if (i <= 0) {
            String[] strArr = ROMAN_LETTERS;
            return strArr[strArr.length - 1];
        }
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (true) {
            String[] strArr2 = ROMAN_LETTERS;
            if (i2 >= strArr2.length) {
                return sb.toString();
            }
            String str = strArr2[i2];
            int i3 = ROMAN_VALUES[i2];
            while (i >= i3) {
                i -= i3;
                sb.append(str);
            }
            i2++;
        }
    }

    public String getChineseLegalSimplified(int i) {
        if (i <= 0 || i > 99999) {
            return String.valueOf(CN_SIMPLIFIED[0]);
        }
        if (i <= 9) {
            return String.valueOf(CN_SIMPLIFIED[i]);
        }
        StringBuilder sb = new StringBuilder();
        String valueOf = String.valueOf(i);
        int length = valueOf.length();
        boolean z = false;
        for (int i2 = 0; i2 < length; i2++) {
            int charAt = valueOf.charAt(i2) - '0';
            if (charAt > 0) {
                sb.append(CN_SIMPLIFIED[charAt]);
                int i3 = (length - i2) - 2;
                if (i3 >= 0) {
                    sb.append(CN_SIMPLIFIED_SERIES[i3]);
                }
                z = true;
            } else if (z && i2 != length - 1) {
                sb.append(CN_SIMPLIFIED[0]);
                z = false;
            }
        }
        if (sb.charAt(sb.length() - 1) == CN_SIMPLIFIED[0]) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public String getChineseCountingThousand(int i) {
        if (i <= 0 || i > 99999) {
            return String.valueOf(CN_THOUSAND[0]);
        }
        if (i <= 9) {
            return String.valueOf(CN_THOUSAND[i]);
        }
        StringBuilder sb = new StringBuilder();
        String valueOf = String.valueOf(i);
        int length = valueOf.length();
        boolean z = false;
        for (int i2 = 0; i2 < length; i2++) {
            int charAt = valueOf.charAt(i2) - '0';
            if (charAt > 0) {
                sb.append(CN_THOUSAND[charAt]);
                int i3 = (length - i2) - 2;
                if (i3 >= 0) {
                    sb.append(CN_THOUSAND_SERIES[i3]);
                }
                z = true;
            } else if (z && i2 != length - 1) {
                sb.append(CN_THOUSAND[0]);
                z = false;
            }
        }
        char charAt2 = sb.charAt(sb.length() - 1);
        char[] cArr = CN_THOUSAND;
        if (charAt2 == cArr[0]) {
            sb.deleteCharAt(sb.length() - 1);
        }
        if (i > 10 && i < 20 && sb.charAt(0) == cArr[1]) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    public String getOrdinal(int i) {
        int i2 = i % 10;
        String str = i2 == 1 ? "st" : i2 == 2 ? "nd" : i2 == 3 ? "rd" : HtmlTags.TH;
        return String.valueOf(i) + str;
    }

    public String getBulletText(ListData listData, ListLevel listLevel, DocAttr docAttr, int i) {
        if (listLevel.getNumberText() == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (char c : listLevel.getNumberText()) {
            if (c < 0 || c >= 9) {
                stringBuffer.append(c);
            } else {
                ListLevel level = listData.getLevel(c);
                int startAt = level.getStartAt() + (docAttr.rootType == 1 ? level.getNormalParaCount() : level.getParaCount());
                if (c < i && startAt > level.getStartAt()) {
                    startAt--;
                }
                stringBuffer.append(getNumberStr(startAt, level.getNumberFormat()));
            }
        }
        if (listLevel.getFollowChar() == 1) {
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }

    public String getCardinalText(int i) {
        String reverseString = reverseString(String.valueOf(i));
        String[] strArr = new String[5];
        int length = reverseString.length() % 3;
        if (length == 1) {
            reverseString = reverseString + "00";
        } else if (length == 2) {
            reverseString = reverseString + "0";
        }
        String str = "";
        for (int i2 = 0; i2 <= (reverseString.length() / 3) - 1; i2++) {
            int i3 = i2 * 3;
            strArr[i2] = reverseString(reverseString.substring(i3, i3 + 3));
            if (strArr[i2].equals("000")) {
                str = w3(strArr[i2]) + str;
            } else if (i2 != 0) {
                str = w3(strArr[i2]) + " " + dw(String.valueOf(i2)) + " " + str;
            } else {
                str = w3(strArr[i2]);
            }
        }
        return toUpperCaseFirstOne(str);
    }

    private String reverseString(String str) {
        int length = str.length();
        String[] strArr = new String[str.length()];
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            strArr[i] = str.substring(i, i2);
            i = i2;
        }
        String str2 = "";
        for (int i3 = length - 1; i3 >= 0; i3--) {
            str2 = str2 + strArr[i3];
        }
        return str2;
    }

    private String zr4(String str) {
        return new String[]{"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"}[Integer.parseInt(str.substring(0, 1))];
    }

    private String zr3(String str) {
        return new String[]{"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"}[Integer.parseInt(str.substring(2, 3))];
    }

    private String zr2(String str) {
        String[] strArr = new String[20];
        strArr[10] = "ten";
        strArr[11] = "eleven";
        strArr[12] = "twelve";
        strArr[13] = "thirteen";
        strArr[14] = "fourteen";
        strArr[15] = "fifteen";
        strArr[16] = "sixteen";
        strArr[17] = "seventeen";
        strArr[18] = "eighteen";
        strArr[19] = "nineteen";
        return strArr[Integer.parseInt(str.substring(1, 3))];
    }

    private String zr1(String str) {
        String[] strArr = new String[10];
        strArr[1] = "ten";
        strArr[2] = "twenty";
        strArr[3] = "thirty";
        strArr[4] = "forty";
        strArr[5] = "fifty";
        strArr[6] = "sixty";
        strArr[7] = "seventy";
        strArr[8] = "eighty";
        strArr[9] = "ninety";
        return strArr[Integer.parseInt(str.substring(1, 2))];
    }

    private String dw(String str) {
        String[] strArr = new String[5];
        strArr[0] = "";
        strArr[1] = "thousand";
        strArr[2] = "million";
        strArr[3] = "billion";
        return strArr[Integer.parseInt(str)];
    }

    private String w2(String str) {
        if (str.substring(1, 2).equals("0")) {
            return zr3(str);
        }
        if (str.substring(1, 2).equals("1")) {
            return zr2(str);
        }
        if (str.substring(2, 3).equals("0")) {
            return zr1(str);
        }
        return zr1(str) + "-" + zr3(str);
    }

    private String w3(String str) {
        if (str.substring(0, 1).equals("0")) {
            return w2(str);
        }
        if (str.substring(1, 3).equals("00")) {
            return zr4(str) + " hundred";
        }
        return zr4(str) + " hundred " + w2(str);
    }

    public String toUpperCaseFirstOne(String str) {
        if (str.equals("")) {
            return String.valueOf(0);
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
