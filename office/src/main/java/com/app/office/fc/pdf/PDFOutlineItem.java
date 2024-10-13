package com.app.office.fc.pdf;

public class PDFOutlineItem {
    private final int level;
    private final int pageNumber;
    private final String title;

    public PDFOutlineItem(int i, String str, int i2) {
        this.level = i;
        this.title = str;
        this.pageNumber = i2;
    }

    public int getLevel() {
        return this.level;
    }

    public String getTitle() {
        return this.title;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }
}
