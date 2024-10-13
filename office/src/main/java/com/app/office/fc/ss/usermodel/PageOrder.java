package com.app.office.fc.ss.usermodel;

public enum PageOrder {
    DOWN_THEN_OVER(1),
    OVER_THEN_DOWN(2);
    
    private static PageOrder[] _table;
    private int order;

    static {
        int i;
        _table = new PageOrder[3];
        for (PageOrder pageOrder : values()) {
            _table[pageOrder.getValue()] = pageOrder;
        }
    }

    private PageOrder(int i) {
        this.order = i;
    }

    public int getValue() {
        return this.order;
    }

    public static PageOrder valueOf(int i) {
        return _table[i];
    }
}
