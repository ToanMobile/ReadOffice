package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.hwpf.HWPFDocument;
import com.app.office.fc.hwpf.HWPFDocumentCore;

public final class DocumentPosition extends Range {
    public DocumentPosition(HWPFDocument hWPFDocument, int i) {
        super(i, i, (HWPFDocumentCore) hWPFDocument);
    }
}
