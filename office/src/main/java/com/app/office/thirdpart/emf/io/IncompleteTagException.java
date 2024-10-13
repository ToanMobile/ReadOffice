package com.app.office.thirdpart.emf.io;

import java.io.IOException;

public class IncompleteTagException extends IOException {
    private static final long serialVersionUID = -7808675150856818588L;
    private byte[] rest;
    private Tag tag;

    public IncompleteTagException(Tag tag2, byte[] bArr) {
        super("Tag " + tag2 + " contains " + bArr.length + " unread bytes");
        this.tag = tag2;
        this.rest = bArr;
    }

    public Tag getTag() {
        return this.tag;
    }

    public byte[] getBytes() {
        return this.rest;
    }
}
