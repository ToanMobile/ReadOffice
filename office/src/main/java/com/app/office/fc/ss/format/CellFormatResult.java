package com.app.office.fc.ss.format;

public class CellFormatResult {
    public final boolean applies;
    public final String text;
    public final int textColor;

    public CellFormatResult(boolean z, String str, int i) {
        this.applies = z;
        this.text = str;
        this.textColor = !z ? -1 : i;
    }
}
