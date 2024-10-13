package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;

@Internal
public enum FieldsDocumentPart {
    ANNOTATIONS(19),
    ENDNOTES(48),
    FOOTNOTES(18),
    HEADER(17),
    HEADER_TEXTBOX(59),
    MAIN(16),
    TEXTBOX(57);
    
    private final int fibFieldsField;

    private FieldsDocumentPart(int i) {
        this.fibFieldsField = i;
    }

    public int getFibFieldsField() {
        return this.fibFieldsField;
    }
}
