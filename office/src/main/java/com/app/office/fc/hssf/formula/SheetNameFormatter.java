package com.app.office.fc.hssf.formula;

import com.app.office.fc.ss.SpreadsheetVersion;
import com.app.office.fc.ss.util.CellReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SheetNameFormatter {
    private static final Pattern CELL_REF_PATTERN = Pattern.compile("([A-Za-z]+)([0-9]+)");
    private static final char DELIMITER = '\'';

    private SheetNameFormatter() {
    }

    public static String format(String str) {
        StringBuffer stringBuffer = new StringBuffer(str.length() + 2);
        appendFormat(stringBuffer, str);
        return stringBuffer.toString();
    }

    public static void appendFormat(StringBuffer stringBuffer, String str) {
        if (needsDelimiting(str)) {
            stringBuffer.append(DELIMITER);
            appendAndEscape(stringBuffer, str);
            stringBuffer.append(DELIMITER);
            return;
        }
        stringBuffer.append(str);
    }

    public static void appendFormat(StringBuffer stringBuffer, String str, String str2) {
        if (needsDelimiting(str) || needsDelimiting(str2)) {
            stringBuffer.append(DELIMITER);
            stringBuffer.append('[');
            appendAndEscape(stringBuffer, str.replace('[', '(').replace(']', ')'));
            stringBuffer.append(']');
            appendAndEscape(stringBuffer, str2);
            stringBuffer.append(DELIMITER);
            return;
        }
        stringBuffer.append('[');
        stringBuffer.append(str);
        stringBuffer.append(']');
        stringBuffer.append(str2);
    }

    private static void appendAndEscape(StringBuffer stringBuffer, String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == '\'') {
                stringBuffer.append(DELIMITER);
            }
            stringBuffer.append(charAt);
        }
    }

    private static boolean needsDelimiting(String str) {
        int length = str.length();
        if (length < 1) {
            throw new RuntimeException("Zero length string is an invalid sheet name");
        } else if (Character.isDigit(str.charAt(0))) {
            return true;
        } else {
            for (int i = 0; i < length; i++) {
                if (isSpecialChar(str.charAt(i))) {
                    return true;
                }
            }
            return (Character.isLetter(str.charAt(0)) && Character.isDigit(str.charAt(length - 1)) && nameLooksLikePlainCellReference(str)) || nameLooksLikeBooleanLiteral(str);
        }
    }

    private static boolean nameLooksLikeBooleanLiteral(String str) {
        char charAt = str.charAt(0);
        if (charAt != 'F') {
            if (charAt != 'T') {
                if (charAt != 'f') {
                    if (charAt != 't') {
                        return false;
                    }
                }
            }
            return "TRUE".equalsIgnoreCase(str);
        }
        return "FALSE".equalsIgnoreCase(str);
    }

    static boolean isSpecialChar(char c) {
        if (Character.isLetterOrDigit(c)) {
            return false;
        }
        if (c != 9 && c != 10 && c != 13) {
            return (c == '.' || c == '_') ? false : true;
        }
        throw new RuntimeException("Illegal character (0x" + Integer.toHexString(c) + ") found in sheet name");
    }

    static boolean cellReferenceIsWithinRange(String str, String str2) {
        return CellReference.cellReferenceIsWithinRange(str, str2, SpreadsheetVersion.EXCEL97);
    }

    static boolean nameLooksLikePlainCellReference(String str) {
        Matcher matcher = CELL_REF_PATTERN.matcher(str);
        if (!matcher.matches()) {
            return false;
        }
        return cellReferenceIsWithinRange(matcher.group(1), matcher.group(2));
    }
}
