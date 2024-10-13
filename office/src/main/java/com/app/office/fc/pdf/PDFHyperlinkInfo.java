package com.app.office.fc.pdf;

import android.graphics.RectF;

public class PDFHyperlinkInfo extends RectF {
    private int pageNumber;
    private String strURI;

    public PDFHyperlinkInfo(float f, float f2, float f3, float f4, int i, String str) {
        super(f, f2, f3, f4);
        this.pageNumber = i;
        this.strURI = str;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public String getURL() {
        return this.strURI;
    }
}
