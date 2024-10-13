package com.app.office.fc.hssf.record;

public class RecordFormatException extends com.app.office.fc.util.RecordFormatException {
    public RecordFormatException(String str) {
        super(str);
    }

    public RecordFormatException(String str, Throwable th) {
        super(str, th);
    }

    public RecordFormatException(Throwable th) {
        super(th);
    }
}
