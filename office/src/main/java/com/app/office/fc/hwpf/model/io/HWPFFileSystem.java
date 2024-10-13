package com.app.office.fc.hwpf.model.io;

import com.app.office.fc.util.Internal;
import java.util.HashMap;
import java.util.Map;

@Internal
public final class HWPFFileSystem {
    Map<String, HWPFOutputStream> _streams;

    public HWPFFileSystem() {
        HashMap hashMap = new HashMap();
        this._streams = hashMap;
        hashMap.put("WordDocument", new HWPFOutputStream());
        this._streams.put("1Table", new HWPFOutputStream());
        this._streams.put("Data", new HWPFOutputStream());
    }

    public HWPFOutputStream getStream(String str) {
        return this._streams.get(str);
    }
}
