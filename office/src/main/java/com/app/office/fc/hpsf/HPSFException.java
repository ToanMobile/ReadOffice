package com.app.office.fc.hpsf;

public class HPSFException extends Exception {
    private Throwable reason;

    public HPSFException() {
    }

    public HPSFException(String str) {
        super(str);
    }

    public HPSFException(Throwable th) {
        this.reason = th;
    }

    public HPSFException(String str, Throwable th) {
        super(str);
        this.reason = th;
    }

    public Throwable getReason() {
        return this.reason;
    }
}
