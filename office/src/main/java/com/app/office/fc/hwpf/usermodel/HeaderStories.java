package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.HWPFDocument;
import com.app.office.fc.hwpf.model.FileInformationBlock;
import com.app.office.fc.hwpf.model.GenericPropertyNode;
import com.app.office.fc.hwpf.model.PlexOfCps;
import com.app.office.fc.hwpf.model.SubdocumentType;

public final class HeaderStories {
    private Range headerStories;
    private PlexOfCps plcfHdd;
    private boolean stripFields = false;

    public HeaderStories(HWPFDocument hWPFDocument) {
        this.headerStories = hWPFDocument.getHeaderStoryRange();
        FileInformationBlock fileInformationBlock = hWPFDocument.getFileInformationBlock();
        if (fileInformationBlock.getSubdocumentTextStreamLength(SubdocumentType.HEADER) != 0 && fileInformationBlock.getPlcfHddSize() != 0) {
            this.plcfHdd = new PlexOfCps(hWPFDocument.getTableStream(), fileInformationBlock.getPlcfHddOffset(), fileInformationBlock.getPlcfHddSize(), 0);
        }
    }

    @Deprecated
    public String getFootnoteSeparator() {
        return getAt(0);
    }

    @Deprecated
    public String getFootnoteContSeparator() {
        return getAt(1);
    }

    @Deprecated
    public String getFootnoteContNote() {
        return getAt(2);
    }

    @Deprecated
    public String getEndnoteSeparator() {
        return getAt(3);
    }

    @Deprecated
    public String getEndnoteContSeparator() {
        return getAt(4);
    }

    @Deprecated
    public String getEndnoteContNote() {
        return getAt(5);
    }

    public Range getFootnoteSeparatorSubrange() {
        return getSubrangeAt(0);
    }

    public Range getFootnoteContSeparatorSubrange() {
        return getSubrangeAt(1);
    }

    public Range getFootnoteContNoteSubrange() {
        return getSubrangeAt(2);
    }

    public Range getEndnoteSeparatorSubrange() {
        return getSubrangeAt(3);
    }

    public Range getEndnoteContSeparatorSubrange() {
        return getSubrangeAt(4);
    }

    public Range getEndnoteContNoteSubrange() {
        return getSubrangeAt(5);
    }

    @Deprecated
    public String getEvenHeader() {
        return getAt(6);
    }

    @Deprecated
    public String getOddHeader() {
        return getAt(7);
    }

    @Deprecated
    public String getFirstHeader() {
        return getAt(10);
    }

    public Range getEvenHeaderSubrange() {
        return getSubrangeAt(6);
    }

    public Range getOddHeaderSubrange() {
        return getSubrangeAt(7);
    }

    public Range getFirstHeaderSubrange() {
        return getSubrangeAt(10);
    }

    public String getHeader(int i) {
        if (i == 1 && getFirstHeader().length() > 0) {
            return getFirstHeader();
        }
        if (i % 2 != 0 || getEvenHeader().length() <= 0) {
            return getOddHeader();
        }
        return getEvenHeader();
    }

    @Deprecated
    public String getEvenFooter() {
        return getAt(8);
    }

    @Deprecated
    public String getOddFooter() {
        return getAt(9);
    }

    @Deprecated
    public String getFirstFooter() {
        return getAt(11);
    }

    public Range getEvenFooterSubrange() {
        return getSubrangeAt(8);
    }

    public Range getOddFooterSubrange() {
        return getSubrangeAt(9);
    }

    public Range getFirstFooterSubrange() {
        return getSubrangeAt(11);
    }

    public String getFooter(int i) {
        if (i == 1 && getFirstFooter().length() > 0) {
            return getFirstFooter();
        }
        if (i % 2 != 0 || getEvenFooter().length() <= 0) {
            return getOddFooter();
        }
        return getEvenFooter();
    }

    @Deprecated
    private String getAt(int i) {
        PlexOfCps plexOfCps = this.plcfHdd;
        if (plexOfCps == null) {
            return null;
        }
        GenericPropertyNode property = plexOfCps.getProperty(i);
        if (property.getStart() == property.getEnd() || property.getEnd() < property.getStart()) {
            return "";
        }
        String text = this.headerStories.text();
        String substring = text.substring(Math.min(property.getStart(), text.length()), Math.min(property.getEnd(), text.length()));
        if (this.stripFields) {
            return Range.stripFields(substring);
        }
        if (substring.equals("\r\r")) {
            return "";
        }
        return substring;
    }

    private Range getSubrangeAt(int i) {
        PlexOfCps plexOfCps = this.plcfHdd;
        if (plexOfCps == null) {
            return null;
        }
        GenericPropertyNode property = plexOfCps.getProperty(i);
        if (property.getStart() == property.getEnd() || property.getEnd() < property.getStart()) {
            return null;
        }
        int endOffset = this.headerStories.getEndOffset() - this.headerStories.getStartOffset();
        return new Range(this.headerStories.getStartOffset() + Math.min(property.getStart(), endOffset), this.headerStories.getStartOffset() + Math.min(property.getEnd(), endOffset), this.headerStories);
    }

    public Range getRange() {
        return this.headerStories;
    }

    /* access modifiers changed from: protected */
    public PlexOfCps getPlcfHdd() {
        return this.plcfHdd;
    }

    public boolean areFieldsStripped() {
        return this.stripFields;
    }

    public void setAreFieldsStripped(boolean z) {
        this.stripFields = z;
    }
}
