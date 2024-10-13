package com.app.office.thirdpart.emf.io;

import java.io.IOException;
import java.io.InputStream;

public abstract class TaggedInputStream extends ByteCountInputStream {
    protected ActionSet actionSet;
    private TagHeader tagHeader;
    protected TagSet tagSet;

    /* access modifiers changed from: protected */
    public abstract ActionHeader readActionHeader() throws IOException;

    /* access modifiers changed from: protected */
    public abstract TagHeader readTagHeader() throws IOException;

    public TaggedInputStream(InputStream inputStream, TagSet tagSet2, ActionSet actionSet2) {
        this(inputStream, tagSet2, actionSet2, false);
    }

    public TaggedInputStream(InputStream inputStream, TagSet tagSet2, ActionSet actionSet2, boolean z) {
        super(inputStream, z, 8);
        this.tagSet = tagSet2;
        this.actionSet = actionSet2;
    }

    public void addTag(Tag tag) {
        this.tagSet.addTag(tag);
    }

    public Tag readTag() throws IOException {
        TagHeader readTagHeader = readTagHeader();
        this.tagHeader = readTagHeader;
        if (readTagHeader == null) {
            return null;
        }
        int length = (int) readTagHeader.getLength();
        Tag tag = this.tagSet.get(this.tagHeader.getTag());
        pushBuffer(length);
        Tag read = tag.read(this.tagHeader.getTag(), this, length);
        byte[] popBuffer = popBuffer();
        if (popBuffer == null) {
            return read;
        }
        throw new IncompleteTagException(read, popBuffer);
    }

    public TagHeader getTagHeader() {
        return this.tagHeader;
    }

    public void addAction(Action action) {
        this.actionSet.addAction(action);
    }

    public Action readAction() throws IOException {
        ActionHeader readActionHeader = readActionHeader();
        if (readActionHeader == null) {
            return null;
        }
        int length = (int) readActionHeader.getLength();
        Action action = this.actionSet.get(readActionHeader.getAction());
        pushBuffer(length);
        Action read = action.read(readActionHeader.getAction(), this, length);
        byte[] popBuffer = popBuffer();
        if (popBuffer == null) {
            return read;
        }
        throw new IncompleteActionException(read, popBuffer);
    }
}
