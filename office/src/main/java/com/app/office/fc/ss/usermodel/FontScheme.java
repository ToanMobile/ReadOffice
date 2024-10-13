package com.app.office.fc.ss.usermodel;

public enum FontScheme {
    NONE(1),
    MAJOR(2),
    MINOR(3);
    
    private static FontScheme[] _table;
    private int value;

    static {
        int i;
        _table = new FontScheme[4];
        for (FontScheme fontScheme : values()) {
            _table[fontScheme.getValue()] = fontScheme;
        }
    }

    private FontScheme(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static FontScheme valueOf(int i) {
        return _table[i];
    }
}
