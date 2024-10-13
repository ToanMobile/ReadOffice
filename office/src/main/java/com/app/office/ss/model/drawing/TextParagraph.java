package com.app.office.ss.model.drawing;

import com.app.office.simpletext.font.Font;
import com.app.office.ss.model.style.Alignment;

public class TextParagraph {
    private Alignment align = new Alignment();
    private Font font;
    private String textRun;

    public void setTextRun(String str) {
        this.textRun = str;
    }

    public String getTextRun() {
        return this.textRun;
    }

    public void setFont(Font font2) {
        this.font = font2;
    }

    public Font getFont() {
        return this.font;
    }

    public void setHorizontalAlign(short s) {
        this.align.setHorizontalAlign(s);
    }

    public short getHorizontalAlign() {
        return this.align.getHorizontalAlign();
    }

    public void setVerticalAlign(short s) {
        this.align.setVerticalAlign(s);
    }

    public short getVerticalAlign() {
        return this.align.getVerticalAlign();
    }

    public void dispose() {
        this.textRun = null;
        this.font = null;
        Alignment alignment = this.align;
        if (alignment != null) {
            alignment.dispose();
            this.align = null;
        }
    }
}
