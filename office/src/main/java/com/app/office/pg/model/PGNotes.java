package com.app.office.pg.model;

public class PGNotes {
    private String notes;

    public PGNotes(String str) {
        this.notes = str;
    }

    public void setNotes(String str) {
        this.notes = str;
    }

    public String getNotes() {
        return this.notes;
    }

    public void dispose() {
        this.notes = null;
    }
}
