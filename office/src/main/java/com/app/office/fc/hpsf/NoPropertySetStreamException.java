package com.app.office.fc.hpsf;

public class NoPropertySetStreamException extends HPSFException {
    public NoPropertySetStreamException() {
    }

    public NoPropertySetStreamException(String str) {
        super(str);
    }

    public NoPropertySetStreamException(Throwable th) {
        super(th);
    }

    public NoPropertySetStreamException(String str, Throwable th) {
        super(str, th);
    }
}
