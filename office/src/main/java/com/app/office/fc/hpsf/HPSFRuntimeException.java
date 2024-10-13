package com.app.office.fc.hpsf;

import java.io.PrintStream;
import java.io.PrintWriter;

public class HPSFRuntimeException extends RuntimeException {
    private Throwable reason;

    public HPSFRuntimeException() {
    }

    public HPSFRuntimeException(String str) {
        super(str);
    }

    public HPSFRuntimeException(Throwable th) {
        this.reason = th;
    }

    public HPSFRuntimeException(String str, Throwable th) {
        super(str);
        this.reason = th;
    }

    public Throwable getReason() {
        return this.reason;
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream printStream) {
        Throwable reason2 = getReason();
        super.printStackTrace(printStream);
        if (reason2 != null) {
            printStream.println("Caused by:");
            reason2.printStackTrace(printStream);
        }
    }

    public void printStackTrace(PrintWriter printWriter) {
        Throwable reason2 = getReason();
        super.printStackTrace(printWriter);
        if (reason2 != null) {
            printWriter.println("Caused by:");
            reason2.printStackTrace(printWriter);
        }
    }
}
