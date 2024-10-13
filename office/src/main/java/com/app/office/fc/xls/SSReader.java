package com.app.office.fc.xls;

import com.app.office.system.AbstractReader;

public class SSReader extends AbstractReader {
    public void abortCurrentReading() {
        this.abortReader = false;
    }
}
