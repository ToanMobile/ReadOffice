package com.app.office.thirdpart.emf.io;

import java.io.IOException;

public class UndefinedTag extends Tag {
    private int[] bytes;

    public int getTagType() {
        return 0;
    }

    public UndefinedTag() {
        this(-1, new int[0]);
    }

    public UndefinedTag(int i, int[] iArr) {
        super(i, 3);
        this.bytes = iArr;
    }

    public Tag read(int i, TaggedInputStream taggedInputStream, int i2) throws IOException {
        return new UndefinedTag(i, taggedInputStream.readUnsignedByte(i2));
    }

    public String toString() {
        return "UNDEFINED TAG: " + getTag() + " length: " + this.bytes.length;
    }
}
