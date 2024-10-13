package com.app.office.fc.hwpf.sprm;

import com.app.office.fc.util.Internal;

@Internal
public abstract class SprmUncompressor {
    public static boolean getFlag(int i) {
        return i != 0;
    }

    protected SprmUncompressor() {
    }
}
