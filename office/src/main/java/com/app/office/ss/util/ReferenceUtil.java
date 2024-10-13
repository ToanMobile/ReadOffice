package com.app.office.ss.util;

public class ReferenceUtil {
    private static ReferenceUtil util = new ReferenceUtil();

    public static ReferenceUtil instance() {
        return util;
    }

    public int getColumnIndex(String str) {
        int i = 0;
        while (i < str.length() && (str.charAt(i) < '0' || str.charAt(i) > '9')) {
            i++;
        }
        return HeaderUtil.instance().getColumnHeaderIndexByText(str.substring(0, i));
    }

    public int getRowIndex(String str) {
        int i = 0;
        if (str.indexOf(":") > 0) {
            str = str.substring(0, str.indexOf(":"));
        }
        while (i < str.length() && (str.charAt(i) < '0' || str.charAt(i) > '9')) {
            i++;
        }
        return Integer.parseInt(str.substring(i, str.length())) - 1;
    }
}
