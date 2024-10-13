package com.app.office.pg.model;

public class PGPlaceholderUtil {
    public static final String BODY = "body";
    public static final String CHART = "chart";
    public static final String CLIPART = "clipArt";
    public static final String CTRTITLE = "ctrTitle";
    public static final String DIAGRAM = "dgm";
    public static final String DT = "dt";
    public static final String FTR = "ftr";
    public static final String HALF = "half";
    public static final String HEADER = "hdr";
    public static final String MEDIA = "media";
    public static final String OBJECT = "obj";
    public static final String PICTURE = "pic";
    public static final String SLDNUM = "sldNum";
    public static final String SLIDEIMAGE = "sldImg";
    public static final String SUBTITLE = "subTitle";
    public static final String TABLE = "tbl";
    public static final String TITLE = "title";
    private static PGPlaceholderUtil kit = new PGPlaceholderUtil();

    public String processType(String str, String str2) {
        return str2;
    }

    public static PGPlaceholderUtil instance() {
        return kit;
    }

    public boolean isTitle(String str) {
        return "title".equals(str) || CTRTITLE.equals(str);
    }

    public boolean isBody(String str) {
        return !"title".equals(str) && !CTRTITLE.equals(str) && !DT.equals(str) && !FTR.equals(str) && !SLDNUM.equals(str);
    }

    public boolean isTitleOrBody(String str) {
        return "title".equals(str) || CTRTITLE.equals(str) || SUBTITLE.equals(str) || "body".equals(str);
    }

    public boolean isHeaderFooter(String str) {
        return DT.equals(str) || FTR.equals(str) || SLDNUM.equals(str);
    }

    public boolean isDate(String str) {
        return DT.equals(str);
    }

    public boolean isFooter(String str) {
        return FTR.equals(str);
    }

    public boolean isSldNum(String str) {
        return SLDNUM.equals(str);
    }

    public String checkTypeName(String str) {
        return CTRTITLE.equals(str) ? "title" : str;
    }
}
