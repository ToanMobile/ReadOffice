package com.app.office.fc.util;

import java.io.PrintStream;

public class SystemOutLogger extends POILogger {
    private String _cat;

    public void initialize(String str) {
        this._cat = str;
    }

    public void log(int i, Object obj) {
        log(i, obj, (Throwable) null);
    }

    public void log(int i, Object obj, Throwable th) {
        if (check(i)) {
            PrintStream printStream = System.out;
            printStream.println("[" + this._cat + "] " + obj);
            if (th != null) {
                th.printStackTrace(System.out);
            }
        }
    }

    public boolean check(int i) {
        int i2;
        try {
            i2 = Integer.parseInt(System.getProperty("poi.log.level", WARN + ""));
        } catch (SecurityException unused) {
            i2 = POILogger.DEBUG;
        }
        return i >= i2;
    }
}
