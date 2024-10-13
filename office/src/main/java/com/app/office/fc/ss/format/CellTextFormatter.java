package com.app.office.fc.ss.format;

import com.app.office.fc.ss.format.CellFormatPart;
import java.util.regex.Matcher;

public class CellTextFormatter extends CellFormatter {
    static final CellFormatter SIMPLE_TEXT = new CellTextFormatter("@");
    private final String desc;
    private final int[] textPos;

    public CellTextFormatter(String str) {
        super(str);
        final int[] iArr = new int[1];
        String stringBuffer = CellFormatPart.parseFormat(str, CellFormatType.TEXT, new CellFormatPart.PartHandler() {
            public String handlePart(Matcher matcher, String str, CellFormatType cellFormatType, StringBuffer stringBuffer) {
                if (!str.equals("@")) {
                    return null;
                }
                int[] iArr = iArr;
                iArr[0] = iArr[0] + 1;
                return "\u0000";
            }
        }).toString();
        this.desc = stringBuffer;
        int i = 0;
        this.textPos = new int[iArr[0]];
        int length = stringBuffer.length() - 1;
        while (true) {
            int[] iArr2 = this.textPos;
            if (i < iArr2.length) {
                iArr2[i] = this.desc.lastIndexOf("\u0000", length);
                length = this.textPos[i] - 1;
                i++;
            } else {
                return;
            }
        }
    }

    public void formatValue(StringBuffer stringBuffer, Object obj) {
        int length = stringBuffer.length();
        String obj2 = obj.toString();
        stringBuffer.append(this.desc);
        int i = 0;
        while (true) {
            int[] iArr = this.textPos;
            if (i < iArr.length) {
                int i2 = iArr[i] + length;
                stringBuffer.replace(i2, i2 + 1, obj2);
                i++;
            } else {
                return;
            }
        }
    }

    public void simpleValue(StringBuffer stringBuffer, Object obj) {
        SIMPLE_TEXT.formatValue(stringBuffer, obj);
    }
}
