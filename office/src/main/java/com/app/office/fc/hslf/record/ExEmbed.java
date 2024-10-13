package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.POILogger;

public class ExEmbed extends RecordContainer {
    private byte[] _header;
    private CString clipboardName;
    protected RecordAtom embedAtom;
    private CString menuName;
    private ExOleObjAtom oleObjAtom;
    private CString progId;

    protected ExEmbed(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        findInterestingChildren();
    }

    public ExEmbed() {
        this._header = new byte[8];
        this._children = new Record[5];
        byte[] bArr = this._header;
        bArr[0] = 15;
        LittleEndian.putShort(bArr, 2, (short) ((int) getRecordType()));
        CString cString = new CString();
        cString.setOptions(16);
        CString cString2 = new CString();
        cString2.setOptions(32);
        CString cString3 = new CString();
        cString3.setOptions(48);
        this._children[0] = new ExEmbedAtom();
        this._children[1] = new ExOleObjAtom();
        this._children[2] = cString;
        this._children[3] = cString2;
        this._children[4] = cString3;
        findInterestingChildren();
    }

    private void findInterestingChildren() {
        if (this._children[0] instanceof ExEmbedAtom) {
            this.embedAtom = (ExEmbedAtom) this._children[0];
        } else {
            POILogger pOILogger = this.logger;
            int i = POILogger.ERROR;
            pOILogger.log(i, (Object) "First child record wasn't a ExEmbedAtom, was of type " + this._children[0].getRecordType());
        }
        if (this._children[1] instanceof ExOleObjAtom) {
            this.oleObjAtom = (ExOleObjAtom) this._children[1];
        } else {
            POILogger pOILogger2 = this.logger;
            int i2 = POILogger.ERROR;
            pOILogger2.log(i2, (Object) "Second child record wasn't a ExOleObjAtom, was of type " + this._children[1].getRecordType());
        }
        for (int i3 = 2; i3 < this._children.length; i3++) {
            if (this._children[i3] instanceof CString) {
                CString cString = (CString) this._children[i3];
                int options = cString.getOptions() >> 4;
                if (options == 1) {
                    this.menuName = cString;
                } else if (options == 2) {
                    this.progId = cString;
                } else if (options == 3) {
                    this.clipboardName = cString;
                }
            }
        }
    }

    public ExEmbedAtom getExEmbedAtom() {
        return (ExEmbedAtom) this.embedAtom;
    }

    public ExOleObjAtom getExOleObjAtom() {
        return this.oleObjAtom;
    }

    public String getMenuName() {
        CString cString = this.menuName;
        if (cString == null) {
            return null;
        }
        return cString.getText();
    }

    public void setMenuName(String str) {
        CString cString = this.menuName;
        if (cString != null) {
            cString.setText(str);
        }
    }

    public String getProgId() {
        CString cString = this.progId;
        if (cString == null) {
            return null;
        }
        return cString.getText();
    }

    public void setProgId(String str) {
        CString cString = this.progId;
        if (cString != null) {
            cString.setText(str);
        }
    }

    public String getClipboardName() {
        CString cString = this.clipboardName;
        if (cString == null) {
            return null;
        }
        return cString.getText();
    }

    public void setClipboardName(String str) {
        CString cString = this.clipboardName;
        if (cString != null) {
            cString.setText(str);
        }
    }

    public long getRecordType() {
        return (long) RecordTypes.ExEmbed.typeID;
    }

    public void dispose() {
        super.dispose();
        this._header = null;
        RecordAtom recordAtom = this.embedAtom;
        if (recordAtom != null) {
            recordAtom.dispose();
            this.embedAtom = null;
        }
        ExOleObjAtom exOleObjAtom = this.oleObjAtom;
        if (exOleObjAtom != null) {
            exOleObjAtom.dispose();
            this.oleObjAtom = null;
        }
        CString cString = this.menuName;
        if (cString != null) {
            cString.dispose();
            this.menuName = null;
        }
        CString cString2 = this.progId;
        if (cString2 != null) {
            cString2.dispose();
            this.progId = null;
        }
        CString cString3 = this.clipboardName;
        if (cString3 != null) {
            cString3.dispose();
            this.clipboardName = null;
        }
    }
}
