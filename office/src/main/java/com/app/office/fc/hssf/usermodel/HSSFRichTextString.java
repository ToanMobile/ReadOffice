package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.model.InternalWorkbook;
import com.app.office.fc.hssf.record.LabelSSTRecord;
import com.app.office.fc.hssf.record.common.UnicodeString;
import com.app.office.fc.ss.usermodel.IFont;
import com.app.office.fc.ss.usermodel.RichTextString;
import java.util.Iterator;

public final class HSSFRichTextString implements Comparable<HSSFRichTextString>, RichTextString {
    public static final short NO_FONT = 0;
    private InternalWorkbook _book;
    private LabelSSTRecord _record;
    private UnicodeString _string;

    public HSSFRichTextString() {
        this("");
    }

    public HSSFRichTextString(String str) {
        if (str == null) {
            this._string = new UnicodeString("");
        } else {
            this._string = new UnicodeString(str);
        }
    }

    HSSFRichTextString(InternalWorkbook internalWorkbook, LabelSSTRecord labelSSTRecord) {
        setWorkbookReferences(internalWorkbook, labelSSTRecord);
        this._string = internalWorkbook.getSSTString(labelSSTRecord.getSSTIndex());
    }

    /* access modifiers changed from: package-private */
    public void setWorkbookReferences(InternalWorkbook internalWorkbook, LabelSSTRecord labelSSTRecord) {
        this._book = internalWorkbook;
        this._record = labelSSTRecord;
    }

    private UnicodeString cloneStringIfRequired() {
        if (this._book == null) {
            return this._string;
        }
        return (UnicodeString) this._string.clone();
    }

    private void addToSSTIfRequired() {
        InternalWorkbook internalWorkbook = this._book;
        if (internalWorkbook != null) {
            int addSSTString = internalWorkbook.addSSTString(this._string);
            this._record.setSSTIndex(addSSTString);
            this._string = this._book.getSSTString(addSSTString);
        }
    }

    public void applyFont(int i, int i2, short s) {
        if (i > i2) {
            throw new IllegalArgumentException("Start index must be less than end index.");
        } else if (i < 0 || i2 > length()) {
            throw new IllegalArgumentException("Start and end index not in range.");
        } else if (i != i2) {
            short s2 = 0;
            if (i2 != length()) {
                s2 = getFontAtIndex(i2);
            }
            UnicodeString cloneStringIfRequired = cloneStringIfRequired();
            this._string = cloneStringIfRequired;
            Iterator<UnicodeString.FormatRun> formatIterator = cloneStringIfRequired.formatIterator();
            if (formatIterator != null) {
                while (formatIterator.hasNext()) {
                    UnicodeString.FormatRun next = formatIterator.next();
                    if (next.getCharacterPos() >= i && next.getCharacterPos() < i2) {
                        formatIterator.remove();
                    }
                }
            }
            this._string.addFormatRun(new UnicodeString.FormatRun((short) i, s));
            if (i2 != length()) {
                this._string.addFormatRun(new UnicodeString.FormatRun((short) i2, s2));
            }
            addToSSTIfRequired();
        }
    }

    public void applyFont(int i, int i2, IFont iFont) {
        applyFont(i, i2, ((HSSFFont) iFont).getIndex());
    }

    public void applyFont(IFont iFont) {
        applyFont(0, this._string.getCharCount(), iFont);
    }

    public void clearFormatting() {
        UnicodeString cloneStringIfRequired = cloneStringIfRequired();
        this._string = cloneStringIfRequired;
        cloneStringIfRequired.clearFormatting();
        addToSSTIfRequired();
    }

    public int getSSTIndex() {
        return this._record.getSSTIndex();
    }

    public String getString() {
        return this._string.getString();
    }

    public UnicodeString getUnicodeString() {
        return cloneStringIfRequired();
    }

    /* access modifiers changed from: package-private */
    public UnicodeString getRawUnicodeString() {
        return this._string;
    }

    /* access modifiers changed from: package-private */
    public void setUnicodeString(UnicodeString unicodeString) {
        this._string = unicodeString;
    }

    public int length() {
        return this._string.getCharCount();
    }

    public short getFontAtIndex(int i) {
        int formatRunCount = this._string.getFormatRunCount();
        UnicodeString.FormatRun formatRun = null;
        int i2 = 0;
        while (i2 < formatRunCount) {
            UnicodeString.FormatRun formatRun2 = this._string.getFormatRun(i2);
            if (formatRun2.getCharacterPos() > i) {
                break;
            }
            i2++;
            formatRun = formatRun2;
        }
        if (formatRun == null) {
            return 0;
        }
        return formatRun.getFontIndex();
    }

    public int numFormattingRuns() {
        return this._string.getFormatRunCount();
    }

    public int getIndexOfFormattingRun(int i) {
        return this._string.getFormatRun(i).getCharacterPos();
    }

    public short getFontOfFormattingRun(int i) {
        return this._string.getFormatRun(i).getFontIndex();
    }

    public int compareTo(HSSFRichTextString hSSFRichTextString) {
        return this._string.compareTo(hSSFRichTextString._string);
    }

    public boolean equals(Object obj) {
        if (obj instanceof HSSFRichTextString) {
            return this._string.equals(((HSSFRichTextString) obj)._string);
        }
        return false;
    }

    public String toString() {
        return this._string.toString();
    }

    public void applyFont(short s) {
        applyFont(0, this._string.getCharCount(), s);
    }
}
