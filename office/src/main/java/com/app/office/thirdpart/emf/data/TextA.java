package com.app.office.thirdpart.emf.data;

import android.graphics.Point;
import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class TextA extends Text {
    public TextA(Point point, String str, int i, Rectangle rectangle, int[] iArr) {
        super(point, str, i, rectangle, iArr);
    }

    public static TextA read(EMFInputStream eMFInputStream) throws IOException {
        Point readPOINTL = eMFInputStream.readPOINTL();
        int readDWORD = eMFInputStream.readDWORD();
        eMFInputStream.readDWORD();
        int readDWORD2 = eMFInputStream.readDWORD();
        Rectangle readRECTL = eMFInputStream.readRECTL();
        eMFInputStream.readDWORD();
        String str = new String(eMFInputStream.readBYTE(readDWORD), Charset.defaultCharset().name());
        int i = readDWORD % 4;
        if (i != 0) {
            for (int i2 = 0; i2 < 4 - i; i2++) {
                eMFInputStream.readBYTE();
            }
        }
        int[] iArr = new int[readDWORD];
        for (int i3 = 0; i3 < readDWORD; i3++) {
            iArr[i3] = eMFInputStream.readDWORD();
        }
        return new TextA(readPOINTL, str, readDWORD2, readRECTL, iArr);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.string.length(); i++) {
            stringBuffer.append(",");
            stringBuffer.append(this.widths[i]);
        }
        stringBuffer.append(']');
        stringBuffer.setCharAt(0, '[');
        return "  TextA\n    pos: " + this.pos + "\n    options: " + this.options + "\n    bounds: " + this.bounds + "\n    string: " + this.string + "\n    widths: " + stringBuffer;
    }
}
