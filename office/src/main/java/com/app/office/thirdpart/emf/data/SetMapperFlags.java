package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class SetMapperFlags extends EMFTag {
    private int flags;

    public SetMapperFlags() {
        super(16, 1);
    }

    public SetMapperFlags(int i) {
        this();
        this.flags = i;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new SetMapperFlags(eMFInputStream.readDWORD());
    }

    public String toString() {
        return super.toString() + "\n  flags: " + this.flags;
    }
}
