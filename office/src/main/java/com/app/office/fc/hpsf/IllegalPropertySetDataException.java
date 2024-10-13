package com.app.office.fc.hpsf;

public class IllegalPropertySetDataException extends HPSFRuntimeException {
    public IllegalPropertySetDataException() {
    }

    public IllegalPropertySetDataException(String str) {
        super(str);
    }

    public IllegalPropertySetDataException(Throwable th) {
        super(th);
    }

    public IllegalPropertySetDataException(String str, Throwable th) {
        super(str, th);
    }
}
