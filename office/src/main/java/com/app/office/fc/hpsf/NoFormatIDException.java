package com.app.office.fc.hpsf;

public class NoFormatIDException extends HPSFRuntimeException {
    public NoFormatIDException() {
    }

    public NoFormatIDException(String str) {
        super(str);
    }

    public NoFormatIDException(Throwable th) {
        super(th);
    }

    public NoFormatIDException(String str, Throwable th) {
        super(str, th);
    }
}
