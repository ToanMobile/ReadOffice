package com.app.office.fc.ss.usermodel;

public enum FontFamily {
    NOT_APPLICABLE(0),
    ROMAN(1),
    SWISS(2),
    MODERN(3),
    SCRIPT(4),
    DECORATIVE(5);
    
    private static FontFamily[] _table;
    private int family;

    static {
        int i;
        _table = new FontFamily[6];
        for (FontFamily fontFamily : values()) {
            _table[fontFamily.getValue()] = fontFamily;
        }
    }

    private FontFamily(int i) {
        this.family = i;
    }

    public int getValue() {
        return this.family;
    }

    public static FontFamily valueOf(int i) {
        return _table[i];
    }
}
