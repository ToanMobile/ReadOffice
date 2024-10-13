package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;

@Internal
public enum FSPADocumentPart {
    HEADER(41),
    MAIN(40);
    
    private final int fibFieldsField;

    private FSPADocumentPart(int i) {
        this.fibFieldsField = i;
    }

    public int getFibFieldsField() {
        return this.fibFieldsField;
    }
}
