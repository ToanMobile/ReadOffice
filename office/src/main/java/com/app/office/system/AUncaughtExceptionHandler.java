package com.app.office.system;

import java.lang.Thread;

public class AUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private IControl control;

    public AUncaughtExceptionHandler(IControl iControl) {
        this.control = iControl;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        this.control.getSysKit().getErrorKit().writerLog(th);
    }

    public void dispose() {
        this.control = null;
    }
}
