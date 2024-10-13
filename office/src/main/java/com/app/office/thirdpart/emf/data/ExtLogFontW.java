package com.app.office.thirdpart.emf.data;

import com.app.office.simpletext.font.Font;
import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import java.io.IOException;

public class ExtLogFontW implements EMFConstants, GDIObject {
    private int culture;
    private LogFontW font;
    private String fullName;
    private int match;
    private Panose panose;
    private String style;
    private int styleSize;
    private byte[] vendorID;
    private int version;

    public ExtLogFontW(LogFontW logFontW, String str, String str2, int i, int i2, int i3, byte[] bArr, int i4, Panose panose2) {
        this.font = logFontW;
        this.fullName = str;
        this.style = str2;
        this.version = i;
        this.styleSize = i2;
        this.match = i3;
        this.vendorID = bArr;
        this.culture = i4;
        this.panose = panose2;
    }

    public ExtLogFontW(Font font2) {
        this.font = new LogFontW(font2);
        this.fullName = "";
        this.style = "";
        this.version = 0;
        this.styleSize = 0;
        this.match = 0;
        this.vendorID = new byte[]{0, 0, 0, 0};
        this.culture = 0;
        this.panose = new Panose();
    }

    public ExtLogFontW(EMFInputStream eMFInputStream) throws IOException {
        this.font = new LogFontW(eMFInputStream);
        this.fullName = eMFInputStream.readWCHAR(64);
        this.style = eMFInputStream.readWCHAR(32);
        this.version = eMFInputStream.readDWORD();
        this.styleSize = eMFInputStream.readDWORD();
        this.match = eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        this.vendorID = eMFInputStream.readBYTE(4);
        this.culture = eMFInputStream.readDWORD();
        this.panose = new Panose(eMFInputStream);
        eMFInputStream.readWORD();
        eMFInputStream.popBuffer();
    }

    public String toString() {
        return super.toString() + "\n  LogFontW\n" + this.font.toString() + "\n    fullname: " + this.fullName + "\n    style: " + this.style + "\n    version: " + this.version + "\n    stylesize: " + this.styleSize + "\n    match: " + this.match + "\n    vendorID: " + this.vendorID + "\n    culture: " + this.culture + "\n" + this.panose.toString();
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.setFont(this.font.getFont());
        eMFRenderer.setEscapement(this.font.getEscapement());
    }
}
