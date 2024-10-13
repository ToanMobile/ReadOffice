package com.app.office.fc.hpsf;

public class MarkUnsupportedException extends HPSFException {
    public MarkUnsupportedException() {
    }

    public MarkUnsupportedException(String str) {
        super(str);
    }

    public MarkUnsupportedException(Throwable th) {
        super(th);
    }

    public MarkUnsupportedException(String str, Throwable th) {
        super(str, th);
    }
}
