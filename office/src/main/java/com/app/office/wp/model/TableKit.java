package com.app.office.wp.model;

public class TableKit {
    private static TableKit kit = new TableKit();

    public static TableKit instance() {
        return kit;
    }
}
