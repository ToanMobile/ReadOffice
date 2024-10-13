package com.app.office.fc.hssf.usermodel;

import androidx.core.net.MailTo;
import com.app.office.fc.hssf.record.HyperlinkRecord;
import com.app.office.fc.ss.usermodel.IHyperlink;

public class HSSFHyperlink implements IHyperlink {
    public static final int LINK_DOCUMENT = 2;
    public static final int LINK_EMAIL = 3;
    public static final int LINK_FILE = 4;
    public static final int LINK_URL = 1;
    protected int link_type;
    protected HyperlinkRecord record = null;

    public HSSFHyperlink(int i) {
        this.link_type = i;
        HyperlinkRecord hyperlinkRecord = new HyperlinkRecord();
        this.record = hyperlinkRecord;
        if (i != 1) {
            if (i == 2) {
                hyperlinkRecord.newDocumentLink();
                return;
            } else if (i != 3) {
                if (i == 4) {
                    hyperlinkRecord.newFileLink();
                    return;
                }
                return;
            }
        }
        hyperlinkRecord.newUrlLink();
    }

    protected HSSFHyperlink(HyperlinkRecord hyperlinkRecord) {
        this.record = hyperlinkRecord;
        if (hyperlinkRecord.isFileLink()) {
            this.link_type = 4;
        } else if (hyperlinkRecord.isDocumentLink()) {
            this.link_type = 2;
        } else if (hyperlinkRecord.getAddress() == null || !hyperlinkRecord.getAddress().startsWith(MailTo.MAILTO_SCHEME)) {
            this.link_type = 1;
        } else {
            this.link_type = 3;
        }
    }

    public int getFirstRow() {
        return this.record.getFirstRow();
    }

    public void setFirstRow(int i) {
        this.record.setFirstRow(i);
    }

    public int getLastRow() {
        return this.record.getLastRow();
    }

    public void setLastRow(int i) {
        this.record.setLastRow(i);
    }

    public int getFirstColumn() {
        return this.record.getFirstColumn();
    }

    public void setFirstColumn(int i) {
        this.record.setFirstColumn((short) i);
    }

    public int getLastColumn() {
        return this.record.getLastColumn();
    }

    public void setLastColumn(int i) {
        this.record.setLastColumn((short) i);
    }

    public String getAddress() {
        return this.record.getAddress();
    }

    public String getTextMark() {
        return this.record.getTextMark();
    }

    public void setTextMark(String str) {
        this.record.setTextMark(str);
    }

    public String getShortFilename() {
        return this.record.getShortFilename();
    }

    public void setShortFilename(String str) {
        this.record.setShortFilename(str);
    }

    public void setAddress(String str) {
        this.record.setAddress(str);
    }

    public String getLabel() {
        return this.record.getLabel();
    }

    public void setLabel(String str) {
        this.record.setLabel(str);
    }

    public int getType() {
        return this.link_type;
    }
}
