package com.app.office.fc.hslf.model;

import com.app.office.fc.hslf.record.FontEntityAtom;
import com.app.office.fc.hssf.usermodel.HSSFFont;

public final class PPFont {
    public static final byte ANSI_CHARSET = 0;
    public static final PPFont ARIAL;
    public static final PPFont COURIER_NEW;
    public static final byte DEFAULT_CHARSET = 1;
    public static final byte DEFAULT_PITCH = 0;
    public static final byte FF_DECORATIVE = 80;
    public static final byte FF_DONTCARE = 0;
    public static final byte FF_MODERN = 48;
    public static final byte FF_ROMAN = 16;
    public static final byte FF_SCRIPT = 64;
    public static final byte FF_SWISS = 32;
    public static final byte FIXED_PITCH = 1;
    public static final byte SYMBOL_CHARSET = 2;
    public static final PPFont TIMES_NEW_ROMAN;
    public static final byte VARIABLE_PITCH = 2;
    public static final PPFont WINGDINGS;
    protected int charset;
    protected int flags;
    protected String name;
    protected int pitch;
    protected int type;

    public PPFont() {
    }

    public PPFont(FontEntityAtom fontEntityAtom) {
        this.name = fontEntityAtom.getFontName();
        this.charset = fontEntityAtom.getCharSet();
        this.type = fontEntityAtom.getFontType();
        this.flags = fontEntityAtom.getFontFlags();
        this.pitch = fontEntityAtom.getPitchAndFamily();
    }

    public void setFontName(String str) {
        this.name = str;
    }

    public String getFontName() {
        return this.name;
    }

    public void setCharSet(int i) {
        this.charset = i;
    }

    public int getCharSet() {
        return this.charset;
    }

    public void setFontFlags(int i) {
        this.flags = i;
    }

    public int getFontFlags() {
        return this.flags;
    }

    public void setFontType(int i) {
        this.type = i;
    }

    public int getFontType() {
        return this.type;
    }

    public void setPitchAndFamily(int i) {
        this.pitch = i;
    }

    public int getPitchAndFamily() {
        return this.pitch;
    }

    static {
        PPFont pPFont = new PPFont();
        ARIAL = pPFont;
        pPFont.setFontName(HSSFFont.FONT_ARIAL);
        pPFont.setCharSet(0);
        pPFont.setFontType(4);
        pPFont.setFontFlags(0);
        pPFont.setPitchAndFamily(34);
        PPFont pPFont2 = new PPFont();
        TIMES_NEW_ROMAN = pPFont2;
        pPFont2.setFontName("Times New Roman");
        pPFont2.setCharSet(0);
        pPFont2.setFontType(4);
        pPFont2.setFontFlags(0);
        pPFont2.setPitchAndFamily(18);
        PPFont pPFont3 = new PPFont();
        COURIER_NEW = pPFont3;
        pPFont3.setFontName("Courier New");
        pPFont3.setCharSet(0);
        pPFont3.setFontType(4);
        pPFont3.setFontFlags(0);
        pPFont3.setPitchAndFamily(49);
        PPFont pPFont4 = new PPFont();
        WINGDINGS = pPFont4;
        pPFont4.setFontName("Wingdings");
        pPFont4.setCharSet(2);
        pPFont4.setFontType(4);
        pPFont4.setFontFlags(0);
        pPFont4.setPitchAndFamily(2);
    }
}
