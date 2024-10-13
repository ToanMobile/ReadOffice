package com.app.office.common.bookmark;

public class Bookmark {
    private long end;
    private String name;
    private long start;

    public Bookmark(String str, long j, long j2) {
        this.name = str;
        this.start = j;
        this.end = j2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public long getStart() {
        return this.start;
    }

    public void setStart(long j) {
        this.start = j;
    }

    public long getEnd() {
        return this.end;
    }

    public void setEnd(long j) {
        this.end = j;
    }
}
