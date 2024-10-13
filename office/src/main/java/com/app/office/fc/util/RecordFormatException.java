package com.app.office.fc.util;

public class RecordFormatException extends RuntimeException {
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
