package com.app.office.fc.hslf.usermodel;

import com.app.office.fc.hslf.record.ExOleObjStg;
import java.io.IOException;
import java.io.InputStream;

public class ObjectData {
    private ExOleObjStg storage;

    public ObjectData(ExOleObjStg exOleObjStg) {
        this.storage = exOleObjStg;
    }

    public InputStream getData() {
        return this.storage.getData();
    }

    public void setData(byte[] bArr) throws IOException {
        this.storage.setData(bArr);
    }

    public ExOleObjStg getExOleObjStg() {
        return this.storage;
    }

    public void dispose() {
        ExOleObjStg exOleObjStg = this.storage;
        if (exOleObjStg != null) {
            exOleObjStg.dispose();
            this.storage = null;
        }
    }
}
