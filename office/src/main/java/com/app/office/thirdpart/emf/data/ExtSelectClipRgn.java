package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFRenderer;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class ExtSelectClipRgn extends AbstractClipPath {
    private Region rgn;

    public ExtSelectClipRgn() {
        super(75, 1, 5);
    }

    public ExtSelectClipRgn(int i, Region region) {
        super(75, 1, i);
        this.rgn = region;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        return new ExtSelectClipRgn(eMFInputStream.readDWORD(), eMFInputStream.readDWORD() > 8 ? new Region(eMFInputStream) : null);
    }

    public void render(EMFRenderer eMFRenderer) {
        Region region = this.rgn;
        if (region != null && region.getBounds() != null) {
            render(eMFRenderer, this.rgn.getBounds());
        }
    }
}
