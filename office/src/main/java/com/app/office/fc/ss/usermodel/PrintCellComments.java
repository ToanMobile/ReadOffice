package com.app.office.fc.ss.usermodel;

public enum PrintCellComments {
    NONE(1),
    AS_DISPLAYED(2),
    AT_END(3);
    
    private static PrintCellComments[] _table;
    private int comments;

    static {
        int i;
        _table = new PrintCellComments[4];
        for (PrintCellComments printCellComments : values()) {
            _table[printCellComments.getValue()] = printCellComments;
        }
    }

    private PrintCellComments(int i) {
        this.comments = i;
    }

    public int getValue() {
        return this.comments;
    }

    public static PrintCellComments valueOf(int i) {
        return _table[i];
    }
}
