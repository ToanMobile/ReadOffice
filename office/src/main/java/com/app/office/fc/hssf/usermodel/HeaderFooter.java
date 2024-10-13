package com.app.office.fc.hssf.usermodel;

public abstract class HeaderFooter implements com.app.office.fc.ss.usermodel.HeaderFooter {
    /* access modifiers changed from: protected */
    public abstract String getRawText();

    /* access modifiers changed from: protected */
    public abstract void setHeaderFooterText(String str);

    protected HeaderFooter() {
    }

    private String[] splitParts() {
        String rawText = getRawText();
        String str = "";
        String str2 = str;
        String str3 = str2;
        while (true) {
            if (rawText.length() <= 1) {
                rawText = str2;
                break;
            } else if (rawText.charAt(0) != '&') {
                break;
            } else {
                int length = rawText.length();
                char charAt = rawText.charAt(1);
                if (charAt == 'C') {
                    if (rawText.indexOf("&L") >= 0) {
                        length = Math.min(length, rawText.indexOf("&L"));
                    }
                    if (rawText.indexOf("&R") >= 0) {
                        length = Math.min(length, rawText.indexOf("&R"));
                    }
                    str2 = rawText.substring(2, length);
                    rawText = rawText.substring(length);
                } else if (charAt == 'L') {
                    if (rawText.indexOf("&C") >= 0) {
                        length = Math.min(length, rawText.indexOf("&C"));
                    }
                    if (rawText.indexOf("&R") >= 0) {
                        length = Math.min(length, rawText.indexOf("&R"));
                    }
                    str = rawText.substring(2, length);
                    rawText = rawText.substring(length);
                } else if (charAt != 'R') {
                    break;
                } else {
                    if (rawText.indexOf("&C") >= 0) {
                        length = Math.min(length, rawText.indexOf("&C"));
                    }
                    if (rawText.indexOf("&L") >= 0) {
                        length = Math.min(length, rawText.indexOf("&L"));
                    }
                    str3 = rawText.substring(2, length);
                    rawText = rawText.substring(length);
                }
            }
        }
        return new String[]{str, rawText, str3};
    }

    public final String getLeft() {
        return splitParts()[0];
    }

    public final void setLeft(String str) {
        updatePart(0, str);
    }

    public final String getCenter() {
        return splitParts()[1];
    }

    public final void setCenter(String str) {
        updatePart(1, str);
    }

    public final String getRight() {
        return splitParts()[2];
    }

    public final void setRight(String str) {
        updatePart(2, str);
    }

    private void updatePart(int i, String str) {
        String[] splitParts = splitParts();
        if (str == null) {
            str = "";
        }
        splitParts[i] = str;
        updateHeaderFooterText(splitParts);
    }

    private void updateHeaderFooterText(String[] strArr) {
        String str = strArr[0];
        String str2 = strArr[1];
        String str3 = strArr[2];
        if (str2.length() >= 1 || str.length() >= 1 || str3.length() >= 1) {
            StringBuilder sb = new StringBuilder(64);
            sb.append("&C");
            sb.append(str2);
            sb.append("&L");
            sb.append(str);
            sb.append("&R");
            sb.append(str3);
            setHeaderFooterText(sb.toString());
            return;
        }
        setHeaderFooterText("");
    }

    public static String fontSize(short s) {
        return "&" + s;
    }

    public static String font(String str, String str2) {
        return "&\"" + str + "," + str2 + "\"";
    }

    public static String page() {
        return MarkupTag.PAGE_FIELD.getRepresentation();
    }

    public static String numPages() {
        return MarkupTag.NUM_PAGES_FIELD.getRepresentation();
    }

    public static String date() {
        return MarkupTag.DATE_FIELD.getRepresentation();
    }

    public static String time() {
        return MarkupTag.TIME_FIELD.getRepresentation();
    }

    public static String file() {
        return MarkupTag.FILE_FIELD.getRepresentation();
    }

    public static String tab() {
        return MarkupTag.SHEET_NAME_FIELD.getRepresentation();
    }

    public static String startBold() {
        return MarkupTag.BOLD_FIELD.getRepresentation();
    }

    public static String endBold() {
        return MarkupTag.BOLD_FIELD.getRepresentation();
    }

    public static String startUnderline() {
        return MarkupTag.UNDERLINE_FIELD.getRepresentation();
    }

    public static String endUnderline() {
        return MarkupTag.UNDERLINE_FIELD.getRepresentation();
    }

    public static String startDoubleUnderline() {
        return MarkupTag.DOUBLE_UNDERLINE_FIELD.getRepresentation();
    }

    public static String endDoubleUnderline() {
        return MarkupTag.DOUBLE_UNDERLINE_FIELD.getRepresentation();
    }

    public static String stripFields(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        for (MarkupTag representation : MarkupTag.values()) {
            String representation2 = representation.getRepresentation();
            while (true) {
                int indexOf = str.indexOf(representation2);
                if (indexOf <= -1) {
                    break;
                }
                str = str.substring(0, indexOf) + str.substring(indexOf + representation2.length());
            }
        }
        return str.replaceAll("\\&\\d+", "").replaceAll("\\&\".*?,.*?\"", "");
    }

    private enum MarkupTag {
        SHEET_NAME_FIELD("&A", false),
        DATE_FIELD("&D", false),
        FILE_FIELD("&F", false),
        FULL_FILE_FIELD("&Z", false),
        PAGE_FIELD("&P", false),
        TIME_FIELD("&T", false),
        NUM_PAGES_FIELD("&N", false),
        PICTURE_FIELD("&G", false),
        BOLD_FIELD("&B", true),
        ITALIC_FIELD("&I", true),
        STRIKETHROUGH_FIELD("&S", true),
        SUBSCRIPT_FIELD("&Y", true),
        SUPERSCRIPT_FIELD("&X", true),
        UNDERLINE_FIELD("&U", true),
        DOUBLE_UNDERLINE_FIELD("&E", true);
        
        private final boolean _occursInPairs;
        private final String _representation;

        private MarkupTag(String str, boolean z) {
            this._representation = str;
            this._occursInPairs = z;
        }

        public String getRepresentation() {
            return this._representation;
        }

        public boolean occursPairs() {
            return this._occursInPairs;
        }
    }
}
