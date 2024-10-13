package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;

@Internal
public enum SubdocumentType {
    MAIN(3),
    FOOTNOTE(4),
    HEADER(5),
    MACRO(6),
    ANNOTATION(7),
    ENDNOTE(8),
    TEXTBOX(9),
    HEADER_TEXTBOX(10);
    
    public static final SubdocumentType[] ORDERED = null;
    private final int fibLongFieldIndex;

    static {
        SubdocumentType subdocumentType;
        SubdocumentType subdocumentType2;
        SubdocumentType subdocumentType3;
        SubdocumentType subdocumentType4;
        SubdocumentType subdocumentType5;
        SubdocumentType subdocumentType6;
        SubdocumentType subdocumentType7;
        SubdocumentType subdocumentType8;
        ORDERED = new SubdocumentType[]{subdocumentType, subdocumentType2, subdocumentType3, subdocumentType4, subdocumentType5, subdocumentType6, subdocumentType7, subdocumentType8};
    }

    private SubdocumentType(int i) {
        this.fibLongFieldIndex = i;
    }

    public int getFibLongFieldIndex() {
        return this.fibLongFieldIndex;
    }
}
