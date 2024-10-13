package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.BytePropertyNode;

@Deprecated
public abstract class BytePropertyNode<T extends BytePropertyNode<T>> extends PropertyNode<T> {
    private final int endBytes;
    private final int startBytes;

    public BytePropertyNode(int i, int i2, CharIndexTranslator charIndexTranslator, Object obj) {
        super(charIndexTranslator.getCharIndex(i), charIndexTranslator.getCharIndex(i2, charIndexTranslator.getCharIndex(i)), obj);
        if (i <= i2) {
            this.startBytes = i;
            this.endBytes = i2;
            return;
        }
        throw new IllegalArgumentException("fcStart (" + i + ") > fcEnd (" + i2 + ")");
    }

    public BytePropertyNode(int i, int i2, Object obj) {
        super(i, i2, obj);
        if (i <= i2) {
            this.startBytes = -1;
            this.endBytes = -1;
            return;
        }
        throw new IllegalArgumentException("charStart (" + i + ") > charEnd (" + i2 + ")");
    }

    @Deprecated
    public int getStartBytes() {
        return this.startBytes;
    }

    @Deprecated
    public int getEndBytes() {
        return this.endBytes;
    }
}
