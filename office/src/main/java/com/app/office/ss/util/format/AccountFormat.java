package com.app.office.ss.util.format;

import java.text.DecimalFormat;

public class AccountFormat {
    private static final double ZERO = 1.0E-6d;
    private static AccountFormat af = new AccountFormat();

    private AccountFormat() {
    }

    public static AccountFormat instance() {
        return af;
    }

    public String format(String str, double d) {
        String[] split = str.split(";");
        int length = split.length;
        if (length == 1) {
            return parse(split[0], d, false);
        }
        if (length == 2) {
            return parse(split[0] + ";" + split[1], d, false);
        } else if (length != 3 && length != 4) {
            return "";
        } else {
            if (Math.abs(d) <= ZERO) {
                return parse(split[2], 0.0d, true);
            }
            return parse(split[0] + ";" + split[1], d, false);
        }
    }

    private String parse(String str, double d, boolean z) {
        String[] split = str.split(";");
        int indexOf = str.indexOf("*");
        if (Math.abs(d) >= ZERO || split.length != 1) {
            DecimalFormat decimalFormat = new DecimalFormat(str.replace("*", ""));
            decimalFormat.format(Double.valueOf(d));
            if (d > 0.0d) {
                d += 1.0E-9d;
            } else if (d < 0.0d) {
                d -= 1.0E-9d;
            }
            String format = decimalFormat.format(Double.valueOf(d));
            return format.substring(0, indexOf) + "*" + format.substring(indexOf, format.length());
        }
        String substring = str.substring(0, indexOf + 1);
        int indexOf2 = str.indexOf(45);
        String replace = str.replace("#", "").replace("?", " ");
        return substring + replace.substring(indexOf2 - 1, replace.length());
    }
}
