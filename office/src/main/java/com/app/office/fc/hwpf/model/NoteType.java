package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;

@Internal
public enum NoteType {
    ENDNOTE(46, 47),
    FOOTNOTE(2, 3);
    
    private final int fibDescriptorsFieldIndex;
    private final int fibTextPositionsFieldIndex;

    private NoteType(int i, int i2) {
        this.fibDescriptorsFieldIndex = i;
        this.fibTextPositionsFieldIndex = i2;
    }

    public int getFibDescriptorsFieldIndex() {
        return this.fibDescriptorsFieldIndex;
    }

    public int getFibTextPositionsFieldIndex() {
        return this.fibTextPositionsFieldIndex;
    }
}
