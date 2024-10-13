package com.app.office.fc.hslf.model;

import com.app.office.fc.hslf.record.CString;
import com.app.office.fc.hslf.record.Document;
import com.app.office.fc.hslf.record.HeadersFootersContainer;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.hslf.record.RecordTypes;
import com.app.office.fc.hslf.usermodel.SlideShow;

public final class HeadersFooters {
    private HeadersFootersContainer _container;
    private boolean _newRecord;
    private SlideShow _ppt;
    private boolean _ppt2007 = false;
    private Sheet _sheet;

    public HeadersFooters(HeadersFootersContainer headersFootersContainer, SlideShow slideShow, boolean z, boolean z2) {
        this._container = headersFootersContainer;
        this._newRecord = z;
        this._ppt = slideShow;
    }

    public HeadersFooters(HeadersFootersContainer headersFootersContainer, Sheet sheet, boolean z, boolean z2) {
        this._container = headersFootersContainer;
        this._newRecord = z;
        this._sheet = sheet;
    }

    public String getHeaderText() {
        HeadersFootersContainer headersFootersContainer = this._container;
        return getPlaceholderText(10, headersFootersContainer == null ? null : headersFootersContainer.getHeaderAtom());
    }

    public void setHeaderText(String str) {
        if (this._newRecord) {
            attach();
        }
        setHeaderVisible(true);
        CString headerAtom = this._container.getHeaderAtom();
        if (headerAtom == null) {
            headerAtom = this._container.addHeaderAtom();
        }
        headerAtom.setText(str);
    }

    public String getFooterText() {
        HeadersFootersContainer headersFootersContainer = this._container;
        return getPlaceholderText(9, headersFootersContainer == null ? null : headersFootersContainer.getFooterAtom());
    }

    public void setFootersText(String str) {
        if (this._newRecord) {
            attach();
        }
        setFooterVisible(true);
        CString footerAtom = this._container.getFooterAtom();
        if (footerAtom == null) {
            footerAtom = this._container.addFooterAtom();
        }
        footerAtom.setText(str);
    }

    public String getDateTimeText() {
        HeadersFootersContainer headersFootersContainer = this._container;
        return getPlaceholderText(7, headersFootersContainer == null ? null : headersFootersContainer.getUserDateAtom());
    }

    public void setDateTimeText(String str) {
        if (this._newRecord) {
            attach();
        }
        setUserDateVisible(true);
        setDateTimeVisible(true);
        CString userDateAtom = this._container.getUserDateAtom();
        if (userDateAtom == null) {
            userDateAtom = this._container.addUserDateAtom();
        }
        userDateAtom.setText(str);
    }

    public boolean isFooterVisible() {
        return isVisible(32, 9);
    }

    public void setFooterVisible(boolean z) {
        if (this._newRecord) {
            attach();
        }
        this._container.getHeadersFootersAtom().setFlag(32, z);
    }

    public boolean isHeaderVisible() {
        return isVisible(16, 10);
    }

    public void setHeaderVisible(boolean z) {
        if (this._newRecord) {
            attach();
        }
        this._container.getHeadersFootersAtom().setFlag(16, z);
    }

    public boolean isDateTimeVisible() {
        return isVisible(1, 7);
    }

    public void setDateTimeVisible(boolean z) {
        if (this._newRecord) {
            attach();
        }
        this._container.getHeadersFootersAtom().setFlag(1, z);
    }

    public boolean isUserDateVisible() {
        return isVisible(4, 7);
    }

    public void setUserDateVisible(boolean z) {
        if (this._newRecord) {
            attach();
        }
        this._container.getHeadersFootersAtom().setFlag(4, z);
    }

    public boolean isSlideNumberVisible() {
        return isVisible(8, 8);
    }

    public void setSlideNumberVisible(boolean z) {
        if (this._newRecord) {
            attach();
        }
        this._container.getHeadersFootersAtom().setFlag(8, z);
    }

    public int getDateTimeFormat() {
        return this._container.getHeadersFootersAtom().getFormatId();
    }

    public void setDateTimeFormat(int i) {
        if (this._newRecord) {
            attach();
        }
        this._container.getHeadersFootersAtom().setFormatId(i);
    }

    private void attach() {
        Record record;
        Document documentRecord = this._ppt.getDocumentRecord();
        Record[] childRecords = documentRecord.getChildRecords();
        int i = 0;
        while (true) {
            if (i >= childRecords.length) {
                record = null;
                break;
            } else if (childRecords[i].getRecordType() == ((long) RecordTypes.List.typeID)) {
                record = childRecords[i];
                break;
            } else {
                i++;
            }
        }
        documentRecord.addChildAfter(this._container, record);
        this._newRecord = false;
    }

    private boolean isVisible(int i, int i2) {
        if (!this._ppt2007) {
            return this._container.getHeadersFootersAtom().getFlag(i);
        }
        Sheet sheet = this._sheet;
        if (sheet == null) {
            sheet = this._ppt.getSlidesMasters()[0];
        }
        TextShape placeholder = sheet.getPlaceholder(i2);
        if (placeholder == null || placeholder.getText() == null) {
            return false;
        }
        return true;
    }

    private String getPlaceholderText(int i, CString cString) {
        if (this._ppt2007) {
            Sheet sheet = this._sheet;
            if (sheet == null) {
                sheet = this._ppt.getSlidesMasters()[0];
            }
            TextShape placeholder = sheet.getPlaceholder(i);
            String text = placeholder != null ? placeholder.getText() : null;
            if ("*".equals(text)) {
                return null;
            }
            return text;
        } else if (cString == null) {
            return null;
        } else {
            return cString.getText();
        }
    }
}
