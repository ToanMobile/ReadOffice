package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;

public final class Comment2000 extends RecordContainer {
    private static long _type = 12000;
    private byte[] _header;
    private CString authorInitialsRecord;
    private CString authorRecord;
    private Comment2000Atom commentAtom;
    private CString commentRecord;

    public Comment2000Atom getComment2000Atom() {
        return this.commentAtom;
    }

    public String getAuthor() {
        CString cString = this.authorRecord;
        if (cString == null) {
            return null;
        }
        return cString.getText();
    }

    public void setAuthor(String str) {
        this.authorRecord.setText(str);
    }

    public String getAuthorInitials() {
        CString cString = this.authorInitialsRecord;
        if (cString == null) {
            return null;
        }
        return cString.getText();
    }

    public void setAuthorInitials(String str) {
        this.authorInitialsRecord.setText(str);
    }

    public String getText() {
        CString cString = this.commentRecord;
        if (cString == null) {
            return null;
        }
        return cString.getText();
    }

    public void setText(String str) {
        this.commentRecord.setText(str);
    }

    protected Comment2000(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        findInterestingChildren();
    }

    private void findInterestingChildren() {
        for (Record record : this._children) {
            if (record instanceof CString) {
                CString cString = (CString) record;
                int options = cString.getOptions() >> 4;
                if (options == 0) {
                    this.authorRecord = cString;
                } else if (options == 1) {
                    this.commentRecord = cString;
                } else if (options == 2) {
                    this.authorInitialsRecord = cString;
                }
            } else if (record instanceof Comment2000Atom) {
                this.commentAtom = (Comment2000Atom) record;
            }
        }
    }

    public Comment2000() {
        this._header = new byte[8];
        this._children = new Record[4];
        byte[] bArr = this._header;
        bArr[0] = 15;
        LittleEndian.putShort(bArr, 2, (short) ((int) _type));
        CString cString = new CString();
        CString cString2 = new CString();
        CString cString3 = new CString();
        cString.setOptions(0);
        cString2.setOptions(16);
        cString3.setOptions(32);
        this._children[0] = cString;
        this._children[1] = cString2;
        this._children[2] = cString3;
        this._children[3] = new Comment2000Atom();
        findInterestingChildren();
    }

    public long getRecordType() {
        return _type;
    }

    public void dispose() {
        this._header = null;
        CString cString = this.authorRecord;
        if (cString != null) {
            cString.dispose();
            this.authorRecord = null;
        }
        CString cString2 = this.authorInitialsRecord;
        if (cString2 != null) {
            cString2.dispose();
            this.authorInitialsRecord = null;
        }
        CString cString3 = this.commentRecord;
        if (cString3 != null) {
            cString3.dispose();
            this.commentRecord = null;
        }
        Comment2000Atom comment2000Atom = this.commentAtom;
        if (comment2000Atom != null) {
            comment2000Atom.dispose();
            this.commentAtom = null;
        }
    }
}
