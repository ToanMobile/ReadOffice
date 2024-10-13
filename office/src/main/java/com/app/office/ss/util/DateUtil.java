package com.app.office.ss.util;

import com.app.office.fc.ss.usermodel.ICell;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.style.CellStyle;
import com.app.office.thirdpart.emf.EMFConstants;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class DateUtil {
    private static final int BAD_DATE = -1;
    private static final long DAY_MILLISECONDS = 86400000;
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final Pattern TIME_SEPARATOR_PATTERN = Pattern.compile(":");
    private static final Pattern date_ptrn1 = Pattern.compile("^\\[\\$\\-.*?\\]");
    private static final Pattern date_ptrn2 = Pattern.compile("^\\[[a-zA-Z]+\\]");
    private static final Pattern date_ptrn3 = Pattern.compile("^[\\[\\]yYmMdDhHsS\\-/,. :\"\\\\]+0*[ampAMP/]*$");
    private static final Pattern date_ptrn4 = Pattern.compile("^\\[([hH]+|[mM]+|[sS]+)\\]");

    public static boolean isInternalDateFormat(int i) {
        switch (i) {
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                return true;
            default:
                switch (i) {
                    case 45:
                    case 46:
                    case 47:
                        return true;
                    default:
                        return false;
                }
        }
    }

    public static boolean isValidExcelDate(double d) {
        return d > -4.9E-324d;
    }

    protected DateUtil() {
    }

    public static double getExcelDate(Date date) {
        return getExcelDate(date, false);
    }

    public static double getExcelDate(Date date, boolean z) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return internalGetExcelDate(gregorianCalendar, z);
    }

    public static double getExcelDate(Calendar calendar, boolean z) {
        return internalGetExcelDate((Calendar) calendar.clone(), z);
    }

    private static double internalGetExcelDate(Calendar calendar, boolean z) {
        if (!z && calendar.get(1) < 1900) {
            return -1.0d;
        }
        if (z && calendar.get(1) < 1904) {
            return -1.0d;
        }
        double absoluteDay = (((double) ((((((calendar.get(11) * 60) + calendar.get(12)) * 60) + calendar.get(13)) * 1000) + calendar.get(14))) / 8.64E7d) + ((double) absoluteDay(dayStart(calendar), z));
        if (z || absoluteDay < 60.0d) {
            return z ? absoluteDay - 1.0d : absoluteDay;
        }
        return absoluteDay + 1.0d;
    }

    public static Date getJavaDate(double d) {
        return getJavaDate(d, false);
    }

    public static Date getJavaDate(double d, boolean z) {
        if (!isValidExcelDate(d)) {
            return null;
        }
        int floor = (int) Math.floor(d);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        setCalendar(gregorianCalendar, floor, (int) (((d - ((double) floor)) * 8.64E7d) + 0.5d), z);
        return gregorianCalendar.getTime();
    }

    public static void setCalendar(Calendar calendar, int i, int i2, boolean z) {
        int i3;
        int i4;
        if (z) {
            i3 = 1;
            i4 = 1904;
        } else {
            i3 = i < 61 ? 0 : -1;
            i4 = 1900;
        }
        calendar.set(i4, 0, i + i3, 0, 0, 0);
        calendar.set(14, i2);
    }

    public static boolean isADateFormat(int i, String str) {
        if (isInternalDateFormat(i)) {
            return true;
        }
        if (str == null || str.length() == 0) {
            return false;
        }
        StringBuilder sb = new StringBuilder(str.length());
        int i2 = 0;
        while (i2 < str.length()) {
            char charAt = str.charAt(i2);
            if (i2 < str.length() - 1) {
                int i3 = i2 + 1;
                char charAt2 = str.charAt(i3);
                if (charAt == '\\') {
                    if (!(charAt2 == ' ' || charAt2 == '\\')) {
                        switch (charAt2) {
                            case ',':
                            case '-':
                            case '.':
                                break;
                        }
                    }
                    i2++;
                } else if (charAt == ';' && charAt2 == '@') {
                    i2 = i3;
                    i2++;
                }
            }
            sb.append(charAt);
            i2++;
        }
        String sb2 = sb.toString();
        if (date_ptrn4.matcher(sb2).matches()) {
            return true;
        }
        String replaceAll = date_ptrn2.matcher(date_ptrn1.matcher(sb2).replaceAll("")).replaceAll("");
        if (replaceAll.indexOf(59) > 0 && replaceAll.indexOf(59) < replaceAll.length() - 1) {
            replaceAll = replaceAll.substring(0, replaceAll.indexOf(59));
        }
        return date_ptrn3.matcher(replaceAll).matches();
    }

    public static boolean isCellDateFormatted(Cell cell) {
        CellStyle cellStyle;
        if (cell == null || !isValidExcelDate(cell.getNumberValue()) || (cellStyle = cell.getCellStyle()) == null) {
            return false;
        }
        return isADateFormat(cellStyle.getNumberFormatID(), cellStyle.getFormatCode());
    }

    public static boolean isCellInternalDateFormatted(ICell iCell) {
        if (iCell != null && isValidExcelDate(iCell.getNumericCellValue())) {
            return isInternalDateFormat(iCell.getCellStyle().getDataFormat());
        }
        return false;
    }

    protected static int absoluteDay(Calendar calendar, boolean z) {
        return calendar.get(6) + daysInPriorYears(calendar.get(1), z);
    }

    private static int daysInPriorYears(int i, boolean z) {
        int i2 = 1900;
        if ((z || i >= 1900) && (!z || i >= 1900)) {
            int i3 = i - 1;
            int i4 = (((i3 / 4) - (i3 / 100)) + (i3 / EMFConstants.FW_NORMAL)) - 460;
            if (z) {
                i2 = 1904;
            }
            return ((i - i2) * 365) + i4;
        }
        throw new IllegalArgumentException("'year' must be 1900 or greater");
    }

    private static Calendar dayStart(Calendar calendar) {
        calendar.get(11);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.get(11);
        return calendar;
    }

    private static final class FormatException extends Exception {
        public FormatException(String str) {
            super(str);
        }
    }

    public static double convertTime(String str) {
        try {
            return convertTimeInternal(str);
        } catch (FormatException e) {
            throw new IllegalArgumentException("Bad time format '" + str + "' expected 'HH:MM' or 'HH:MM:SS' - " + e.getMessage());
        }
    }

    private static double convertTimeInternal(String str) throws FormatException {
        String str2;
        int length = str.length();
        if (length < 4 || length > 8) {
            throw new FormatException("Bad length");
        }
        String[] split = TIME_SEPARATOR_PATTERN.split(str);
        int length2 = split.length;
        if (length2 == 2) {
            str2 = "00";
        } else if (length2 == 3) {
            str2 = split[2];
        } else {
            throw new FormatException("Expected 2 or 3 fields but got (" + split.length + ")");
        }
        String str3 = split[0];
        String str4 = split[1];
        int parseInt = parseInt(str3, "hour", 24);
        return ((double) (parseInt(str2, "second", 60) + ((parseInt(str4, "minute", 60) + (parseInt * 60)) * 60))) / 86400.0d;
    }

    public static Date parseYYYYMMDDDate(String str) {
        try {
            return parseYYYYMMDDDateInternal(str);
        } catch (FormatException e) {
            throw new IllegalArgumentException("Bad time format " + str + " expected 'YYYY/MM/DD' - " + e.getMessage());
        }
    }

    private static Date parseYYYYMMDDDateInternal(String str) throws FormatException {
        if (str.length() == 10) {
            String substring = str.substring(0, 4);
            String substring2 = str.substring(5, 7);
            String substring3 = str.substring(8, 10);
            GregorianCalendar gregorianCalendar = new GregorianCalendar(parseInt(substring, "year", -32768, 32767), parseInt(substring2, "month", 1, 12) - 1, parseInt(substring3, "day", 1, 31), 0, 0, 0);
            gregorianCalendar.set(14, 0);
            return gregorianCalendar.getTime();
        }
        throw new FormatException("Bad length");
    }

    private static int parseInt(String str, String str2, int i) throws FormatException {
        return parseInt(str, str2, 0, i - 1);
    }

    private static int parseInt(String str, String str2, int i, int i2) throws FormatException {
        try {
            int parseInt = Integer.parseInt(str);
            if (parseInt >= i && parseInt <= i2) {
                return parseInt;
            }
            throw new FormatException(str2 + " value (" + parseInt + ") is outside the allowable range(0.." + i2 + ")");
        } catch (NumberFormatException unused) {
            throw new FormatException("Bad int format '" + str + "' for " + str2 + " field");
        }
    }
}
