package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class CreateBrushIndirect extends EMFTag {
    private LogBrush32 brush;
    private int index;

    public CreateBrushIndirect() {
        super(39, 1);
    }

    public CreateBrushIndirect(int i, LogBrush32 logBrush32) {
        this();
        this.index = i;
        this.brush = logBrush32;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new CreateBrushIndirect(eMFInputStream.readDWORD(), new LogBrush32(eMFInputStream));
    }

    public String toString() {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(this.index) + "\n" + this.brush.toString();
    }

    public void render(EMFRenderer eMFRenderer) {
        eMFRenderer.storeGDIObject(this.index, this.brush);
    }
}
