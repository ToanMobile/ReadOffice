package com.app.office.fc.hslf.model;

import com.app.office.fc.hslf.record.Comment2000;

public final class Comment {
    private Comment2000 _comment2000;

    public Comment(Comment2000 comment2000) {
        this._comment2000 = comment2000;
    }

    /* access modifiers changed from: protected */
    public Comment2000 getComment2000() {
        return this._comment2000;
    }

    public String getAuthor() {
        return this._comment2000.getAuthor();
    }

    public void setAuthor(String str) {
        this._comment2000.setAuthor(str);
    }

    public String getAuthorInitials() {
        return this._comment2000.getAuthorInitials();
    }

    public void setAuthorInitials(String str) {
        this._comment2000.setAuthorInitials(str);
    }

    public String getText() {
        return this._comment2000.getText();
    }

    public void setText(String str) {
        this._comment2000.setText(str);
    }
}
