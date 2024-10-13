package com.app.office.fc.hslf.exceptions;

public final class HSLFException extends RuntimeException {
    public HSLFException() {
    }

    public HSLFException(String str) {
        super(str);
    }

    public HSLFException(String str, Throwable th) {
        super(str, th);
    }

    public HSLFException(Throwable th) {
        super(th);
    }
}
