package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.hssf.record.HeaderRecord;
import com.app.office.fc.hssf.record.aggregates.PageSettingsBlock;
import com.app.office.fc.ss.usermodel.Header;

public final class HSSFHeader extends HeaderFooter implements Header {
    private final PageSettingsBlock _psb;

    protected HSSFHeader(PageSettingsBlock pageSettingsBlock) {
        this._psb = pageSettingsBlock;
    }

    /* access modifiers changed from: protected */
    public String getRawText() {
        HeaderRecord header = this._psb.getHeader();
        if (header == null) {
            return "";
        }
        return header.getText();
    }

    /* access modifiers changed from: protected */
    public void setHeaderFooterText(String str) {
        HeaderRecord header = this._psb.getHeader();
        if (header == null) {
            this._psb.setHeader(new HeaderRecord(str));
            return;
        }
        header.setText(str);
    }
}
