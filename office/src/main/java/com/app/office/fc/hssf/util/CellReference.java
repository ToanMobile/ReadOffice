package com.app.office.fc.hssf.util;

public final class CellReference extends com.app.office.fc.ss.util.CellReference {
    public CellReference(String str) {
        super(str);
    }

    public CellReference(int i, int i2) {
        super(i, i2, true, true);
    }

    public CellReference(int i, int i2, boolean z, boolean z2) {
        super((String) null, i, i2, z, z2);
    }

    public CellReference(String str, int i, int i2, boolean z, boolean z2) {
        super(str, i, i2, z, z2);
    }
}
