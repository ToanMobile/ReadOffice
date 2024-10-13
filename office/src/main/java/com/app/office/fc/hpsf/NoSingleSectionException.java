package com.app.office.fc.hpsf;

public class NoSingleSectionException extends HPSFRuntimeException {
    public NoSingleSectionException() {
    }

    public NoSingleSectionException(String str) {
        super(str);
    }

    public NoSingleSectionException(Throwable th) {
        super(th);
    }

    public NoSingleSectionException(String str, Throwable th) {
        super(str, th);
    }
}
