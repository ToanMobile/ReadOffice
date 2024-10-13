package com.app.office.fc.hpsf;

public class UnexpectedPropertySetTypeException extends HPSFException {
    public UnexpectedPropertySetTypeException() {
    }

    public UnexpectedPropertySetTypeException(String str) {
        super(str);
    }

    public UnexpectedPropertySetTypeException(Throwable th) {
        super(th);
    }

    public UnexpectedPropertySetTypeException(String str, Throwable th) {
        super(str, th);
    }
}
