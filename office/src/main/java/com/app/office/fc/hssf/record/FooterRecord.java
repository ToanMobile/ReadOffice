package com.app.office.fc.hssf.record;

public final class FooterRecord extends HeaderFooterBase {
    public static final short sid = 21;

    public short getSid() {
        return 21;
    }

    public FooterRecord(String str) {
        super(str);
    }

    public FooterRecord(RecordInputStream recordInputStream) {
        super(recordInputStream);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[FOOTER]\n");
        stringBuffer.append("    .footer = ");
        stringBuffer.append(getText());
        stringBuffer.append("\n");
        stringBuffer.append("[/FOOTER]\n");
        return stringBuffer.toString();
    }

    public Object clone() {
        return new FooterRecord(getText());
    }
}
