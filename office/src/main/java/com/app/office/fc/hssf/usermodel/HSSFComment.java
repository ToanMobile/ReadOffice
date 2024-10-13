package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.hssf.record.NoteRecord;
import com.app.office.fc.hssf.record.TextObjectRecord;
import com.app.office.fc.ss.usermodel.Comment;
import com.app.office.fc.ss.usermodel.RichTextString;

public class HSSFComment extends HSSFTextbox implements Comment {
    private String _author;
    private int _col;
    private NoteRecord _note;
    private int _row;
    private TextObjectRecord _txo;
    private boolean _visible;

    public /* bridge */ /* synthetic */ RichTextString getString() {
        return super.getString();
    }

    public HSSFComment(EscherContainerRecord escherContainerRecord, HSSFShape hSSFShape, HSSFAnchor hSSFAnchor) {
        super(escherContainerRecord, hSSFShape, hSSFAnchor);
        setShapeType(25);
        setFillColor(134217808, 255);
        this._visible = false;
        this._author = "";
    }

    protected HSSFComment(NoteRecord noteRecord, TextObjectRecord textObjectRecord) {
        this((EscherContainerRecord) null, (HSSFShape) null, (HSSFAnchor) null);
        this._txo = textObjectRecord;
        this._note = noteRecord;
    }

    public void setVisible(boolean z) {
        NoteRecord noteRecord = this._note;
        if (noteRecord != null) {
            noteRecord.setFlags(z ? (short) 2 : 0);
        }
        this._visible = z;
    }

    public boolean isVisible() {
        return this._visible;
    }

    public int getRow() {
        return this._row;
    }

    public void setRow(int i) {
        NoteRecord noteRecord = this._note;
        if (noteRecord != null) {
            noteRecord.setRow(i);
        }
        this._row = i;
    }

    public int getColumn() {
        return this._col;
    }

    public void setColumn(int i) {
        NoteRecord noteRecord = this._note;
        if (noteRecord != null) {
            noteRecord.setColumn(i);
        }
        this._col = i;
    }

    @Deprecated
    public void setColumn(short s) {
        setColumn((int) s);
    }

    public String getAuthor() {
        return this._author;
    }

    public void setAuthor(String str) {
        NoteRecord noteRecord = this._note;
        if (noteRecord != null) {
            noteRecord.setAuthor(str);
        }
        this._author = str;
    }

    public void setString(RichTextString richTextString) {
        HSSFRichTextString hSSFRichTextString = (HSSFRichTextString) richTextString;
        if (hSSFRichTextString.numFormattingRuns() == 0) {
            hSSFRichTextString.applyFont(0);
        }
        TextObjectRecord textObjectRecord = this._txo;
        if (textObjectRecord != null) {
            textObjectRecord.setStr(hSSFRichTextString);
        }
        super.setString(richTextString);
    }

    /* access modifiers changed from: protected */
    public NoteRecord getNoteRecord() {
        return this._note;
    }

    /* access modifiers changed from: protected */
    public TextObjectRecord getTextObjectRecord() {
        return this._txo;
    }
}
