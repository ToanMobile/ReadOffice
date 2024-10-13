package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;

@Internal
@Deprecated
public final class CPSplitCalculator {
    private FileInformationBlock fib;

    public int getMainDocumentStart() {
        return 0;
    }

    public CPSplitCalculator(FileInformationBlock fileInformationBlock) {
        this.fib = fileInformationBlock;
    }

    public int getMainDocumentEnd() {
        return this.fib.getCcpText();
    }

    public int getFootnoteStart() {
        return getMainDocumentEnd();
    }

    public int getFootnoteEnd() {
        return getFootnoteStart() + this.fib.getCcpFtn();
    }

    public int getHeaderStoryStart() {
        return getFootnoteEnd();
    }

    public int getHeaderStoryEnd() {
        return getHeaderStoryStart() + this.fib.getCcpHdd();
    }

    public int getCommentsStart() {
        return getHeaderStoryEnd();
    }

    public int getCommentsEnd() {
        return getCommentsStart() + this.fib.getCcpCommentAtn();
    }

    public int getEndNoteStart() {
        return getCommentsEnd();
    }

    public int getEndNoteEnd() {
        return getEndNoteStart() + this.fib.getCcpEdn();
    }

    public int getMainTextboxStart() {
        return getEndNoteEnd();
    }

    public int getMainTextboxEnd() {
        return getMainTextboxStart() + this.fib.getCcpTxtBx();
    }

    public int getHeaderTextboxStart() {
        return getMainTextboxEnd();
    }

    public int getHeaderTextboxEnd() {
        return getHeaderTextboxStart() + this.fib.getCcpHdrTxtBx();
    }
}
