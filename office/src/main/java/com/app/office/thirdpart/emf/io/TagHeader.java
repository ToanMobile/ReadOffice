package com.app.office.thirdpart.emf.io;

public class TagHeader {
    long length;
    int tagID;

    public TagHeader(int i, long j) {
        this.tagID = i;
        this.length = j;
    }

    public void setTag(int i) {
        this.tagID = i;
    }

    public int getTag() {
        return this.tagID;
    }

    public void setLength(long j) {
        this.length = j;
    }

    public long getLength() {
        return this.length;
    }
}
