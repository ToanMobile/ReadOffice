package com.app.office.fc.hwpf.model;

import androidx.core.app.FrameMetricsAggregator;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;

@Internal
public abstract class FormattedDiskPage {
    protected int _crun;
    protected byte[] _fkp;
    protected int _offset;

    /* access modifiers changed from: protected */
    public abstract byte[] getGrpprl(int i);

    public FormattedDiskPage() {
    }

    public FormattedDiskPage(byte[] bArr, int i) {
        this._crun = LittleEndian.getUnsignedByte(bArr, i + FrameMetricsAggregator.EVERY_DURATION);
        this._fkp = bArr;
        this._offset = i;
    }

    /* access modifiers changed from: protected */
    public int getStart(int i) {
        return LittleEndian.getInt(this._fkp, this._offset + (i * 4));
    }

    /* access modifiers changed from: protected */
    public int getEnd(int i) {
        return LittleEndian.getInt(this._fkp, this._offset + ((i + 1) * 4));
    }

    public int size() {
        return this._crun;
    }
}
