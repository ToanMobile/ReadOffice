package com.app.office.thirdpart.emf;

import com.app.office.thirdpart.emf.data.GDIObject;
import com.app.office.thirdpart.emf.io.Tag;
import com.app.office.thirdpart.emf.io.TaggedInputStream;
import java.io.IOException;

public abstract class EMFTag extends Tag implements GDIObject {
    public abstract EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException;

    public void render(EMFRenderer eMFRenderer) {
    }

    protected EMFTag(int i, int i2) {
        super(i, i2);
    }

    public Tag read(int i, TaggedInputStream taggedInputStream, int i2) throws IOException {
        return read(i, (EMFInputStream) taggedInputStream, i2);
    }

    public String toString() {
        return "EMFTag " + getName() + " (" + getTag() + ")";
    }
}
