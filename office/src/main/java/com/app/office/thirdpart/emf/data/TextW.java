package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFInputStream;
import java.io.IOException;

public class TextW extends Text {
    public TextW(Point point, String str, int i, Rectangle rectangle, int[] iArr) {
        super(point, str, i, rectangle, iArr);
    }

    public static TextW read(EMFInputStream eMFInputStream) throws IOException {
        Point readPOINTL = eMFInputStream.readPOINTL();
        int readDWORD = eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        int readDWORD2 = eMFInputStream.readDWORD();
        Rectangle readRECTL = eMFInputStream.readRECTL();
        eMFInputStream.readDWORD();
        int i = readDWORD * 2;
        String str = new String(eMFInputStream.readBYTE(i), "UTF-16LE");
        int i2 = i % 4;
        if (i2 != 0) {
            for (int i3 = 0; i3 < 4 - i2; i3++) {
                eMFInputStream.readBYTE();
            }
        }
        int[] iArr = new int[readDWORD];
        for (int i4 = 0; i4 < readDWORD; i4++) {
            iArr[i4] = eMFInputStream.readDWORD();
        }
        return new TextW(readPOINTL, str, readDWORD2, readRECTL, iArr);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.string.length(); i++) {
            stringBuffer.append(",");
            stringBuffer.append(this.widths[i]);
        }
        stringBuffer.append(']');
        stringBuffer.setCharAt(0, '[');
        return "  TextW\n    pos: " + this.pos + "\n    options: " + this.options + "\n    bounds: " + this.bounds + "\n    string: " + this.string + "\n    widths: " + stringBuffer;
    }
}
