package com.app.office.fc.hssf.record;

public final class HeaderRecord extends HeaderFooterBase {
    public static final short sid = 20;

    public short getSid() {
        return 20;
    }

    public HeaderRecord(String str) {
        super(str);
    }

    public HeaderRecord(RecordInputStream recordInputStream) {
        super(recordInputStream);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[HEADER]\n");
        stringBuffer.append("    .header = ");
        stringBuffer.append(getText());
        stringBuffer.append("\n");
        stringBuffer.append("[/HEADER]\n");
        return stringBuffer.toString();
    }

    public Object clone() {
        return new HeaderRecord(getText());
    }
}
