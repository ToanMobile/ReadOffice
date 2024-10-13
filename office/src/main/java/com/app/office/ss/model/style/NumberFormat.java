package com.app.office.ss.model.style;

public class NumberFormat {
    private String formatCode;
    private short numFmtId;

    public NumberFormat() {
        this.numFmtId = 0;
        this.formatCode = "General";
    }

    public NumberFormat(short s, String str) {
        this.numFmtId = s;
        this.formatCode = str;
    }

    public void setNumberFormatID(short s) {
        this.numFmtId = s;
    }

    public short getNumberFormatID() {
        return this.numFmtId;
    }

    public void setFormatCode(String str) {
        this.formatCode = str;
    }

    public String getFormatCode() {
        return this.formatCode;
    }

    public void dispose() {
        this.formatCode = null;
    }
}
