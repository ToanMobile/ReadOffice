package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.POILogger;

public class ExHyperlink extends RecordContainer {
    private static long _type = 4055;
    private byte[] _header;
    private ExHyperlinkAtom linkAtom;
    private CString linkDetailsA;
    private CString linkDetailsB;

    public ExHyperlinkAtom getExHyperlinkAtom() {
        return this.linkAtom;
    }

    public String getLinkURL() {
        CString cString = this.linkDetailsB;
        if (cString == null) {
            return null;
        }
        return cString.getText();
    }

    public String getLinkTitle() {
        CString cString = this.linkDetailsA;
        if (cString == null) {
            return null;
        }
        return cString.getText();
    }

    public void setLinkURL(String str) {
        CString cString = this.linkDetailsB;
        if (cString != null) {
            cString.setText(str);
        }
    }

    public void setLinkTitle(String str) {
        CString cString = this.linkDetailsA;
        if (cString != null) {
            cString.setText(str);
        }
    }

    public String _getDetailsA() {
        CString cString = this.linkDetailsA;
        if (cString == null) {
            return null;
        }
        return cString.getText();
    }

    public String _getDetailsB() {
        CString cString = this.linkDetailsB;
        if (cString == null) {
            return null;
        }
        return cString.getText();
    }

    protected ExHyperlink(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        findInterestingChildren();
    }

    private void findInterestingChildren() {
        if (this._children[0] instanceof ExHyperlinkAtom) {
            this.linkAtom = (ExHyperlinkAtom) this._children[0];
        } else {
            POILogger pOILogger = this.logger;
            int i = POILogger.ERROR;
            pOILogger.log(i, (Object) "First child record wasn't a ExHyperlinkAtom, was of type " + this._children[0].getRecordType());
        }
        for (int i2 = 1; i2 < this._children.length; i2++) {
            if (!(this._children[i2] instanceof CString)) {
                POILogger pOILogger2 = this.logger;
                int i3 = POILogger.ERROR;
                pOILogger2.log(i3, (Object) "Record after ExHyperlinkAtom wasn't a CString, was of type " + this._children[1].getRecordType());
            } else if (this.linkDetailsA == null) {
                this.linkDetailsA = (CString) this._children[i2];
            } else {
                this.linkDetailsB = (CString) this._children[i2];
            }
        }
    }

    public ExHyperlink() {
        this._header = new byte[8];
        this._children = new Record[3];
        byte[] bArr = this._header;
        bArr[0] = 15;
        LittleEndian.putShort(bArr, 2, (short) ((int) _type));
        CString cString = new CString();
        CString cString2 = new CString();
        cString.setOptions(0);
        cString2.setOptions(16);
        this._children[0] = new ExHyperlinkAtom();
        this._children[1] = cString;
        this._children[2] = cString2;
        findInterestingChildren();
    }

    public long getRecordType() {
        return _type;
    }

    public void dispose() {
        this._header = null;
        CString cString = this.linkDetailsA;
        if (cString != null) {
            cString.dispose();
            this.linkDetailsA = null;
        }
        CString cString2 = this.linkDetailsB;
        if (cString2 != null) {
            cString2.dispose();
            this.linkDetailsB = null;
        }
        ExHyperlinkAtom exHyperlinkAtom = this.linkAtom;
        if (exHyperlinkAtom != null) {
            exHyperlinkAtom.dispose();
            this.linkAtom = null;
        }
    }
}
