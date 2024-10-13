package com.app.office.fc.ss.usermodel;

import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExcelStyleDateFormatter extends SimpleDateFormat {
    public static final char HH_BRACKET_SYMBOL = '';
    public static final char H_BRACKET_SYMBOL = '';
    public static final char LL_BRACKET_SYMBOL = '';
    public static final char L_BRACKET_SYMBOL = '';
    public static final char MMMMM_START_SYMBOL = '';
    public static final char MMMMM_TRUNCATE_SYMBOL = '';
    public static final char MM_BRACKET_SYMBOL = '';
    public static final char M_BRACKET_SYMBOL = '';
    public static final char SS_BRACKET_SYMBOL = '';
    public static final char S_BRACKET_SYMBOL = '';
    private double dateToBeFormatted;
    private DecimalFormat format1digit = new DecimalFormat("0");
    private DecimalFormat format2digits = new DecimalFormat("00");
    private DecimalFormat format3digit = new DecimalFormat("0");
    private DecimalFormat format4digits = new DecimalFormat("00");

    public ExcelStyleDateFormatter() {
        DataFormatter.setExcelStyleRoundingMode(this.format1digit, RoundingMode.DOWN);
        DataFormatter.setExcelStyleRoundingMode(this.format2digits, RoundingMode.DOWN);
        DataFormatter.setExcelStyleRoundingMode(this.format3digit);
        DataFormatter.setExcelStyleRoundingMode(this.format4digits);
        this.dateToBeFormatted = 0.0d;
    }

    public ExcelStyleDateFormatter(String str) {
        super(processFormatPattern(str));
        DataFormatter.setExcelStyleRoundingMode(this.format1digit, RoundingMode.DOWN);
        DataFormatter.setExcelStyleRoundingMode(this.format2digits, RoundingMode.DOWN);
        DataFormatter.setExcelStyleRoundingMode(this.format3digit);
        DataFormatter.setExcelStyleRoundingMode(this.format4digits);
        this.dateToBeFormatted = 0.0d;
    }

    public ExcelStyleDateFormatter(String str, DateFormatSymbols dateFormatSymbols) {
        super(processFormatPattern(str), dateFormatSymbols);
        DataFormatter.setExcelStyleRoundingMode(this.format1digit, RoundingMode.DOWN);
        DataFormatter.setExcelStyleRoundingMode(this.format2digits, RoundingMode.DOWN);
        DataFormatter.setExcelStyleRoundingMode(this.format3digit);
        DataFormatter.setExcelStyleRoundingMode(this.format4digits);
        this.dateToBeFormatted = 0.0d;
    }

    public ExcelStyleDateFormatter(String str, Locale locale) {
        super(processFormatPattern(str), locale);
        DataFormatter.setExcelStyleRoundingMode(this.format1digit, RoundingMode.DOWN);
        DataFormatter.setExcelStyleRoundingMode(this.format2digits, RoundingMode.DOWN);
        DataFormatter.setExcelStyleRoundingMode(this.format3digit);
        DataFormatter.setExcelStyleRoundingMode(this.format4digits);
        this.dateToBeFormatted = 0.0d;
    }

    private static String processFormatPattern(String str) {
        return str.replaceAll("MMMMM", "MMM").replaceAll("\\[H\\]", String.valueOf(H_BRACKET_SYMBOL)).replaceAll("\\[HH\\]", String.valueOf(HH_BRACKET_SYMBOL)).replaceAll("\\[m\\]", String.valueOf(M_BRACKET_SYMBOL)).replaceAll("\\[mm\\]", String.valueOf(MM_BRACKET_SYMBOL)).replaceAll("\\[s\\]", String.valueOf(S_BRACKET_SYMBOL)).replaceAll("\\[ss\\]", String.valueOf(SS_BRACKET_SYMBOL)).replaceAll("s.000", "s.S").replaceAll("s.00", "s.").replaceAll("s.0", "s.");
    }

    public void setDateToBeFormatted(double d) {
        this.dateToBeFormatted = d;
    }

    public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        String stringBuffer2 = super.format(date, stringBuffer, fieldPosition).toString();
        if (stringBuffer2.indexOf(57345) != -1) {
            stringBuffer2 = stringBuffer2.replaceAll("(\\w)\\w+", "$1");
        }
        if (!(stringBuffer2.indexOf(57360) == -1 && stringBuffer2.indexOf(57361) == -1)) {
            double d = (double) (((float) this.dateToBeFormatted) * 24.0f);
            stringBuffer2 = stringBuffer2.replaceAll(String.valueOf(H_BRACKET_SYMBOL), this.format1digit.format(d)).replaceAll(String.valueOf(HH_BRACKET_SYMBOL), this.format2digits.format(d));
        }
        if (!(stringBuffer2.indexOf(57362) == -1 && stringBuffer2.indexOf(57363) == -1)) {
            double d2 = (double) (((float) this.dateToBeFormatted) * 24.0f * 60.0f);
            stringBuffer2 = stringBuffer2.replaceAll(String.valueOf(M_BRACKET_SYMBOL), this.format1digit.format(d2)).replaceAll(String.valueOf(MM_BRACKET_SYMBOL), this.format2digits.format(d2));
        }
        if (!(stringBuffer2.indexOf(57364) == -1 && stringBuffer2.indexOf(57365) == -1)) {
            double d3 = (double) ((float) (this.dateToBeFormatted * 24.0d * 60.0d * 60.0d));
            stringBuffer2 = stringBuffer2.replaceAll(String.valueOf(S_BRACKET_SYMBOL), this.format1digit.format(d3)).replaceAll(String.valueOf(SS_BRACKET_SYMBOL), this.format2digits.format(d3));
        }
        if (!(stringBuffer2.indexOf(57366) == -1 && stringBuffer2.indexOf(57367) == -1)) {
            double d4 = this.dateToBeFormatted;
            float floor = (float) ((d4 - Math.floor(d4)) * 24.0d * 60.0d * 60.0d);
            float f = floor - ((float) ((int) floor));
            stringBuffer2 = stringBuffer2.replaceAll(String.valueOf(L_BRACKET_SYMBOL), this.format3digit.format((double) (10.0f * f))).replaceAll(String.valueOf(LL_BRACKET_SYMBOL), this.format4digits.format((double) (f * 100.0f)));
        }
        return new StringBuffer(stringBuffer2);
    }
}
