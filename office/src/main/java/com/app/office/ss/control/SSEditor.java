package com.app.office.ss.control;

import com.app.office.common.shape.IShape;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.animate.IAnimation;
import com.app.office.simpletext.control.IHighlight;
import com.app.office.simpletext.control.IWord;
import com.app.office.simpletext.model.IDocument;
import com.app.office.system.IControl;

public class SSEditor implements IWord {
    private Spreadsheet ss;

    public IDocument getDocument() {
        return null;
    }

    public byte getEditType() {
        return 1;
    }

    public IHighlight getHighlight() {
        return null;
    }

    public IAnimation getParagraphAnimation(int i) {
        return null;
    }

    public String getText(long j, long j2) {
        return "";
    }

    public IShape getTextBox() {
        return null;
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        return null;
    }

    public long viewToModel(int i, int i2, boolean z) {
        return 0;
    }

    public SSEditor(Spreadsheet spreadsheet) {
        this.ss = spreadsheet;
    }

    public IControl getControl() {
        return this.ss.getControl();
    }

    public void dispose() {
        this.ss = null;
    }
}
