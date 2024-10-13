package com.app.office.thirdpart.emf.data;

import com.app.office.simpletext.font.Font;
import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import java.io.IOException;

public class LogFontW implements EMFConstants, GDIObject {
    private int charSet;
    private int clipPrecision;
    private int escapement;
    private String faceFamily;
    private Font font;
    private int height;
    private boolean italic;
    private int orientation;
    private int outPrecision;
    private int pitchAndFamily;
    private int quality;
    private boolean strikeout;
    private boolean underline;
    private int weight;
    private int width;

    public LogFontW(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2, boolean z3, int i6, int i7, int i8, int i9, int i10, String str) {
        this.height = i;
        this.width = i2;
        this.escapement = i3;
        this.orientation = i4;
        this.weight = i5;
        this.italic = z;
        this.underline = z2;
        this.strikeout = z3;
        this.charSet = i6;
        this.outPrecision = i7;
        this.clipPrecision = i8;
        this.quality = i9;
        this.pitchAndFamily = i10;
        this.faceFamily = str;
    }

    public LogFontW(Font font2) {
        this.height = (int) (-font2.getFontSize());
        this.width = 0;
        this.escapement = 0;
        this.orientation = 0;
        this.weight = font2.isBold() ? 700 : EMFConstants.FW_NORMAL;
        this.italic = font2.isItalic();
        this.underline = false;
        this.strikeout = false;
        this.charSet = 0;
        this.outPrecision = 0;
        this.clipPrecision = 0;
        this.quality = 4;
        this.pitchAndFamily = 0;
        this.faceFamily = font2.getName();
    }

    public LogFontW(EMFInputStream eMFInputStream) throws IOException {
        this.height = eMFInputStream.readLONG();
        this.width = eMFInputStream.readLONG();
        this.escapement = eMFInputStream.readLONG();
        this.orientation = eMFInputStream.readLONG();
        this.weight = eMFInputStream.readLONG();
        this.italic = eMFInputStream.readBOOLEAN();
        this.underline = eMFInputStream.readBOOLEAN();
        this.strikeout = eMFInputStream.readBOOLEAN();
        this.charSet = eMFInputStream.readBYTE();
        this.outPrecision = eMFInputStream.readBYTE();
        this.clipPrecision = eMFInputStream.readBYTE();
        this.quality = eMFInputStream.readBYTE();
        this.pitchAndFamily = eMFInputStream.readBYTE();
        this.faceFamily = eMFInputStream.readWCHAR(32);
    }

    public Font getFont() {
        if (this.font == null) {
            int i = 0;
            if (this.italic) {
                i = 2;
            }
            if (this.weight > 400) {
                i |= 1;
            }
            this.font = new Font(this.faceFamily, i, Math.abs(this.height));
        }
        return this.font;
    }

    public int getEscapement() {
        return this.escapement;
    }

    public String toString() {
        return "  LogFontW\n    height: " + this.height + "\n    width: " + this.width + "\n    orientation: " + this.orientation + "\n    weight: " + this.weight + "\n    italic: " + this.italic + "\n    underline: " + this.underline + "\n    strikeout: " + this.strikeout + "\n    charSet: " + this.charSet + "\n    outPrecision: " + this.outPrecision + "\n    clipPrecision: " + this.clipPrecision + "\n    quality: " + this.quality + "\n    pitchAndFamily: " + this.pitchAndFamily + "\n    faceFamily: " + this.faceFamily;
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.setFont(this.font);
    }
}
