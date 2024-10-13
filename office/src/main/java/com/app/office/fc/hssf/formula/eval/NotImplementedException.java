package com.app.office.fc.hssf.formula.eval;

public final class NotImplementedException extends RuntimeException {
    public NotImplementedException(String str) {
        super(str);
    }

    public NotImplementedException(String str, NotImplementedException notImplementedException) {
        super(str, notImplementedException);
    }
}
