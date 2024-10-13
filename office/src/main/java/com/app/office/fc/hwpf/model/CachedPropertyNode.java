package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.sprm.SprmBuffer;
import com.app.office.fc.util.Internal;
import java.lang.ref.SoftReference;

@Internal
public final class CachedPropertyNode extends PropertyNode<CachedPropertyNode> {
    protected SoftReference<Object> _propCache;

    public CachedPropertyNode(int i, int i2, SprmBuffer sprmBuffer) {
        super(i, i2, sprmBuffer);
    }

    /* access modifiers changed from: protected */
    public void fillCache(Object obj) {
        this._propCache = new SoftReference<>(obj);
    }

    /* access modifiers changed from: protected */
    public Object getCacheContents() {
        SoftReference<Object> softReference = this._propCache;
        if (softReference == null) {
            return null;
        }
        return softReference.get();
    }

    public SprmBuffer getSprmBuf() {
        return (SprmBuffer) this._buf;
    }
}
