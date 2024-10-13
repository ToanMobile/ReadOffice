package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.POILogger;

public final class HeadersFootersContainer extends RecordContainer {
    public static final int FOOTERATOM = 2;
    public static final int HEADERATOM = 1;
    public static final short NotesHeadersFootersContainer = 79;
    public static final short SlideHeadersFootersContainer = 63;
    public static final int USERDATEATOM = 0;
    private byte[] _header;
    private CString csDate;
    private CString csFooter;
    private CString csHeader;
    private HeadersFootersAtom hdAtom;

    protected HeadersFootersContainer(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        for (int i3 = 0; i3 < this._children.length; i3++) {
            if (this._children[i3] instanceof HeadersFootersAtom) {
                this.hdAtom = (HeadersFootersAtom) this._children[i3];
            } else if (this._children[i3] instanceof CString) {
                CString cString = (CString) this._children[i3];
                int options = cString.getOptions() >> 4;
                if (options == 0) {
                    this.csDate = cString;
                } else if (options == 1) {
                    this.csHeader = cString;
                } else if (options != 2) {
                    POILogger pOILogger = this.logger;
                    int i4 = POILogger.WARN;
                    pOILogger.log(i4, (Object) "Unexpected CString.Options in HeadersFootersContainer: " + options);
                } else {
                    this.csFooter = cString;
                }
            } else {
                POILogger pOILogger2 = this.logger;
                int i5 = POILogger.WARN;
                pOILogger2.log(i5, (Object) "Unexpected record in HeadersFootersContainer: " + this._children[i3]);
            }
        }
    }

    public HeadersFootersContainer(short s) {
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putShort(bArr, 0, s);
        LittleEndian.putShort(this._header, 2, (short) ((int) getRecordType()));
        HeadersFootersAtom headersFootersAtom = new HeadersFootersAtom();
        this.hdAtom = headersFootersAtom;
        this._children = new Record[]{headersFootersAtom};
        this.csFooter = null;
        this.csHeader = null;
        this.csDate = null;
    }

    public long getRecordType() {
        return (long) RecordTypes.HeadersFooters.typeID;
    }

    public int getOptions() {
        return LittleEndian.getShort(this._header, 0);
    }

    public HeadersFootersAtom getHeadersFootersAtom() {
        return this.hdAtom;
    }

    public CString getUserDateAtom() {
        return this.csDate;
    }

    public CString getHeaderAtom() {
        return this.csHeader;
    }

    public CString getFooterAtom() {
        return this.csFooter;
    }

    public CString addUserDateAtom() {
        CString cString = this.csDate;
        if (cString != null) {
            return cString;
        }
        CString cString2 = new CString();
        this.csDate = cString2;
        cString2.setOptions(0);
        addChildAfter(this.csDate, this.hdAtom);
        return this.csDate;
    }

    public CString addHeaderAtom() {
        CString cString = this.csHeader;
        if (cString != null) {
            return cString;
        }
        CString cString2 = new CString();
        this.csHeader = cString2;
        cString2.setOptions(16);
        HeadersFootersAtom headersFootersAtom = this.hdAtom;
        CString cString3 = this.csDate;
        addChildAfter(this.csHeader, headersFootersAtom);
        return this.csHeader;
    }

    public CString addFooterAtom() {
        CString cString = this.csFooter;
        if (cString != null) {
            return cString;
        }
        CString cString2 = new CString();
        this.csFooter = cString2;
        cString2.setOptions(32);
        Record record = this.hdAtom;
        Record record2 = this.csHeader;
        if (!(record2 == null && (record2 = this.csDate) == null)) {
            record = record2;
        }
        addChildAfter(this.csFooter, record);
        return this.csFooter;
    }

    public void dispose() {
        this._header = null;
        HeadersFootersAtom headersFootersAtom = this.hdAtom;
        if (headersFootersAtom != null) {
            headersFootersAtom.dispose();
            this.hdAtom = null;
        }
        CString cString = this.csDate;
        if (cString != null) {
            cString.dispose();
            this.csDate = null;
        }
        CString cString2 = this.csHeader;
        if (cString2 != null) {
            cString2.dispose();
            this.csHeader = null;
        }
        CString cString3 = this.csFooter;
        if (cString3 != null) {
            cString3.dispose();
            this.csFooter = null;
        }
    }
}
