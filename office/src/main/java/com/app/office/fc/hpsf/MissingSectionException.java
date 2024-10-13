package com.app.office.fc.hpsf;

public class MissingSectionException extends HPSFRuntimeException {
    public MissingSectionException() {
    }

    public MissingSectionException(String str) {
        super(str);
    }

    public MissingSectionException(Throwable th) {
        super(th);
    }

    public MissingSectionException(String str, Throwable th) {
        super(str, th);
    }
}
