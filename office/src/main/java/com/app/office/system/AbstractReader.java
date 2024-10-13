package com.app.office.system;

import java.io.File;

public class AbstractReader implements IReader {
    /* access modifiers changed from: protected */
    public boolean abortReader;
    /* access modifiers changed from: protected */
    public IControl control;

    public void backReader() throws Exception {
    }

    public Object getModel() throws Exception {
        return null;
    }

    public boolean isReaderFinish() {
        return true;
    }

    public boolean searchContent(File file, String str) throws Exception {
        return false;
    }

    public void abortReader() {
        this.abortReader = true;
    }

    public boolean isAborted() {
        return this.abortReader;
    }

    public IControl getControl() {
        return this.control;
    }

    public void dispose() {
        this.control = null;
    }
}
