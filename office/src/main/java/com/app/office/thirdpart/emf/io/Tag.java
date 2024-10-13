package com.app.office.thirdpart.emf.io;

import java.io.IOException;

public abstract class Tag {
    public static final int DEFAULT_TAG = -1;
    private String name = null;
    private int tagID;
    private int version;

    public int getTagType() {
        return 0;
    }

    public abstract Tag read(int i, TaggedInputStream taggedInputStream, int i2) throws IOException;

    public abstract String toString();

    protected Tag(int i, int i2) {
        this.tagID = i;
        this.version = i2;
    }

    public int getTag() {
        return this.tagID;
    }

    public int getVersion() {
        return this.version;
    }

    public String getName() {
        if (this.name == null) {
            String name2 = getClass().getName();
            this.name = name2;
            int lastIndexOf = name2.lastIndexOf(".");
            this.name = lastIndexOf >= 0 ? this.name.substring(lastIndexOf + 1) : this.name;
        }
        return this.name;
    }
}
