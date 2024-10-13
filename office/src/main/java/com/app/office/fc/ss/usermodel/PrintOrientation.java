package com.app.office.fc.ss.usermodel;

public enum PrintOrientation {
    DEFAULT(1),
    PORTRAIT(2),
    LANDSCAPE(3);
    
    private static PrintOrientation[] _table;
    private int orientation;

    static {
        int i;
        _table = new PrintOrientation[4];
        for (PrintOrientation printOrientation : values()) {
            _table[printOrientation.getValue()] = printOrientation;
        }
    }

    private PrintOrientation(int i) {
        this.orientation = i;
    }

    public int getValue() {
        return this.orientation;
    }

    public static PrintOrientation valueOf(int i) {
        return _table[i];
    }
}
